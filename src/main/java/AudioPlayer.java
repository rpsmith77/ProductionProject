public class AudioPlayer extends Product implements MultimediaControl {

  // class variables
  private final String supportedAudioFormats;
  private final String supportedPlaylistFormats;

  // constructor
  AudioPlayer(String name, String manufacturer, String supportedAudioFormats,
      String supportedPlaylistFormats) {
    super(name, manufacturer, ItemType.AUDIO);
    this.supportedAudioFormats = supportedAudioFormats;
    this.supportedPlaylistFormats = supportedPlaylistFormats;
  }

  // play audio
  public void play() {
    System.out.println("Playing");
  }

  // stop audio
  public void stop() {
    System.out.println("Stopping");
  }

  // go to previous song
  public void previous() {
    System.out.println("Previous");
  }

  // go to the next song
  public void next() {
    System.out.println("Next");
  }

  // display player info
  public String toString() {
    return super.toString() + "Supported Audio Formats: " + supportedAudioFormats + "\n" +
        "Supported Playlist Formats: " + supportedPlaylistFormats + "\n";
  }
}
