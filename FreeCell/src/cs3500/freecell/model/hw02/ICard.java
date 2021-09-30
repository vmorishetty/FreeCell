package cs3500.freecell.model.hw02;

/**
 * Specifies operations for different types of cards.
 */
public interface ICard {

  /**
   * Gets the number of a card.
   * @return CardNumber
   */
  CardNumber getNumber();

  /**
   * Gets the suit of a card.
   * @return CardSuit
   */
  CardSuit getSuit();
}
