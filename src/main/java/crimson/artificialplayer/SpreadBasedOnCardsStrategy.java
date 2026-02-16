package crimson.artificialplayer;

import crimson.model.Color;
import crimson.model.ReadOnlyCrimsonGame;

public class SpreadBasedOnCardsStrategy implements CrimsonStrategy {

  @Override
  public StrategyMove chooseMove(ReadOnlyCrimsonGame game, Color color) {
    int legalMoves = 0;
    for (int rowIndex = 0; rowIndex < game.getBoard().getRows(); rowIndex++) {
      for (int colIndex = 0; colIndex < game.getBoard().getCols(); colIndex++) {
        for (int card = 0; card < game.getHandOfPlayer(color).size(); card++) {
          if (card < game.getHandOfPlayer(color).size()
                  && game.getHandOfPlayer(color).get(card) != null) {
            if (game.isMoveLegal(card, rowIndex, colIndex, color)
                    && game.getBoard().getSpotAt(rowIndex, colIndex).getPawns()
                    <= game.getHandOfPlayer(color).get(card).getCost()) {
              legalMoves++;
            }
          }
        }
      }
    }
    if (legalMoves >= game.getHandOfPlayer(color).size() / 2
            || legalMoves >= game.getBoard().getRows()) {
      return new SpreadInfluenceStrategy().chooseMove(game, color);
    } else {
      return new EnsureMostPlayableStrategy().chooseMove(game, color);
    }
  }

}