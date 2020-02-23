package cs3500.freecell.model;

import java.util.List;
import java.util.stream.Collectors;

import cs3500.freecell.card.Card;

import static cs3500.freecell.card.Card.isSecondCardInDescOrder;

/**
 * A class that represents a different Freecell Model, allowing for multi-card moves.
 * * Fields:
 *  * 1) the actual cascape piles that are represented as a List of List of Card.
 *  * 2) the actual open piles that are represented as a List of List of Card.
 *  * 3) the actual foundation piles that are represented as a List of List of Card.
 *  * 4) boolean "gameIsRunning": true if game has started, false otherwise.
 */
public class FreecellModelMulti extends FreecellModel {

  @Override
  public void move(PileType source,
                   int pileNumber, int cardIndex, PileType destination, int destPileNumber) {

    if ((destination == null) || (source == null)) {
      throw new IllegalArgumentException("No nulls are allowed");
    }
    if (!gameIsRunning) {
      throw new IllegalStateException("The game has not been started yet");
    }
    if (isGameOver()) {
      throw new IllegalStateException("Game is over, I guess you won");
    }

    // Creating a copy of the given card build.
    List<Card> copyOfBuild = streamCollect(source, pileNumber, cardIndex);

    // If user wants to move 1 card, it is a simple move.
    if (copyOfBuild.size() <= 1) {
      super.move(source, pileNumber, cardIndex, destination, destPileNumber);
      // Otherwise, it is a multi-move.
    } else {
      // Check that the multi-move can only be done between cascade piles.
      if (source != PileType.CASCADE || destination != PileType.CASCADE) {
        throw new IllegalArgumentException("Can move multiple cards "
                + "to and from cascade piles only");
      }

      // Checking if the given card build contains more than the
      // maximum possible number of cards to move.
      long numOfEmptyOpenPiles = calculateNumOfEmptyOpenPiles();
      long numOfEmptyCascadePiles = calculateNumOfEmptyCascPiles();
      double maxNumberOfCardsToMove = ((numOfEmptyOpenPiles + 1)
              * (Math.pow(2, numOfEmptyCascadePiles)));
      if (copyOfBuild.size() > maxNumberOfCardsToMove) {
        throw new IllegalArgumentException("Cannot move this many cards. Move less.");
      }

      // Checking the card build validity.
      if (cardBuildNotValid(copyOfBuild)) {
        throw new IllegalArgumentException("This card build is invalid.");
      }
      // Checking if the given card build can go onto the last dest pile card.
      if (lastDestCardDoesNotFit(destPileNumber, copyOfBuild)) {
        throw new IllegalArgumentException("The last card in the "
                + "destination cascade pile does not fit into the card build");
      }

      for (Card card : copyOfBuild) {
        cascadePiles.get(pileNumber).remove(cascadePiles.get(pileNumber).size() - 1);
        cascadePiles.get(destPileNumber).add(card);
      }
    }
  }

  /**
   * A helper method that creates a card build that the user wants to use for multi-move,
   * using the given pile number for any cascade pile.
   * @param source     a given type of pile to pick from.
   * @param pileNumber a given pile number as an int.
   * @param cardIndex  a given card index as an int.
   * @return a list of {@link Card}, essentially a card build for a multi-move.
   */
  protected List<Card> streamCollect(PileType source, int pileNumber, int cardIndex) {
    switch (source) {
      case CASCADE:
        return cascadePiles.get(pileNumber).stream()
                .filter(card -> cascadePiles.get(pileNumber).indexOf(card) >= (cardIndex))
                .collect(Collectors.toList());
      case OPEN:
        return openPiles.get(pileNumber).stream()
                .filter(card -> openPiles.get(pileNumber).indexOf(card) >= (cardIndex))
                .collect(Collectors.toList());
      case FOUNDATION:
        return foundationPiles.get(pileNumber).stream()
                .filter(card -> foundationPiles.get(pileNumber).indexOf(card) >= (cardIndex))
                .collect(Collectors.toList());
      default:
        return null;
    }
  }

  /**
   * A helper method to count the number of empty open piles to use in multi-move.
   * @return an int - the number of empty open piles, if any.
   */
  protected long calculateNumOfEmptyOpenPiles() {
    return openPiles.stream()
            .filter(pile -> pile.size() == 0)
            .count();
  }

  /**
   * A helper method to count the number of empty cascade piles to use in multi-move.
   * @return an int - the number of empty cascade piles, if any.
   */
  protected long calculateNumOfEmptyCascPiles() {
    return cascadePiles.stream()
            .filter(pile -> pile.size() == 0)
            .count();
  }

  /**
   * A helper method to check if the given multi-move card build is invalid.
   * A valid card build has to be in descending value order and the cards have to switch colors.
   * @param build a given card build - a list of Card.
   * @return boolean. True if the build is not valid, false if it valid.
   */
  protected boolean cardBuildNotValid(List<Card> build) {
    if (build.size() < 2) {
      throw new IllegalArgumentException("Build size is less than 2");
    }
    if (build.size() == 2) {
      if (!isSecondCardInDescOrder(build.get(0), build.get(1))) {
        return true;
      }
    }
    for (int i = 0; i < build.size() - 1; i++) {
      if (!isSecondCardInDescOrder(build.get(i), build.get(i + 1))) {
        return true;
      }
    }
    return false;
  }

  /**
   * A helper method to check if the first card of the given build fits onto the
   * last card of the destination cascade pile (since we are allowed to only move stacks of cards
   * to cascade piles).
   * @param destPileNumber a given destination cascade pile number.
   * @param build          a given card build (list of Card).
   * @return boolean, true if the last pile card and the first build card do not
   *         form a valid build.
   */
  protected boolean lastDestCardDoesNotFit(int destPileNumber, List<Card> build) {
    if (cascadePiles.get(destPileNumber).size() == 0) {
      return false;
    }

    Card destPileLastCard = getLastCard(cascadePiles, destPileNumber);
    Card cardBuildFirstCard = build.get(0);

    return (destPileLastCard.getValue().toNum() != cardBuildFirstCard.getValue().toNum() + 1)
            || (destPileLastCard.isSameColor(cardBuildFirstCard));
  }
}
