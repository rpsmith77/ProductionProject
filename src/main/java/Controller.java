import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class Controller {

  @FXML
  private Label lblOutput;

  public void sayHello() {
    lblOutput.setText("Hello FXML!");

  }

  public void addProduct(ActionEvent actionEvent) {
    System.out.println("You pressed 'Add Product'");
  }

  public void recordProduction(ActionEvent actionEvent) {
    System.out.println("You pressed 'Record Product'");
  }
}