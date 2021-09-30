package cs3500.freecell.model.hw04;

import cs3500.freecell.model.PileType;
import cs3500.freecell.model.hw02.ICard;
import cs3500.freecell.model.hw02.SimpleFreecellModel;

/**
 * Represents a Freecell model game where a multi-move can be made to make the game simpler.
 */
public class MultiMoveFreecellModel extends SimpleFreecellModel {

  /**
   * Constructs a MultiMoveFreecellModel.
   */
  public MultiMoveFreecellModel() {
    super();
  }

  @Override
  public void move(PileType source, int pileNumber, int cardIndex, PileType destination,
      int destPileNumber) throws IllegalArgumentException, IllegalStateException {

    if (!started) {
      throw new IllegalArgumentException("Game has not started");
    }
    if (source == null || destination == null) {
      throw new IllegalArgumentException("Null source types");
    }

    if (source.equals(PileType.CASCADE)) {
      int numCardToMove = 1;

      if (destination.equals(PileType.CASCADE)) {
        ICard sourceCard = getCascadeCardAt(pileNumber, cardIndex);

        if (getNumCardsInCascadePile(destPileNumber) != 0) {
          ICard destCard = getCascadeCardAt(destPileNumber,
              getNumCardsInCascadePile(destPileNumber) - 1);

          if (!sourceCard.getSuit().ifOpposite(destCard.getSuit())
              || sourceCard.getNumber().getNumberValue() + 1 != destCard.getNumber()
              .getNumberValue()) {
            throw new IllegalArgumentException("Invalid build with destination card");
          }
        }

        for (int i = cardIndex; i < getNumCardsInCascadePile(pileNumber) - 1; i++) {
          ICard top = getCascadeCardAt(pileNumber, i);
          ICard next = getCascadeCardAt(pileNumber, i + 1);
          if (top.getSuit().ifOpposite(next.getSuit()) && top.getNumber().getNumberValue() - 1
              == next.getNumber().getNumberValue()) {
            numCardToMove++;
          } else {
            throw new IllegalArgumentException("Invalid Build");
          }
        }

        int numOpenEmptyPiles = 1;
        int numCascadeEmptyPiles = 0;

        for (int x = 0; x < getNumOpenPiles(); x++) {
          if (getNumCardsInOpenPile(x) == 0) {
            numOpenEmptyPiles++;
          }
        }

        for (int y = 0; y < getNumCascadePiles(); y++) {
          if (getNumCardsInCascadePile(y) == 0) {
            numCascadeEmptyPiles++;
          }
        }

        if (numCardToMove > numOpenEmptyPiles * (int) (Math.pow(2, numCascadeEmptyPiles))) {
          throw new IllegalArgumentException("Not enough piles to move");
        }

        for (int z = cardIndex; z < getNumCardsInCascadePile(pileNumber); z++) {
          cascade.get(destPileNumber).addCard(getCascadeCardAt(pileNumber, z));
          cascade.get(pileNumber).removeCard(getCascadeCardAt(pileNumber, z));
          z--;
        }
      }
      else {
        super.move(source, pileNumber, cardIndex, destination, destPileNumber);
      }
    }

    else {
      super.move(source, pileNumber, cardIndex, destination, destPileNumber);
    }
  }
}