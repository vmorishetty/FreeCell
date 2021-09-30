import static org.junit.Assert.assertEquals;

import cs3500.freecell.model.FreecellModel;
import cs3500.freecell.model.FreecellModelCreator;
import cs3500.freecell.model.FreecellModelCreator.GameType;
import cs3500.freecell.model.FreecellModelState;
import cs3500.freecell.model.PileType;
import cs3500.freecell.model.hw02.ICard;
import cs3500.freecell.model.hw02.SimpleFreecellModel;
import cs3500.freecell.view.FreecellTextView;
import cs3500.freecell.view.FreecellView;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import org.junit.Test;

/**
 * Tests for the FreecellTextView class to ensure all methods work properly.
 */
public class FreecellTextViewTest {
  FreecellModelState<ICard> simpleModel = new SimpleFreecellModel();
  FreecellView view = new FreecellTextView(simpleModel);

  @Test
  public void testNotStartGameToString() {
    assertEquals("", view.toString());
  }

  @Test
  public void testMiddleGameToString() {
    //SimpleFreecellModel model = new SimpleFreecellModel();
    FreecellModel<ICard> model = FreecellModelCreator.create(GameType.MULTIMOVE);
    List<ICard> deck = model.getDeck();
    Collections.shuffle(deck, new Random(4));
    model.startGame(deck, 10, 4, false);
    model.move(PileType.CASCADE, 9, 4, PileType.FOUNDATION, 1);
    model.move(PileType.CASCADE, 5, 4, PileType.OPEN, 3);
    model.move(PileType.CASCADE, 5, 3, PileType.OPEN, 2);
    model.move(PileType.CASCADE, 5, 2, PileType.FOUNDATION, 1);
    model.move(PileType.CASCADE, 0, 5, PileType.CASCADE, 8);
    FreecellView view2 = new FreecellTextView(model);
    assertEquals("F1:\n"
        + "F2:A♦, 2♦\n"
        + "F3:\n"
        + "F4:\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:4♠\n"
        + "O4:3♠\n"
        + "C1: 4♦, K♣, K♦, 6♥, J♠\n"
        + "C2: 7♠, 9♦, A♥, 7♣, 9♣, J♥\n"
        + "C3: 5♥, Q♣, 5♣, Q♠, Q♥\n"
        + "C4: Q♦, A♣, 5♠, 10♣, 8♥\n"
        + "C5: 8♣, 8♠, 7♦, 7♥, A♠\n"
        + "C6: K♥, 2♣\n"
        + "C7: 8♦, 2♠, 6♠, 4♣, K♠\n"
        + "C8: 3♥, 6♣, 6♦, 10♥, 10♦\n"
        + "C9: 2♥, 3♦, 9♠, 3♣, 10♠, 9♥\n"
        + "C10: 5♦, 4♥, J♦, J♣", view2.toString());
  }

  @Test
  public void testGameOverToString() {
    SimpleFreecellModel model = new SimpleFreecellModel();
    model.startGame(model.getDeck(), 52, 5, false);
    for (int i = 0; i < 52; i++) {
      model.move(PileType.CASCADE, i, 0, PileType.FOUNDATION, i % 4);
    }
    FreecellView view2 = new FreecellTextView(model);
    assertEquals("F1:A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣, 9♣, 10♣, J♣, Q♣, K♣\n"
        + "F2:A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n"
        + "F3:A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n"
        + "F4:A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦, Q♦, K♦\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "O5:\n"
        + "C1: \n"
        + "C2: \n"
        + "C3: \n"
        + "C4: \n"
        + "C5: \n"
        + "C6: \n"
        + "C7: \n"
        + "C8: \n"
        + "C9: \n"
        + "C10: \n"
        + "C11: \n"
        + "C12: \n"
        + "C13: \n"
        + "C14: \n"
        + "C15: \n"
        + "C16: \n"
        + "C17: \n"
        + "C18: \n"
        + "C19: \n"
        + "C20: \n"
        + "C21: \n"
        + "C22: \n"
        + "C23: \n"
        + "C24: \n"
        + "C25: \n"
        + "C26: \n"
        + "C27: \n"
        + "C28: \n"
        + "C29: \n"
        + "C30: \n"
        + "C31: \n"
        + "C32: \n"
        + "C33: \n"
        + "C34: \n"
        + "C35: \n"
        + "C36: \n"
        + "C37: \n"
        + "C38: \n"
        + "C39: \n"
        + "C40: \n"
        + "C41: \n"
        + "C42: \n"
        + "C43: \n"
        + "C44: \n"
        + "C45: \n"
        + "C46: \n"
        + "C47: \n"
        + "C48: \n"
        + "C49: \n"
        + "C50: \n"
        + "C51: \n"
        + "C52: ", view2.toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullAppendable() {
    FreecellView view2 = new FreecellTextView(simpleModel, null);
  }

  @Test
  public void testRenderBoardGameNotStarted() throws IOException {
    Appendable ap = new StringBuilder();
    FreecellView view2 = new FreecellTextView(simpleModel, ap);
    view2.renderBoard();
    assertEquals("\n", ap.toString());
  }

  @Test
  public void testRenderBoardGameStarted() throws IOException {
    Appendable ap = new StringBuilder();
    SimpleFreecellModel model = new SimpleFreecellModel();
    model.startGame(model.getDeck(), 4, 4, false);
    FreecellView view2 = new FreecellTextView(model, ap);
    view2.renderBoard();
    assertEquals("F1:\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣, 9♣, 10♣, J♣, Q♣, K♣\n"
        + "C2: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n"
        + "C3: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n"
        + "C4: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦, Q♦, K♦\n", ap.toString());
  }

  @Test
  public void testRenderMessage()  throws IOException {
    Appendable ap = new StringBuilder();
    SimpleFreecellModel model = new SimpleFreecellModel();
    model.startGame(model.getDeck(), 4, 4, false);
    FreecellView view2 = new FreecellTextView(model, ap);
    view2.renderMessage("Banana");
    assertEquals("Banana", ap.toString());
  }

  @Test
  public void testRenderMessageEmpty()  throws IOException {
    Appendable ap = new StringBuilder();
    SimpleFreecellModel model = new SimpleFreecellModel();
    model.startGame(model.getDeck(), 4, 4, false);
    FreecellView view2 = new FreecellTextView(model, ap);
    view2.renderMessage("");
    assertEquals("", ap.toString());
  }
}