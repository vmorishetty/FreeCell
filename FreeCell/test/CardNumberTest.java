import static org.junit.Assert.assertEquals;

import cs3500.freecell.model.hw02.CardNumber;
import org.junit.Test;

/**
 * Tests for the CardNumber enumeration to ensure all methods work properly.
 */
public class CardNumberTest {
  CardNumber ace = CardNumber.ACE;
  CardNumber seven = CardNumber.SEVEN;
  CardNumber queen = CardNumber.QUEEN;

  @Test
  public void testGetNumberValue() {
    assertEquals(1, ace.getNumberValue());
    assertEquals(7, seven.getNumberValue());
    assertEquals(12, queen.getNumberValue());

  }

}