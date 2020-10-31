import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
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
  public TableColumn<Product, String> productNameCol;

  @FXML
  public TableColumn<Product, String> manufacturerNameCol;

  @FXML
  public TableColumn<Product, ItemType> itemTypeCol;

  @FXML
  private TextField txtProductName;

  @FXML
  private TextField txtManufacturer;

  @FXML
  private Button btnAddProduct;

  @FXML
  private Button btnRecordProduction;

  ObservableList<Product> productionLine = FXCollections.observableArrayList();


  // button 'Add Product' is pressed in Product Tab
  public void addProduct(ActionEvent actionEvent) {
    addProductDb();
  }

  // button 'Record Production' is pressed int Production Tab
  public void recordProduction(ActionEvent actionEvent) {
    System.out.println("You pressed 'Record Product'");
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
    // Set Table
    productionLine = populateList();
    productNameCol.setCellFactory(new PropertyValueFactory("Name"));
    manufacturerNameCol.setCellFactory(new PropertyValueFactory("Manufacturer"));
    itemTypeCol.setCellFactory(new PropertyValueFactory("Type"));
    tableViewProduct.setItems(productionLine);
    // testing
    for (Product product : productionLine) {
      System.out.println(product + "\n");
    }
  }

  public static ObservableList<Product> populateList() {
    Screen newScreen = new Screen("720x480", 40, 22);
    return FXCollections.observableArrayList(
        new Widget("Please", "Work", ItemType.AUDIO)
    );
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

      productionLine.add(widget);

      // testing
      for (Product product : productionLine) {
        System.out.println(product);
      }

//      tableViewProduct.getItems().add(widget);

      // STEP 4: Clean-up environment
      stmt.close();
      conn.close();
    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();

    }

  }

  // test
  public static void testMultimedia() {
    AudioPlayer newAudioProduct = new AudioPlayer("DP-X1A", "Onkyo",
        "DSD/FLAC/ALAC/WAV/AIFF/MQA/Ogg-Vorbis/MP3/AAC", "M3U/PLS/WPL");
    Screen newScreen = new Screen("720x480", 40, 22);
    MoviePlayer newMovieProduct = new MoviePlayer("DBPOWER MK101", "OracleProduction",
        newScreen,
        MonitorType.LCD);
    ArrayList<MultimediaControl> productList = new ArrayList<MultimediaControl>();
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