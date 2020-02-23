import org.junit.Test;

import java.io.StringReader;
import java.util.Arrays;

import cs3500.freecell.card.Card;
import cs3500.freecell.model.FreecellModel;
import cs3500.freecell.controller.FreecellController;
import cs3500.freecell.controller.IFreecellController;

import static org.junit.Assert.assertEquals;

/**
 * A test class for the FreecellController.
 * Contains all of the tests I came up with.
 */
public class FreecellControllerTest {

  @Test
  public void testSingleValidMove() {
    FreecellModel model = new FreecellModel();
    StringBuilder gameLog = new StringBuilder();
    StringReader input = new StringReader("C1 13 F1 q");
    IFreecellController<Card> controller = new FreecellController(input, gameLog);
    controller.playGame(model.getDeck(), model, 4, 1, false);

    assertEquals(1, model.getFoundationPiles().get(0).size());
    assertEquals(12, model.getCascadePiles().get(0).size());
  }

  @Test
  public void testInvalidSourcePileTypeAsRow() {
    FreecellModel model = new FreecellModel();
    StringBuilder gameLog = new StringBuilder();
    StringReader input = new StringReader("M1 13 F1 q");
    IFreecellController<Card> controller = new FreecellController(input, gameLog);
    controller.playGame(model.getDeck(), model, 4, 1, false);

    String[] lines = gameLog.toString().split("\n");
    String lastMsg = String.join("\n",
            Arrays.copyOfRange(lines, lines.length - 3, lines.length));

    assertEquals("First input pile letter is incorrect.\n"
            + "Please enter your source pile, card index, and dest pile:\n"
            + "Game quit prematurely.", lastMsg);
  }

  @Test
  public void testInvalidSourcePileNumberAsRow() {
    FreecellModel model = new FreecellModel();
    StringBuilder gameLog = new StringBuilder();
    StringReader input = new StringReader("C80 13 F1 q");
    IFreecellController<Card> controller = new FreecellController(input, gameLog);
    controller.playGame(model.getDeck(), model, 4, 1, false);

    String[] lines = gameLog.toString().split("\n");
    String lastMsg = String.join("\n",
            Arrays.copyOfRange(lines, lines.length - 3, lines.length));

    assertEquals("Invalid move. Try again.\n"
            + "Please enter your source pile, card index, and dest pile:\n"
            + "Game quit prematurely.", lastMsg);
  }

  @Test
  public void testBogusSourceInputAsRow() {
    FreecellModel model = new FreecellModel();
    StringBuilder gameLog = new StringBuilder();
    StringReader input = new StringReader("!$(! 13 F1 q");
    IFreecellController<Card> controller = new FreecellController(input, gameLog);
    controller.playGame(model.getDeck(), model, 4, 1, false);

    String[] lines = gameLog.toString().split("\n");
    String lastMsg = String.join("\n",
            Arrays.copyOfRange(lines, lines.length - 3, lines.length));

    assertEquals("First input pile letter is incorrect.\n"
            + "Please enter your source pile, card index, and dest pile:\n"
            + "Game quit prematurely.", lastMsg);
  }

  @Test
  public void testBogusInputForPileNumber() {
    FreecellModel model = new FreecellModel();
    StringBuilder gameLog = new StringBuilder();
    StringReader input = new StringReader("C@#$ 13 F1 q");
    IFreecellController<Card> controller = new FreecellController(input, gameLog);
    controller.playGame(model.getDeck(), model, 4, 1, false);

    String[] lines = gameLog.toString().split("\n");
    String lastMsg = String.join("\n",
            Arrays.copyOfRange(lines, lines.length - 6, lines.length));

    assertEquals("Please enter your source pile, card index, and dest pile:\n"
            + "Cannot parse first input source pile number.\n"
            + "Please enter your source pile, card index, and dest pile:\n"
            + "First input pile letter is incorrect.\n"
            + "Please enter your source pile, card index, and dest pile:\n"
            + "Game quit prematurely.", lastMsg);
  }

