package crimson.gui;

import crimson.model.Color;
import crimson.model.ReadOnlyCrimsonGame;
import crimson.view.CrimsonView;
import crimson.view.ViewActions;
import java.io.IOException;
import javax.swing.JFrame;

/**
 * Initializes the GUI panel.
 */
public class BasicWindow extends JFrame implements CrimsonView {

  Contents contentModel;

  @Override
  public String render() throws IOException {
    return "";
  }

  @Override
  public void refresh() {
    this.repaint();
  }


  public static ReadOnlyCrimsonGame model;

  /**
   * Creates and initializes the window.
   *
   * @param model - the game state.
   */
  public BasicWindow(ReadOnlyCrimsonGame model, Color playerColor) {
    contentModel = new Contents(model, playerColor);
    super.setTitle("Red Player");
    super.setSize(700, 700);
    super.setResizable(true);
    super.add(contentModel);
    super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    if (playerColor.equals(Color.BLUE)) {
      super.setTitle("Blue Player");
      super.setLocation(750, 0);
    }
  }


  public static ReadOnlyCrimsonGame getModel() {
    return model;
  }

  /**
   * Sets the board to visible.
   */
  public void setVisible(boolean state) {
    super.setVisible(state);
  }

  @Override
  public void addClickListener(ViewActions listener) {
    contentModel.addClickListener(listener);
  }

  @Override
  public void addKeyListener(ViewActions listener) {
    contentModel.addKeyListener(listener);
  }


}