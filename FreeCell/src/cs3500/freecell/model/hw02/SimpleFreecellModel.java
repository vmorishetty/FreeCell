package cs3500.freecell.model.hw02;

import cs3500.freecell.model.FreecellModel;
import cs3500.freecell.model.PileType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Represents a simple model for the game Freecell that plays the game and represents the
 * functionality of the game.
 */
public class SimpleFreecellModel implements FreecellModel<ICard> {
  // changed cascade to be protected so that in multi-move model I can add and remove multiple cards
  // without having to do multiple valid single moves because that calculation would be difficult
  protected final ArrayList<IPile<ICard>> cascade;
  private final ArrayList<IPile<ICard>> open;
  private final Foundation[] foundation;
  // changed this to protected so I can throw an error in multi-move model if game hasn't started
  protected boolean started;

  /**
   * Constructs a simpleFreecellModel.
   */
  public SimpleFreecellModel() {
    this.cascade = new ArrayList<>();
    this.open = new ArrayList<>();
    this.foundation = new Foundation[4];
    this.started = false;
  }

  /**
   * Creates a deck of 52 cards.
   * @return List {@ICard}
   */
  @Override
  public List<ICard> getDeck() {
    List<ICard> cards = new ArrayList<>();
    for (CardNumber num: CardNumber.values()) {
      for (CardSuit suit: CardSuit.values()) {
        cards.add(new Card(num,suit));
      }
    }
    return cards;
  }

  /**
   * Starts the game of Freecell and places deck into cascade piles.
   * @param deck            the deck to be dealt
   * @param numCascadePiles number of cascade piles
   * @param numOpenPiles    number of open piles
   * @param shuffle         if true, shuffle the deck else deal the deck as-is
   * @throws IllegalArgumentException if number of open pile less than 1, number of cascade piles
   *                                  less than 4, and if the deck has duplicates and not 52 cards
   */
  @Override
  public void startGame(List<ICard> deck, int numCascadePiles, int numOpenPiles, boolean shuffle)
      throws IllegalArgumentException {
    if (numCascadePiles < 4 || numOpenPiles < 1) {
      throw new IllegalArgumentException("Size of game does not fit range");
    }
    cascade.clear();
    open.clear();
    Arrays.fill(foundation, null);

    for (ICard c: deck) {
      int count = 0;
      for (ICard c2: deck) {
        if (c == c2) {
          count++;
        }
        if (count > 1) {
          throw new IllegalArgumentException("Deck has duplicates");
        }
      }
    }
    if (deck.size() != 52) {
      throw new IllegalArgumentException("Deck is not 52 cards");
    }
    started = true;
    if (shuffle) {
      Collections.shuffle(deck);
    }
    for (int i = 0; i < numOpenPiles; i++) {
      open.add(new Open());
    }
    for (int x = 0; x < numCascadePiles; x++) {
      cascade.add(new Cascade());
    }
    for (int y = 0; y < deck.size(); y++) {
      cascade.get(y % numCascadePiles).addCard(deck.get(y));
    }
    for (int z = 0; z < 4; z++) {
      foundation[z] = new Foundation();
    }
  }

  /**
   * Moves cards to/from the three piles.
   * @param source         the type of the source pile see @link{PileType}
   * @param pileNumber     the pile number of the given type, starting at 0
   * @param cardIndex      the index of the card to be moved from the source
   *                       pile, starting at 0
   * @param destination    the type of the destination pile (see
   * @param destPileNumber the pile number of the given type, starting at 0
   * @throws IllegalArgumentException  if the move is not valid
   * @throws IllegalStateException  if the game has not started
   */
  @Override
  public void move(PileType source, int pileNumber, int cardIndex, PileType destination,
      int destPileNumber) throws IllegalArgumentException, IllegalStateException {
    if (!started) {
      throw new IllegalStateException("Game has not started");
    }

    if (source == null || destination == null) {
      throw new IllegalArgumentException("No null piles");
    }

    if (pileNumber < 0 || destPileNumber < 0) {
      throw new IllegalArgumentException("Negative index");
    }

    ICard sourceCard;

    if (source.equals(PileType.CASCADE)) {
      sourceCard = this.getCascadeCardAt(pileNumber, cardIndex);
      if (!sourceCard.equals(cascade.get(pileNumber).getTop())) {
        throw new IllegalArgumentException("Must remove card from top of pile");
      }
      // NEEDED TO ADD A FUNCTION CALL SO CARD DOESNT GET REMOVED DURING INVALID MOVES
      delegateDest(destination, sourceCard, destPileNumber);
      cascade.get(pileNumber).removeCard(sourceCard);
    }

    else if (source.equals(PileType.OPEN)) {
      sourceCard = this.getOpenCardAt(pileNumber);
      if (sourceCard == null) {
        throw new IllegalArgumentException("open does not contain anything");
      }
      // NEEDED TO ADD A FUNCTION CALL SO CARD DOESNT GET REMOVED DURING INVALID MOVES
      delegateDest(destination, sourceCard, destPileNumber);
      open.get(pileNumber).removeCard(sourceCard);
    }

    else if (source.equals(PileType.FOUNDATION)) {
      throw new IllegalArgumentException("Cannot remove card from a Foundation pile");
    }

    else {
      throw new IllegalArgumentException("Not one of the three Pile Types used in game");
    }
  }

