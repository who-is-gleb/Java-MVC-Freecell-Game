package cs3500.freecell.card;

/**
 * An Enum class class to represents a Card's value.
 * Has 1 field that represents its value as a String.
 */
public enum Value {
  A("A"),
  TWO("2"),
  THREE("3"),
  FOUR("4"),
  FIVE("5"),
  SIX("6"),
  SEVEN("7"),
  EIGHT("8"),
  NINE("9"),
  TEN("10"),
  J("J"),
  Q("Q"),
  K("K");

  private final String disp;

  /**
   * A constructor for {@link Value} class. Sets display value of an object of this class.
   * @param disp   a String for value.
   */
  Value(String disp) {
    if (disp == null) {
      throw new IllegalArgumentException("Suit and value can't be null");
    }
    this.disp = disp;
  }

  @Override
  public String toString() {
    return disp;
  }

  /**
   * Returns the numeric comparable value of a card.
   * E.g. Ace is 1, Two is 2, and so on.
   * @return an int representing a numeric value of a card.
   */
  public int toNum() {
    switch (disp) {
      case "A":
        return 1;
      case "2":
        return 2;
      case "3":
        return 3;
      case "4":
        return 4;
      case "5":
        return 5;
      case "6":
        return 6;
      case "7":
        return 7;
      case "8":
        return 8;
      case "9":
        return 9;
      case "10":
        return 10;
      case "J":
        return 11;
      case "Q":
        return 12;
      case "K":
        return 13;
      default:
        return -1;
    }
  }
}
