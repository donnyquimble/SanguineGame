package crimson.artificialplayer;

import crimson.model.Color;
import crimson.model.CrimsonGame;
import crimson.model.ReadOnlyCrimsonGame;

/**
 * EXAMPLE JAVADOC - REPLACE.
 */
public class BasicArtificialCrimsonPlayer implements ArtificialCrimsonPlayer {

  CrimsonGame game;
  Color color;

  /**
   * EXAMPLE JAVADOC - REPLACE.
   */
  public BasicArtificialCrimsonPlayer(CrimsonGame game) {
    this.game = game;
    this.color = game.getCurrentTurn();
  }

  @Override
  public void playStrategy(CrimsonStrategy strategy) {
    ReadOnlyCrimsonGame readGame = game;
    StrategyMove move = strategy.chooseMove(readGame, color);
    if (move != null) {
      if (!game.playCardAt(move.getCardIndex(), move.row(), move.col())) {
        game.passTurn();
      } else {
        game.getPlayer(color).removeCardFromHand(move.getCardIndex());
      }
    } else {
      game.passTurn();
    }
  }

}
