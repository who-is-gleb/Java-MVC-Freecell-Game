import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import cs3500.freecell.card.Card;
import cs3500.freecell.model.FreecellModel;
import cs3500.freecell.model.PileType;
import cs3500.freecell.card.Suit;
import cs3500.freecell.card.Value;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * A test class for the FreecellModel.
 * Contains all of the tests I came up with.
 */
public class FreecellModelTest {
  private FreecellModel fm1 = new FreecellModel();
  private List<Card> deck = fm1.getDeck();

  @Test
  public void testThatGameStateAfterStartLookGoodWith4Cascade() {
    System.out.println(fm1.getDeck());
    fm1.startGame(fm1.getDeck(), 4, 1, true);
    System.out.println(fm1.getGameState());
    assertEquals(4, fm1.getCascadePiles().size());
  }

  @Test
  public void testThatGameStateAfterStartLookGoodWith8Cascade() {
    System.out.println(fm1.getDeck());
    fm1.startGame(fm1.getDeck(), 8, 1, false);
    System.out.println(fm1.getGameState());
    assertEquals(8, fm1.getCascadePiles().size());
  }

  // getDeck() and Card tests
  @Test
  public void testModelDefaultConstructorDeckLength() {
    assertEquals(deck.size(), 52);
  }

  @Test
  public void testThatDeckLooksLegit() {
    System.out.println(deck);
    fm1.startGame(fm1.getDeck(), 4, 1, false);
    assertEquals("K♠", fm1.getCascadePiles().get(0).get(0).toString());
  }

  @Test
  public void testCardToString() {
    Card card = new Card(Suit.D, Value.J);
    assertEquals("J♦", card.toString());
  }

  // startGame() tests
  @Test
  public void testStartGameCascadeSize4() {
    fm1.startGame(deck, 4, 1, false);
    assertEquals(4, fm1.getCascadePiles().size());
  }

  @Test
  public void testStartGameCascadeSize8() {
    fm1.startGame(deck, 8, 1, false);
    assertEquals(8, fm1.getCascadePiles().size());
  }

  @Test
  public void testStartGameCascadeSize6() {
    fm1.startGame(deck, 6, 1, false);
    assertEquals(6, fm1.getCascadePiles().size());
  }

  @Test
  public void testStartGameCascadePile1Length() {
    fm1.startGame(deck, 8, 1, false);
    assertEquals(7, fm1.getCascadePiles().get(0).size());
  }

  @Test
  public void testStartGameCascadePile2Length() {
    fm1.startGame(deck, 8, 1, false);
    assertEquals(7, fm1.getCascadePiles().get(1).size());
  }

  @Test
  public void testStartGameOpenPileLength() {
    fm1.startGame(deck, 8, 1, false);
    assertEquals(0, fm1.getOpenPiles().get(0).size());
  }

  @Test
  public void testStartGameFoundationPile1Length() {
    fm1.startGame(deck, 8, 1, false);
    assertEquals(0, fm1.getFoundationPiles().get(0).size());
  }

  @Test
  public void testStartGameAllCascadePilesListLength() {
    fm1.startGame(deck, 8, 1, false);
    assertEquals(8, fm1.getCascadePiles().size());
  }

  @Test
  public void testStartGameAllOpenPilesListLength() {
    fm1.startGame(deck, 8, 1, false);
    assertEquals(1, fm1.getOpenPiles().size());
  }

  // Validate startGame arguments
  @Test (expected = IllegalArgumentException.class )
  public void testStartGameIllegalCascadeNum() {
    fm1.startGame(deck, 2,
            1, false);
  }

  @Test (expected = IllegalArgumentException.class )
  public void testStartGameIllegalCascadeNumAndIllegalOpenNum() {
    fm1.startGame(deck, 2, 0, false);
  }

  @Test (expected = IllegalArgumentException.class )
  public void testStartGameIllegalDeckSizeAndDuplicateValue() {
    List<Card> deckCopy = new ArrayList<>(deck);
    deckCopy.add(new Card(Suit.S, Value.NINE));
    fm1.startGame(deckCopy, 8, 1, false);
  }