  // NEEDED TO CREATE THIS FUNCTION DUE TO A BUG THAT FAILED MOVES WOULD REMOVE THE CARD FROM GAME
  /**
   * Helper function to delegate the destionation of a move.
   * @param dest      the type of the destination pile see @link{PileType}
   * @param source    the card that is being moved
   * @param destPile  the pile that the source card will be moving to
   */
  private void delegateDest(PileType dest, ICard source, int destPile) {
    if (dest.equals(PileType.CASCADE)) {
      if (destPile >= getNumCascadePiles()) {
        throw new IllegalArgumentException("Cascade out of bounds");
      }
      destCascade(source, destPile);
    }
    else if (dest.equals(PileType.OPEN)) {
      if (destPile >= getNumOpenPiles()) {
        throw new IllegalArgumentException("Open out of bounds");
      }
      open.get(destPile).addCard(source);
    }
    else if (dest.equals(PileType.FOUNDATION)) {
      if (destPile > 3) {
        throw new IllegalArgumentException("Foundation out of bounds");
      }
      destFoundation(source, destPile);
    }
    else {
      throw new IllegalArgumentException("Not one of the three Pile Types used in game");
    }
  }

  /**
   * Helper function for move that deals with a destination of cascade.
   * @param source      the type of the source pile see @link{PileType}
   * @param pileNumber   the destination pile number starting at 0
   */
  private void destCascade(ICard source, int pileNumber) {
    if (getNumCardsInCascadePile(pileNumber) != 0) {
      ICard destCard = cascade.get(pileNumber).getTop();

      if (destCard.getNumber().getNumberValue() - 1 == source.getNumber()
          .getNumberValue() && source.getSuit().ifOpposite(destCard.getSuit())) {
        cascade.get(pileNumber).addCard(source);
      }
      else {
        throw new IllegalArgumentException("Card is not of one number below and/or opposite suit");
      }
    }
    else {
      cascade.get(pileNumber).addCard(source);
    }
  }

  /**
   * Helper function for move that deals with a destination of foundation.
   * @param source      the type of the source pile see @link{PileType}
   * @param pileNumber   the destination pile number starting at 0
   */
  private void destFoundation(ICard source, int pileNumber) {
    if (source.getNumber().equals(CardNumber.ACE) && getNumCardsInFoundationPile(pileNumber) == 0) {
      foundation[pileNumber].addCard(source);


    }
    else if (getNumCardsInFoundationPile(pileNumber) == 0) {
      throw new IllegalArgumentException("Cannot add a Non-Ace card to empty Foundation Pile");
    }
    else if (getNumCardsInFoundationPile(pileNumber) != 0) {
      ICard destCard = foundation[pileNumber].getTop();

      if (destCard.getSuit().equals(source.getSuit())
          && destCard.getNumber().getNumberValue() + 1 == source.getNumber().getNumberValue()) {
        foundation[pileNumber].addCard(source);
      }
      else {
        throw new IllegalArgumentException("Card is not of same Suit and/or Number of previous");
      }
    }
  }

  /**
   * Determines if the all 52 cards are in the foundation piles.
   * @return  boolean
   */
  @Override
  public boolean isGameOver() {
    return foundation[0].getSize() + foundation[1].getSize()
        + foundation[2].getSize() + foundation[3].getSize() == 52;
  }

  /**
   * Gets how many cards are in a given foundation pile.
   * @param index the index of the foundation pile, starting at 0
   * @return  int
   * @throws IllegalArgumentException if the index is less than 0 or greater than 3
   * @throws IllegalStateException    if the game has not started
   */
  @Override
  public int getNumCardsInFoundationPile(int index)
      throws IllegalArgumentException, IllegalStateException {
    if (!started) {
      throw new IllegalStateException("Game has not started");
    }
    if (index < 0 || index > 3) {
      throw new IllegalArgumentException("Foundation Pile index out of bounds");
    }
    return foundation[index].getSize();
  }

  /**
   * Gets how many cascade piles there are and returns -1 if game has not started.
   * @return  int
   */
  @Override
  public int getNumCascadePiles() {
    if (!started) {
      return -1;
    }
    return cascade.size();
  }

