public enum ItemType {
  AUDIO("AU"),
  AUDIO_MOBILE("AM"),
  VISUAL("VI"),
  VISUAL_MOBILE("VM");


  public final String code;

  ItemType(String code) {
    this.code = code;
  }

  private String code() {
    return code;
  }

}
