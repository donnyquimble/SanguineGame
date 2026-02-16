package crimson.artificialplayer;

import crimson.model.Color;
import crimson.model.CrimsonBoardSpot;
import crimson.model.CrimsonCard;
import crimson.model.CrimsonGame;
import crimson.model.ReadOnlyCrimsonGame;
import crimson.model.SpotType;
import java.util.List;

public class SpreadInfluenceStrategy implements CrimsonStrategy {

  @Override
  public StrategyMove chooseMove(ReadOnlyCrimsonGame game, Color color) {
    double maxScore = 0;
    StrategyMove move = null;
    for (int rowIndex = 0; rowIndex < game.getBoard().getRows(); rowIndex++) {
      for (int colIndex = 0; colIndex < game.getBoard().getCols(); colIndex++) {
        for (int card = 0; card < game.getHandOfPlayer(color).size(); card++) {
          CrimsonCard crimsonCard = game.getHandOfPlayer(color).get(card);
          if (card < game.getHandOfPlayer(color).size()
                  && game.getHandOfPlayer(color).get(card) != null) {
            if (game.isMoveLegal(card, rowIndex, colIndex, color)
                    && game.getBoard().getSpotAt(rowIndex, colIndex).getPawns()
                    <= game.getHandOfPlayer(color).get(card).getCost()) {
              double gotScore = this.getScore(maxScore, crimsonCard,
                      rowIndex, colIndex, game, color);
              if (maxScore < gotScore) {
                maxScore = gotScore;
                move = new StrategyMove(rowIndex, colIndex, card);
              }
            }
          }
        }
      }
    }
    if (move == null) {
      return new TryTwoStrategy(new TryTwoStrategy(
              new WinRowPowerStrategy(), new IncreaseLowPowerRowStrategy()),
              new SweeperCrimsonStrategy()).chooseMove(game, color);
    }
    return move;
  }

  private double getScore(double maxScore, CrimsonCard crimsonCard, int rowIndex,
                          int colIndex, ReadOnlyCrimsonGame game, Color color) {
    int tempInfluence = 0;
    int tempInfluenceIncrease = 0;
    int tempInfluenceTakeover = 0;
    for (List<Integer> influence : crimsonCard.getInfluence()) {
      if (rowIndex + influence.getFirst() >= 0 && colIndex + influence.getLast() >= 0
          && rowIndex + influence.getFirst() < game.getBoard().getRows()
              && colIndex + influence.getLast() < game.getBoard().getCols()) {
        CrimsonBoardSpot spot = game.getBoard().getSpotAt(
                rowIndex + influence.getFirst(), colIndex + influence.getLast());
        if (spot.getSpotType() == SpotType.PAWNS) {
          if (spot.getColor() == color) {
            tempInfluenceIncrease++;
          } else {
            tempInfluenceTakeover += spot.getPawns();
          }
        } else if (spot.getSpotType() == SpotType.EMPTY) {
          tempInfluence++;
        }
      }
    }
    double score = (tempInfluence * 1.5 + (tempInfluenceIncrease * 0.25) + (tempInfluenceTakeover));
    return Math.max(score, maxScore);
  }

}
