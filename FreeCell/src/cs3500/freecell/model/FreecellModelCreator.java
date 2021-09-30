package cs3500.freecell.model;


import cs3500.freecell.model.hw02.SimpleFreecellModel;
import cs3500.freecell.model.hw04.MultiMoveFreecellModel;

/**
 * Factory Class to produce Freecell models of single move game or multi-move games.
 */
public class FreecellModelCreator {

  /**
   * Enumeration that limits the types of game allowed to play.
   */
  public enum GameType { SINGLEMOVE, MULTIMOVE }

  /**
   * Factory Method to produce different move type of Freecell game.
   * @param type    whether the game is single or multi-moves
   * @return        FreecellModel
   * @throws IllegalArgumentException   if type is null
   */
  public static FreecellModel create(GameType type) throws IllegalArgumentException {
    if (type == null) {
      throw new IllegalArgumentException("GameType cant be empty");
    }
    else if (type.equals(GameType.SINGLEMOVE)) {
      return new SimpleFreecellModel();
    }
    else if (type.equals(GameType.MULTIMOVE)) {
      return new MultiMoveFreecellModel();
    }
    else {
      throw new IllegalArgumentException("Not an available move type");
    }
  }
}
