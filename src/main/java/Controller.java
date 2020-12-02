import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Controller class for the GUI. Contains interactions between the GUI, classes, and the database
 *
 * @author Ryan Smith
 */
public class Controller {

  // FXML objects
  @FXML
  public ChoiceBox<ItemType> choiceItemType;

  @FXML
  public ComboBox<String> cmbQuantity;

  @FXML
  public TextArea txtAreaProductionLog;

  @FXML
  public TableView<Product> tableViewProduct;

  @FXML
  public TableColumn<?, ?> productNameCol;

  @FXML
  public TableColumn<?, ?> manufacturerNameCol;

  @FXML
  public TableColumn<?, ?> itemTypeCol;

  @FXML
  public TableColumn<?, ?> prodIdCol;

  @FXML
  public ListView<Product> listViewProduce;

  @FXML
  private TextField txtProductName;

  @FXML
  private TextField txtManufacturer;

  @FXML
  private Button btnAddProduct;

  @FXML
  private Button btnRecordProduction;

  // contains list of Products
  final ObservableList<Product> productionLine = FXCollections.observableArrayList();

  // contains list of produced products
  final ArrayList<ProductionRecord> productionRecords = new ArrayList<>();

  // keeps track of total of each product type for serial number
  int countAU;
  int countAM;
  int countVI;
  int countVM;

  /**
   * button 'Add Product' is pressed in Product Tab and adds product to product table in database
   */
  public void addProduct() {
    addProductDb();
  }


  /**
   * button 'Record Production' is pressed int Production Tab records production of specified
   * product
   */
  public void recordProduction() {
    // Associate each product with index number from list view
    ObservableList<?> selectedIndices = listViewProduce.getSelectionModel().getSelectedIndices();
    // make as many products as specified in combo box
    for (int i = 0; i < Integer.parseInt(cmbQuantity.getValue()); i++) {
      // select product from selectedIndices
      for (Object o : selectedIndices) {
        ProductionRecord pr = null;
        // create specified product
        switch (productionLine.get((int) o).getType()) {
          case AUDIO:
            pr = new ProductionRecord(productionLine.get((int) o), countAU++);
            break;
          case VISUAL:
            pr = new ProductionRecord(productionLine.get((int) o), countVI++);
            break;
          case AUDIO_MOBILE:
            pr = new ProductionRecord(productionLine.get((int) o), countAM++);
            break;
          case VISUAL_MOBILE:
            pr = new ProductionRecord(productionLine.get((int) o), countVM++);
            break;
        }
        // add production to productionRecords list
        productionRecords.add(pr);
        // add production to db
        pr = recordProductionDb(pr);
        // update production record log
        txtAreaProductionLog.setText(txtAreaProductionLog.getText() + "\n" + pr.toString());
      }
    }
  }

  /**
   * Automatically run at the beginning. Sets up all the table's and links with DB
   */
  public void initialize() {

    setProductionLogTable();

    // add 1 - 10 to combo box
    for (int i = 1; i <= 10; i++) {
      cmbQuantity.getItems().add(String.valueOf(i));
    }
    // custom entry valid
    cmbQuantity.setEditable(true);
    cmbQuantity.getSelectionModel().selectFirst();

    // show valid item types in choice box
    for (ItemType i : ItemType.values()) {
      choiceItemType.getItems().add(i);
    }
    choiceItemType.getSelectionModel().selectFirst();

  }

  /**
   * Populate production log table and production record with data from DB
   */
  private void setProductionLogTable() {
    final String JDBC_DRIVER = "org.h2.Driver";
    final String DB_URL = "jdbc:h2:./res/ProdDB";

    //  Database credentials
    final String USER = "";
    final String PASS = "";
    Connection conn;
    Statement stmt;

    try {
      // STEP 1: Register JDBC driver
      Class.forName(JDBC_DRIVER);

      //STEP 2: Open a connection
      conn = DriverManager.getConnection(DB_URL, USER, PASS);

      //STEP 3: Execute a query
      stmt = conn.createStatement();

      // Select all entries in PRODUCT table
      ResultSet rs = stmt.executeQuery("SELECT * FROM PRODUCT");
      // Loop through all entries in PRODUCT table
      while (rs.next()) {
        int id = rs.getInt(1);
        String name = rs.getString(2);
        String manufacturer = rs.getString(4);
        String strType = rs.getString(3);
        ItemType type;
        // Assign proper Item Type
        switch (strType) {
          case "AUDIO":
            type = ItemType.AUDIO;
            break;
          case "AUDIO_MOBILE":
            type = ItemType.AUDIO_MOBILE;
            break;
          case "VISUAL":
            type = ItemType.VISUAL;
            break;
          case "VISUAL_MOBILE":
            type = ItemType.VISUAL_MOBILE;
            break;
          default:
            type = null;
            break;
        }
        // add product to product line
        Widget widget = new Widget(name, manufacturer, type);
        widget.setId(id);
        productionLine.add(widget);
      }

      // select all entries from PRODCUTIONRECORD table
      rs = stmt.executeQuery("SELECT * FROM PRODUCTIONRECORD");
      // loop through all entries
      while (rs.next()) {
        int prodNum = rs.getInt(1);
        int prodId = rs.getInt(2);
        String serialNum = rs.getString(3);
        Date dateProd = rs.getTimestamp(4);

        // increment count for each product to ensure correct serial numbers
        setProductionCounts(serialNum);

        // create and add product to productionRecords list
        ProductionRecord pr = new ProductionRecord(prodNum, prodId, serialNum, dateProd);
        productionRecords.add(pr);
        // update production record log
        txtAreaProductionLog.setText(txtAreaProductionLog.getText() + "\n" + pr.toString());
      }

      // STEP 4: Clean-up environment
      stmt.close();
      conn.close();
    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();

    }
    // Set Production List Table
    productNameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
    manufacturerNameCol.setCellValueFactory(new PropertyValueFactory<>("Manufacturer"));
    itemTypeCol.setCellValueFactory(new PropertyValueFactory<>("Type"));
    prodIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
    tableViewProduct.setItems(productionLine);
    // Set Produce list view
    listViewProduce.setItems(productionLine);

  }

