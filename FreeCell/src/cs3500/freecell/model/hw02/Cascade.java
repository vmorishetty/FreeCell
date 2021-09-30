package cs3500.freecell.model.hw02;

import java.util.ArrayList;

/**
 * Represents a cascade pile from the game Freecell.
 */
public class Cascade implements IPile<ICard> {
  private ArrayList<ICard> cascade;

  /**
   * Constructs a cascade pile.
   */
  public Cascade() {
    this.cascade = new ArrayList<ICard>();
  }


  /**
   * Adds a card to the cascade pile.
   * @param card  the card to be added to the pile
   */
  @Override
  public void addCard(ICard card) {
    cascade.add(card);

  }

  /**
   * Removes a card from the cascade pile.
   * @param card  the card to be removed from the pile
   * @throws IllegalArgumentException if the cascade pule does not contain the card or it is not the
   *                                  top card
   */
  @Override
  public void removeCard(ICard card) throws IllegalArgumentException {
    // CHANGED BECAUSE WASNT ALLOWED TO REMOVE FROM MIDDLE BUT NEEDS TO FOR MULTI MOVE
    if (!cascade.contains(card)) {
      throw new IllegalArgumentException("Can't remove card that isn't in list or not at the top");
    }
    cascade.remove(card);

  }

  /**
   * Gets the size of the cascade pile.
   * @return int
   */
  @Override
  public int getSize() {
    return cascade.size();
  }

  /**
   * Gets the card of the given index in the cascade pile.
   * @param index   the index of the pile where the card is located
   * @return  ICard
   * @throws IllegalArgumentException if there are no cards in pile or index out of range
   */
  @Override
  public ICard getCard(int index)throws IllegalArgumentException {
    if (getSize() == 0 || index >= getSize() || index < 0) {
      throw new IllegalArgumentException("Cannot get a card outside the range of Cascade");
    }
    return cascade.get(index);
  }

  /**
   * Gets the top card/last item in pile.
   * @return   ICard
   * @throws IllegalArgumentException if the pile is empty
   */
  @Override
  public ICard getTop() throws IllegalArgumentException {
    if (getSize() == 0) {
      throw new IllegalArgumentException("There is no top of empty Cascade");
    }
    return cascade.get(cascade.size() - 1);
  }

  /**
   * Formats the cascade pile into a string as Card ", ".
   * @return  String
   */
  @Override
  public String toString() {
    String list = "";
    for (int i = 0; i < cascade.size() - 1; i ++) {
      list += cascade.get(i).toString() + ", ";
    }
    if (getSize() != 0) {
      return list + getTop().toString();
    }
    return list;
  }
}
