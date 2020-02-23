package cs3500.freecell.hw02;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A model class for FreecellOperations interface implementation.
 * Fields:
 * 1) the actual cascape piles that are represented as a List of List of Card.
 * 2) the actual open piles that are represented as a List of List of Card.
 * 3) the actual foundation piles that are represented as a List of List of Card.
 * 4) boolean "gameIsRunning": true if game has started, false otherwise.
 */
public class FreecellModel implements FreecellOperations<Card> {
  protected List<List<Card>> cascadePiles;
  protected List<List<Card>> openPiles;
  protected List<List<Card>> foundationPiles;
  protected boolean gameIsRunning = false;

  @Override
  public List<Card> getDeck() {
    ArrayList<Card> returnCards = new ArrayList<>();
    for (int i = Value.values().length - 1; i >= 0; i--) {
      for (int ii = Suit.values().length - 1; ii >= 0; ii--) {
        Card c = new Card(Suit.values()[ii], Value.values()[i]);
        returnCards.add(c);
      }
    }
    return returnCards;
  }

  @Override
  public void startGame(List<Card> deck, int numCascadePiles, int numOpenPiles, boolean shuffle) {
    if (deck == null) {
      throw new IllegalArgumentException("No nulls are allowed");
    }

    // Validating all the arguments to throw exceptions if needed.
    validateArguments(deck, numCascadePiles, numOpenPiles);

    gameIsRunning = true;

    cascadePiles = new ArrayList<>();
    for (int i = 0; i < numCascadePiles; i++) {
      cascadePiles.add(new ArrayList<>());
    }
    openPiles = new ArrayList<>();
    for (int i = 0; i < numOpenPiles; i++) {
      openPiles.add(new ArrayList<>());
    }
    foundationPiles = new ArrayList<>();
    for (int i = 0; i < 4; i++) {
      foundationPiles.add(new ArrayList<>());
    }

    // Distributing cards now.
    List<Card> copyOfDeck = new ArrayList<>(deck);
    if (shuffle) {
      copyOfDeck = shuffleCards(copyOfDeck);
    }

    int accum = 0;
    for (int i = 0; i < deck.size(); i++) {
      if (accum == numCascadePiles) {
        accum = 0;
      }
      cascadePiles.get(accum).add(copyOfDeck.get(i));
      accum += 1;
    }
  }

  /**
   * A helper method for startGame method that validates all the arguments, according to the
   * game instructions: deck size, duplicates, possible suits, piles sizes, and values.
   * @param deck             the given deck to check.
   * @param numCascadePiles  the given num of cascade piles to create.
   * @param numOpenPiles     the given num of open piles to create.
   * @throws IllegalArgumentException if any argument if invalid.
   */
  protected void validateArguments(List<Card> deck, int numCascadePiles, int numOpenPiles) {
    if (deck == null) {
      throw new IllegalArgumentException("No nulls are allowed");
    }
    // Checking for validity of the size of the deck
    if (deck.size() != 52) {
      throw new IllegalArgumentException("The deck size is not valid");
    }
    // Checking for validity of the suits of the deck
    for (Card card: deck) {
      if ((!card.getSuit().toString().equals("♣"))
              && (!card.getSuit().toString().equals("♦"))
              && (!card.getSuit().toString().equals("♥"))
              && (!card.getSuit().toString().equals("♠"))) {
        throw new IllegalArgumentException("The deck has weird suits in it");
      }
    }
    // Checking for duplications in the deck
    Set<String> set = new HashSet<>();
    for (Card card: deck) {
      set.add(card.toString());
    }
    if (set.size() != 52) {
      throw new IllegalArgumentException("The deck has duplicate values in it");
    }
    // Checking piles' numbers validity
    if ((numCascadePiles < 4) || (numOpenPiles < 1)) {
      throw new IllegalArgumentException("Num of piles is incorrect");
    }
  }

