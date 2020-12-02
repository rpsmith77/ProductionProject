/**
 * Represents the screen a product might have
 *
 * @author Ryan Smith
 */
public class Screen implements ScreenSpec {

  // class variables
  private final String resolution;
  private final int refreshRate;
  private final int responseTime;

  /**
   * Default constructor
   * @param resolution: screen pixel resolution
   * @param refreshRate: screen refresh rates in Hz
   * @param responseTime: screen response time in ms
   */
  Screen(String resolution, int refreshRate, int responseTime) {
    this.resolution = resolution;
    this.refreshRate = refreshRate;
    this.responseTime = responseTime;
  }

  /**
   * access screen's resolution
   * @return screen's resolution in pixels
   */
  @Override
  public String getResolution() {
    return resolution;
  }

  /**
   * access screen's refresh rate
   * @return screen's refresh rate in hz
   */
  @Override
  public int getRefreshRate() {
    return refreshRate;
  }

  /**
   * access screen's response time
   * @return screen's response time in ms
   */
  @Override
  public int getResponseTime() {
    return responseTime;
  }

  /**
   * Screen's information in a string
   * @return screen's information in a string
   */
  public String toString() {
    return "Screen:\nResolution: " + resolution + "\n" + "Refresh rate: "
        + refreshRate + "\n" + "Response time: " + responseTime;
  }
}