  /**
   * Gets how many cards are in a given cascade pile.
   * @param index the index of the cascade pile, starting at 0
   * @return  int
   * @throws IllegalArgumentException if index is less than 0 or greater than number of
   *                                  cascade piles
   * @throws IllegalStateException    if the game has not started
   */
  @Override
  public int getNumCardsInCascadePile(int index)
      throws IllegalArgumentException, IllegalStateException {
    if (!started) {
      throw new IllegalStateException("Game has not started");
    }
    if (index < 0 || index >= this.getNumCascadePiles()) {
      throw new IllegalArgumentException("Cascade Pile index out of bounds");
    }
    return cascade.get(index).getSize();
  }

  /**
   * Gets the number of cards in a given open pile.
   * @param index the index of the open pile, starting at 0
   * @return  int
   * @throws IllegalArgumentException   if the index is less than 0 or greater than the number of
   *                                    open piles
   * @throws IllegalStateException      if the game has not started
   */
  @Override
  public int getNumCardsInOpenPile(int index)
      throws IllegalArgumentException, IllegalStateException {
    if (!started) {
      throw new IllegalStateException("Game has not started");
    }
    if (index < 0 || index >= this.getNumOpenPiles()) {
      throw new IllegalArgumentException("Open Pile index out of bounds");
    }
    return open.get(index).getSize();
  }

  /**
   * Gets the number of open piles and returns -1 if the game has not started.
   * @return  int
   */
  @Override
  public int getNumOpenPiles() {
    if (!started) {
      return -1;
    }
    return open.size();
  }

  /**
   * Gets the card at a given foundation pile and index.
   * @param pileIndex the index of the foundation pile, starting at 0
   * @param cardIndex the index of the card in the above foundation pile, starting at 0
   * @return  ICard
   * @throws IllegalArgumentException   if the pileIndex is less than 0 or greater than 3
   * @throws IllegalStateException      if the game has not started
   */
  @Override
  public ICard getFoundationCardAt(int pileIndex, int cardIndex)
      throws IllegalArgumentException, IllegalStateException {
    if (!started) {
      throw new IllegalStateException("Game has not started");
    }
    if (pileIndex < 0 || pileIndex > 3) {
      throw new IllegalArgumentException("Foundation Pile index out of bounds");
    }
    return foundation[pileIndex].getCard(cardIndex);
  }

  /**
   * Gets the card at a given cascade pile and index.
   * @param pileIndex the index of the cascade pile, starting at 0
   * @param cardIndex the index of the card in the above cascade pile, starting at 0
   * @return  ICard
   * @throws IllegalArgumentException if the pileIndex is less than 0 or greater than the number of
   *                                  cascade piles
   * @throws IllegalStateException    if the game has not started
   */
  @Override
  public ICard getCascadeCardAt(int pileIndex, int cardIndex)
      throws IllegalArgumentException, IllegalStateException {
    if (!started) {
      throw new IllegalStateException("Game has not started");
    }
    if (pileIndex < 0 || pileIndex >= this.getNumCascadePiles()) {
      throw new IllegalArgumentException("Cascade Pile index out of bounds");
    }
    return cascade.get(pileIndex).getCard(cardIndex);
  }

  /**
   * Gets the card at the given open pile.
   * @param pileIndex the index of the open pile, starting at 0
   * @return  ICard
   * @throws IllegalArgumentException if the index is less than 0 or greater than the number of
   *                                  open piles
   * @throws IllegalStateException    if the game has not started
   */
  @Override
  public ICard getOpenCardAt(int pileIndex) throws IllegalArgumentException, IllegalStateException {
    if (!started) {
      throw new IllegalStateException("Game has not started");
    }
    if (pileIndex < 0 || pileIndex >= this.getNumOpenPiles()) {
      throw new IllegalArgumentException("Open Pile index out of bounds");
    }
    return open.get(pileIndex).getCard(0);
  }

  /**
   * ToString method to format the model by foundation piles, then open pile, then cascade piles.
   * @return  String
   */
  @Override
  public String toString() throws IllegalStateException {
    if (!started) {
      return "";
    }

    String model = "";
    for (int i = 0; i < foundation.length; i++) {
      model += "F" + (i + 1) + ":" + Objects.toString(foundation[i].toString(), "") + "\n";
    }

    for (int x = 0; x < getNumOpenPiles(); x++) {
      model += "O" + (x + 1) + ":" + open.get(x).toString() + "\n";
    }

    for (int y = 0; y < getNumCascadePiles() - 1; y++) {
      model += "C" + (y + 1) + ": " + cascade.get(y).toString() + "\n";
    }
    model += "C" + getNumCascadePiles() + ": " + cascade.get(getNumCascadePiles() - 1);

    return model;
  }
}