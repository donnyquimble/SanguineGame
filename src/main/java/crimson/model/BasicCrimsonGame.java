package crimson.model;

import crimson.view.CrimsonTextualView;
import crimson.view.CrimsonView;
import java.util.ArrayList;
import java.util.List;

/**
 * The main model for the Storm of Crimson game.
 * This model holds every other class needed to
 * run a full game. It holds two players of each
 * color, a board, the current turn (represented
 * by the color of the player currently moving),
 * and the list of cards to hand out to each
 * player. This class contains methods to get the
 * many variables and autoplay a card using a
 * rudimentary system.
 */
public class BasicCrimsonGame implements CrimsonGame {

  //INVARIANT: The number of rows and columns in the board does not change

  private Color currentTurn = Color.RED;
  private final CrimsonBoard board;
  private final CrimsonPlayer playerRed;
  private final CrimsonPlayer playerBlue;
  private int consecPasses = 0;

  /**
   * The basic constructor for the game. Takes in
   * variables, then checks to ensure there are
   * enough given cards to fill the board and that
   * the number of cards is not less than the hand
   * size. Deals each player the cards they need.
   *
   * @param rows The number of rows in the board
   * @param cols The number of columns in the board
   * @param handSize The size of each players' hand
   * @param cardsRed The list of cards being used for
   *                 the red player
   * @param cardsBlue The list of cards being used for
   *                  the blue player
   * @throws IllegalArgumentException If there aren't
   *         enough cards, or if the hand size is too
   *         large
   */
  public BasicCrimsonGame(int rows, int cols, int handSize, List<CrimsonCard> cardsRed,
                          List<CrimsonCard> cardsBlue) throws IllegalArgumentException {
    this.board = new BasicCrimsonBoard(rows, cols);
    if (cardsRed.size() < 15) {
      throw new IllegalArgumentException("Not enough cards for red");
    }
    if (handSize > cardsRed.size()) {
      throw new IllegalArgumentException("Cannot have less cards than the hand size!");
    }
    playerRed = dealCardsToPlayer(cardsRed, Color.RED, handSize);
    if (cardsBlue != null) {
      if (cardsBlue.size() < 15) {
        throw new IllegalArgumentException("Not enough cards for blue");
      }
      if (handSize > cardsBlue.size()) {
        throw new IllegalArgumentException("Cannot have less cards than the hand size!");
      }
      playerBlue = dealCardsToPlayer(cardsBlue, Color.BLUE, handSize);
    } else {
      playerBlue = dealCardsToPlayer(cardsRed, Color.BLUE, handSize);
    }


  }

  private CrimsonPlayer dealCardsToPlayer(List<CrimsonCard> cards, Color playerColor,
                                          int handSize) {
    List<CrimsonCard> retCards = new ArrayList<>();
    for (CrimsonCard card : cards) {
      switch (playerColor) {
        case BLUE:
          List<List<Integer>> newInfluence = new ArrayList<>();
          for (List<Integer> influence : card.getInfluence()) {
            newInfluence.add(new ArrayList<>(List.of(-(influence.get(0)), influence.get(1))));
          }
          retCards.add(new BasicCrimsonCard(card.getName(), card.getPower(), card.getCost(),
                  newInfluence, playerColor));
          break;
        case RED:
        default:
          retCards.add(new BasicCrimsonCard(card.getName(), card.getPower(), card.getCost(),
                  card.getInfluence(), playerColor));
          break;
      }
    }
    return new BasicCrimsonPlayer(retCards, handSize, playerColor);
  }

  @Override
  public CrimsonBoard getBoard() {
    return board;
  }

  @Override
  public CrimsonPlayer getPlayer(Color color) {
    if (playerRed.getColor().equals(color)) {
      return playerRed;
    } else {
      return playerBlue;
    }
  }

  @Override
  public Color getCurrentTurn() {
    return currentTurn;
  }

  @Override
  public List<CrimsonCard> getHandOfPlayer(Color color) {
    CrimsonPlayer player = getPlayer(color);
    return player.getHand();
  }

