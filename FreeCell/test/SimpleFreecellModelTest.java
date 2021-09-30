import static org.junit.Assert.assertEquals;

import cs3500.freecell.model.FreecellModel;
import cs3500.freecell.model.PileType;
import cs3500.freecell.model.hw02.Card;
import cs3500.freecell.model.hw02.CardNumber;
import cs3500.freecell.model.hw02.CardSuit;
import cs3500.freecell.model.hw02.ICard;
import cs3500.freecell.model.hw02.SimpleFreecellModel;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import org.junit.Test;

/**
 * Tests for the SimpleFreecellModel class to ensure all methods work properly.
 */
public class SimpleFreecellModelTest {

  FreecellModel<ICard> model = new SimpleFreecellModel();

  @Test
  public void testGetDeck() {
    List<ICard> cards = model.getDeck();
    for (ICard c: cards) {
      int count = 0;
      for (ICard c2: cards) {
        if (c == c2) {
          assertEquals(c, c2);
          count++;
        }
        assertEquals(false, count > 1);
      }
    }
    assertEquals(52, cards.size());
  }

  @Test
  public void testStartGameNoShuffle() {
    model.startGame(model.getDeck(), 8, 4, false);
    assertEquals(4, model.getNumOpenPiles());
    assertEquals(8, model.getNumCascadePiles());
    assertEquals(7, model.getNumCardsInCascadePile(0));
    assertEquals(6, model.getNumCardsInCascadePile(6));
    assertEquals("A♣", model.getCascadeCardAt(0,0).toString());
    assertEquals("A♠", model.getCascadeCardAt(1,0).toString());
    assertEquals("3♣", model.getCascadeCardAt(0,1).toString());
  }

