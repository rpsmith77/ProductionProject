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
import javafx.event.ActionEvent;
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
  public ListView<Product> listViewProduce;

  @FXML
  private TextField txtProductName;

  @FXML
  private TextField txtManufacturer;

  @FXML
  private Button btnAddProduct;

  @FXML
  private Button btnRecordProduction;

  ObservableList<Product> productionLine = FXCollections.observableArrayList();

  ArrayList<ProductionRecord> productionRecords = new ArrayList<>();

  int countAU;
  int countAM;
  int countVI;
  int countVM;

  // button 'Add Product' is pressed in Product Tab
  public void addProduct(ActionEvent actionEvent) {
    addProductDb();
  }

  // button 'Record Production' is pressed int Production Tab
  public void recordProduction(ActionEvent actionEvent) {
    ObservableList selectedIndices = listViewProduce.getSelectionModel().getSelectedIndices();
    for (int i = 0; i < Integer.parseInt(cmbQuantity.getValue()); i++) {
      for (Object o : selectedIndices) {
        // replace i with counter
        ProductionRecord pr = null;
        switch (productionLine.get((int) o).getType()){
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
        productionRecords.add(pr);
        recordProductionDb(pr);
        txtAreaProductionLog.setText(txtAreaProductionLog.getText() + "\n" + pr.toString());
      }
    }
  }

  // automatically run
  public void initialize() {
    // connect to data base
    connectDb();

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

    // test
//    testMultimedia();
  }

  private void setProductionLogTable() {
    final String JDBC_DRIVER = "org.h2.Driver";
    final String DB_URL = "jdbc:h2:./res/ProdDB";

    //  Database credentials
    final String USER = "";
    final String PASS = "";
    Connection conn = null;
    Statement stmt = null;

    try {
      // STEP 1: Register JDBC driver
      Class.forName(JDBC_DRIVER);

      //STEP 2: Open a connection
      conn = DriverManager.getConnection(DB_URL, USER, PASS);

      //STEP 3: Execute a query
      stmt = conn.createStatement();

      ResultSet rs = stmt.executeQuery("SELECT * FROM PRODUCT");

      while (rs.next()) {
        int id = rs.getInt(1);
        String name = rs.getString(2);
        String manufacturer = rs.getString(4);
        String strType = rs.getString(3);
        ItemType type;
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
        Widget widget = new Widget(name, manufacturer, type);
        widget.setId(id);
        productionLine.add(widget);
      }

      rs = stmt.executeQuery("SELECT * FROM PRODUCTIONRECORD");

      while (rs.next()){
        int prodNum = rs.getInt(1);
        int prodId = rs.getInt(2);
        String serialNum = rs.getString(3);
        Date dateProd = rs.getDate(4);

        setProductionCounts(serialNum);

        ProductionRecord pr = new ProductionRecord(prodNum, prodId, serialNum, dateProd);
        productionRecords.add(pr);
        txtAreaProductionLog.setText(txtAreaProductionLog.getText() + "\n" + pr.toString());
      }



      // STEP 4: Clean-up environment
      stmt.close();
      conn.close();
    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();

    }
    // Set Table
    productNameCol.setCellValueFactory(new PropertyValueFactory("Name"));
    manufacturerNameCol.setCellValueFactory(new PropertyValueFactory("Manufacturer"));
    itemTypeCol.setCellValueFactory(new PropertyValueFactory("Type"));
    tableViewProduct.setItems(productionLine);
    listViewProduce.setItems(productionLine);

  }

  public void setProductionCounts(String serialNum){
    String type = String.valueOf(serialNum.charAt(3)) + String.valueOf(serialNum.charAt(4));
    switch (type){
      case "AU":
        countAU++;
      case "AM":
        countAM++;
      case "VI":
        countVI++;
      case "VM":
        countVM++;
    }
  }

  // connect to database
  public void connectDb() {
    final String JDBC_DRIVER = "org.h2.Driver";
    final String DB_URL = "jdbc:h2:./res/ProdDB";

    //  Database credentials
    final String USER = "";
    final String PASS = "";
    Connection conn = null;
    Statement stmt = null;

    try {
      // STEP 1: Register JDBC driver
      Class.forName(JDBC_DRIVER);

      //STEP 2: Open a connection
      conn = DriverManager.getConnection(DB_URL, USER, PASS);

      //STEP 3: Execute a query
      stmt = conn.createStatement();

      // STEP 4: Clean-up environment
      stmt.close();
      conn.close();
    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();

    }
  }

  public void addProductDb() {
    final String JDBC_DRIVER = "org.h2.Driver";
    final String DB_URL = "jdbc:h2:./res/ProdDB";

    //  Database credentials
    final String USER = "";
    final String PASS = "";
    Connection conn = null;
    Statement stmt = null;

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

      // add product to observable list
      productionLine.add(widget);

      // STEP 4: Clean-up environment
      stmt.close();
      conn.close();
    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();

    }

  }

  public void recordProductionDb(ProductionRecord pr){
    final String JDBC_DRIVER = "org.h2.Driver";
    final String DB_URL = "jdbc:h2:./res/ProdDB";

    //  Database credentials
    final String USER = "";
    final String PASS = "";
    Connection conn = null;
    Statement stmt = null;

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
      java.sql.Date sqlDate = new java.sql.Date(pr.getDateProduced().getTime());

      PreparedStatement preparedStatement = conn.prepareStatement(sql);
      preparedStatement.setInt(1, pr.getProductId());
      preparedStatement.setString(2, pr.getSerialNumber());
      preparedStatement.setDate(3, sqlDate);
      preparedStatement.executeUpdate();

      // STEP 4: Clean-up environment
      stmt.close();
      conn.close();
    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();

    }

  }

  // test
//  public static void testMultimedia() {
//    AudioPlayer newAudioProduct = new AudioPlayer("DP-X1A", "Onkyo",
//        "DSD/FLAC/ALAC/WAV/AIFF/MQA/Ogg-Vorbis/MP3/AAC", "M3U/PLS/WPL");
//    Screen newScreen = new Screen("720x480", 40, 22);
//    MoviePlayer newMovieProduct = new MoviePlayer("DBPOWER MK101", "OracleProduction",
//        newScreen,
//        MonitorType.LCD);
//    ArrayList<MultimediaControl> productList = new ArrayList<MultimediaControl>();
//    productList.add(newAudioProduct);
//    productList.add(newMovieProduct);
//    for (MultimediaControl p : productList) {
//      System.out.println(p);
//      p.play();
//      p.stop();
//      p.next();
//      p.previous();
//    }
//  }
}