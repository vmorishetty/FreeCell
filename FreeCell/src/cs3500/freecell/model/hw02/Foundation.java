package cs3500.freecell.model.hw02;

import java.util.ArrayList;

/**
 * Represents a foundation pile from Freecell.
 */
public class Foundation implements IPile<ICard> {
  private ArrayList<ICard> foundation;

  /**
   * Constructs a foundation pile.
   */
  public Foundation() {
    this.foundation = new ArrayList<ICard>();
  }


  /**
   * Adds a card to the foundation pile.
   * @param card  the card to be added to the pile
   */
  @Override
  public void addCard(ICard card) {
    foundation.add(card);
  }

  /**
   * Removes a card from the foundation pile.
   * @param card  the card to be removed from the pile
   * @throws IllegalArgumentException cannot remove any card from the foundation pile
   */
  @Override
  public void removeCard(ICard card) throws IllegalArgumentException {
    throw new IllegalArgumentException("Cannot remove a card from foundation pile");
  }

  /**
   * Gets the size of a foundation pile.
   * @return int
   */
  @Override
  public int getSize() {
    return foundation.size();
  }

  /**
   * Gets the card of a foundation pile at the given index.
   * @param index   the index of the pile where the card is located
   * @return  ICard
   * @throws IllegalArgumentException if card is outside the size of foundation pile
   */
  @Override
  public ICard getCard(int index) throws IllegalArgumentException {
    if (getSize() == 0 || index >= getSize() || index < 0) {
      throw new IllegalArgumentException("Cannot get a card outside range of Foundation pile");
    }
    return foundation.get(index);
  }

  /**
   * Gets the top card/last item in foundation pile.
   * @return ICard
   * @throws IllegalArgumentException if the foundation pile is empty
   */
  @Override
  public ICard getTop() throws IllegalArgumentException {
    if (getSize() == 0) {
      throw new IllegalArgumentException("No top of empty Foundation Pile");
    }
    return foundation.get(foundation.size() - 1);
  }

  /**
   * Formats the Foundation pile into a String by Card then ",".
   * @return String
   */
  @Override
  public String toString() {
    String list = "";
    for (int i = 0; i < foundation.size() - 1; i ++) {
      list += foundation.get(i).toString() + ", ";
    }
    if (getSize() != 0) {
      return list + getTop().toString();
    }
    return list;
  }
}