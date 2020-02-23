package cs3500.freecell.model;

import cs3500.freecell.card.Card;

/**
 * A factory class that has a method to return either a FreecellModel object or a
 * FreecellModelMulti object.
 */
public class FreecellModelCreator {

  /**
   * An enum class GameType, defined inside the factory class, that represents either a
   * singlemove Freecell model or a multimove Freecell model.
   */
  public enum GameType {
    SINGLEMOVE, MULTIMOVE;
  }

  /**
   * A method to create an instance of either FreecellModel or FreecellModelMulti,
   * depending on the given parameter.
   * @param type an enum GameType.
   * @return A FreecellOperations instance.
   */
  public static FreecellOperations<Card> create(GameType type) {
    if (type == null) {
      return null;
    }
    switch (type) {
      case SINGLEMOVE:
        return new FreecellModel();
      case MULTIMOVE:
        return new FreecellModelMulti();
      default:
        throw new IllegalArgumentException(".create() received an invalid argument");
    }
  }
}
