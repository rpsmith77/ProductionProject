import java.util.Date;

/**
 * Represents the product created and hold the production number, product id, serial number, and the
 * date and time produced.
 *
 * @author Ryan Smith
 */
public class ProductionRecord {

  /**
   * production number of this product
   */
  private int productionNumber;
  /**
   * product ID from Product class, tied to Product DB
   */
  private int productId;
  /**
   * unique serial number based on manufacturer, type, and amount produced
   */
  private String serialNumber;
  /**
   * date and time produced
   */
  private Date dateProduced;

  /**
   * Constructor that takes in a product and num product to create proper record of production
   *
   * @param product:      product being produced
   * @param numOfProduct: product number for item type
   */
  ProductionRecord(Product product, int numOfProduct) {
    setProductId(product.getId());
    setSerialNumber(product.getManufacturer().substring(0, 3)
        + product.getType().code + String.format("%05d", numOfProduct));
    dateProduced = new Date();
  }

  /**
   * Constructor that takes data from database and creates an object with it
   *
   * @param productionNumber: product ID from tied to db
   * @param productId:        unique serial number based on manufacturer, type, and amount produced
   * @param serialNumber:     unique serial number based on manufacturer, type, and amount produced
   * @param dateProduced:     product ID from Product class, tied to Product DB
   */
  ProductionRecord(int productionNumber, int productId, String serialNumber, Date dateProduced) {
    this.productionNumber = productionNumber;
    this.productId = productId;
    this.serialNumber = serialNumber;
    this.dateProduced = dateProduced;
  }

  /**
   * set production number
   *
   * @param productionNumber: this record's production number
   */
  public void setProductionNumber(int productionNumber) {
    this.productionNumber = productionNumber;
  }

  /**
   * set product ID
   *
   * @param productId: this record's product id
   */
  public void setProductId(int productId) {
    this.productId = productId;
  }

  /**
   * set serial number
   *
   * @param serialNumber: this record's serial number
   */
  public void setSerialNumber(String serialNumber) {
    this.serialNumber = serialNumber;
  }

  /**
   * set date produced
   *
   * @param dateProduced: this record's date produced
   */
  public void setDateProduced(Date dateProduced) {
    this.dateProduced = dateProduced;
  }

  /**
   * get production number
   *
   * @return this product's production number
   */
  public int getProductionNumber() {
    return productionNumber;
  }

  /**
   * get product id
   *
   * @return this product's id
   */
  public int getProductId() {
    return productId;
  }

  /**
   * get serial number
   *
   * @return this product's serial number
   */
  public String getSerialNumber() {
    return serialNumber;
  }

  /**
   * get date produced
   *
   * @return this product's date produced
   */
  public Date getDateProduced() {
    return dateProduced;
  }

  /**
   * String of production record information
   *
   * @return String of production record information
   */
  public String toString() {
    return "Prod. Num: " + productionNumber + " Product ID: " + productId
        + " Serial Num: " + serialNumber + " Date: " + dateProduced;
  }
}
