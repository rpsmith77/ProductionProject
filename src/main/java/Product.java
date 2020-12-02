/**
 * Represents a product, which implements Item.
 * The product has an ID, name, type, and manufacturer.
 *
 * @author Ryan Smith
 */
public abstract class Product implements Item {

  // class variables
  private int id;
  private String name;
  private final ItemType type;
  private String manufacturer;

  /**
   * Default constructor
   * @param name: product name
   * @param manufacturer: product manufacturer
   * @param type: product item type
   */
  Product(String name, String manufacturer, ItemType type) {
    this.name = name;
    this.manufacturer = manufacturer;
    this.type = type;
  }

  /**
   * set product id
   * @param id: unique number to identify this product as
   */
  public void setId(int id){
    this.id = id;
  }

  /**
   * access id
   * @return id number as an int
   */
  public int getId() {
    return id;
  }

  /**
   * set product name
   * @param name: products name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * access product name
   * @return name of product
   */
  public String getName() {
    return name;
  }

  /**
   * Set manufacturer
   * @param manufacturer: products manufacturer
   */
  public void setManufacturer(String manufacturer) {
    this.manufacturer = manufacturer;
  }

  /**
   * access manufacturer
   * @return name of manufacturer
   */
  public String getManufacturer() {
    return manufacturer;
  }

  /**
   * access products type
   * @return return products type as enum
   */
  public ItemType getType() {
    return type;
  }

  /**
   * String version of product's information
   * @return product's information
   */
  public String toString() {
    return "Name: " + name + "\n" + "Manufacturer: " + manufacturer + "\n" + "Type: " + type + "\n";
  }
}