  @Test
  public void testInvalidCardIndexInputAsRow() {
    FreecellModel model = new FreecellModel();
    StringBuilder gameLog = new StringBuilder();
    StringReader input = new StringReader("C1 20 F1 q");
    IFreecellController<Card> controller = new FreecellController(input, gameLog);
    controller.playGame(model.getDeck(), model, 4, 1, false);

    String[] lines = gameLog.toString().split("\n");
    String lastMsg = String.join("\n",
            Arrays.copyOfRange(lines, lines.length - 3, lines.length));

    assertEquals("Invalid move. Try again.\n"
            + "Please enter your source pile, card index, and dest pile:\n"
            + "Game quit prematurely.", lastMsg);
  }

  @Test
  public void testBogusCardIndexInputAsRow() {
    FreecellModel model = new FreecellModel();
    StringBuilder gameLog = new StringBuilder();
    StringReader input = new StringReader("C1 !@ F1 q");
    IFreecellController<Card> controller = new FreecellController(input, gameLog);
    controller.playGame(model.getDeck(), model, 4, 1, false);

    String[] lines = gameLog.toString().split("\n");
    String lastMsg = String.join("\n",
            Arrays.copyOfRange(lines, lines.length - 3, lines.length));

    assertEquals("Cannot parse second input card index number, please enter it again.\n"
            + "Cannot parse second input card index number, please enter it again.\n"
            + "Game quit prematurely.", lastMsg);
  }

  @Test
  public void testInvalidDestinationPileTypeAsRow() {
    FreecellModel model = new FreecellModel();
    StringBuilder gameLog = new StringBuilder();
    StringReader input = new StringReader("C1 13 Z1 q");
    IFreecellController<Card> controller = new FreecellController(input, gameLog);
    controller.playGame(model.getDeck(), model, 4, 1, false);

    String[] lines = gameLog.toString().split("\n");
    String lastMsg = String.join("\n",
            Arrays.copyOfRange(lines, lines.length - 3, lines.length));

    assertEquals("Please enter your source pile, card index, and dest pile:\n"
            + "Third input pile letter is incorrect, please enter it again.\n"
            + "Game quit prematurely.", lastMsg);
  }

  @Test
  public void testInvalidDestinationPileNumberAsRow() {
    FreecellModel model = new FreecellModel();
    StringBuilder gameLog = new StringBuilder();
    StringReader input = new StringReader("C1 13 F80 q");
    IFreecellController<Card> controller = new FreecellController(input, gameLog);
    controller.playGame(model.getDeck(), model, 4, 1, false);

    String[] lines = gameLog.toString().split("\n");
    String lastMsg = String.join("\n",
            Arrays.copyOfRange(lines, lines.length - 3, lines.length));

    assertEquals("Invalid move. Try again.\n"
            + "Please enter your source pile, card index, and dest pile:\n"
            + "Game quit prematurely.", lastMsg);
  }

  @Test
  public void testBogusDestinationInputAsRow() {
    FreecellModel model = new FreecellModel();
    StringBuilder gameLog = new StringBuilder();
    StringReader input = new StringReader("C1 13 !%@ q");
    IFreecellController<Card> controller = new FreecellController(input, gameLog);
    controller.playGame(model.getDeck(), model, 4, 1, false);

    String[] lines = gameLog.toString().split("\n");
    String lastMsg = String.join("\n",
            Arrays.copyOfRange(lines, lines.length - 3, lines.length));

    assertEquals("Please enter your source pile, card index, and dest pile:\n"
            + "Third input pile letter is incorrect, please enter it again.\n"
            + "Game quit prematurely.", lastMsg);
  }

  @Test
  public void testBogusInputForDestinationPileNumber() {
    FreecellModel model = new FreecellModel();
    StringBuilder gameLog = new StringBuilder();
    StringReader input = new StringReader("C1 13 F!@% q");
    IFreecellController<Card> controller = new FreecellController(input, gameLog);
    controller.playGame(model.getDeck(), model, 4, 1, false);

    String[] lines = gameLog.toString().split("\n");
    String lastMsg = String.join("\n",
            Arrays.copyOfRange(lines, lines.length - 3, lines.length));

    assertEquals("Please enter your source pile, card index, and dest pile:\n"
            + "Cannot parse third input source pile number, please enter it again.\n"
            + "Game quit prematurely.", lastMsg);
  }

