import static org.junit.Assert.assertEquals;

import cs3500.freecell.model.FreecellModel;
import cs3500.freecell.model.FreecellModelCreator;
import cs3500.freecell.model.FreecellModelCreator.GameType;
import cs3500.freecell.model.hw02.ICard;
import cs3500.freecell.model.hw02.SimpleFreecellModel;
import cs3500.freecell.model.hw04.MultiMoveFreecellModel;
import org.junit.Test;

/**
 * Tests for the FreecellModelCreater class to ensure all methods work properly.
 */
public class FreecellModelCreatorTest {

  @Test
  public void testCreateSimple() {
    FreecellModel<ICard> model = FreecellModelCreator.create(GameType.SINGLEMOVE);
    assertEquals(true, model instanceof SimpleFreecellModel);
  }

  @Test
  public void testCreateMultiMove() {
    FreecellModel<ICard> model = FreecellModelCreator.create(GameType.MULTIMOVE);
    assertEquals(true, model instanceof MultiMoveFreecellModel);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCreateNull() {
    FreecellModel<ICard> model = FreecellModelCreator.create(null);
  }



}