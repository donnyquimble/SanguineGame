package crimson.model;

import crimson.view.CrimsonView;
import crimson.view.ViewActions;

@SuppressWarnings("checkstyle:AbbreviationAsWordInName")
public class BasicCrimsonGUIController implements CrimsonController, ViewActions {

  private final CrimsonView view;
  private CrimsonGame model;

  public BasicCrimsonGUIController(CrimsonView view) {
    this.view = view;
  }

  @Override
  public void playGame(CrimsonGame crimsonGame) {
    this.model = crimsonGame;
    this.view.addClickListener(this);
    this.view.addKeyListener(this);
    this.view.setVisible(true);
  }

  @Override
  public void handleCellClick(int row, int col, int cardInHand) {
    try {
      System.out.printf(
          "Attempting to place card %s at Row %s and Column %s\n", cardInHand, row, col);
      model.playCardAt(cardInHand, row, col);
      System.out.println("Attempt completed");
    } catch (IllegalArgumentException | IllegalStateException ex) {
      System.out.println(ex.getMessage());
    }
    view.refresh();
  }

  @Override
  public void handleKeyPress() {
    model.passTurn();
    view.refresh();
  }

}