  @Test(expected = IllegalStateException.class)
  public void testAppendableFail() {
    FreecellModel model = new FreecellModel();
    StringReader input = new StringReader("C1 13 F1 C1 12 F1");
    Appendable gameLog = new FailingAppendable();
    IFreecellController<Card> controller = new FreecellController(input, gameLog);
    controller.playGame(model.getDeck(), model, 4, 1, false);
  }

  @Test
  public void testMultipleInvalidMoves() {
    FreecellModel model = new FreecellModel();
    StringBuilder gameLog = new StringBuilder();
    StringReader input = new StringReader("! @ # $ % ^ & q");
    IFreecellController<Card> controller = new FreecellController(input, gameLog);
    controller.playGame(model.getDeck(), model, 4, 1, false);

    String[] lines = gameLog.toString().split("\n");
    assertEquals(26, lines.length);
  }

  @Test
  public void testQcomesInsteadOfFirstInput() {
    FreecellModel model = new FreecellModel();
    StringBuilder gameLog = new StringBuilder();
    StringReader input = new StringReader("q 13 F1");
    IFreecellController<Card> controller = new FreecellController(input, gameLog);
    controller.playGame(model.getDeck(), model, 4, 1, false);

    String[] lines = gameLog.toString().split("\n");
    String lastMsg = String.join("\n",
            Arrays.copyOfRange(lines, lines.length - 2, lines.length));
    System.out.println(lastMsg);

    assertEquals("Please enter your source pile, card index, and dest pile:"
            + "\n" + "Game quit prematurely.", lastMsg);
  }

  @Test
  public void testQcomesInsteadOfSecondInput() {
    FreecellModel model = new FreecellModel();
    StringBuilder gameLog = new StringBuilder();
    StringReader input = new StringReader("C1 q F1");
    IFreecellController<Card> controller = new FreecellController(input, gameLog);
    controller.playGame(model.getDeck(), model, 4, 1, false);

    String[] lines = gameLog.toString().split("\n");
    String lastMsg = String.join("\n",
            Arrays.copyOfRange(lines, lines.length - 2, lines.length));
    System.out.println(lastMsg);

    assertEquals("Please enter your source pile, card index, and dest pile:"
            + "\n" + "Game quit prematurely.", lastMsg);
  }

  @Test
  public void testQcomesInsteadOfThirdInput() {
    FreecellModel model = new FreecellModel();
    StringBuilder gameLog = new StringBuilder();
    StringReader input = new StringReader("C1 13 q");
    IFreecellController<Card> controller = new FreecellController(input, gameLog);
    controller.playGame(model.getDeck(), model, 4, 1, false);

    String[] lines = gameLog.toString().split("\n");
    String lastMsg = String.join("\n",
            Arrays.copyOfRange(lines, lines.length - 2, lines.length));
    System.out.println(lastMsg);

    assertEquals("Please enter your source pile, card index, and dest pile:"
            + "\n" + "Game quit prematurely.", lastMsg);
  }

  @Test
  public void testQcomesINSIDEOfThirdInput() {
    FreecellModel model = new FreecellModel();
    StringBuilder gameLog = new StringBuilder();
    StringReader input = new StringReader("C4 13 q4 q");
    IFreecellController<Card> controller = new FreecellController(input, gameLog);
    controller.playGame(model.getDeck(), model, 4, 1, false);

    String[] lines = gameLog.toString().split("\n");
    String lastMsg = String.join("\n",
            Arrays.copyOfRange(lines, lines.length - 3, lines.length));

    assertEquals("Please enter your source pile, card index, and dest pile:" + "\n"
            + "Third input pile letter is incorrect, please enter it again." + "\n"
            + "Game quit prematurely.", lastMsg);
  }

  @Test
  public void testValidInputButInvalidMoveByModel() {
    FreecellModel model = new FreecellModel();
    StringBuilder gameLog = new StringBuilder();
    StringReader input = new StringReader("C20 13 F1 q");
    IFreecellController<Card> controller = new FreecellController(input, gameLog);
    controller.playGame(model.getDeck(), model, 4, 1, false);

    String[] lines = gameLog.toString().split("\n");
    String lastMsg = String.join("\n",
            Arrays.copyOfRange(lines, lines.length - 4, lines.length));

    assertEquals("Please enter your source pile, card index, and dest pile:" + "\n"
            + "Invalid move. Try again." + "\n"
            + "Please enter your source pile, card index, and dest pile:" + "\n"
            + "Game quit prematurely.", lastMsg);
  }

