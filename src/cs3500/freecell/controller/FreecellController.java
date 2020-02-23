package cs3500.freecell.controller;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import cs3500.freecell.card.Card;
import cs3500.freecell.model.FreecellOperations;
import cs3500.freecell.model.PileType;

/**
 * A controller for the Freecell game.
 * It handles the user's input, validates it, passes to Freecell Model, and outputs the result.
 * Has 2 fields: Appendable out for transmitting the output and Scanner scan for receiving input.
 */
public class FreecellController implements IFreecellController<Card> {
  private final Appendable out;
  private final Scanner scan;

  /**
   * A constructor for Freecell controller.
   * @param in  input.
   * @param out output.
   */
  public FreecellController(Readable in, Appendable out) {
    if (in == null || out == null) {
      throw new IllegalArgumentException("Readable and Appendable can't be null");
    }
    this.out = out;
    scan = new Scanner(in);
  }

  @Override
  public void playGame(List<Card> deck, FreecellOperations<Card> model,
                       int numCascades, int numOpens, boolean shuffle) {
    // checking for null inputs
    checkForNullInputs(deck, model);

    try {
      // trying to start the game
      try {
        startTheGame(deck, model, numCascades, numOpens, shuffle);
      } catch (Exception e) {
        out.append("Could not start game.");
        return;
      }

      // trying to run the game in a while loop
      while (!model.isGameOver()) {
        out.append("Please enter your source pile, card index, and dest pile:\n");

        // getting the first input as String
        String firstInput = getNextInput();
        if (checkIfQuit(firstInput)) {
          return;
        }
        if (checkIfPileLetterIsInvalid(String.valueOf(firstInput.charAt(0)))) {
          out.append("First input pile letter is incorrect.\n");
          continue;
        }
        String firstInputLetter = String.valueOf(firstInput.charAt(0));
        if (cannotParseNumberValue(firstInput.substring(1))) {
          out.append("Cannot parse first input source pile number.\n");
          continue;
        }
        int firstInputNumber = Integer.parseInt(firstInput.substring(1));

        // getting the second input as String
        String secondInput = getNextInput();
        if (checkIfQuit(secondInput)) {
          return;
        }
        if (cannotParseNumberValue(secondInput)) {
          while (cannotParseNumberValue(secondInput)) {
            out.append("Cannot parse second input card index number, please enter it again.\n");
            secondInput = getNextInput();
            if (checkIfQuit(secondInput)) {
              return;
            }
          }
        }
        int secondInputNumber = Integer.parseInt(secondInput);

        // getting the third input as String
        String thirdInput = getNextInput();
        if (checkIfQuit(thirdInput)) {
          return;
        }
        if (checkIfPileLetterIsInvalid(String.valueOf(thirdInput.charAt(0)))) {
          while (checkIfPileLetterIsInvalid(String.valueOf(thirdInput.charAt(0)))) {
            out.append("Third input pile letter is incorrect, please enter it again.\n");
            thirdInput = getNextInput();
            if (checkIfQuit(thirdInput)) {
              return;
            }
          }
        }
        String thirdInputLetter = String.valueOf(thirdInput.charAt(0));
        if (cannotParseNumberValue(thirdInput.substring(1))) {
          while (cannotParseNumberValue(thirdInput.substring(1))) {
            out.append("Cannot parse third input source pile number, please enter it again.\n");
            thirdInput = getNextInput();
            if (checkIfQuit(thirdInput)) {
              return;
            }
          }
        }
        int thirdInputNumber = Integer.parseInt(thirdInput.substring(1));


        // trying to execute a successful move
        tryExecuteAMove(model, firstInputLetter, firstInputNumber, secondInputNumber,
                thirdInputLetter, thirdInputNumber);

        // checking if game is over at the end of the move
        if (model.isGameOver()) {
          out.append(model.getGameState()).append("Game over.");
          return;
        }
      }
    } catch (IOException ioe) {
      throw new IllegalStateException("Append failed", ioe);
    }
  }

