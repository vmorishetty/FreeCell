import static org.junit.Assert.assertEquals;

import cs3500.freecell.model.hw02.Card;
import cs3500.freecell.model.hw02.CardNumber;
import cs3500.freecell.model.hw02.CardSuit;
import cs3500.freecell.model.hw02.ICard;
import org.junit.Test;

/**
 * Tests for the Card class to ensure all methods work properly.
 */
public class CardTest {

  ICard aceSpades = new Card(CardNumber.ACE, CardSuit.SPADE);
  ICard fourClubs = new Card(CardNumber.FOUR, CardSuit.CLUB);
  ICard jackHeart = new Card(CardNumber.JACK, CardSuit.HEART);
  ICard kingDiamond = new Card(CardNumber.KING, CardSuit.DIAMOND);

  @Test
  public void testGetNumber() {
    assertEquals(CardNumber.ACE, aceSpades.getNumber());
    assertEquals(CardNumber.FOUR, fourClubs.getNumber());
    assertEquals(CardNumber.JACK, jackHeart.getNumber());
    assertEquals(CardNumber.KING, kingDiamond.getNumber());
  }

  @Test
  public void testGetSuit() {
    assertEquals(CardSuit.HEART, jackHeart.getSuit());
    assertEquals(CardSuit.CLUB, fourClubs.getSuit());
    assertEquals(CardSuit.SPADE, aceSpades.getSuit());
    assertEquals(CardSuit.DIAMOND, kingDiamond.getSuit());
  }

  @Test
  public void testToString() {
    assertEquals("J♥", jackHeart.toString());
    assertEquals("4♣", fourClubs.toString());
    assertEquals("A♠", aceSpades.toString());
    assertEquals("K♦", kingDiamond.toString());

  }


}