/**
 * Enum for item types
 *
 * @author Ryan Smith
 */
public enum ItemType {
  AUDIO("AU"),
  AUDIO_MOBILE("AM"),
  VISUAL("VI"),
  VISUAL_MOBILE("VM");

  // text representation of enum
  public final String code;

  /**
   * default constructor
   *
   * @param code: two letter representation of enum
   */
  ItemType(String code) {
    this.code = code;
  }

  /**
   * return ItemType as its code
   *
   * @return code for item type
   */
  private String code() {
    return code;
  }

}
