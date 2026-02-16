package crimson.model;

/**
 * The spots on the board.
 * This interface contains methods to place a card,
 * update the pawns, and get the many variables
 * stored here.
 * Will contain the following variables to track
 * the game:
 * SpotType spotType: Contains values {EMPTY, PAWNS, CARD}.
 *                    Determines the type of space this is
 *                    as it relates to the board. EMPTY is an
 *                    empty space, PAWNS is a space with a
 *                    value of single-color pawns on it, and
 *                    CARD is a space with a card on it that
 *                    gives a power. Once a space is CARD, it
 *                    cannot be modified.
 * int pawnNum: The number of pawns in the space. Always
 *              0 if the SpotType is not PAWNS
 * int power: The power of the spot as it influences the
 *            board. Always 0 if the SpotType is not CARD
 * Color color: Contains values {BLANK, RED, BLUE}. Tracks
 *              the color (A.K.A. player) of the spot
 */
public interface CrimsonBoardSpot {

  /**
   * This method will place a given card at this spot.
   * Checks if the move is valid, and if it is, changes
   * the spot accordingly.
   *
   * @param card The card being used to modify the board
   * @throws IllegalStateException If the card is
   *         unable to be placed at the given
   *         position, either because the cost is
   *         too high, or there is a card occupying
   *         the space
   */
  void placeCard(CrimsonCard card) throws IllegalStateException;

  /**
   * Updates the pawns in the spot by the passed number.
   *
   * @param change The number to modify the pawns by
   * @throws IllegalStateException If the spot is in an
   *         invalid state and the pawns cannot be
   *         modified
   * @throws IllegalArgumentException If the method
   *         attempts to add 0 pawns
   */
  void updatePawns(int change, Color color) throws IllegalStateException, IllegalArgumentException;


  /**
   * Gets the power of the board spot.
   *
   * @return The integer value representing
   *         the power of the spot
   */
  int getPower();


  /**
   * Gets the color of the spot. A spot can be
   * colored because of a card or pawns.
   *
   * @return The enum Color value representing
   *         the color of the spot
   */
  Color getColor();


  /**
   * Gets the number of pawns at the spot. This
   * represents the maximum card cost that can
   * currently be used here.
   *
   * @return The integer value representing the
   *         number of pawns in the spot
   */
  int getPawns();


  /**
   * Gets the type of spot. This can be BLANK,
   * PAWNS, or CARD.
   *
   * @return The enum SpotType value representing
   *         the type of spot this is.
   */
  SpotType getSpotType();

}
