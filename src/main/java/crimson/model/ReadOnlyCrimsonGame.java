package crimson.model;

import java.util.List;

/**
 * EXAMPLE JAVADOC - REPLACE.
 */
public interface ReadOnlyCrimsonGame {

  /**
   * Gets the board the game is using.
   *
   * @return The CrimsonBoard representing the
   *         board of the game.
   */
  CrimsonBoard getBoard();

  /**
   * Gets a CrimsonPlayer in the game based on a
   * passed color.
   *
   * @param color The enum Color value of the
   *              player being retrieved
   * @return The CrimsonPlayer representing the
   *         player of the passed color
   */
  CrimsonPlayer getPlayer(Color color);

  /**
   * Gets the current color playing the game.
   *
   * @return The enum Color value representing whose
   *         turn it currently is
   */
  Color getCurrentTurn();

  /**
   * EXAMPLE JAVADOC - REPLACE.
   */
  List<CrimsonCard> getHandOfPlayer(Color color);

  /**
   * EXAMPLE JAVADOC - REPLACE.
   */
  boolean isMoveLegal(int card, int row, int col, Color color);

  /**
   * EXAMPLE JAVADOC - REPLACE.
   */
  int rowScore(Color color, int row);

  /**
   * EXAMPLE JAVADOC - REPLACE.
   */
  int playerScore(Color color);

  /**
   * EXAMPLE JAVADOC - REPLACE.
   */
  Color didPlayerWin();

  /**
   * EXAMPLE JAVADOC - REPLACE.
   */
  boolean isGameOver();

  /**
   * EXAMPLE JAVADOC - REPLACE.
   */
  Color getOppositeColor(Color color);

}
