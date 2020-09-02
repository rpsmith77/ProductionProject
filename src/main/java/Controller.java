import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class Controller {

  @FXML
  public TextField txtProductName;
  public TextField txtManufacturer;
  public Button btnAddProduct;
  public Button btnRecordProduction;


  public void addProduct(ActionEvent actionEvent) {
    System.out.println("You pressed 'Add Product'");
  }

  public void recordProduction(ActionEvent actionEvent) {
    System.out.println("You pressed 'Record Product'");
  }
}