package cs3500.freecell.card;

/**
 * An Enum class that represents a Card's suit.
 * Can either be Clubs, Diamonds, Hearts, or Spades.
 * Has 1 field that represents its suit as a String.
 */
public enum Suit {
  C("♣"), D("♦"), H("♥"), S("♠");

  private final String disp;

  /**
   * A constructor for {@link Suit} class. Sets display suit of an object of this class.
   * @param disp   a String for suit type.
   */
  Suit(String disp) {
    if (disp == null) {
      throw new IllegalArgumentException("Suit and value can't be null");
    }
    this.disp = disp;
  }

  @Override
  public String toString() {
    return disp;
  }
}
