package crimson.artificialplayer;

/**
 * EXAMPLE JAVADOC - REPLACE.
 */
public record StrategyMove(int row, int col, int handCardIndex) {

  /**
   * EXAMPLE JAVADOC - REPLACE.
   */
  @Override
  public int row() {
    return row;
  }

  /**
   * EXAMPLE JAVADOC - REPLACE.
   */
  @Override
  public int col() {
    return col;
  }

  public int getCardIndex() {
    return handCardIndex;
  }

}
