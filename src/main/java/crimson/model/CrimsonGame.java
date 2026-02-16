package crimson.model;

/**
 * Creates and plays through a full game of Storm of Crimson.
 * This game model will create the board, players, and spots,
 * then play through the game using an automatic card
 * placement system. It contains methods to get the many
 * variables stored in the game, get the current turn, and
 * autoplay a card.
 * Will hold the following variables to track the game:
 * CrimsonBoard board: The board that the game will use
 * CrimsonPlayer playerRed: The red player
 * CrimsonPlayer playerBlue: The blue player
 * List CrimsonCard cards: The list of cards used for the game.
 */
public interface CrimsonGame extends ReadOnlyCrimsonGame {

  /**
   * EXAMPLE JAVADOC - REPLACE.
   */
  boolean playCardAt(int playerHandIndex, int row, int col);

  /**
   * EXAMPLE JAVADOC - REPLACE.
   */
  void passTurn();

}
