/**
 * Uses the Product abstract class to create a product that can be added to the database and used to
 * record production
 *
 * @author Ryan Smith
 */
public class Widget extends Product {

  Widget(String name, String manufacturer, ItemType type) {
    super(name, manufacturer, type);
  }
}