  @Test
  public void testStartGameShuffle() {
    List<ICard> deck = model.getDeck();
    Collections.shuffle(deck, new Random(4));
    model.startGame(deck, 10, 4, false);
    assertEquals(4, model.getNumOpenPiles());
    assertEquals(10, model.getNumCascadePiles());
    assertEquals(6, model.getNumCardsInCascadePile(0));
    assertEquals(5, model.getNumCardsInCascadePile(6));
    assertEquals(0, model.getNumCardsInOpenPile(3));
    assertEquals(0, model.getNumCardsInFoundationPile(2));
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
  public void testMoveCascadeToFoundationStack() {
    List<ICard> deck = model.getDeck();
    Collections.shuffle(deck, new Random(4));
    model.startGame(deck, 10, 4, false);
    model.move(PileType.CASCADE, 9, 4, PileType.FOUNDATION, 1);
    model.move(PileType.CASCADE, 5, 4, PileType.OPEN, 3);
    model.move(PileType.CASCADE, 5, 3, PileType.OPEN, 2);
    model.move(PileType.CASCADE, 5, 2, PileType.FOUNDATION, 1);
    assertEquals(2, model.getNumCardsInCascadePile(5));
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
  public void testDeckDuplicates() {
    List<ICard> deck = model.getDeck();
    deck.add(new Card(CardNumber.EIGHT, CardSuit.CLUB));
    model.startGame(deck, 10, 4, false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNotEnoughCardsInDeck() {
    List<ICard> deck = model.getDeck();
    deck.remove(7);
    model.startGame(deck, 10, 4, false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNotEnoughOpenPiles() {
    model.startGame(model.getDeck(), 10, -2, false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNotEnoughCascadePiles() {
    model.startGame(model.getDeck(), 2, 5, false);
  }

  @Test(expected = IllegalStateException.class)
  public void testGameNotStartedMove() {
    model.move(PileType.CASCADE, 3, 4, PileType.OPEN, 7);
  }

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
  public void testMoveMiddleCard() {
    model.startGame(model.getDeck(), 10, 5, false);
    model.move(PileType.CASCADE, 3, 1, PileType.OPEN, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveFromFoundation() {
    List<ICard> deck = model.getDeck();
    Collections.shuffle(deck, new Random(4));
    model.startGame(deck, 10, 4, false);
    model.move(PileType.CASCADE, 9, 4, PileType.FOUNDATION, 1);
    model.move(PileType.FOUNDATION, 1, 0, PileType.OPEN, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveNotOppositeSuit() {
    List<ICard> deck = model.getDeck();
    Collections.shuffle(deck, new Random(4));
    model.startGame(deck, 10, 4, false);
    model.move(PileType.CASCADE, 0, 5, PileType.CASCADE, 7);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveNotNumberBelow() {
    List<ICard> deck = model.getDeck();
    Collections.shuffle(deck, new Random(4));
    model.startGame(deck, 10, 4, false);
    model.move(PileType.CASCADE, 0, 5, PileType.CASCADE, 6);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveNonAceToEmptyFoundation() {
    List<ICard> deck = model.getDeck();
    Collections.shuffle(deck, new Random(4));
    model.startGame(deck, 10, 4, false);
    model.move(PileType.CASCADE, 0, 5, PileType.FOUNDATION, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveWrongSuitToFoundation() {
    List<ICard> deck = model.getDeck();
    Collections.shuffle(deck, new Random(4));
    model.startGame(deck, 10, 4, false);
    model.startGame(deck, 10, 4, false);
    model.move(PileType.CASCADE, 4, 4, PileType.FOUNDATION, 1);
    model.move(PileType.CASCADE, 5, 4, PileType.OPEN, 3);
    model.move(PileType.CASCADE, 5, 3, PileType.OPEN, 2);
    model.move(PileType.CASCADE, 5, 2, PileType.FOUNDATION, 1);
    model.move(PileType.CASCADE, 0, 5, PileType.CASCADE, 8);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveWrongNumberToFoundation() {
    List<ICard> deck = model.getDeck();
    Collections.shuffle(deck, new Random(4));
    model.startGame(deck, 10, 4, false);
    model.startGame(deck, 10, 4, false);
    model.move(PileType.CASCADE, 4, 4, PileType.FOUNDATION, 1);
    model.move(PileType.CASCADE, 4, 4, PileType.FOUNDATION, 1);
  }

  @Test(expected = IllegalStateException.class)
  public void testGameNotStartedNumCardsInFoundation() {
    model.getNumCardsInFoundationPile(4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testOutOfBoundsOfNumCardsInFoundation() {
    model.startGame(model.getDeck(), 4, 5, true);
    model.getNumCardsInFoundationPile(6);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNegativeOutOfBoundsOfNumCardsInFoundation() {
    model.startGame(model.getDeck(), 4, 5, true);
    model.getFoundationCardAt(-1,4);
  }

  @Test
  public void testGetNumCascadePiles() {
    assertEquals(-1,model.getNumCascadePiles());
    model.startGame(model.getDeck(), 4, 5, true);
    assertEquals(4, model.getNumCascadePiles());
  }

  @Test(expected = IllegalStateException.class)
  public void testGameNotStartedNumCardsInCascade() {
    model.getNumCardsInCascadePile(4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testOutOfBoundsOfNumCardsInCascade() {
    model.startGame(model.getDeck(), 4, 5, true);
    model.getNumCardsInCascadePile(7);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNegativeOutOfBoundsOfNumCardsInCascade() {
    model.startGame(model.getDeck(), 4, 5, true);
    model.getNumCardsInCascadePile(-1);
  }

  @Test(expected = IllegalStateException.class)
  public void testGameNotStartedNumCardsInOpen() {
    model.getNumCardsInOpenPile(4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testOutOfBoundsOfNumCardsInOpen() {
    model.startGame(model.getDeck(), 4, 5, true);
    model.getNumCardsInOpenPile(7);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNegativeOutOfBoundsOfNumCardsInOpen() {
    model.startGame(model.getDeck(), 4, 5, true);
    model.getNumCardsInOpenPile(-1);
  }

  @Test
  public void testGetNumOpenPiles() {
    assertEquals(-1, model.getNumOpenPiles());
    model.startGame(model.getDeck(), 4, 5, true);
    assertEquals(5, model.getNumOpenPiles());
  }

  @Test(expected = IllegalStateException.class)
  public void testGameNotStartedGetFoundationCardAt() {
    model.getFoundationCardAt(3,3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGameOutOfBoundsGetFoundationCardAt() {
    model.startGame(model.getDeck(), 4, 5, true);
    model.getFoundationCardAt(3,3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGameNegativeOutOfBoundsGetFoundationCardAt() {
    model.startGame(model.getDeck(), 4, 5, true);
    model.getFoundationCardAt(-1,3);
  }

  @Test(expected = IllegalStateException.class)
  public void testGameNotStartedGetCascadeCardAt() {
    model.getCascadeCardAt(3,3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGameOutOfBoundsGetCascadeCardAt() {
    model.startGame(model.getDeck(), 4, 5, true);
    model.getCascadeCardAt(6,3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGameNegativeOutOfBoundsGetCascadeCardAt() {
    model.startGame(model.getDeck(), 4, 5, true);
    model.getCascadeCardAt(-88,3);
  }

  @Test(expected = IllegalStateException.class)
  public void testGameNotStartedGetOpenCardAt() {
    model.getOpenCardAt(3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGameOutOfBoundsGetOpenCardAt() {
    model.startGame(model.getDeck(), 4, 5, true);
    model.getOpenCardAt(99);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGameNegativeOutOfBoundsGetOpenCardAt() {
    model.startGame(model.getDeck(), 4, 5, true);
    model.getOpenCardAt(-12);
  }

  @Test
  public void testGameOver() {
    model.startGame(model.getDeck(), 52, 5, false);
    assertEquals(false, model.isGameOver());
    for (int i = 0; i < 52; i++) {
      model.move(PileType.CASCADE, i, 0, PileType.FOUNDATION, i % 4);
    }
    assertEquals(true, model.isGameOver());
  }

  @Test
  public void testToStringGameNotStarted() {
    assertEquals("", model.toString());
  }

  @Test
  public void testToStringStartGame() {
    List<ICard> deck = model.getDeck();
    Collections.shuffle(deck, new Random(4));
    model.startGame(deck, 10, 4, false);
    assertEquals("F1:\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: 4♦, K♣, K♦, 6♥, J♠, 9♥\n"
        + "C2: 7♠, 9♦, A♥, 7♣, 9♣, J♥\n"
        + "C3: 5♥, Q♣, 5♣, Q♠, Q♥\n"
        + "C4: Q♦, A♣, 5♠, 10♣, 8♥\n"
        + "C5: 8♣, 8♠, 7♦, 7♥, A♠\n"
        + "C6: K♥, 2♣, 2♦, 4♠, 3♠\n"
        + "C7: 8♦, 2♠, 6♠, 4♣, K♠\n"
        + "C8: 3♥, 6♣, 6♦, 10♥, 10♦\n"
        + "C9: 2♥, 3♦, 9♠, 3♣, 10♠\n"
        + "C10: 5♦, 4♥, J♦, J♣, A♦", model.toString());
  }

  @Test
  public void testToStingMidGame() {
    List<ICard> deck = model.getDeck();
    Collections.shuffle(deck, new Random(4));
    model.startGame(deck, 10, 4, false);
    model.move(PileType.CASCADE, 9, 4, PileType.FOUNDATION, 1);
    model.move(PileType.CASCADE, 5, 4, PileType.OPEN, 3);
    model.move(PileType.CASCADE, 5, 3, PileType.OPEN, 2);
    model.move(PileType.CASCADE, 5, 2, PileType.FOUNDATION, 1);
    model.move(PileType.CASCADE, 0, 5, PileType.CASCADE, 8);
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
        + "C10: 5♦, 4♥, J♦, J♣", model.toString());
  }

  @Test
  public void testEndGameToString() {
    model.startGame(model.getDeck(), 52, 5, false);
    for (int i = 0; i < 52; i++) {
      model.move(PileType.CASCADE, i, 0, PileType.FOUNDATION, i % 4);
    }
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
        + "C52: ", model.toString());
  }

  @Test
  public void testRestartGame() {
    List<ICard> deck = model.getDeck();
    Collections.shuffle(deck, new Random(4));
    model.startGame(deck, 10, 4, false);
    model.move(PileType.CASCADE, 9, 4, PileType.FOUNDATION, 1);
    model.move(PileType.CASCADE, 5, 4, PileType.OPEN, 3);
    model.move(PileType.CASCADE, 5, 3, PileType.OPEN, 2);
    model.move(PileType.CASCADE, 5, 2, PileType.FOUNDATION, 1);
    model.move(PileType.CASCADE, 0, 5, PileType.CASCADE, 8);
    model.startGame(deck, 10, 4, false);
    assertEquals("F1:\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: 4♦, K♣, K♦, 6♥, J♠, 9♥\n"
        + "C2: 7♠, 9♦, A♥, 7♣, 9♣, J♥\n"
        + "C3: 5♥, Q♣, 5♣, Q♠, Q♥\n"
        + "C4: Q♦, A♣, 5♠, 10♣, 8♥\n"
        + "C5: 8♣, 8♠, 7♦, 7♥, A♠\n"
        + "C6: K♥, 2♣, 2♦, 4♠, 3♠\n"
        + "C7: 8♦, 2♠, 6♠, 4♣, K♠\n"
        + "C8: 3♥, 6♣, 6♦, 10♥, 10♦\n"
        + "C9: 2♥, 3♦, 9♠, 3♣, 10♠\n"
        + "C10: 5♦, 4♥, J♦, J♣, A♦", model.toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMultiMove() {
    model.startGame(model.getDeck(), 26, 2, false);
    model.move(PileType.CASCADE, 0, 1, PileType.OPEN, 0);
    model.move(PileType.CASCADE, 0, 0, PileType.OPEN, 1);
    model.move(PileType.CASCADE, 19, 1, PileType.CASCADE, 25);
    model.move(PileType.CASCADE, 25, 1, PileType.CASCADE, 0);
  }
}