  // Test deck is not changed after a few changes


  // Test shuffle cards

  // Test move method
  @Test
  public void testValidMoveFromCascToOpen() {
    fm1.startGame(deck, 4, 1, false);
    fm1.move(PileType.CASCADE, 0, 12, PileType.OPEN, 0);
    assertEquals("A♠", fm1.getOpenPiles().get(0).get(0).toString());
  }

  @Test (expected = IllegalStateException.class)
  public void testInvalidMoveBeforeGameStarts() {
    fm1.move(PileType.OPEN, 0, 650, PileType.FOUNDATION, 0);
    assertEquals("K♣", fm1.getFoundationPiles().get(0).get(0).toString());
  }

  // Test new game move
  @Test
  public void testValidMoveFromCascToFoundation() {
    fm1.startGame(deck, 4, 1, false);
    fm1.move(PileType.CASCADE, 0, 12, PileType.FOUNDATION, 1);
    assertEquals("A♠", fm1.getFoundationPiles().get(1).get(0).toString());
  }

  // Test invalid move
  @Test (expected = IllegalArgumentException.class)
  public void testInvalidMoveFromCascToCasc() {
    fm1.startGame(deck, 4, 1, false);
    fm1.move(PileType.CASCADE, 0, 650, PileType.CASCADE, 1);
  }

  // Test getCard() method
  @Test
  public void testValidGetCard() {
    fm1.startGame(deck, 4, 1, false);
    assertEquals("Q♠", fm1.getCard(PileType.CASCADE, 0,
            1).toString());
  }

  @Test (expected = IllegalArgumentException.class)
  public void testInvalidGetCard() {
    fm1.startGame(deck, 4, 1, false);
    fm1.getCard(PileType.CASCADE, 5, 1);
  }

  @Test (expected = IllegalStateException.class)
  public void testInvalidGetCardBeforeBeginning() {
    fm1.getCard(PileType.CASCADE, 5,
            1);
  }

  // Test gameState()
  @Test
  public void testGameStateBeforeBegin() {
    assertEquals("", fm1.getGameState());
  }

  // Test .equals on Card
  @Test
  public void testCardsEqual() {
    Card c0 = new Card(Suit.S, Value.THREE);
    Card c1 = new Card(Suit.S, Value.THREE);
    assertEquals(c0, c1);
  }

  // Test incomplete deck shuffle
  @Test (expected = IllegalArgumentException.class)
  public void testIncompleteDeckShuffle() {
    List<Card> incompleteDeck = fm1.getDeck();
    incompleteDeck.remove(0);
    fm1.startGame(incompleteDeck, 4, 1, true);
  }

  @Test
  public void testGameNotStartedIsItOver() {
    assertFalse(fm1.isGameOver());
  }

  @Test
  public void testDealCorrectlyNoShuffleEqual() {
    List<Card> deck = fm1.getDeck();
    fm1.startGame(deck, 4, 2, false);
    List<Card> deck2 = fm1.getDeck();
    FreecellModel fm2 = new FreecellModel();
    fm2.startGame(deck2, 4, 2, false);
    assertEquals(fm1.getGameState(), fm2.getGameState());
  }

  @Test
  public void testNoBlankSpacesAfterEmptyPiles() {
    fm1.startGame(fm1.getDeck(), 4, 1, false);
    assertFalse(fm1.getGameState().contains("F1: "));
  }

  @Test
  public void testMoveToEmptyOpenPileNoShuffle() {
    fm1.startGame(fm1.getDeck(), 4, 1, false);
    fm1.move(PileType.CASCADE, 0, 12, PileType.OPEN, 0);
    System.out.println(fm1.getGameState());
    assertEquals("A♠", fm1.getOpenPiles().get(0).get(0).toString());
  }