  /**
   * A method that tries to read the next input. If there is none, throws an exception.
   * @return String - user's input.
   * @throws IllegalStateException if cannot read the next input.
   */
  private String getNextInput() {
    try {
      return scan.next();
    } catch (NoSuchElementException e) {
      throw new IllegalStateException("No more elements to work with");
    }
  }

  /**
   * A method that calls the model to start the game and appends to the Appendable if it starts.
   * @param deck         a given deck (list of cards).
   * @param model        a given Freecell model object.
   * @param numCascades  a given number of cascade piles to create.
   * @param numOpens     a given number of open piles to create.
   * @param shuffle      a boolean for shuffling. If true, then shuffle the deck.
   * @throws IOException if reading or appending does not work.
   */
  private void startTheGame(List<Card> deck, FreecellOperations<Card> model, int numCascades,
                            int numOpens, boolean shuffle) throws IOException {
    model.startGame(deck, numCascades, numOpens, shuffle);
    out.append("Game is started. \n");
    out.append(model.getGameState()).append("\n");
  }

  /**
   * A method to try parse a number input.
   * @param inputNumber a number input in a form of String.
   * @return true if this method cannot parse String input to an int successfully.
   */
  private boolean cannotParseNumberValue(String inputNumber) {
    try {
      Integer.parseInt(inputNumber);
      return false;
    } catch (Exception e) {
      return true;
    }
  }

  /**
   * A method to check if input pile letter is invalid (meaning any letter other than C, F, or O.
   * @param input a given input string.
   * @return a boolean. TRUE IF input letter is INVALID.
   */
  private boolean checkIfPileLetterIsInvalid(String input) {
    return (!input.equals("C"))
            && (!input.equals("F"))
            && (!input.equals("O"));
  }

  /**
   * A method to check if the given input is for quitting the game.
   * @param input A string for user's input.
   * @return a boolean. True if the input is q or Q.
   * @throws IOException if reading or appending fails.
   */
  private boolean checkIfQuit(String input)
          throws IOException {
    if (input.equals("q") || (input.equals("Q"))) {
      out.append("Game quit prematurely.").append("\n");
      return true;
    } else {
      return false;
    }
  }

  /**
   * A method that checks if the given deck or Freecell model are nulls.
   * @param deck  a given deck of cards, as a lsit of {@link Card}.
   * @param model a given Freecell model instance.
   */
  private void checkForNullInputs(List<Card> deck, FreecellOperations<Card> model) {
    if ((deck == null) || (model == null)) {
      throw new IllegalArgumentException("Nulls are not allowed.");
    }
  }

  /**
   * A helper method that tries to execute a model's move.
   * @param model           a given FreecellModel.
   * @param sourcePileType  a given source pile type as String.
   * @param sourcePileNum   a given source pile number as int.
   * @param cardIndexInt    a given source pile card index as int.
   * @param destPileType    a given destination pile type as String.
   * @param destPileNum     a given destination pile number as int.
   * @throws IOException when reading or writing fails.
   */
  private void tryExecuteAMove(FreecellOperations<Card> model, String sourcePileType,
                               int sourcePileNum, int cardIndexInt, String destPileType,
                               int destPileNum) throws IOException {
    try {
      model.move(returnPileType(sourcePileType), sourcePileNum - 1,
              cardIndexInt - 1, returnPileType(destPileType),
              destPileNum - 1);
      out.append("Move is done.").append("\n");
      out.append(model.getGameState()).append("\n").append("\n");
    } catch (Exception e) {
      out.append("Invalid move. Try again.").append("\n");
    }
  }

  /**
   * A method to return a {@link PileType} enum objects based on a given string.
   * @param pileType A string to represent a desired pile type. E.g. "C" for PileType.CASCADE.
   * @return a {@link PileType} object.
   */
  private PileType returnPileType(String pileType) {
    switch (pileType) {
      case("C"):
        return PileType.CASCADE;
      case("F"):
        return PileType.FOUNDATION;
      case("O"):
        return PileType.OPEN;
      default:
        throw new IllegalArgumentException("The given pile type string is wrong");
    }
  }
}
