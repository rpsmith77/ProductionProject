public class MoviePlayer extends Product implements MultimediaControl {

  Screen screen;
  MonitorType monitorType;

  MoviePlayer(String name, String manufacturer, Screen screen, MonitorType monitorType) {
    super(name, manufacturer, "VISUAL");
    this.screen = screen;
    this.monitorType = monitorType;

  }

  @Override
  public void play() {
    System.out.println("Playing");
  }

  @Override
  public void stop() {
    System.out.println("Stopped");
  }

  @Override
  public void previous() {
    System.out.println("Previous");
  }

  @Override
  public void next() {
    System.out.println("Next");
  }

  @Override
  public String toString() {
    return screen.toString() + "\nMonitor type: " + monitorType;
  }
}