  @Test
  public void testValidInputButInvalidCardIndexByModel() {
    FreecellModel model = new FreecellModel();
    StringBuilder gameLog = new StringBuilder();
    StringReader input = new StringReader("C1 11 F1 q");
    IFreecellController<Card> controller = new FreecellController(input, gameLog);
    controller.playGame(model.getDeck(), model, 4, 1, false);

    String[] lines = gameLog.toString().split("\n");
    String lastMsg = String.join("\n",
            Arrays.copyOfRange(lines, lines.length - 4, lines.length));

    assertEquals("Please enter your source pile, card index, and dest pile:" + "\n"
            + "Invalid move. Try again." + "\n"
            + "Please enter your source pile, card index, and dest pile:" + "\n"
            + "Game quit prematurely.", lastMsg);
  }

  @Test
  public void testValidInputButInvalidDestinationMoveByModel() {
    FreecellModel model = new FreecellModel();
    StringBuilder gameLog = new StringBuilder();
    StringReader input = new StringReader("C20 13 F0 q");
    IFreecellController<Card> controller = new FreecellController(input, gameLog);
    controller.playGame(model.getDeck(), model, 4, 1, false);

    String[] lines = gameLog.toString().split("\n");
    String lastMsg = String.join("\n",
            Arrays.copyOfRange(lines, lines.length - 4, lines.length));

    assertEquals("Please enter your source pile, card index, and dest pile:" + "\n"
            + "Invalid move. Try again." + "\n"
            + "Please enter your source pile, card index, and dest pile:" + "\n"
            + "Game quit prematurely.", lastMsg);
  }

  @Test
  public void testValidFirstMoveButInvalidSecondMoveToOpen() {
    FreecellModel model = new FreecellModel();
    StringBuilder gameLog = new StringBuilder();
    StringReader input = new StringReader("C1 13 O1 C1 12 O1 q");
    IFreecellController<Card> controller = new FreecellController(input, gameLog);
    controller.playGame(model.getDeck(), model, 4, 1, false);

    String[] lines = gameLog.toString().split("\n");
    String lastMsg = String.join("\n",
            Arrays.copyOfRange(lines, lines.length - 5, lines.length));

    assertEquals(26, lines.length);
  }

  @Test
  public void testValidFirstMoveButInvalidSecondMoveToFoundation() {
    FreecellModel model = new FreecellModel();
    StringBuilder gameLog = new StringBuilder();
    StringReader input = new StringReader("C1 13 F1 C2 13 F1 q");
    IFreecellController<Card> controller = new FreecellController(input, gameLog);
    controller.playGame(model.getDeck(), model, 4, 1, false);

    String[] lines = gameLog.toString().split("\n");
    String lastMsg = String.join("\n",
            Arrays.copyOfRange(lines, lines.length - 5, lines.length));

    assertEquals(26, lines.length);
  }

  @Test
  public void testWinGame() {
    FreecellModel model = new FreecellModel();
    StringBuilder gameLog = new StringBuilder();
    StringReader input = new StringReader("C1 13 F1 C1 12 F1 C1 11 F1 C1 10 F1 C1 9 F1 " +
            "C1 8 F1 C1 7 F1 C1 6 F1 C1 5 F1 C1 4 F1 C1 3 F1 C1 2 F1 C1 1 F1 "
            + "C2 13 F2 C2 12 F2 C2 11 F2 C2 10 F2 C2 9 F2 C2 8 F2 C2 7 F2 C2 6 F2 " +
            "C2 5 F2 C2 4 F2 C2 3 F2 C2 2 F2 C2 1 F2 "
            + "C3 13 F3 C3 12 F3 C3 11 F3 C3 10 F3 C3 9 F3 C3 8 F3 C3 7 F3 C3 6 F3 C3 5 F3 " +
            "C3 4 F3 C3 3 F3 C3 2 F3 C3 1 F3 "
            + "C4 13 F4 C4 12 F4 C4 11 F4 C4 10 F4 C4 9 F4 C4 8 F4 C4 7 F4 C4 6 F4 C4 5 F4 " +
            "C4 4 F4 C4 3 F4 C4 2 F4 C4 1 F4");
    IFreecellController<Card> controller = new FreecellController(input, gameLog);
    controller.playGame(model.getDeck(), model, 4, 1, false);

    String[] lines = gameLog.toString().split("\n");
    String lastMsg = String.join("\n",
            Arrays.copyOfRange(lines, lines.length - 5, lines.length));

    assertEquals(636, lines.length);
    assertEquals("C3:\n" + "C4:\n" + "\n" + "\n" + "Game over.", lastMsg);
  }

