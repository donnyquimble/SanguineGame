package crimson.artificialplayer;

import crimson.model.Color;
import crimson.model.ReadOnlyCrimsonGame;

/*
 * EXAMPLE JAVADOC - REPLACE.
 */
public class TryTwoStrategy implements CrimsonStrategy {

  CrimsonStrategy first;
  CrimsonStrategy second;

  /**
   * EXAMPLE JAVADOC - REPLACE.
   */
  public TryTwoStrategy(CrimsonStrategy first, CrimsonStrategy second) {
    this.first = first;
    this.second = second;
  }

  @Override
  public StrategyMove chooseMove(ReadOnlyCrimsonGame game, Color color) {
    StrategyMove ans = this.first.chooseMove(game, color);
    if (ans != null) {
      return ans;
    }
    return this.second.chooseMove(game, color);
  }
}
