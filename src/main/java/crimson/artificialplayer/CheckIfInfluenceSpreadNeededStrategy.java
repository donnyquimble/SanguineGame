package crimson.artificialplayer;

import crimson.model.Color;
import crimson.model.ReadOnlyCrimsonGame;
import crimson.model.SpotType;

public class CheckIfInfluenceSpreadNeededStrategy implements CrimsonStrategy {

  @Override
  public StrategyMove chooseMove(ReadOnlyCrimsonGame game, Color color) {
    int spacesWithPawns = 0;
    int legalMoves = 0;
    for (int rowIndex = 0; rowIndex < game.getBoard().getRows(); rowIndex++) {
      for (int colIndex = 0; colIndex < game.getBoard().getCols(); colIndex++) {
        if (game.getBoard().getSpotAt(rowIndex, colIndex).getColor() == color
            && game.getBoard().getSpotAt(rowIndex, colIndex).getSpotType() == SpotType.PAWNS) {
          spacesWithPawns++;
        }
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
    if (spacesWithPawns > game.getBoard().getRows() + 1
        && legalMoves >= game.getHandOfPlayer(color).size() / 2) {
      return new TryTwoStrategy(new TryTwoStrategy(
          new WinRowPowerStrategy(), new IncreaseLowPowerRowStrategy()),
          new SweeperCrimsonStrategy()).chooseMove(game, color);
    } else {
      return new SpreadBasedOnCardsStrategy().chooseMove(game, color);
    }
  }
}
