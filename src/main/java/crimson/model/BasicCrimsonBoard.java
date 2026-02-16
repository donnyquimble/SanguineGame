package crimson.model;

import java.util.ArrayList;
import java.util.List;

/**
 * The board for the Storm of Crimson game.
 * The board consists of a certain number of rows
 * and columns. Each spot is filled in with a
 * CrimsonBoardSpot. The left and right columns
 * are filled in with 1-pawn slots of Red and Blue,
 * respectively. The methods in this class enable
 * an outsider to add cards to the board, check
 * what the condition of a spot is, and calculate
 * the score of a row for either player.
 *
 */
public class BasicCrimsonBoard implements CrimsonBoard {

  private final List<List<CrimsonBoardSpot>> spots = new ArrayList<>();
  private final int rows;
  private final int cols;

  /**
   * The constructor for this board. Takes in an
   * amount of rows and columns, then creates a
   * board of the proper size, filling in the
   * left and right columns with their respective pawns
   *
   * @param rows The number of rows in the board
   * @param cols The number of columns in the board
   */
  public BasicCrimsonBoard(int rows, int cols) throws IllegalArgumentException {
    if (rows < 1 || cols < 2 || cols % 2 != 1) {
      throw new IllegalArgumentException("Rows or columns are invalid");
    }
    this.rows = rows;
    this.cols = cols;
    startGame();
  }

  private void startGame() {
    for (int row = 0; row < rows; row++) {
      spots.add(new ArrayList<>());
      for (int col = 0; col < cols; col++) {
        if (col == 0) {
          spots.get(row).add(new BasicCrimsonBoardSpot(SpotType.PAWNS, 1, 0, Color.RED));
        } else if (col == cols - 1) {
          spots.get(row).add(new BasicCrimsonBoardSpot(SpotType.PAWNS, 1, 0, Color.BLUE));
        } else {
          spots.get(row).add(new BasicCrimsonBoardSpot(SpotType.EMPTY, 0, 0, Color.BLANK));
        }
      }
    }
  }

  @Override
  public void addCard(CrimsonCard card, int row, int col)
      throws IllegalStateException, IllegalArgumentException {
    if (col < 0 || row < 0 || row > spots.size() || col > spots.get(row).size()) {
      throw new IllegalArgumentException("Cols or rows are invalid");
    }
    CrimsonBoardSpot spot = spots.get(row).get(col);
    if (spot.getColor() != card.getColor() && spot.getColor() != Color.BLANK) {
      throw new IllegalStateException("Colors do not match");
    }
    if (spot.getPawns() < card.getCost()) {
      throw new IllegalStateException("Card costs too much for the spot");
    }
    spot.placeCard(card);
    for (List<Integer> influence : card.getInfluence()) {
      int influenceCol = col + influence.get(0);
      int influenceRow = row - influence.get(1);
      if (!(influenceRow < 0 || influenceCol < 0 || influenceRow >= spots.size()
          || influenceCol >= spots.get(influenceRow).size())) {
        getSpotAt(influenceRow, influenceCol).updatePawns(1, card.getColor());
      }
    }
  }

  @Override
  public CrimsonBoardSpot getSpotAt(int row, int col) throws IllegalArgumentException {
    return spots.get(row).get(col);
  }

  @Override
  public int getRows() {
    return rows;
  }

  @Override
  public int getCols() {
    return cols;
  }

  /**
   * Calculates the score of a row for a given
   * player. Takes in the integer index of the
   * row and the color of the player to
   * calculate the score of, and returns the
   * integer value representing their power in
   * that row.
   *
   * @param row The row which the score is being
   *            calculated for
   * @param color The player color who's score is
   *              being obtained
   */
  public int calculateRowScore(int row, Color color) {
    int currentRowScore = 0;
    for (int spaceNum = 0; spaceNum < cols; spaceNum++) {
      CrimsonBoardSpot currentSpot = getSpotAt(row, spaceNum);
      if (currentSpot.getColor().equals(color)) {
        currentRowScore += getSpotAt(row, spaceNum).getPower();
      }
    }
    return currentRowScore;
  }
}