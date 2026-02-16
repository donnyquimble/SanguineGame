package crimson.artificialplayer;

import crimson.model.Color;
import crimson.model.CrimsonBoardSpot;
import crimson.model.CrimsonCard;
import crimson.model.ReadOnlyCrimsonGame;
import java.util.ArrayList;
import java.util.List;

/**
 * EXAMPLE JAVADOC - REPLACE.
 */
public class WinRowPowerStrategy extends IncreaseLowPowerRowStrategy implements CrimsonStrategy {

  @Override
  public StrategyMove chooseMove(ReadOnlyCrimsonGame game, Color color) {
    List<Integer> rowsUsed = new ArrayList<>();
    StrategyMove move = null;
    int powerOfMove = 0;
    for (int row = 0;  row < game.getBoard().getRows(); row++) {
      int rowIndex = getLeastPowerRow(color, game, rowsUsed);
      if (game.rowScore(color, rowIndex) < game.rowScore(game.getOppositeColor(color), rowIndex)) {
        move = getMoveForRow(game, color, rowIndex, powerOfMove);
        powerOfMove = game.getHandOfPlayer(color).get(move.getCardIndex()).getPower();
      }
      rowsUsed.add(rowIndex);
    }
    return move;
  }

}