  @Override
  public boolean isMoveLegal(int card, int row, int col, Color color) {
    CrimsonBoardSpot spot = board.getSpotAt(row, col);
    try {
      CrimsonCard crimsonCard = getHandOfPlayer(color).get(card);
      return (spot.getSpotType() == SpotType.PAWNS)
              && (spot.getColor() == crimsonCard.getColor() || spot.getColor() == Color.BLANK)
              && (spot.getPawns() >= crimsonCard.getCost());
    } catch (NullPointerException ex) {
      throw new IllegalArgumentException(String.format("Card was null! "
              + "Size of player hand was %s, card attempted to grab was %s, card in list was %s, "
              + "the players hand was %s", getPlayer(color).getHand().size(), card,
              getHandOfPlayer(color).get(card), getHandOfPlayer(color)));
    }

  }

  @Override
  public int rowScore(Color color, int row) {
    int currentRowScore = 0;
    for (int spaceNum = 0; spaceNum < board.getCols(); spaceNum++) {
      CrimsonBoardSpot currentSpot = board.getSpotAt(row, spaceNum);
      if (currentSpot.getColor().equals(color)) {
        currentRowScore += board.getSpotAt(row, spaceNum).getPower();
      }
    }
    return currentRowScore;
  }

  @Override
  public int playerScore(Color color) {
    int currentPlayerScore = 0;
    for (int row = 0; row < board.getRows(); row++) {
      Color opposingColor;
      if (color == Color.RED) {
        opposingColor = Color.BLUE;
      } else {
        opposingColor = Color.RED;
      }
      int opposingRowScore = rowScore(opposingColor, row);
      int currentRowScore = rowScore(color, row);
      if (currentRowScore > opposingRowScore) {
        currentPlayerScore += currentRowScore;
      }
    }
    return currentPlayerScore;
  }

  @Override
  public Color didPlayerWin() {
    if (isGameOver()) {
      int redScore = playerScore(Color.RED);
      int blueScore = playerScore(Color.BLUE);
      if (redScore > blueScore) {
        return Color.RED;
      } else if (blueScore > redScore) {
        return Color.BLUE;
      }
    }
    return Color.BLANK;
  }

  @Override
  public boolean isGameOver() {
    return consecPasses >= 2;
  }

  private void updateTurn(boolean passed) {
    if (currentTurn.equals(Color.RED)) {
      currentTurn = Color.BLUE;
    } else {
      currentTurn = Color.RED;
    }
    if (!passed) {
      consecPasses = 0;
    } else {
      consecPasses++;
    }
  }

  /**
   * EXAMPLE JAVADOC - REPLACE.
   */
  @Override
  public boolean playCardAt(int playerHandIndex, int row, int col) {
    if (isMoveLegal(playerHandIndex, row, col, getCurrentTurn())) {
      board.addCard(getPlayer(getCurrentTurn()).getHand().get(playerHandIndex), row, col);
      getPlayer(getCurrentTurn()).removeCardFromHand(playerHandIndex);
      updateTurn(false);
      return true;
    }
    return false;
  }


  @Override
  public void passTurn() {
    this.updateTurn(true);
  }

  private StringBuilder appendCardDetails(CrimsonPlayer player, CrimsonCard card,
                                          int rowIndex, int colIndex) {
    StringBuilder ret = new StringBuilder();
    ret.append(String.format("Played %s %s at space (Row: %s, Column: %s)\n",
            player.getColor().toString().charAt(0)
                    + player.getColor().toString().substring(1).toLowerCase(),
            card.getName(), rowIndex + 1, colIndex + 1));
    ret.append(String.format("""
                This move changed the board to the following:
                %s
                """, displayBoardOnly()));
    ret.append(String.format("Removing %s from %s player hand\n",
            card.getName(), player.getColor().toString().charAt(0)
                    + player.getColor().toString().substring(1).toLowerCase()));
    return ret;
  }

  /**
   * EXAMPLE JAVADOC - REPLACE.
   */
  @Override
  public Color getOppositeColor(Color color) {
    if (color == Color.RED) {
      return Color.RED;
    } else if (color == Color.BLUE) {
      return Color.BLUE;
    } else {
      return Color.BLANK;
    }
  }

  /**
   * This method was only created to make the
   * sequence of moves easier to read through
   * the command line. It is entirely separate
   * from the model.
   *
   * @return The string of the board by itself
   */
  private String displayBoardOnly() {
    CrimsonView view = new CrimsonTextualView(this);
    String[] text = view.toString().split("\n");
    StringBuilder boardString = new StringBuilder();
    for (int index = 0; index < board.getRows(); index++) {
      boardString.append(text[index]).append("\n");
    }
    return boardString.toString();
  }
}
