package crimson.filescanner;

import crimson.model.BasicCrimsonCard;
import crimson.model.BasicCrimsonGame;
import crimson.model.Color;
import crimson.model.CrimsonCard;
import crimson.model.CrimsonGame;
import crimson.view.CrimsonView;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * A file reader that can read a config file
 * and return a model, list of cards, and
 * view.
 */
public class ConfigFileScanner implements ConfigReader {

  private final Scanner readerRed;
  private final Scanner readerBlue;
  private final CrimsonGame model;
  private final List<CrimsonCard> cardsRed;
  private final List<CrimsonCard> cardsBlue;

  /**
   * EXAMPLE JAVADOC - REPLACE.
   *
   * @param scannerRed SCANNER
   * @param scannerBlue SCANNER
   */
  public ConfigFileScanner(int rows, int columns, Scanner scannerRed, Scanner scannerBlue) {
    this.readerRed = scannerRed;
    if (scannerBlue != null) {
      this.readerBlue = scannerBlue;
    } else {
      this.readerBlue = scannerRed;
    }
    cardsRed = createListOfCards(readerRed);
    cardsBlue = createListOfCards(readerBlue);
    List<CrimsonCard> cardListRed = new ArrayList<>();
    cardListRed.addAll(cardsRed);
    List<CrimsonCard> cardListBlue = new ArrayList<>();
    cardListBlue.addAll(cardsBlue);
    model = new BasicCrimsonGame(rows, columns, 5, cardListRed, cardListBlue);
  }

  private List<CrimsonCard> createListOfCards(Scanner reader) {
    List<CrimsonCard> crimsonCards = new ArrayList<>();
    int lineNumberInCard = 0;
    int x = -2;
    int y = 2;
    String cardName = "Example";
    int cost = 1;
    int power = 1;
    List<List<Integer>> influence = new ArrayList<>();
    while (reader.hasNextLine()) {
      if (lineNumberInCard == 0) {
        String line = reader.nextLine();
        String[] lineList = line.split(" ");
        cardName = lineList[0];
        cost = Integer.parseInt(lineList[1]);
        power = Integer.parseInt(lineList[2]);
      } else {
        String[] influenceRow = reader.nextLine().split("");
        for (String symbol : influenceRow) {
          if (symbol.equals("I")) {
            influence.add(List.of(x, y));
          }
          x++;
        }
        x = -2;
        y--;
      }
      lineNumberInCard++;
      if (lineNumberInCard > 5) {
        CrimsonCard newCard = new BasicCrimsonCard(cardName, power, cost, influence, Color.BLANK);
        crimsonCards.add(newCard);
        cardName = "Example";
        cost = 1;
        power = 1;
        influence = new ArrayList<>();
        y = 2;
        lineNumberInCard = 0;
      }
    }
    return crimsonCards;
  }

  @Override
  public CrimsonGame getModel() {
    return this.model;
  }

  @Override
  public List<CrimsonCard> getCards(Color color) {
    if (color == Color.BLUE) {
      return cardsBlue;
    } else {
      return cardsRed;
    }
  }


}
