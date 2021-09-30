package cs3500.freecell.view;

import cs3500.freecell.model.FreecellModelState;
import java.io.IOException;

/**
 * Represents a Text/String based visual of the Freecell game.
 */
public class FreecellTextView implements FreecellView {

  private final FreecellModelState<?> model;
  private final Appendable ap;

  /**
   * Constructs a freecellTextView.
   * @param model   a simple model of the free cell game
   * @throws IllegalArgumentException   if the freecell model is null
   */
  public FreecellTextView(FreecellModelState<?> model) throws IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException();
    }
    this.model = model;
    this.ap = System.out;
  }

  /**
   * Constructs a FreecellTextView.
   * @param model   a model of the Freecell game
   * @param ap      outputs the game into a view
   */
  public FreecellTextView(FreecellModelState<?> model, Appendable ap) {
    if (model == null || ap == null) {
      throw new IllegalArgumentException("Model or Appendable is null");
    }
    this.model = model;
    this.ap = ap;
  }

  /**
   * Formats the free cell game according to its own toString format.
   * @return String
   */
  @Override
  public String toString() {
    return model.toString();
  }

  /**
   * Renders the Freecell Game via toString method.
   * @throws IOException if input is invalid
   */
  @Override
  public void renderBoard() throws IOException {
    ap.append(this + "\n");

  }

  /**
   * Renders the messages of the Game to guide the user on incorrect inputs.
   * @param message the message to be transmitted
   * @throws IOException  if input is invalid
   */
  @Override
  public void renderMessage(String message) throws IOException {
    ap.append(message);
  }
}
