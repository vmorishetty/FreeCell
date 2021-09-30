package cs3500.freecell.model.hw02;

/**
 * Specifies operations for different types of piles.
 * @param <T> generic data type
 */
public interface IPile<T> {

  /**
   * Adds a card to the pile.
   * @param card  the card to be added to the pile
   */
  void addCard(ICard card);

  /**
   * Removes a card from the pile.
   * @param card  the card to be removed from the pile
   */
  void removeCard(ICard card);

  /**
   * Gets the size of pile.
   * @return  int
   */
  int getSize();

  /**
   * Gets the card of a pile at a given index.
   * @param index   the index of the pile where the card is located
   * @return  ICard
   */
  ICard getCard(int index);

  /**
   * Gets the top card, or last item of a pile.
   * @return  ICard
   */
  ICard getTop();
}
