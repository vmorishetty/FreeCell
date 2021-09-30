package cs3500.freecell.model.hw02;

/**
 * Represents a card in a deck.
 */
public class Card implements ICard {

  private final CardNumber number;
  private final CardSuit suit;

  /**
   * Constructs a card.
   *
   * @param number    the value of a card
   * @param suit      the suit of the card
   */
  public Card(CardNumber number, CardSuit suit) {
    this.number = number;
    this.suit = suit;
  }

  /**
   * Gets the number of a card.
   * @return CardNumber
   */
  @Override
  public CardNumber getNumber() {
    return number;
  }

  /**
   * Gets the suit of a card.
   * @return CardSuit
   */
  @Override
  public CardSuit getSuit() {
    return suit;
  }

  /**
   * Formats the card into a string as Number""Suit.
   * @return String
   */
  @Override
  public String toString() {
    String card = "";
    if (number.equals(CardNumber.ACE)) {
      card += "A";
    }
    else if (number.equals(CardNumber.JACK)) {
      card += "J";
    }
    else if (number.equals(CardNumber.QUEEN)) {
      card += "Q";
    }
    else if (number.equals(CardNumber.KING)) {
      card += "K";
    }
    else {
      card += String.valueOf(number.getNumberValue());
    }
    return card + suit.getSuit();
  }
}
