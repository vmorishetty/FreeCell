package cs3500.freecell.model.hw02;

/**
 * Enumeration of card number of Ace, 2-10, Jack, Queen, King.
 */
public enum CardNumber {
  ACE(1), TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7),
  EIGHT(8), NINE(9), TEN(10), JACK(11), QUEEN(12), KING(13);
  private final int number;

  /**
   * Constructs a card number.
   * @param number the number value of a card
   */
  CardNumber(int number) {
    this.number = number;
  }

  /**
   * gets the numerical value of a card.
   * @return int
   */
  public int getNumberValue() {
    return number;
  }
}