  @Test
  public void testWinGameWithInvalidInputsAndInvalidMoves() {
    FreecellModel model = new FreecellModel();
    StringBuilder gameLog = new StringBuilder();
    StringReader input = new StringReader("C1 13 F1 C1 12 F1 C1 11 F1 C1 10 F1 C1 9 F1 " +
            "C1 8 F1 C1 7 F1 C1 6 F1 C1 5 F1 C1 4 F1 C1 3 F1 C1 2 F1 C1 1 F1 "
            + "C2 13 F2 C2 12 F2 C2 11 F2 C2 10 F2 C2 9 F2 C2 8 F2 C2 7 F2 C2 6 F2 " +
            "C2 5 F2 C2 4 F2 C2 3 F2 C2 2 F2 C2 1 F2 "
            + "C3 13 F3 C3 12 F3 C3 11 F3 C3 10 F3 C3 9 F3 C3 8 F3 C3 7 F3 C3 6 F3 C3 5 F3 " +
            "C3 4 F3 C3 3 F3 C3 2 F3 C3 1 F3 "
            + "C4 13 F4 C4 12 F4 C4 11 F4 C4 10 F4 C4 9 F4 C4 8 F4 C4 7 F4 C4 6 F4 C4 5 F4 " +
            "C4 4 F4 C4 3 F4 C4 2 F4 "
            + "C1 0 M4 " + "!@1 0 M4 " + "C! 0 M4 @ @ " //various invalid inputs and invalid moves
            + "C4 1 F4");
    IFreecellController<Card> controller = new FreecellController(input, gameLog);
    controller.playGame(model.getDeck(), model, 4, 1, false);

    String[] lines = gameLog.toString().split("\n");
    String lastMsg = String.join("\n",
            Arrays.copyOfRange(lines, lines.length - 5, lines.length));

    assertEquals(648, lines.length);
    assertEquals("C3:\n" + "C4:\n" + "\n" + "\n" + "Game over.", lastMsg);
  }

  @Test
  public void testInputAfterGameIsOver() {
    FreecellModel model = new FreecellModel();
    StringBuilder gameLog = new StringBuilder();
    StringReader input = new StringReader("C1 13 F1 C1 12 F1 C1 11 F1 C1 10 F1 C1 9 F1 " +
            "C1 8 F1 C1 7 F1 C1 6 F1 C1 5 F1 C1 4 F1 C1 3 F1 C1 2 F1 C1 1 F1 "
            + "C2 13 F2 C2 12 F2 C2 11 F2 C2 10 F2 C2 9 F2 C2 8 F2 C2 7 F2 C2 6 F2 " +
            "C2 5 F2 C2 4 F2 C2 3 F2 C2 2 F2 C2 1 F2 "
            + "C3 13 F3 C3 12 F3 C3 11 F3 C3 10 F3 C3 9 F3 C3 8 F3 C3 7 F3 C3 6 F3 C3 5 F3 " +
            "C3 4 F3 C3 3 F3 C3 2 F3 C3 1 F3 "
            + "C4 13 F4 C4 12 F4 C4 11 F4 C4 10 F4 C4 9 F4 C4 8 F4 C4 7 F4 C4 6 F4 C4 5 F4 " +
            "C4 4 F4 C4 3 F4 C4 2 F4 "
            + "C1 0 M4 " + "!@1 0 M4 " + "C! 0 M4 " //various invalid inputs and invalid moves
            + "C4 1 F4 !! q");
    IFreecellController<Card> controller = new FreecellController(input, gameLog);
    controller.playGame(model.getDeck(), model, 4, 1, false);

    String[] lines = gameLog.toString().split("\n");
    String lastMsg = String.join("\n",
            Arrays.copyOfRange(lines, lines.length - 5, lines.length));

    assertEquals(644, lines.length);
    assertEquals("C3:\n" + "C4:\n" + "\n" + "\n" + "Game over.", lastMsg);
  }

