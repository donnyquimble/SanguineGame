package crimson.artificialplayer;

import crimson.model.Color;
import crimson.model.CrimsonBoardSpot;
import crimson.model.CrimsonCard;
import crimson.model.ReadOnlyCrimsonGame;
import crimson.model.SpotType;
import java.util.ArrayList;
import java.util.List;

public class EnsureMostPlayableStrategy implements CrimsonStrategy {

  List<List<Integer>> legalMoves = new ArrayList<>();

  @Override
  public StrategyMove chooseMove(ReadOnlyCrimsonGame game, Color color) {
    double maxScoreChange = 0;
    StrategyMove move = null;
    createLegalMoves(game);
    for (int rowIndex = 0; rowIndex < game.getBoard().getRows(); rowIndex++) {
      for (int colIndex = 0; colIndex < game.getBoard().getCols(); colIndex++) {
        for (int card = 0; card < game.getHandOfPlayer(color).size(); card++) {
          CrimsonCard crimsonCard = game.getHandOfPlayer(color).get(card);
          if (card < game.getHandOfPlayer(color).size()
                  && game.getHandOfPlayer(color).get(card) != null) {
            if (game.isMoveLegal(card, rowIndex, colIndex, color)
                    && game.getBoard().getSpotAt(rowIndex, colIndex).getPawns()
                    <= game.getHandOfPlayer(color).get(card).getCost()) {
              legalMoves.get(rowIndex).set(colIndex, legalMoves.get(rowIndex).get(colIndex) + 1);
              double gotScore = getScore(
                      maxScoreChange, crimsonCard, rowIndex, colIndex, game, color);
              if (maxScoreChange < gotScore) {
                maxScoreChange = gotScore;
                move = new StrategyMove(rowIndex, colIndex, card);
              }
            }
          }

        }
      }
    }
    if (maxScoreChange <= 0) {
      return new TryTwoStrategy(new TryTwoStrategy(
              new WinRowPowerStrategy(), new IncreaseLowPowerRowStrategy()),
              new SweeperCrimsonStrategy()).chooseMove(game, color);
    }
    return move;
  }

  private void createLegalMoves(ReadOnlyCrimsonGame game) {
    for (int rowIndex = 0; rowIndex < game.getBoard().getRows(); rowIndex++) {
      legalMoves.add(new ArrayList<>());
      for (int colIndex = 0; colIndex < game.getBoard().getCols(); colIndex++) {
        legalMoves.get(rowIndex).add(0);
      }
    }
  }

  private double getScore(double maxScore, CrimsonCard crimsonCard, int rowIndex,
                          int colIndex, ReadOnlyCrimsonGame game, Color color) {
    int tempLegalChange = 0;
    for (List<Integer> influence : crimsonCard.getInfluence()) {
      if (rowIndex + influence.getFirst() >= 0 && colIndex + influence.getLast() >= 0
          && rowIndex + influence.getFirst() < game.getBoard().getRows()
              && colIndex + influence.getLast() < game.getBoard().getCols()) {
        CrimsonBoardSpot spot = game.getBoard().getSpotAt(
                rowIndex + influence.getFirst(), colIndex + influence.getLast());
        int movesAtSpot = legalMoves.get(rowIndex + influence.getFirst())
                .get(colIndex + influence.getLast());
        int newMovesAtSpot = 0;
        for (int card = 0; card < game.getHandOfPlayer(color).size(); card++) {
          if (game.getHandOfPlayer(color).get(card).getCost() <= spot.getPawns() + 1) {
            newMovesAtSpot++;
          }
        }
        tempLegalChange += (newMovesAtSpot - movesAtSpot);
      }
      tempLegalChange -= legalMoves.get(rowIndex).get(colIndex);
    }
    return Math.max(tempLegalChange, maxScore);
  }
}