  /**
   * A helper method for the startGame method. In particular, this method shuffles cards
   * if called.
   * @param copyOfDeck a given deck of {@link Card}.
   * @return           a new shuffled deck of {@link Card}.
   */
  protected List<Card> shuffleCards(List<Card> copyOfDeck) {
    if (copyOfDeck == null) {
      throw new IllegalArgumentException("No nulls are allowed");
    }
    List<Card> shuffledCopy = new ArrayList<>(copyOfDeck);
    Card removedCard1 = shuffledCopy.remove(0);
    Card removedCard2 = shuffledCopy.remove(0);
    Card removedCard3 = shuffledCopy.remove(0);
    shuffledCopy.add(removedCard1);
    shuffledCopy.add(removedCard2);
    shuffledCopy.add(removedCard3);
    return shuffledCopy;
  }

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

    if (source == PileType.OPEN) {
      if ((openPiles.get(pileNumber).size() == 0)
              || (cardIndex != openPiles.get(pileNumber).size() - 1)) {
        throw new IllegalArgumentException("Invalid open source pile input.");
      } else {
        moveHelper(pileNumber, destination, destPileNumber, openPiles);
      }
    } else if (source == PileType.FOUNDATION) {
      throw new IllegalArgumentException("You cannot move from foundation piles");

    } else if (source == PileType.CASCADE) {
      if ((cascadePiles.get(pileNumber).size() == 0)
              || (cardIndex != cascadePiles.get(pileNumber).size() - 1)) {
        throw new IllegalArgumentException("Invalid cascade source pile input.");
      } else {
        moveHelper(pileNumber, destination, destPileNumber, cascadePiles);
      }
    }
  }

  /**
   * A helper method for the move method.
   * Takes parameters, removes the last card from a source pile, and then adds
   * that card to the destination pile.
   * Also checks for various movement restrictions.
   * @param pileNumber       source pile number
   * @param destination      destination pile type
   * @param destPileNumber   destination pile number
   * @param sourcePile   type of the source pile
   * @throws IllegalArgumentException when trying to move to an occupied open pile or when
   *                trying to move a card that doesn't match the criteria of suit or value.
   */
  protected void moveHelper(int pileNumber, PileType destination,
                          int destPileNumber, List<List<Card>> sourcePile) {

    // I need to put the card back if exception is thrown
    Card takenCard = sourcePile.get(pileNumber)
            .remove(sourcePile.get(pileNumber).size() - 1);

    if (destination == PileType.OPEN) {
      if (openPiles.get(destPileNumber).size() > 0) {
        sourcePile.get(pileNumber).add(takenCard);
        throw new IllegalArgumentException("Open piles can only contain 1 card");
      } else {
        openPiles.get(destPileNumber).add(takenCard);
      }
    }

    if (destination == PileType.FOUNDATION) {
      if (foundationPiles.get(destPileNumber).size() == 0) {
        foundationPiles.get(destPileNumber).add(takenCard);
      } else if ((getLastCard(foundationPiles, destPileNumber)
              .getSuit().equals(takenCard.getSuit()))
              && (takenCard.getValue().toNum()
              == (getLastCard(foundationPiles, destPileNumber).getValue().toNum() + 1))) {
        foundationPiles.get(destPileNumber).add(takenCard);
      } else {
        sourcePile.get(pileNumber).add(takenCard);
        throw new IllegalArgumentException("This card can't go to this foundation pile");
      }
    }

    if (destination == PileType.CASCADE) {
      if (cascadePiles.get(destPileNumber).size() == 0) {
        cascadePiles.get(destPileNumber).add(takenCard);
      } else if ((takenCard.getValue().toNum()
              == getLastCard(cascadePiles, destPileNumber).getValue().toNum() - 1)
              && (!takenCard.isSameColor(getLastCard(cascadePiles, destPileNumber)))) {
        cascadePiles.get(destPileNumber).add(takenCard);
      } else {
        sourcePile.get(pileNumber).add(takenCard);
        throw new IllegalArgumentException("This card can't go to this cascade pile");
      }
    }
  }

  /**
   * Returns the last card from the given pile.
   * @param thePile    the pile type to get the card from.
   * @param pileNumber the number of the pile to get the card from.
   * @return           {@link Card} the last card from the pile.
   */
  protected Card getLastCard(List<List<Card>> thePile, int pileNumber) {
    return thePile.get(pileNumber).get(thePile.get(pileNumber).size() - 1);
  }

  @Override
  public boolean isGameOver() {
    if (gameIsRunning) {
      if ((foundationPiles.get(0).size() == 13)
              && (foundationPiles.get(1).size() == 13)
              && (foundationPiles.get(2).size() == 13)
              && (foundationPiles.get(3).size() == 13)) {
        gameIsRunning = false;
        return true;
      } else {
        return false;
      }
    } else {
      return false;
    }
  }

  @Override
  public Card getCard(PileType pile, int pileNumber, int cardIndex) {
    if (!gameIsRunning) {
      throw new IllegalStateException("The game has not been started");
    }
    if (pile == null) {
      throw new IllegalArgumentException("PileType should not be null");
    }

    try {
      if (pile == PileType.OPEN) {
        return openPiles.get(pileNumber).get(cardIndex);
      } else if (pile == PileType.FOUNDATION) {
        return foundationPiles.get(pileNumber).get(cardIndex);
      } else if (pile == PileType.CASCADE) {
        return cascadePiles.get(pileNumber).get(cardIndex);
      } else {
        throw new IllegalArgumentException("There is no such card");
      }
    } catch (Exception e) {
      throw new IllegalArgumentException("Index was out of bounds");
    }
  }

  @Override
  public String getGameState() {
    StringBuilder answer = new StringBuilder();

    if (!gameIsRunning) {
      return "";
    } else {
      for (int i = 0; i < foundationPiles.size(); i++) {
        if (foundationPiles.get(i).size() == 0) {
          answer.append("F").append(i + 1).append(":").append("\n");
        } else {
          answer.append("F").append(i + 1).append(":");
          String s = foundationPiles.get(i).stream().map(Card::toString)
                  .collect(Collectors.joining(", "));
          answer.append(" ").append(s).append("\n");
        }
      }

      for (int i = 0; i < openPiles.size(); i++) {
        if (openPiles.get(i).size() == 0) {
          answer.append("O").append(i + 1).append(":").append("\n");
        } else {
          answer.append("O").append(i + 1).append(":");
          String s = openPiles.get(i).stream().map(Card::toString)
                  .collect(Collectors.joining(", "));
          answer.append(" ").append(s).append("\n");
        }
      }

      for (int i = 0; i < cascadePiles.size(); i++) {
        if (cascadePiles.get(i).size() == 0) {
          answer.append("C").append(i + 1).append(":").append("\n");
        } else {
          if (i == cascadePiles.size() - 1) {
            answer.append("C").append(i + 1).append(":");
            String s = cascadePiles.get(i).stream().map(Card::toString)
                    .collect(Collectors.joining(", "));
            answer.append(" ").append(s);
          } else {
            answer.append("C").append(i + 1).append(":");
            String s = cascadePiles.get(i).stream().map(Card::toString)
                    .collect(Collectors.joining(", "));
            answer.append(" ").append(s).append("\n");
          }
        }
      }
    }
    return answer.toString();
  }


  /**
   * A getter method for getting cascade piles list.
   * @return a list of list of {@link Card}.
   */
  public List<List<Card>> getCascadePiles() {
    return cascadePiles;
  }

  /**
   * A getter method for getting open piles list.
   * @return a list of list of {@link Card}.
   */
  public List<List<Card>> getOpenPiles() {
    return openPiles;
  }

  /**
   * A getter method for getting foundation piles list.
   * @return a list of list of {@link Card}.
   */
  public List<List<Card>> getFoundationPiles() {
    return foundationPiles;
  }
}