  /**
   * increment count for each product to ensure correct serial numbers to increment count for each
   * product to ensure correct serial numbers
   *
   * @param serialNum: take produced item's serial number
   */
  public void setProductionCounts(String serialNum) {
    // sample serial number 'AppAU00000'. parses AU
    String type = serialNum.charAt(3) + String.valueOf(serialNum.charAt(4));
    switch (type) {
      case "AU":
        countAU++;
        break;
      case "AM":
        countAM++;
        break;
      case "VI":
        countVI++;
        break;
      case "VM":
        countVM++;
        break;
    }
  }

  /**
   * add product to PRODUCT table and then update GUI product table
   */
  public void addProductDb() {
    final String JDBC_DRIVER = "org.h2.Driver";
    final String DB_URL = "jdbc:h2:./res/ProdDB";

    //  Database credentials
    final String USER = "";
    final String PASS = "";
    Connection conn;
    Statement stmt;

    try {
      // STEP 1: Register JDBC driver
      Class.forName(JDBC_DRIVER);

      //STEP 2: Open a connection
      conn = DriverManager.getConnection(DB_URL, USER, PASS);

      //STEP 3: Execute a query
      stmt = conn.createStatement();

      // take user choices and enter into Product Table
      Widget widget = new Widget(txtProductName.getText(),
          txtManufacturer.getText(), choiceItemType.getValue());
      String sql = "INSERT INTO Product(type, manufacturer, name) "
          + "VALUES (?, ?, ?)";
      PreparedStatement preparedStatement = conn.prepareStatement(sql);
      preparedStatement.setString(1, widget.getType().toString());
      preparedStatement.setString(2, widget.getManufacturer());
      preparedStatement.setString(3, widget.getName());
      preparedStatement.executeUpdate();

      // set product id
      // https://www.tutorialspoint.com/get-the-last-record-from-a-table-in-mysql-database-with-java
      ResultSet rs = stmt.executeQuery("SELECT * FROM PRODUCT ORDER BY ID DESC LIMIT 1");
      if (rs.next()) {
        widget.setId(rs.getInt(1));
      }

      // add product to observable list
      productionLine.add(widget);

      // STEP 4: Clean-up environment
      stmt.close();
      conn.close();
    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();

    }

  }

  /**
   * Takes the produced item, adds it to PRODUCTIONRECORD in table, then return product with proper
   * production number.
   *
   * @param pr: production record to record in PRODUCTIONRECORD table
   * @return updated production record to add to Production log in GUI
   */
  public ProductionRecord recordProductionDb(ProductionRecord pr) {
    final String JDBC_DRIVER = "org.h2.Driver";
    final String DB_URL = "jdbc:h2:./res/ProdDB";

    //  Database credentials
    final String USER = "";
    final String PASS = "";
    Connection conn;
    Statement stmt;

    try {
      // STEP 1: Register JDBC driver
      Class.forName(JDBC_DRIVER);

      //STEP 2: Open a connection
      conn = DriverManager.getConnection(DB_URL, USER, PASS);

      //STEP 3: Execute a query
      stmt = conn.createStatement();

      // take user choices and enter into Production Record Table
      String sql = "INSERT INTO PRODUCTIONRECORD(PRODUCT_ID, SERIAL_NUM, DATE_PRODUCED) "
          + "VALUES (?, ?, ?)";

      // convert java.util.date to java.sql.date
      java.sql.Timestamp sqlDate = new java.sql.Timestamp(pr.getDateProduced().getTime());

      PreparedStatement preparedStatement = conn.prepareStatement(sql);
      preparedStatement.setInt(1, pr.getProductId());
      preparedStatement.setString(2, pr.getSerialNumber());
      preparedStatement.setTimestamp(3, sqlDate);
      preparedStatement.executeUpdate();

      // set proper production number
      ResultSet rs = stmt.executeQuery("SELECT * FROM PRODUCTIONRECORD "
          + "ORDER BY PRODUCTION_NUM DESC LIMIT 1");
      if (rs.next()) {
        pr.setProductionNumber(rs.getInt(1));
      }

      // STEP 4: Clean-up environment
      stmt.close();
      conn.close();
    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();

    }

    return pr;
  }

  /**
   * testing multimedia controls
   *
   * @author Scott Vanselow
   */
  public static void testMultimedia() {

    AudioPlayer newAudioProduct = new AudioPlayer("DP-X1A", "Onkyo",
        "DSD/FLAC/ALAC/WAV/AIFF/MQA/Ogg-Vorbis/MP3/AAC", "M3U/PLS/WPL");
    Screen newScreen = new Screen("720x480", 40, 22);
    MoviePlayer newMovieProduct = new MoviePlayer("DBPOWER MK101", "OracleProduction", newScreen,
        MonitorType.LCD);
    ArrayList<MultimediaControl> productList = new ArrayList<>();

    productList.add(newAudioProduct);
    productList.add(newMovieProduct);

    for (MultimediaControl p : productList) {
      System.out.println(p);
      p.play();
      p.stop();
      p.next();
      p.previous();
    }

  }

}