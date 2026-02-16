package crimson.model;

import java.util.List;

/**
 * The cards used to play the game. These cards
 * contain methods able to get the many variables
 * stored here. These cards are completely
 * unchanging post-construction.
 * Will contain the following variables to track
 * the game:
 * String name: The name of the card
 * int power: The power (AKA value) of the card.
 *            This represents how much the card
 *            will influence the ending score of
 *            the row it is placed on.
 * int cost: The cost of the card. This represents
 *           how many pawns must exist on the spot
 *           the card is placed at for a successful
 *           placement.
 * List List Integer influence: The influence of the
 *                              card. This represents
 *                              the spots around the
 *                              card whose pawns change
 *                              when the card is placed.
 * Color color: The color of the card. This represents
 *              which player the card belongs to.
 */
public interface CrimsonCard {


  /**
   * Gets the name of the card.
   *
   * @return The String representing the name
   *         of the card
   */
  String getName();

  /**
   * Gets the power of the card.
   *
   * @return The integer value representing the
   *         power of the card
   */
  int getPower();

  /**
   * Gets the cost of the card.
   *
   * @return The integer value representing the
   *         cost of the card
   */
  int getCost();

  /**
   * Gets the influence of the card.
   *
   * @return The 2D Array of integer values
   *         representing the influence of the
   *         card
   */
  List<List<Integer>> getInfluence();

  /**
   * Gets the color of the card.
   *
   * @return The enum Color value representing
   *         the color of the card
   */
  Color getColor();
}
