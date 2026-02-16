package crimson.model;

import java.util.List;

/**
 * A basic card for the Storm of Crimson game.
 * This card holds a name, cost, power,
 * influence, and color. It contains methods
 * to get each of these many variables.
 * Otherwise, this class only serves to hold
 * the card, and contains no changing variables.
 * Creates the card using passed variables.
 *
 * @param cardName      The name of the card
 * @param cardPower     The power of the card
 * @param cardCost      The cost of the card
 * @param cardInfluence The cards influence is stored as a list of
 *                      lists of two integer values, representing
 *                      x and y distances from the card's placement.
 *                      For instance, a card's influence may look
 *                      like:
 *                      XXXXX
 *                      XXIXX
 *                      XICIX
 *                      XXIXX
 *                      XXXXX
 *                      This would translate into a list of:
 *                      ((1, 0), (-1, 0), (0, 1), (0, -1))
 *                      in any ordering. A positive x value of 1
 *                      is 1 spot to the right of the card, while
 *                      a positive y value is 1 spot up.
 * @param cardColor     The color of the card.
 */
public record BasicCrimsonCard(String cardName, int cardPower, int cardCost,
                               List<List<Integer>> cardInfluence, Color cardColor)
    implements CrimsonCard {
  @Override
  public String getName() {
    return cardName;
  }

  @Override
  public int getPower() {
    return cardPower;
  }

  @Override
  public int getCost() {
    return cardCost;
  }

  @Override
  public List<List<Integer>> getInfluence() {
    return cardInfluence;
  }

  @Override
  public Color getColor() {
    return cardColor;
  }
}
