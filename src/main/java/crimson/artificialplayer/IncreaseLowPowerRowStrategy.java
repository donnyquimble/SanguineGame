package crimson.artificialplayer;

import crimson.model.Color;
import crimson.model.CrimsonBoardSpot;
import crimson.model.CrimsonCard;
import crimson.model.ReadOnlyCrimsonGame;
import java.util.ArrayList;
import java.util.List;

public class IncreaseLowPowerRowStrategy implements CrimsonStrategy {

  @Override
  public StrategyMove chooseMove(ReadOnlyCrimsonGame game, Color color) {
    List<Integer> rowsUsed = new ArrayList<>();
    StrategyMove move = null;
    int powerOfMove = 0;
    int rowIndex = getLeastPowerRow(color, game, rowsUsed);
    move = getMoveForRow(game, color, rowIndex, powerOfMove);
    return move;
  }

  int getLeastPowerRow(Color color, ReadOnlyCrimsonGame game, List<Integer> rowsUsed) {
    int leastPower = 0;
    int currentRow = 0;
    for (int row = 0; row < game.getBoard().getRows(); row++) {
      if (row == 0 || game.rowScore(color, row)
          - game.rowScore(game.getOppositeColor(color), row) < leastPower) {
        leastPower = game.rowScore(color, row);
        currentRow = row;
      }
    }
    return currentRow;
  }

  StrategyMove getMoveForRow(ReadOnlyCrimsonGame game, Color color,
                             int rowIndex, int powerOfMove) {
    int tempPower = powerOfMove;
    StrategyMove move = null;
    for (int col = 0; col < game.getBoard().getCols(); col++) {
      CrimsonBoardSpot checkSpot = game.getBoard().getSpotAt(rowIndex, col);
      int spotPawns = checkSpot.getPawns();
      for (int cardInHand = 0; cardInHand < game.getHandOfPlayer(color).size(); cardInHand++) {
        if (cardInHand < game.getHandOfPlayer(color).size()
                && game.getHandOfPlayer(color).get(cardInHand) != null) {
          CrimsonCard card = game.getHandOfPlayer(color).get(cardInHand);
          if (game.isMoveLegal(cardInHand, rowIndex, col, color) && card.getCost() <= spotPawns
                  && card.getPower() > tempPower) {
            move = new StrategyMove(rowIndex, col, cardInHand);
            tempPower = card.getPower();
          }
        }
      }
    }
    return move;
  }

}
