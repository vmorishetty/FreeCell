import static org.junit.Assert.assertEquals;

import cs3500.freecell.model.hw02.Card;
import cs3500.freecell.model.hw02.CardNumber;
import cs3500.freecell.model.hw02.CardSuit;
import cs3500.freecell.model.hw02.Cascade;
import cs3500.freecell.model.hw02.ICard;
import cs3500.freecell.model.hw02.IPile;
import org.junit.Test;

/**
 * Tests for the Cascade class to ensure all methods work properly.
 */
public class CascadeTest {
  IPile<ICard> cascade1 = new Cascade();

  @Test
  public void testEmptySize() {
    assertEquals(0, cascade1.getSize());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetNoCard() {
    cascade1.getCard(0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetOutOfBounds() {
    cascade1.addCard(new Card(CardNumber.EIGHT, CardSuit.CLUB));
    cascade1.getCard(5);
  }

  @Test
  public void testAddKing() {
    ICard king = new Card(CardNumber.KING, CardSuit.CLUB);
    cascade1.addCard(king);
    assertEquals(king, cascade1.getCard(0));
  }

  @Test
  public void testSizeOfOne() {
    ICard king = new Card(CardNumber.KING, CardSuit.CLUB);
    cascade1.addCard(king);
    assertEquals(1, cascade1.getSize());
  }

  @Test
  public void testAddFour() {
    ICard king = new Card(CardNumber.KING, CardSuit.CLUB);
    ICard queen = new Card(CardNumber.QUEEN, CardSuit.DIAMOND);
    ICard jack = new Card(CardNumber.JACK, CardSuit.SPADE);
    ICard ten = new Card(CardNumber.TEN, CardSuit.HEART);
    cascade1.addCard(king);
    assertEquals(1, cascade1.getSize());
    cascade1.addCard(queen);
    assertEquals(2, cascade1.getSize());
    assertEquals(queen, cascade1.getCard(1));
    assertEquals(king, cascade1.getCard(0));
    assertEquals(queen, cascade1.getTop());
    cascade1.addCard(jack);
    assertEquals(3, cascade1.getSize());
    assertEquals(jack, cascade1.getCard(2));
    assertEquals(jack, cascade1.getTop());
    cascade1.addCard(ten);
    assertEquals(4, cascade1.getSize());
    assertEquals(ten, cascade1.getCard(3));
    assertEquals(ten, cascade1.getTop());
  }

  @Test
  public void testRemoveTop() {
    ICard king = new Card(CardNumber.KING, CardSuit.CLUB);
    ICard queen = new Card(CardNumber.QUEEN, CardSuit.DIAMOND);
    ICard jack = new Card(CardNumber.JACK, CardSuit.SPADE);
    ICard ten = new Card(CardNumber.TEN, CardSuit.HEART);
    cascade1.addCard(king);
    cascade1.addCard(queen);
    cascade1.addCard(jack);
    cascade1.addCard(ten);
    assertEquals(ten, cascade1.getCard(3));
    assertEquals(jack, cascade1.getCard(2));
    assertEquals(queen, cascade1.getCard(1));
    assertEquals(king, cascade1.getCard(0));
    assertEquals(ten, cascade1.getTop());
    cascade1.removeCard(ten);
    assertEquals(jack, cascade1.getTop());
    cascade1.removeCard(jack);
    assertEquals(queen, cascade1.getTop());
    cascade1.removeCard(queen);
    assertEquals(king, cascade1.getTop());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemoveMiddle() {
    ICard king = new Card(CardNumber.KING, CardSuit.CLUB);
    ICard queen = new Card(CardNumber.QUEEN, CardSuit.DIAMOND);
    ICard jack = new Card(CardNumber.JACK, CardSuit.SPADE);
    ICard ten = new Card(CardNumber.TEN, CardSuit.HEART);
    cascade1.addCard(king);
    cascade1.addCard(queen);
    cascade1.addCard(jack);
    cascade1.addCard(ten);
    cascade1.removeCard(queen);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemoveWrongCard() {
    ICard king = new Card(CardNumber.KING, CardSuit.CLUB);
    ICard queen = new Card(CardNumber.QUEEN, CardSuit.DIAMOND);
    ICard jack = new Card(CardNumber.JACK, CardSuit.SPADE);
    ICard ten = new Card(CardNumber.TEN, CardSuit.HEART);
    cascade1.addCard(king);
    cascade1.removeCard(queen);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemoveFromEmpty() {
    ICard ten = new Card(CardNumber.TEN, CardSuit.HEART);
    cascade1.removeCard(ten);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetNoTOp() {
    cascade1.getTop();
  }

  @Test
  public void testToString() {
    ICard king = new Card(CardNumber.KING, CardSuit.CLUB);
    ICard queen = new Card(CardNumber.QUEEN, CardSuit.DIAMOND);
    ICard jack = new Card(CardNumber.JACK, CardSuit.SPADE);
    ICard ten = new Card(CardNumber.TEN, CardSuit.HEART);

    assertEquals("", cascade1.toString());
    cascade1.addCard(king);
    assertEquals("K♣", cascade1.toString());
    cascade1.addCard(queen);
    assertEquals("K♣, Q♦", cascade1.toString());
    cascade1.addCard(jack);
    assertEquals("K♣, Q♦, J♠", cascade1.toString());
    cascade1.addCard(ten);
    assertEquals("K♣, Q♦, J♠, 10♥", cascade1.toString());
  }
}