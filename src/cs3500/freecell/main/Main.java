package cs3500.freecell.main;

import java.io.InputStreamReader;

import cs3500.freecell.card.Card;
import cs3500.freecell.controller.FreecellController;
import cs3500.freecell.model.FreecellModelCreator;
import cs3500.freecell.model.FreecellOperations;

/**
 * Run a Tic Tac Toe game interactively on the console.
 */
public class Main {
  /**
   * Run a Tic Tac Toe game interactively on the console.
   */
  public static void main(String[] args) {
    FreecellOperations<Card> model = FreecellModelCreator
            .create(FreecellModelCreator.GameType.MULTIMOVE);

    new FreecellController(new InputStreamReader(System.in), System.out)
            .playGame(model.getDeck(), model, 4, 2, false);
  }
}