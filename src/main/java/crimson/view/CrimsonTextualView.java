package crimson.view;

import crimson.model.Color;
import crimson.model.CrimsonBoardSpot;
import crimson.model.CrimsonCard;
import crimson.model.CrimsonGame;
import java.util.ArrayList;
import java.util.List;


/**
 * The class that displays the Crimson model,
 * formatting it into an actual readable screen.
 */
public record CrimsonTextualView(CrimsonGame model) implements CrimsonView {
  /**
   * Constructor, sets the Crimson game equal to
   * the model to be displayed.
   *
   * @param model - the current Crimson game being played.
   */
  public CrimsonTextualView {
  }

  /**
   * Displays the current game state.
   */
  @Override
  public String render() {
    return this.toString();
  }

  @Override
  public void refresh() {

  }

  @Override
  public void setVisible(boolean state) {

  }

  @Override
  public void addClickListener(ViewActions listener) {

  }

  @Override
  public void addKeyListener(ViewActions listener) {

  }

  /**
   * Displays the current state of the board.
   * If the board spot contains a card, displays 'R' or 'B' if the card is red or blue respectively.
   * If the spot is empty, displays 'X'.
   * If a spot contains pawns, displays the number of pawns on the spot.
   * At the end of each row, displays the score of the row, red on the left, blue on the right.
   *
   * @return - the formatted string containing the board.
   */
  public String displayBoard() {
    StringBuilder boardString = new StringBuilder();
    int rows = model.getBoard().getRows();
    int cols = model.getBoard().getCols();
    List<List<String>> boardList = new ArrayList<>(rows);
    for (int row = 0; row < rows; row++) {
      boardList.add(new ArrayList<>());
      for (int col = 0; col < cols; col++) {
        CrimsonBoardSpot currentSpot = model.getBoard().getSpotAt(row, col);
        switch (currentSpot.getSpotType()) {
          case EMPTY:
            boardList.get(row).add("_");
            break;
          case PAWNS:
            boardList.get(row).add("" + currentSpot.getPawns());
            break;
          case CARD:
            if (currentSpot.getColor().equals(Color.BLUE)) {
              boardList.get(row).add("B");
            } else if (currentSpot.getColor().equals(Color.RED)) {
              boardList.get(row).add("R");
            }
            break;
          default:
            throw new IllegalArgumentException();
        }
      }
      boardList.get(row).addFirst(model.getBoard().calculateRowScore(row, Color.RED) + " ");
      boardList.get(row).addLast(" " + model.getBoard().calculateRowScore(row, Color.BLUE));
    }
    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols + 2; col++) {
        boardString.append(boardList.get(row).get(col));
      }
      boardString.append("\n");
    }
    return boardString.toString();
  }

  /**
   * Formats the cards into a readable view.
   * Formatted as 'Name', 'Cost', 'Value', 'Influence'.
   *
   * @return - the formatted list of each card in the player's hand.
   */
  public String displayCards() {
    StringBuilder cardString = new StringBuilder();
    for (int i = 0; i < model.getPlayer(model.getCurrentTurn()).getHand().size(); i++) {
      CrimsonCard currentCard = model.getPlayer(model.getCurrentTurn()).getHand().get(i);
      cardString.append(currentCard.getName()).append("\nCost: ").append(currentCard.getCost())
          .append("\nValue: ").append(currentCard.getPower()).append("\n")
          .append(displayInfluence(currentCard)).append("\n\n");
    }
    return cardString.toString();
  }

  /**
   * Displays the card influence in a readable table.
   * Since the influences are stored as directions ((0, 1) is one to the right of the card),
   * this function converts that list of coordinates into a readable 5x5 table.
   *
   * @param card - the current card.
   * @return - the formatted influence list.
   */
  public String displayInfluence(CrimsonCard card) {
    StringBuilder influenceString = new StringBuilder();
    List<List<String>> influenceList = new ArrayList<>(List.of(
            List.of("X", "X", "X", "X", "X"),
            List.of("X", "X", "X", "X", "X"),
            List.of("X", "X", "C", "X", "X"),
            List.of("X", "X", "X", "X", "X"),
            List.of("X", "X", "X", "X", "X")));

    for (List<Integer> influence : card.getInfluence()) {
      int influenceCol = 2 - influence.get(0);
      int influenceRow = 2 - influence.get(1);
      try {
        List<String> listCopy = new ArrayList<>(influenceList.get(influenceRow));
        listCopy.set(influenceCol, "I");
        influenceList.set(influenceRow, listCopy);
      } catch (IndexOutOfBoundsException e) {
        throw new IllegalArgumentException("Invalid influence argument.");
      }
    }
    for (int row = 0; row < 5; row++) {
      for (int col = 0; col < 5; col++) {
        influenceString.append(influenceList.get(row).get(col));
      }
      influenceString.append("\n");
    }
    return influenceString.toString();
  }


  /**
   * ToString - displays the current game.
   *
   * @return - returns the full game, which consists of the foundation piles,
   *           the draw pile, and the deck of cards.
   */
  @Override
  public String toString() {
    return (displayBoard() + "\n" + displayCards());
  }
}