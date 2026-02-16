package crimson;

import crimson.artificialplayer.CheckIfInfluenceSpreadNeededStrategy;
import crimson.artificialplayer.CrimsonStrategy;
import crimson.artificialplayer.IncreaseLowPowerRowStrategy;
import crimson.artificialplayer.StrategyMove;
import crimson.artificialplayer.SweeperCrimsonStrategy;
import crimson.artificialplayer.TryTwoStrategy;
import crimson.artificialplayer.WinRowPowerStrategy;
import crimson.filescanner.ConfigFileScanner;
import crimson.gui.BasicWindow;
import crimson.model.BasicCrimsonGUIController;
import crimson.model.Color;
import crimson.model.CrimsonGame;
import crimson.model.ReadOnlyCrimsonGame;
import crimson.view.CrimsonView;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 * The main class for the game. Responsible for
 * playing through a full game of Storm of Crimson.
 */
public class Sanguine {

  /**
   * Takes in a list of arguments and passes them into
   * the scanner, then uses that scanner to find a file
   * and play through the game.
   *
   * @param args The arguments being passed into the model
   */
  public static void main(String[] args)
          throws IllegalArgumentException, IllegalStateException {
    args = new String[]{"5 7 docs/example.deck docs/demo.deck human hard"};
    String inputStream = "";
    for (String userInput : args) {
      inputStream += userInput;
    }
    String[] splitArgs = (inputStream.split(" "));
    for (String userInput : splitArgs) {
      inputStream += userInput;
    }
    if (splitArgs.length < 5) {
      System.out.println("No file path passed in");
    }
    File configRed = new File(splitArgs[2].replace('/', File.separatorChar));
    File configBlue;
    if (splitArgs.length < 6) {
      configBlue = configRed;
    } else {
      configBlue = new File(splitArgs[3].replace('/', File.separatorChar));
    }

    try (Scanner readerRed = new Scanner(configRed)) {
      try (Scanner readerBlue = new Scanner(configBlue)) {
        ConfigFileScanner configFile = new ConfigFileScanner(Integer.parseInt(splitArgs[0]),
                Integer.parseInt(splitArgs[1]), readerRed, readerBlue);
        CrimsonGame model = configFile.getModel();
        ReadOnlyCrimsonGame readModel = configFile.getModel();
        CrimsonView redView = new BasicWindow(readModel, Color.RED);
        CrimsonView blueView = new BasicWindow(readModel, Color.BLUE);
        BasicCrimsonGUIController redController = new BasicCrimsonGUIController(redView);
        BasicCrimsonGUIController blueController = new BasicCrimsonGUIController(blueView);
        redController.playGame(model);
        blueController.playGame(model);
        boolean gameOver = false;
        while (!gameOver) {
          redView.render();
          blueView.render();
          String strategyRed;
          String strategyBlue;
          if (splitArgs.length < 6) {
            strategyRed = splitArgs[3];
            strategyBlue = splitArgs[4];
          } else {
            strategyRed = splitArgs[4];
            strategyBlue = splitArgs[5];
          }
          CrimsonStrategy playedRedStrategy = null;
          CrimsonStrategy playedBlueStrategy = null;
          CrimsonStrategy easyStrategy = new TryTwoStrategy(new TryTwoStrategy(
                  new WinRowPowerStrategy(), new IncreaseLowPowerRowStrategy()),
                  new SweeperCrimsonStrategy());
          CrimsonStrategy hardStrategy = new CheckIfInfluenceSpreadNeededStrategy();

          if (!strategyRed.equalsIgnoreCase("human")) {
            if (strategyRed.equalsIgnoreCase("easy")) {
              playedRedStrategy = easyStrategy;
            } else if (strategyRed.equalsIgnoreCase("hard")) {
              playedRedStrategy = hardStrategy;
            } else {
              throw new IllegalArgumentException("Invalid Strategy.");
            }
            makeStrategy(playedRedStrategy, readModel, model, redController, Color.RED);
          }

          if (!strategyBlue.equalsIgnoreCase("human")) {
            if (strategyBlue.equalsIgnoreCase("easy")) {
              playedBlueStrategy = easyStrategy;
            } else if (strategyBlue.equalsIgnoreCase("hard")) {
              playedBlueStrategy = hardStrategy;
            } else {
              throw new IllegalArgumentException("Invalid Strategy.");
            }
            makeStrategy(playedBlueStrategy, readModel, model, blueController, Color.BLUE);
          }

          redView.refresh();
          blueView.refresh();
          gameOver = model.isGameOver();
        }
        Thread.sleep(5000);
        printEndGame(model);
      }
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("Error finding config file: " + e.getMessage());
    } catch (IOException e) {
      throw new IllegalStateException("Bad IO");
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }


  public static void makeStrategy(CrimsonStrategy strategy,
                                  ReadOnlyCrimsonGame readModel, CrimsonGame model,
                                  BasicCrimsonGUIController controller, Color playerColor) {
    StrategyMove move = strategy.chooseMove(readModel, model.getCurrentTurn());
    if (model.getCurrentTurn() == playerColor) {
      if (move == null) {
        controller.handleKeyPress();
      } else {
        controller.handleCellClick(move.row(), move.col(),
                move.handCardIndex());
      }
    }
  }

  private static void printEndGame(CrimsonGame model) {
    StringBuilder endGame = new StringBuilder();
    endGame.append("Game over! Ending scores:\n");
    int redScores = 0;
    int blueScores = 0;
    for (int row = 0; row < model.getBoard().getRows(); row++) {
      int redScore = model.rowScore(Color.RED, row);
      redScores += redScore;
      int blueScore = model.rowScore(Color.BLUE, row);
      blueScores += blueScore;
      endGame.append(String.format("Row %s Scores: Red: %d, Blue: %d.\n", row + 1,
              redScore, blueScore));
    }
    endGame.append(String.format("Final scores: Red with %d, Blue with %s.\n",
            model.playerScore(Color.RED), model.playerScore(Color.BLUE)));
    if (model.didPlayerWin() == Color.RED) {
      endGame.append("Red wins!");
    } else if (model.didPlayerWin() == Color.BLUE) {
      endGame.append("Blue wins!");
    } else {
      endGame.append("Game is a draw!");
    }
    System.out.println(endGame);
  }

}