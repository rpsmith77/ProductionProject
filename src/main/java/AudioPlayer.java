/**
 * Represents an audio player that can play, stop, go forward, and go backward. Stores supported
 * playlist formats
 *
 * @author Ryan Smith
 */
public class AudioPlayer extends Product implements MultimediaControl {

  // class variables
  private final String supportedAudioFormats;
  private final String supportedPlaylistFormats;

  /**
   * Default constructor
   * @param name: name of player
   * @param manufacturer: manufacturer name
   * @param supportedAudioFormats: supported audio formats
   * @param supportedPlaylistFormats: supported playlist formats
   */
  AudioPlayer(String name, String manufacturer, String supportedAudioFormats,
      String supportedPlaylistFormats) {
    super(name, manufacturer, ItemType.AUDIO);
    this.supportedAudioFormats = supportedAudioFormats;
    this.supportedPlaylistFormats = supportedPlaylistFormats;
  }

  /**
   * Audio player 'plays'
   */
  public void play() {
    System.out.println("Playing");
  }

  /**
   * Audio player 'stops'
   */
  public void stop() {
    System.out.println("Stopping");
  }

  /**
   * Audio player plays 'previous'
   */
  public void previous() {
    System.out.println("Previous");
  }

  /**
   * Audio player plays 'next'
   */
  public void next() {
    System.out.println("Next");
  }

  /**
   * convert audio player info into string
   * @return  string information of audio player
   */
  public String toString() {
    return super.toString() + "Supported Audio Formats: " + supportedAudioFormats + "\n" +
        "Supported Playlist Formats: " + supportedPlaylistFormats;
  }
}
