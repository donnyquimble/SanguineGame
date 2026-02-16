package crimson.view;

import java.io.IOException;

/**
 * This is an interface for the view of the model. Used to display the model
 * for the user.
 */
public interface CrimsonView {

  /**
   * Converts the view model to string form.
   *
   * @return - the String view of them model.
   */
  public String toString();

  /**
   * Similar to above function.
   *
   * @return - the formatted version of the model.
   * @throws IOException - if the model is invalid.
   */
  public String render() throws IOException;

  /**
   * Refreshes the GUI.
   */
  public void refresh();

  /**
   * Toggles the game's visibility.
   *
   * @param state - whether to make the game visible.
   */
  public void setVisible(boolean state);

  void addClickListener(ViewActions listener);

  void addKeyListener(ViewActions listener);
}