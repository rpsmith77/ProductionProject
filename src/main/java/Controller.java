import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class Controller {

  @FXML
  private TextField txtProductName;

  @FXML
  private TextField txtManufacturer;

  @FXML
  private Button btnAddProduct;

  @FXML
  private Button btnRecordProduction;


  public void addProduct(ActionEvent actionEvent) {
    System.out.println("You pressed 'Add Product'");
  }

  public void recordProduction(ActionEvent actionEvent) {
    System.out.println("You pressed 'Record Product'");
  }
}