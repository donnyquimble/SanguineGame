package crimson.model;

/**
 * The basic board spot for a Storm of Crimson game.
 * This spot holds what type of spot it is (EMPTY,
 * PAWNS, or CARD), what color the spot is (BLANK,
 * RED, or BLUE), how many pawns are in the spot,
 * and what the power of the spot is. This class
 * contains methods to place a card at this spot,
 * update the pawns at this spot, and get the
 * many stored variables.
 */
public class BasicCrimsonBoardSpot implements CrimsonBoardSpot {

  private int boardSpotPawnNum;
  private int boardSpotPower;
  private Color boardSpotColor;
  private SpotType boardSpotType;

  /**
   * A basic constructor for the board spot. Takes in
   * many variables and sets the global values to
   * the passed values.
   *
   * @param spotType The type of spot this will be
   * @param pawnNum The number of pawns in the spot
   * @param power The power of the spot
   * @param color The color the spot is (whether by
   *              card placement or pawns)
   */
  public BasicCrimsonBoardSpot(SpotType spotType, int pawnNum, int power, Color color) {
    this.boardSpotType = spotType;
    this.boardSpotPawnNum = pawnNum;
    this.boardSpotPower = power;
    this.boardSpotColor = color;
  }

  /**
   * IMPORTANT NOTE -- This method DOES NOT add the
   * influence to the board. This is done at the
   * board level.
   */
  @Override
  public void placeCard(CrimsonCard card) throws IllegalStateException {
    int cost = card.getCost();
    if (cost > this.getPawns()) {
      throw new IllegalStateException("Cost too great.");
    }
    if (boardSpotType != SpotType.PAWNS) {
      throw new IllegalStateException("Board spot is unavailable.");
    }
    boardSpotType = SpotType.CARD;
    boardSpotPawnNum = 0;
    boardSpotPower = card.getPower();
    boardSpotColor = card.getColor();
  }

  @Override
  public void updatePawns(int change, Color color) throws IllegalStateException,
      IllegalArgumentException {
    if (change < 0 || color == Color.BLANK) {
      throw new IllegalArgumentException("Invalid parameters");
    }
    if (boardSpotColor == Color.BLANK) {
      boardSpotColor = color;
    }
    if (boardSpotType != SpotType.CARD) {
      boardSpotType = SpotType.PAWNS;
      if (color == boardSpotColor) {
        boardSpotPawnNum += change;
      } else {
        boardSpotColor = color;
      }
    }
  }

  @Override
  public int getPower() {
    return boardSpotPower;
  }

  @Override
  public Color getColor() {
    return boardSpotColor;
  }

  @Override
  public int getPawns() {
    return boardSpotPawnNum;
  }

  @Override
  public SpotType getSpotType() {
    return boardSpotType;
  }
}
