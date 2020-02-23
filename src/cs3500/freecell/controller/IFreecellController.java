package cs3500.freecell.controller;

import java.util.List;

import cs3500.freecell.model.FreecellOperations;

/**
 * Represents a Controller for Freecell game: handle user moves by executing them using the model;
 * convey move outcomes to the user in some form.
 */
public interface IFreecellController<Card> {

  /**
   * Execute a single game of Freecell given a Freecell Model. When the game is over,
   * the playGame method ends.
   *
   * @param deck         a given deck of {@link cs3500.freecell.card.Card}
   * @param model        a given Freecell Model instance
   * @param numCascades  a given number of cascade piles to create
   * @param numOpens     a given number of open piles to create
   * @param shuffle      true is user wants to shuffle the deck before playing
   * @throws IllegalStateException when the Appendable or Readable fails.
   * @throws IllegalArgumentException when the input arguments are invalid.
   */
  void playGame(List<Card> deck, FreecellOperations<Card> model, int numCascades,
                int numOpens, boolean shuffle);
}
