import static org.junit.Assert.assertEquals;

import cs3500.freecell.model.hw02.CardSuit;
import org.junit.Test;

/**
 * Tests for the CardSuit enumeration class to ensure all methods work properly.
 */
public class CardSuitTest {

  CardSuit club = CardSuit.CLUB;
  CardSuit spade = CardSuit.SPADE;
  CardSuit heart = CardSuit.HEART;
  CardSuit diamond = CardSuit.DIAMOND;

  @Test
  public void testGetSuit() {
    assertEquals("♣", club.getSuit());
    assertEquals("♠", spade.getSuit());
    assertEquals("♥", heart.getSuit());
    assertEquals("♦", diamond.getSuit());
  }

  @Test
  public void testIfOpposite() {
    assertEquals(false, club.ifOpposite(spade));
    assertEquals(false, club.ifOpposite(club));
    assertEquals(false, heart.ifOpposite(diamond));
    assertEquals(false, diamond.ifOpposite(diamond));
    assertEquals(true, spade.ifOpposite(heart));
    assertEquals(true, spade.ifOpposite(diamond));
    assertEquals(true, heart.ifOpposite(spade));
    assertEquals(true, heart.ifOpposite(club));
  }
}