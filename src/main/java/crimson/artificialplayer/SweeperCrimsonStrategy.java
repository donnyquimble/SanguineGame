package crimson.artificialplayer;

import crimson.model.Color;
import crimson.model.CrimsonCard;
import crimson.model.CrimsonPlayer;
import crimson.model.ReadOnlyCrimsonGame;

/**
 * EXAMPLE JAVADOC - REPLACE.
 */
public class SweeperCrimsonStrategy implements CrimsonStrategy {

  @Override
  public StrategyMove chooseMove(ReadOnlyCrimsonGame game, Color color) {
    for (int card = 0; card < game.getHandOfPlayer(color).size(); card++) {
      for (int rowIndex = 0; rowIndex < game.getBoard().getRows(); rowIndex++) {
        for (int colIndex = 0; colIndex < game.getBoard().getCols(); colIndex++) {
          if (card < game.getHandOfPlayer(color).size()
                  && game.getHandOfPlayer(color).get(card) != null) {
            if (game.isMoveLegal(card, rowIndex, colIndex, color)) {
              return new StrategyMove(rowIndex, colIndex, card);
            }
          }
        }
      }
    }
    return null;
  }

}
