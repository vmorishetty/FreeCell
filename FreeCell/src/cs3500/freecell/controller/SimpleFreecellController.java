package cs3500.freecell.controller;

import cs3500.freecell.model.FreecellModel;
import cs3500.freecell.model.PileType;
import cs3500.freecell.model.hw02.ICard;
import cs3500.freecell.view.FreecellTextView;
import cs3500.freecell.view.FreecellView;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * Represents a simple controller of the Freecell game that delegates commands of the user to the
 * model and outputs its results.
 */
public class SimpleFreecellController implements FreecellController<ICard> {
  private final FreecellModel<ICard> model;
  private final Readable rd;
  private final Appendable ap;

  /**
   * Constructs a SimpleFreecellController.
   * @param model   a model of the Freecell game
   * @param rd      reads the inputs of the user
   * @param ap      displays the desired outputs
   * @throws IllegalArgumentException   if any of the above parameters are null
   */
  public SimpleFreecellController(FreecellModel<ICard> model, Readable rd, Appendable ap)
      throws IllegalArgumentException {
    if (rd == null || ap == null  || model == null) {
      throw new IllegalArgumentException("Readable or Appendable or model null");
    }

    this.model = model;
    this.rd = rd;
    this.ap = ap;
  }

  /**
   * Helper method to display messages.
   * @param message   the deisred message to be outputted
   * @param view      a FreecellView that output the desired message
   * @throws IllegalStateException  if input is invalid
   */
  private void write(String message, FreecellView view) throws IllegalStateException {
    try {
      view.renderMessage(message);
    }
    catch (IOException io) {
      throw new IllegalStateException("Cannot append \n");
    }
  }

  /**
   * Plays the game of Freecell according to the user's inputs.
   * @param deck        the deck to be used to play this game
   * @param numCascades the number of cascade piles
   * @param numOpens    the number of open piles
   * @param shuffle     shuffle the deck if true, false otherwise
   * @throws IllegalStateException    if there are no more inputs or inputs are invalid
   * @throws IllegalArgumentException if the deck is null
   */
  @Override
  public void playGame(List<ICard> deck, int numCascades, int numOpens, boolean shuffle)
      throws IllegalStateException, IllegalArgumentException {

    if (deck == null) {
      throw new IllegalArgumentException("Deck or model is null\n");
    }
    FreecellView view = new FreecellTextView(model, ap);
    
    try {
      model.startGame(deck, numCascades, numOpens, shuffle);
    } catch (IllegalArgumentException ae) {
      write("Could not start game.", view);
      return;
    }

    try {
      view.renderBoard();
    }
    catch (IOException io) {
      throw new IllegalStateException("Can't draw\n");
    }

    Scanner scanner = new Scanner(rd);

    while (scanner.hasNext()) {

      boolean foundSource = false;
      boolean foundIndex = false;
      boolean foundDest = false;
      boolean fullInput = false;
      PileType source = null;
      int sourceIndex = -1;
      int cardIndex = -1;
      PileType dest = null;
      int destIndex = -1;
      String input;

      while (!foundSource && scanner.hasNext()) {
        input = scanner.next();
        if (input.equalsIgnoreCase("q")) {
          write("Game quit prematurely.\n", view);
          return;
        }
        try {
          source = validInput(input);
          sourceIndex = Integer.parseInt(input.substring(1)) - 1;
          foundSource = true;
        } catch (NumberFormatException nf) {
          write("Invalid Source Pile index, try again.\n", view);
        } catch (IllegalArgumentException ie) {
          write("Invalid Pile, try again.\n", view);
        }
      }

      while (!foundIndex && scanner.hasNext()) {
        input = scanner.next();
        if (input.equalsIgnoreCase("q")) {
          write("Game quit prematurely.\n", view);
          return;
        }
        try {
          cardIndex = Integer.parseInt(input) - 1;
          foundIndex = true;
        } catch (NumberFormatException nf) {
          write("Invalid Card index, try again.\n", view);
        }
      }

      while (!foundDest && scanner.hasNext()) {
        input = scanner.next();
        if (input.equalsIgnoreCase("q")) {
          write("Game quit prematurely.\n", view);
          return;
        }
        try {
          dest = validInput(input);
          try {
            destIndex = Integer.parseInt(input.substring(1)) - 1;
            fullInput = true;
          }
          catch (NumberFormatException nf) {
            write("Invalid Destination Pile index, try again.\n", view);
          }
          foundDest = true;
        } catch (IllegalArgumentException ie) {
          write("Invalid Pile, try again.\n", view);
        }
      }

      if (source != null && dest != null && fullInput) {
        try {
          model.move(source, sourceIndex, cardIndex, dest, destIndex);
          view.renderBoard();
        } catch (IllegalArgumentException ie) {
          write("Invalid move try again\n", view);
        } catch (IOException io) {
          throw new IllegalStateException("Can't draw\n");
        }
      }

      if (model.isGameOver()) {
        write("Game over.", view);
        return;
      }
    }
    throw new IllegalStateException("Ran out of inputs");
  }

  /**
   * Determines if the inputs ask for a valid pile.
   * @param input the String input for a source or destination pile
   * @return  PileType
   * @throws IllegalArgumentException if the pile isn't a cascade, open, or foundation pile
   */
  private PileType validInput(String input) throws IllegalArgumentException {
    switch (input.charAt(0)) {
      case 'C':
        return PileType.CASCADE;
      case 'F':
        return PileType.FOUNDATION;
      case 'O':
        return PileType.OPEN;
      default:
        throw new IllegalArgumentException("Invalid Pile Type\n");
    }
  }
}
