import cs3500.freecell.controller.FreecellController;
import cs3500.freecell.controller.SimpleFreecellController;
import cs3500.freecell.model.FreecellModel;
import cs3500.freecell.model.FreecellModelCreator;
import cs3500.freecell.model.FreecellModelCreator.GameType;
import cs3500.freecell.model.hw02.ICard;
import java.io.InputStreamReader;

/**
 * Allows me to play the game so I can see if inputs work.
 */
public class TestOutput {

  /**
   * Main.
   * @param args  an array of Strings
   */
  public static void main(String[] args) {
    FreecellModel<ICard> model = FreecellModelCreator.create(GameType.MULTIMOVE);
    FreecellController<ICard> control  = new SimpleFreecellController(model,
        new InputStreamReader(System.in), System.out);
    control.playGame(model.getDeck(), 52,5, false);
  }
}
