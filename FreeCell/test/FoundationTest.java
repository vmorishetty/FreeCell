import static org.junit.Assert.assertEquals;

import cs3500.freecell.model.hw02.Card;
import cs3500.freecell.model.hw02.CardNumber;
import cs3500.freecell.model.hw02.CardSuit;
import cs3500.freecell.model.hw02.Foundation;
import cs3500.freecell.model.hw02.ICard;
import cs3500.freecell.model.hw02.IPile;
import org.junit.Test;

/**
 * Tests for the Foundation class to ensure all methods work properly.
 */
public class FoundationTest {
  IPile<ICard> found1 = new Foundation();

  @Test
  public void testEmptySize() {
    assertEquals(0, found1.getSize());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetNoCard() {
    found1.getCard(0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetOutOfBounds() {
    found1.addCard(new Card(CardNumber.EIGHT, CardSuit.CLUB));
    found1.getCard(3);
  }

  @Test
  public void testAddAce() {
    ICard ace = new Card(CardNumber.ACE, CardSuit.SPADE);
    found1.addCard(ace);
    assertEquals(ace, found1.getCard(0));
  }

  @Test
  public void testSizeOfOne() {
    ICard ace = new Card(CardNumber.ACE, CardSuit.SPADE);
    found1.addCard(ace);
    assertEquals(1, found1.getSize());
  }

  @Test
  public void testAddTwo() {
    ICard ace = new Card(CardNumber.ACE, CardSuit.SPADE);
    ICard two = new Card(CardNumber.TWO, CardSuit.SPADE);
    found1.addCard(ace);
    assertEquals(1, found1.getSize());
    found1.addCard(two);
    assertEquals(2, found1.getSize());
    assertEquals(two, found1.getCard(1));
    assertEquals(ace, found1.getCard(0));
    assertEquals(two, found1.getTop());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetTopOfEmpty() {
    found1.getTop();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemoveTop() {
    ICard ace = new Card(CardNumber.ACE, CardSuit.SPADE);
    ICard two = new Card(CardNumber.TWO, CardSuit.SPADE);
    ICard three = new Card(CardNumber.THREE, CardSuit.SPADE);
    found1.addCard(ace);
    found1.addCard(two);
    assertEquals(two, found1.getCard(1));
    assertEquals(ace, found1.getCard(0));
    assertEquals(two, found1.getTop());
    found1.addCard(three);
    assertEquals(three, found1.getCard(2));
    assertEquals(three, found1.getTop());
    found1.removeCard(three);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemoveMiddle() {
    ICard ace = new Card(CardNumber.ACE, CardSuit.SPADE);
    ICard two = new Card(CardNumber.TWO, CardSuit.SPADE);
    ICard three = new Card(CardNumber.THREE, CardSuit.SPADE);
    found1.addCard(ace);
    found1.addCard(two);
    found1.addCard(three);
    found1.removeCard(two);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemoveEmpty() {
    ICard ace = new Card(CardNumber.ACE, CardSuit.SPADE);
    found1.removeCard(ace);
  }

  @Test
  public void testToString() {
    ICard ace = new Card(CardNumber.ACE, CardSuit.SPADE);
    ICard two = new Card(CardNumber.TWO, CardSuit.SPADE);
    ICard three = new Card(CardNumber.THREE, CardSuit.SPADE);

    assertEquals("", found1.toString());
    found1.addCard(ace);
    assertEquals("A♠", found1.toString());
    found1.addCard(two);
    assertEquals("A♠, 2♠", found1.toString());
    found1.addCard(three);
    assertEquals("A♠, 2♠, 3♠", found1.toString());

  }
}