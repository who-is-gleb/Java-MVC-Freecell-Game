package cs3500.freecell.card;


/**
 * A Card class that represents a Card in a Freecell game.
 * Has two fields: suit and value. Both are Enum classes with limited number of possible values.
 */
public class Card {
  private final Suit suit;
  private final Value value;

  /**
   * A constructor for Card class.
   * @param suit   object of Enum type Suit to set the suit.
   * @param value  object of Enum type Value to set the value.
   */
  public Card(Suit suit, Value value) {
    if ((suit == null) || (value == null)) {
      throw new IllegalArgumentException("Suit and value can't be null");
    }
    this.suit = suit;
    this.value = value;
  }

  public Suit getSuit() {
    return suit;
  }

  public Value getValue() {
    return value;
  }

  /**
   * An overriden toString method to represent a {@link Card} object as a string.
   * @return  concatenated Card's value and suit, e.g. "7♣".
   */
  @Override
  public String toString() {
    return value.toString() + suit.toString();
  }

  /**
   * Checks if two cards are the same color.
   * @return boolean true if cards are the same color.
   */
  public boolean isSameColor(Card card2) {
    if ((suit == Suit.H) || (suit == Suit.D)) {
      return (card2.getSuit() == Suit.H) || (card2.getSuit() == Suit.D);
    } else {
      return (card2.getSuit() == Suit.S) || (card2.getSuit() == Suit.C);
    }
  }

  @Override
  public boolean equals(Object o) {

    if (!(o instanceof Card)) {
      return false;
    }
    return suit.toString().equals(((Card) o).suit.toString())
            && value.toNum() == ((Card) o).value.toNum();
  }

  @Override
  public int hashCode() {
    int result = 31;
    result = result * 17 + value.toNum();
    result = result * 17 + suit.toString().hashCode();
    return result;
  }

  /**
   * A new method for Assignment 4. Takes 2 cards and checks if the second card has different color
   * than the first card, as well as that the second card is one value less than the first card.
   * @param card1 {@link Card} the given card 1.
   * @param card2 the given card 2.
   * @return boolean true if the second one fits onto the first one for cascade piles.
   */
  public static boolean isSecondCardInDescOrder(Card card1, Card card2) {
    return (!card1.isSameColor(card2)
            && (card1.getValue().toNum() == card2.getValue().toNum() + 1));
  }
}
