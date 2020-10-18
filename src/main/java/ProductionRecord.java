import java.util.Date;

public class ProductionRecord {

  private int productionNumber;
  private int productId;
  private String serialNumber;
  private Date dateProduced;

  ProductionRecord(int productId) {
    this.productId = productId;
    productionNumber = 0;
    serialNumber = "0";
    dateProduced = new Date();
  }

  // needs to be from db
  ProductionRecord(int productionNumber, int productId, String serialNumber, Date dateProduced) {
    this.productionNumber = productionNumber;
    this.productId = productId;
    this.serialNumber = serialNumber;
    this.dateProduced = dateProduced;
  }

  public void setProductionNumber(int productionNumber) {
    this.productionNumber = productionNumber;
  }

  public void setProductId(int productId) {
    this.productId = productId;
  }

  public void setSerialNumber(String serialNumber) {
    this.serialNumber = serialNumber;
  }

  public void setDateProduced(Date dateProduced) {
    this.dateProduced = dateProduced;
  }

  public int getProductionNumber() {
    return productionNumber;
  }

  public int getProductId() {
    return productId;
  }

  public String getSerialNumber() {
    return serialNumber;
  }

  public Date getDateProduced() {
    return dateProduced;
  }

  public String toString(){
    return "Prod. Num: " + productionNumber + "\nProd. ID: " + productId
        + "\nSerial Num: " + serialNumber + "\nDate: " + dateProduced;
  }
}