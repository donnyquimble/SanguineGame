package crimson.model;

import java.util.List;

/**
 * The player of the game.
 * This player will be responsible for tracking
 * the hand, as well as adding cards back to the
 * hand when they are removed. This class contains
 * methods able to get the hand and color, as well
 * as remove a card from the hand.
 * Will contain the following variables for tracking
 * the game:
 * List CrimsonCard hand: The list of CrimsonCards in
 *                        the hand of this player.
 * int handSize: The size (in number of cards) the
 *               player hand should be at all times.
 * List CrimsonCard cards: The list of cards being
 *                         used to fill the hand.
 * Color color: The color of the player.
 */
public interface CrimsonPlayer {

  /**
   * Gets the hand of the player.
   *
   * @return The list of CrimsonCards representing
   *         the player's hand
   */
  List<CrimsonCard> getHand();

  /**
   * Gets the color of the player.
   *
   * @return The enum Color value representing the
   *         color of the player.
   */
  Color getColor();

  /**
   * Removes a card from the hand of the player. To
   * be used when placing cards on the board.
   */
  void removeCardFromHand(int index);
}