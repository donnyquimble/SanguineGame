package crimson.model;

/**
 * The board that the game runs on. This board
 * contains methods able to add cards, get
 * spots, and calculate the scores of passed
 * rows.
 * Will contain the following variables to track
 * the game:
 * int rows: The number of rows in the board
 * int columns: The number of columns in the board
 * List List CrimsonBoardSpot: The 2D array of board
 *                             spots representing the
 *                             full board.
 */
public interface CrimsonBoard {

  /**
   * Adds a passed card to the board at a given
   * position, reading it's cost, effects, and
   * power, then properly applying it to the
   * board.
   *
   * @param card The card that is being passed
   *        into the board
   * @param row The row index at which the card
   *        will be placed
   * @param col The column index at which the
   *        card will be placed
   * @throws IllegalStateException If the card is
   *         unable to be placed at the given
   *         position, either because the cost is
   *         too high, or there is a card occupying
   *         the space
   * @throws IllegalArgumentException If the row,
   *         column, or card arguments are invalid
   */
  void addCard(CrimsonCard card, int row, int col)
      throws IllegalStateException, IllegalArgumentException;

  /**
   * Gets the CrimsonBoardSpot of a given position.
   *
   * @param row The row index the card will be
   *        pulled from
   * @param col The column index the card will
   *        be pulled from
   * @throws IllegalArgumentException If the row
   *         or column arguments are invalid
   */
  CrimsonBoardSpot getSpotAt(int row, int col) throws IllegalArgumentException;

  /**
   * Gets the number of rows in the board.
   *
   * @return The number of rows
   */
  int getRows();

  /**
   * Gets the number of columns in the board.
   *
   * @return The number of columns
   */
  int getCols();

  /**
   * Calculates the row score for a passed
   * player at a passed row.
   *
   * @param row The row index to calculate for
   * @param color The color of the player whose
   *              score is being retrieved
   * @return The integer value representing the
   *         power of that row for the passed
   *         player
   */
  int calculateRowScore(int row, Color color);
}
