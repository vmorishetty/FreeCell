

package cs3500.freecell.model.hw02;

/**
 * Represents an Open Pile from the game Freecell.
 */
public class Open implements IPile<ICard> {
  private ICard open;

  /**
   * Constructs an open pile.
   */
  public Open() {
    // Does not contain anything because there is not card when initialized and a card is added
    // later and by making it null it will return the right type for certain functions
  }

  /**
   * Adds a card to the open pile.
   * @param card  the card to be added to the pile
   * @throws IllegalArgumentException if there is already a card
   */
  @Override
  public void addCard(ICard card) throws IllegalArgumentException {
    if (getSize() != 0) {
      throw new IllegalArgumentException("Open Pile cannot have more than one card");
    }
    open = card;

  }

  /**
   * Removes a card from the open pile.
   * @param card  the card to be removed from the pile
   * @throws IllegalArgumentException if there is no card to be removed
   */
  @Override
  public void removeCard(ICard card) throws IllegalArgumentException {
    if (getSize() == 0) {
      throw new IllegalArgumentException("Cannot remove from empty open pile");
    }
    open = null;

  }

  /**
   * Gets the size of an open pile, either 1 or 0.
   * @return int
   */
  @Override
  public int getSize() {
    if (open instanceof Card) {
      return 1;
    }
    return 0;
  }

  /**
   * Gets the card from the open pile.
   * @param index   the index of the pile where the card is located
   * @return  ICard
   */
  @Override
  public ICard getCard(int index) {
    return open;
  }

  /**
   * Gets the top/only card of the pile.
   * @return ICard
   */
  @Override
  public ICard getTop() {
    return getCard(0);
  }

  /**
   * Formats an open pile to String of a card.
   * @return String
   */
  @Override
  public String toString() {
    if (open instanceof ICard) {
      return open.toString();
    }
    return "";
  }
}