  @Test
  public void testSingleValidMoveAfterPlayGameDidNotWork() {
    FreecellModel model = new FreecellModel();
    StringBuilder gameLog = new StringBuilder();
    StringReader input = new StringReader("C1 13 F1 q");
    IFreecellController<Card> controller = new FreecellController(input, gameLog);
    controller.playGame(model.getDeck(), model, -1, 1, false);

    String[] lines = gameLog.toString().split("\n");
    String lastMsg = String.join("\n",
            Arrays.copyOfRange(lines, lines.length - 1, lines.length));

    assertEquals("Could not start game.", lastMsg);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullInputForPlayGame() {
    FreecellModel model = new FreecellModel();
    StringBuilder gameLog = new StringBuilder();
    StringReader input = new StringReader("C1 13 F1 q");
    IFreecellController<Card> controller = new FreecellController(input, gameLog);
    controller.playGame(model.getDeck(), null, -1, 1, false);

    String[] lines = gameLog.toString().split("\n");
    String lastMsg = String.join("\n",
            Arrays.copyOfRange(lines, lines.length - 1, lines.length));
  }

  @Test
  public void testCannotInputValidMoveCannotFinishTheGameAndThenRageQuitScreaming() {
    FreecellModel model = new FreecellModel();
    StringBuilder gameLog = new StringBuilder();
    StringReader input = new StringReader("C1 13 F1 C1 12 F1 C1 11 F1 C1 10 F1 C1 9 F1 " +
            "C1 8 F1 C1 7 F1 C1 6 F1 C1 5 F1 C1 4 F1 C1 3 F1 C1 2 F1 C1 1 F1 "
            + "C2 13 F2 C2 12 F2 C2 11 F2 C2 10 F2 C2 9 F2 C2 8 F2 C2 7 F2 C2 6 F2 " +
            "C2 5 F2 C2 4 F2 C2 3 F2 C2 2 F2 C2 1 F2 "
            + "C3 13 F3 C3 12 F3 C3 11 F3 C3 10 F3 C3 9 F3 C3 8 F3 C3 7 F3 C3 6 F3 C3 5 F3 " +
            "C3 4 F3 C3 3 F3 C3 2 F3 C3 1 F3 "
            + "C4 13 F4 C4 12 F4 C4 11 F4 C4 10 F4 C4 9 F4 C4 8 F4 C4 7 F4 C4 6 F4 C4 5 F4 " +
            "C4 4 F4 C4 3 F4 C4 2 F4 "
            + "C4 0 M4 N4 Z4 V4 F4 q");  //various invalid inputs and invalid moves;
    IFreecellController<Card> controller = new FreecellController(input, gameLog);
    controller.playGame(model.getDeck(), model, 4, 1, false);

    String[] lines = gameLog.toString().split("\n");
    String lastMsg = String.join("\n",
            Arrays.copyOfRange(lines, lines.length - 5, lines.length));

    assertEquals(630, lines.length);
    assertEquals("Third input pile letter is incorrect, please enter it again.\n" +
            "Third input pile letter is incorrect, please enter it again.\n" +
            "Invalid move. Try again.\n" +
            "Please enter your source pile, card index, and dest pile:\n" +
            "Game quit prematurely.", lastMsg);
  }

  @Test(expected = IllegalStateException.class)
  public void testNoMoreInputsToFinish() {
    FreecellModel model = new FreecellModel();
    StringBuilder gameLog = new StringBuilder();
    StringReader input = new StringReader("C1 13 F1 C2 13 14");
    IFreecellController<Card> controller = new FreecellController(input, gameLog);
    controller.playGame(model.getDeck(), model, 4, 1, false);
  }
}