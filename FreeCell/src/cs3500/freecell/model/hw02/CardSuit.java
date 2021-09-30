package cs3500.freecell.model.hw02;

/**
 * Enumeration of a card suit for Club, Spade, Heart, Diamond.
 */
public enum CardSuit {
  CLUB("♣"), SPADE("♠"), HEART("♥"), DIAMOND("♦");
  private final String suit;

  /**
   * Constructs a card suit.
   * @param suit the suit of a card
   */
  CardSuit(String suit) {
    this.suit = suit;
  }

  /**
   * Gets the string value of a suit.
   * @return String
   */
  public String getSuit() {
    return suit;
  }

  /**
   * Determines if two suits are of opposite "color".
   * @param opposite  the suit of the card that "this" may be placed on
   * @return  boolean
   */
  public boolean ifOpposite(CardSuit opposite) {
    if (this.equals(CLUB) || this.equals(SPADE)) {
      return opposite.equals(HEART) || opposite.equals(DIAMOND);
    }
    return opposite.equals(CLUB) || opposite.equals(SPADE);
  }
}