  @Test (expected = IllegalArgumentException.class)
  public void testNullInput() {
    fm1.startGame(null, 4, 1,
            false);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testDuplicateValue() {
    Card invalidCard = new Card(Suit.S, Value.THREE);
    List<Card> someDeck = fm1.getDeck();
    someDeck.remove(0);
    someDeck.add(invalidCard);
    fm1.startGame(someDeck, 4, 1, false);
  }

  @Test
  public void testShuffle() {
    fm1.startGame(fm1.getDeck(), 4, 1, false);
    String gameStateNoShuffle = fm1.getGameState();
    fm1.startGame(fm1.getDeck(), 4, 1, true);
    String gameStateWithShuffle = fm1.getGameState();
    assertNotEquals(gameStateNoShuffle, gameStateWithShuffle);
  }

  @Test
  public void testValidMoveToNonEmptyFoundation() {
    fm1.startGame(fm1.getDeck(), 4, 1, false);
    System.out.println(fm1.getGameState());
    fm1.move(PileType.CASCADE, 1, 12, PileType.FOUNDATION, 1);
    fm1.move(PileType.CASCADE, 1, 11, PileType.FOUNDATION, 1);
    System.out.println(fm1.getGameState());
    assertEquals(fm1.getFoundationPiles().get(1).size(), 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidMoveToNonEmptyFoundation() {
    fm1.startGame(fm1.getDeck(), 4, 1, false);
    fm1.move(PileType.CASCADE, 1, 13, PileType.FOUNDATION, 1);
    fm1.move(PileType.CASCADE, 2, 13, PileType.FOUNDATION, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidMoveToCascade() {
    fm1.startGame(fm1.getDeck(), 4, 1, false);
    fm1.move(PileType.CASCADE, 2, 13, PileType.CASCADE, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidMoveToOccupiedOpen() {
    fm1.startGame(fm1.getDeck(), 4, 1, false);
    fm1.move(PileType.CASCADE, 2, 13, PileType.OPEN, 0);
    fm1.move(PileType.CASCADE, 2, 12, PileType.OPEN, 0);
  }

  @Test
  public void testGameOver() {
    fm1.startGame(fm1.getDeck(), 4, 1, false);

    fm1.move(PileType.CASCADE, 0, 12, PileType.FOUNDATION, 0);
    fm1.move(PileType.CASCADE, 0, 11, PileType.FOUNDATION, 0);
    fm1.move(PileType.CASCADE, 0, 10, PileType.FOUNDATION, 0);
    fm1.move(PileType.CASCADE, 0, 9, PileType.FOUNDATION, 0);
    fm1.move(PileType.CASCADE, 0, 8, PileType.FOUNDATION, 0);
    fm1.move(PileType.CASCADE, 0, 7, PileType.FOUNDATION, 0);
    fm1.move(PileType.CASCADE, 0, 6, PileType.FOUNDATION, 0);
    fm1.move(PileType.CASCADE, 0, 5, PileType.FOUNDATION, 0);
    fm1.move(PileType.CASCADE, 0, 4, PileType.FOUNDATION, 0);
    fm1.move(PileType.CASCADE, 0, 3, PileType.FOUNDATION, 0);
    fm1.move(PileType.CASCADE, 0, 2, PileType.FOUNDATION, 0);
    fm1.move(PileType.CASCADE, 0, 1, PileType.FOUNDATION, 0);
    fm1.move(PileType.CASCADE, 0, 0, PileType.FOUNDATION, 0);

    fm1.move(PileType.CASCADE, 1, 12, PileType.FOUNDATION, 1);
    fm1.move(PileType.CASCADE, 1, 11, PileType.FOUNDATION, 1);
    fm1.move(PileType.CASCADE, 1, 10, PileType.FOUNDATION, 1);
    fm1.move(PileType.CASCADE, 1, 9, PileType.FOUNDATION, 1);
    fm1.move(PileType.CASCADE, 1, 8, PileType.FOUNDATION, 1);
    fm1.move(PileType.CASCADE, 1, 7, PileType.FOUNDATION, 1);
    fm1.move(PileType.CASCADE, 1, 6, PileType.FOUNDATION, 1);
    fm1.move(PileType.CASCADE, 1, 5, PileType.FOUNDATION, 1);
    fm1.move(PileType.CASCADE, 1, 4, PileType.FOUNDATION, 1);
    fm1.move(PileType.CASCADE, 1, 3, PileType.FOUNDATION, 1);
    fm1.move(PileType.CASCADE, 1, 2, PileType.FOUNDATION, 1);
    fm1.move(PileType.CASCADE, 1, 1, PileType.FOUNDATION, 1);
    fm1.move(PileType.CASCADE, 1, 0, PileType.FOUNDATION, 1);

    fm1.move(PileType.CASCADE, 2, 12, PileType.FOUNDATION, 2);
    fm1.move(PileType.CASCADE, 2, 11, PileType.FOUNDATION, 2);
    fm1.move(PileType.CASCADE, 2, 10, PileType.FOUNDATION, 2);
    fm1.move(PileType.CASCADE, 2, 9, PileType.FOUNDATION, 2);
    fm1.move(PileType.CASCADE, 2, 8, PileType.FOUNDATION, 2);
    fm1.move(PileType.CASCADE, 2, 7, PileType.FOUNDATION, 2);
    fm1.move(PileType.CASCADE, 2, 6, PileType.FOUNDATION, 2);
    fm1.move(PileType.CASCADE, 2, 5, PileType.FOUNDATION, 2);
    fm1.move(PileType.CASCADE, 2, 4, PileType.FOUNDATION, 2);
    fm1.move(PileType.CASCADE, 2, 3, PileType.FOUNDATION, 2);
    fm1.move(PileType.CASCADE, 2, 2, PileType.FOUNDATION, 2);
    fm1.move(PileType.CASCADE, 2, 1, PileType.FOUNDATION, 2);
    fm1.move(PileType.CASCADE, 2, 0, PileType.FOUNDATION, 2);

    fm1.move(PileType.CASCADE, 3, 12, PileType.FOUNDATION, 3);
    fm1.move(PileType.CASCADE, 3, 11, PileType.FOUNDATION, 3);
    fm1.move(PileType.CASCADE, 3, 10, PileType.FOUNDATION, 3);
    fm1.move(PileType.CASCADE, 3, 9, PileType.FOUNDATION, 3);
    fm1.move(PileType.CASCADE, 3, 8, PileType.FOUNDATION, 3);
    fm1.move(PileType.CASCADE, 3, 7, PileType.FOUNDATION, 3);
    fm1.move(PileType.CASCADE, 3, 6, PileType.FOUNDATION, 3);
    fm1.move(PileType.CASCADE, 3, 5, PileType.FOUNDATION, 3);
    fm1.move(PileType.CASCADE, 3, 4, PileType.FOUNDATION, 3);
    fm1.move(PileType.CASCADE, 3, 3, PileType.FOUNDATION, 3);
    fm1.move(PileType.CASCADE, 3, 2, PileType.FOUNDATION, 3);
    fm1.move(PileType.CASCADE, 3, 1, PileType.FOUNDATION, 3);
    fm1.move(PileType.CASCADE, 3, 0, PileType.FOUNDATION, 3);

    assertTrue(fm1.isGameOver());
  }

  @Test
  public void testIsGameOverReturnsFalseWhenInProgress() {
    fm1.startGame(fm1.getDeck(), 4, 1, false);
    assertFalse(fm1.isGameOver());
  }

  @Test
  public void testStartGameWhileInProgressRestartsTheGame() {
    fm1.startGame(fm1.getDeck(), 4, 1, false);
    fm1.move(PileType.CASCADE, 3, 12, PileType.FOUNDATION, 3);
    assertEquals(fm1.getFoundationPiles().get(3).size(), 1);
    fm1.startGame(fm1.getDeck(), 4, 1, false);
    assertEquals(fm1.getFoundationPiles().get(3).size(), 0);
  }

  @Test
  public void testGameStateIsEmptyBeforeGameStarted() {
    try {
      fm1.startGame(fm1.getDeck(), -1, 1, false);
    } catch (IllegalArgumentException e) {
      assertEquals("", fm1.getGameState());
    }
  }
}
