import static org.junit.Assert.assertEquals;

import cs3500.freecell.model.FreecellModel;
import cs3500.freecell.model.FreecellModelCreator;
import cs3500.freecell.model.FreecellModelCreator.GameType;
import cs3500.freecell.model.PileType;
import cs3500.freecell.model.hw02.ICard;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import org.junit.Test;

/**
 * Tests for the MultiMoveFreecellModel class to ensure all methods work properly.
 */
public class MultiMoveFreecellModelTest {

  FreecellModel<ICard> model = FreecellModelCreator.create(GameType.MULTIMOVE);

  @Test(expected = IllegalArgumentException.class)
  public void testNullSource() {
    model.startGame(model.getDeck(), 4, 4, false);
    model.move(null, 4, 5, PileType.CASCADE, 4);

  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullDest() {
    model.startGame(model.getDeck(), 4, 4, false);
    model.move(PileType.FOUNDATION, 4, 5, null, 4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullPiles() {
    model.startGame(model.getDeck(), 4, 4, false);
    model.move(null, 4, 5, null, 4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGameNotStarted() {
    model.move(PileType.CASCADE, 4, 5, PileType.OPEN, 4);
  }

  @Test
  public void testMoveCascadeToFound() {
    List<ICard> deck = model.getDeck();
    Collections.shuffle(deck, new Random(4));
    model.startGame(deck, 10, 4, false);
    model.move(PileType.CASCADE, 9, 4, PileType.FOUNDATION, 1);
    assertEquals("A♦", model.getFoundationCardAt(1,0).toString());
    assertEquals(4, model.getNumCardsInCascadePile(9));
    assertEquals(1, model.getNumCardsInFoundationPile(1));
    assertEquals("J♣", model.getCascadeCardAt(9,3).toString());
  }

  @Test
  public void testMoveCascadeToOpen() {
    List<ICard> deck = model.getDeck();
    Collections.shuffle(deck, new Random(4));
    model.startGame(deck, 10, 4, false);
    model.move(PileType.CASCADE, 9, 4, PileType.FOUNDATION, 1);
    model.move(PileType.CASCADE, 5, 4, PileType.OPEN, 3);
    assertEquals("3♠", model.getOpenCardAt(3).toString());
    assertEquals(4, model.getNumCardsInCascadePile(5));
    assertEquals(1, model.getNumCardsInOpenPile(3));
    assertEquals("4♠", model.getCascadeCardAt(5,3).toString());
    model.move(PileType.CASCADE, 5, 3, PileType.OPEN, 2);
    assertEquals("4♠", model.getOpenCardAt(2).toString());
    assertEquals(3, model.getNumCardsInCascadePile(5));
    assertEquals(1, model.getNumCardsInOpenPile(2));
    assertEquals("2♦", model.getCascadeCardAt(5,2).toString());
  }

  @Test
  public void testMoveOpenToOpen() {
    List<ICard> deck = model.getDeck();
    Collections.shuffle(deck, new Random(4));
    model.startGame(deck, 10, 4, false);
    model.move(PileType.CASCADE, 9, 4, PileType.FOUNDATION, 1);
    model.move(PileType.CASCADE, 5, 4, PileType.OPEN, 3);
    model.move(PileType.CASCADE, 5, 3, PileType.OPEN, 2);
    model.move(PileType.OPEN, 2, 3, PileType.OPEN, 1);
    assertEquals("4♠", model.getOpenCardAt(1).toString());
    assertEquals(0, model.getNumCardsInOpenPile(2));
    assertEquals(1, model.getNumCardsInOpenPile(1));
  }

  @Test
  public void testMoveOpenToFoundation() {
    List<ICard> deck = model.getDeck();
    Collections.shuffle(deck, new Random(4));
    model.startGame(deck, 10, 4, false);
    model.move(PileType.CASCADE, 9, 4, PileType.FOUNDATION, 1);
    model.move(PileType.CASCADE, 5, 4, PileType.OPEN, 3);
    model.move(PileType.CASCADE, 5, 3, PileType.OPEN, 2);
    model.move(PileType.CASCADE, 5, 2, PileType.OPEN, 1);
    assertEquals(2, model.getNumCardsInCascadePile(5));
    model.move(PileType.OPEN, 1, 2, PileType.FOUNDATION, 1);
    assertEquals(0, model.getNumCardsInOpenPile(1));
    assertEquals(2, model.getNumCardsInFoundationPile(1));
    assertEquals("2♦", model.getFoundationCardAt(1,1).toString());
  }

  @Test
  public void testMoveCascadeToCascade() {
    List<ICard> deck = model.getDeck();
    Collections.shuffle(deck, new Random(4));
    model.startGame(deck, 10, 4, false);
    model.move(PileType.CASCADE, 9, 4, PileType.FOUNDATION, 1);
    model.move(PileType.CASCADE, 5, 4, PileType.OPEN, 3);
    model.move(PileType.CASCADE, 5, 3, PileType.OPEN, 2);
    model.move(PileType.CASCADE, 5, 2, PileType.FOUNDATION, 1);
    model.move(PileType.CASCADE, 0, 5, PileType.CASCADE, 8);
    assertEquals(5, model.getNumCardsInCascadePile(0));
    assertEquals(6, model.getNumCardsInCascadePile(8));
    assertEquals("9♥", model.getCascadeCardAt(8,5).toString());
    model.move(PileType.CASCADE, 9, 3, PileType.CASCADE, 2);
    assertEquals(3, model.getNumCardsInCascadePile(9));
    assertEquals(6, model.getNumCardsInCascadePile(2));
    assertEquals("J♣", model.getCascadeCardAt(2,5).toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testOutOfBounds() {
    model.startGame(model.getDeck(), 4, 4, true);
    model.move(PileType.CASCADE, -4, 3, PileType.CASCADE, 3);
  }

  @Test
  public void testMultiMove() {
    model.startGame(model.getDeck(), 26, 2, false);
    model.move(PileType.CASCADE, 0, 1, PileType.OPEN, 0);
    model.move(PileType.CASCADE, 0, 0, PileType.OPEN, 1);
    model.move(PileType.CASCADE, 19, 1, PileType.CASCADE, 25);
    model.move(PileType.CASCADE, 25, 1, PileType.CASCADE, 0);
    assertEquals("F1:\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1:7♥\n"
        + "O2:A♣\n"
        + "C1: K♦, Q♠\n"
        + "C2: A♠, 7♦\n"
        + "C3: A♥, 8♣\n"
        + "C4: A♦, 8♠\n"
        + "C5: 2♣, 8♥\n"
        + "C6: 2♠, 8♦\n"
        + "C7: 2♥, 9♣\n"
        + "C8: 2♦, 9♠\n"
        + "C9: 3♣, 9♥\n"
        + "C10: 3♠, 9♦\n"
        + "C11: 3♥, 10♣\n"
        + "C12: 3♦, 10♠\n"
        + "C13: 4♣, 10♥\n"
        + "C14: 4♠, 10♦\n"
        + "C15: 4♥, J♣\n"
        + "C16: 4♦, J♠\n"
        + "C17: 5♣, J♥\n"
        + "C18: 5♠, J♦\n"
        + "C19: 5♥, Q♣\n"
        + "C20: 5♦\n"
        + "C21: 6♣, Q♥\n"
        + "C22: 6♠, Q♦\n"
        + "C23: 6♥, K♣\n"
        + "C24: 6♦, K♠\n"
        + "C25: 7♣, K♥\n"
        + "C26: 7♠", model.toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNotEnoughPiles() {
    model.startGame(model.getDeck(), 26, 2, false);
    model.move(PileType.CASCADE, 0, 1, PileType.OPEN, 0);
    model.move(PileType.CASCADE, 0, 0, PileType.OPEN, 1);
    model.move(PileType.CASCADE, 19, 1, PileType.CASCADE, 25);
    model.move(PileType.CASCADE, 17, 1, PileType.CASCADE, 25);
    model.move(PileType.CASCADE, 25, 1, PileType.CASCADE, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInValidBuild() {
    model.startGame(model.getDeck(), 26, 2, false);
    model.move(PileType.CASCADE, 19, 0, PileType.CASCADE, 25);
  }
}