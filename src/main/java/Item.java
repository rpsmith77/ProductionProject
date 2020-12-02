/**
 * Interface for an Item with an id, name, and manufacturer
 *
 * @author Ryan Smith
 */
public interface Item {

  int getId();

  void setName(String name);

  String getName();

  void setManufacturer(String manufacturer);

  String getManufacturer();

}
