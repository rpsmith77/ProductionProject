/**
 * Represents a movie player. It extends product and has multimedia controls.
 *
 * @author Ryan Smith
 */
public class MoviePlayer extends Product implements MultimediaControl {

  // class variables
  final Screen screen;
  final MonitorType monitorType;

  /**
   * default constructor
   *
   * @param name:         Product name
   * @param manufacturer: Product manufacturer
   * @param screen:       Product's screen information
   * @param monitorType:  Product's monitor type
   */
  MoviePlayer(String name, String manufacturer, Screen screen, MonitorType monitorType) {
    super(name, manufacturer, ItemType.VISUAL);
    this.screen = screen;
    this.monitorType = monitorType;

  }

  /**
   * Play movie
   */
  @Override
  public void play() {
    System.out.println("Playing movie");
  }

  /**
   * stop movie
   */
  @Override
  public void stop() {
    System.out.println("Stopping movie");
  }

  /**
   * previous movie
   */
  @Override
  public void previous() {
    System.out.println("Previous movie");
  }

  /**
   * next movie
   */
  @Override
  public void next() {
    System.out.println("Next movie");
  }

  /**
   * return string version of product's information
   *
   * @return string version of product's information
   */
  @Override
  public String toString() {
    return super.toString() + screen.toString() + "\nMonitor Type: " + monitorType;
  }
}
