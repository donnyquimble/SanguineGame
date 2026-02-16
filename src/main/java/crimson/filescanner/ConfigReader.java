package crimson.filescanner;

import crimson.model.Color;
import crimson.model.CrimsonCard;
import crimson.model.CrimsonGame;
import crimson.view.CrimsonView;
import java.util.List;

/**
 * Will hold the following variables:
 * Scanner reader: The scanner which reads the passed file path.
 * List CrimsonCard cards: The list of cards created from the file.
 * CrimsonGame model: The game model created from the list of cards.
 * CrimsonView view: The view created from the model.
 */
public interface ConfigReader {

  /**
   * Gets the model.
   *
   * @return The CrimsonGame model
   */
  public CrimsonGame getModel();

  /**
   * Gets the list of cards.
   *
   * @return The List CrimsonCard of cards
   */
  List<CrimsonCard> getCards(Color color);


}