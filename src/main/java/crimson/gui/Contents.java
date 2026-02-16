package crimson.gui;

import crimson.model.CrimsonBoardSpot;
import crimson.model.CrimsonCard;
import crimson.model.ReadOnlyCrimsonGame;
import crimson.model.SpotType;
import crimson.view.CrimsonTextualView;
import crimson.view.ViewActions;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;


/**
 * The contents panel. Draws the entire GUI.
 */
public class Contents extends JPanel {
  private int selectedCardIndex = -1;
  private int prevCard;
  private int selectedRow = -1;
  private int selectedCol = -1;
  private int prevRow;
  private int prevCol;
  public int boardWidth;
  public int numRows;
  public int boardHeight;
  public int numCols;
  public Graphics2D g2d;
  public ReadOnlyCrimsonGame model;
  public CrimsonTextualView view;
  public crimson.model.Color playerColor;
  int handSize;

  int panelW;
  int panelH;

  int boardX;
  int boardY;
  int boardW;
  int boardH;

  int cellW;
  int cellH;


  /**
   * Creates the contents panel and initializes the click listener.
   *
   * @param model - the game state to read off of.
   */
  public Contents(ReadOnlyCrimsonGame model, crimson.model.Color playerColor) {
    setFocusable(true);
    requestFocusInWindow();
    this.model = model;
    this.playerColor = playerColor;
  }

  /**
   * Listens for key presses. If the pressed key is 'P',
   * it passes whoever's turn it currently is.
   *
   * @param listener - the GUI listener that tracks key presses
   */
  public void addKeyListener(ViewActions listener) {
    addKeyListener(new KeyListener() {
      @Override
      public void keyTyped(KeyEvent e) {
        if (playerColor.equals(crimson.model.Color.RED)) {
          if (model.getCurrentTurn().equals(crimson.model.Color.RED)) {
            if (e.getKeyChar() == ('p')) {
              System.out.println("RED passed their turn.");
              listener.handleKeyPress();
            }
          }
        } else {
          if (model.getCurrentTurn().equals(crimson.model.Color.BLUE)) {
            if (e.getKeyChar() == ('p')) {
              System.out.println("BLUE passed their turn.");
              listener.handleKeyPress();
            }
          }
        }
      }

      @Override
      public void keyPressed(KeyEvent e) {
      }

      @Override
      public void keyReleased(KeyEvent e) {
      }
    });
  }

  /**
   * Tracks clicks on the board. Detects what square you've clicked on
   * or what card you've clicked on. Does not allow clicks on a board if it isn't
   * that player's turn.
   *
   * @param listener - the GUI listener that tracks key presses
   */
  public void addClickListener(ViewActions listener) {
    this.addMouseListener(new MouseListener() {
      @Override
      public void mouseClicked(MouseEvent e) {
        if (playerColor.equals(crimson.model.Color.RED)) {
          if (model.getCurrentTurn().equals(crimson.model.Color.RED)) {
            handleClick(e.getX(), e.getY());
            if (selectedRow > -1 && selectedCol > -1 && selectedCardIndex > -1) {
              listener.handleCellClick(selectedCol, selectedRow, selectedCardIndex);
              selectedCardIndex = -1;
              selectedCol = -1;
              selectedRow = -1;
            }
          }
        } else {
          if (model.getCurrentTurn().equals(crimson.model.Color.BLUE)) {
            handleClick(e.getX(), e.getY());
            if (selectedRow > -1 && selectedCol > -1 && selectedCardIndex > -1) {
              listener.handleCellClick(selectedCol, selectedRow, selectedCardIndex);
              selectedCardIndex = -1;
              selectedCol = -1;
              selectedRow = -1;
            }
          }
        }
      }

      /**
       * Detects if the user clicks on a board space or card,
       * and marks that respective space or card as clicked.
       *
       * @param x - the X coordinate of the user's click.
       * @param y - the Y coordinate of the user's click.
       */
      private void handleClick(int x, int y) {
        prevRow = selectedRow;
        prevCol = selectedCol;
        prevCard = selectedCardIndex;
        for (int row = 0; row < numRows; row++) {
          for (int col = 0; col < numCols; col++) {
            int x0 = cellW + (cellW * row);
            int y0 = cellH * col;

            if (x >= x0 && x < x0 + cellW && y >= y0 && y < y0 + cellH) {

              g2d.fillOval(x, y, 20, 20);
              selectedRow = row;
              selectedCol = col;
              repaint();
              return;
            }
          }
        }

        for (int i = 0; i < handSize; i++) {
          int cardX = i * panelW / handSize;
          int cardY = panelH / 2;
          int cardWidth = panelW / handSize;
          int cardHeight = panelH / 2;

          if (x >= cardX && x < cardX + cardWidth
                  && y >= cardY && y < cardY + cardHeight) {
            selectedCardIndex = i;

            repaint();
            return;
          }
        }
      }


      @Override
      public void mousePressed(MouseEvent e) {

      }

      @Override
      public void mouseReleased(MouseEvent e) {

      }

      @Override
      public void mouseEntered(MouseEvent e) {

      }

      @Override
      public void mouseExited(MouseEvent e) {

      }
    });
  }

  /**
   * Paints the board, sets up all the functions that do so.
   *
   * @param g the <code>Graphics</code> object to protect.
   */
  @Override
  public void paintComponent(Graphics g) {

    super.paintComponent(g);
    panelW = getWidth();
    panelH = getHeight();

    boardX = (int) (panelW * 0.1);
    boardY = 0;
    boardW = (int) (panelW * 0.8 * 0.9);
    boardH = (int) (panelH * 0.5);

    boardWidth = boardW;
    numRows = model.getBoard().getCols();
    boardHeight = 300 + 28;
    numCols = model.getBoard().getRows();
    cellW = boardW / numRows;
    cellH = boardH / numCols;

    g2d = (Graphics2D) g;
    makeBasicBoard();
    addScores();
    addPawns();
    addHand();
    for (int row = 0; row < numRows; row++) {
      for (int col = 0; col < numCols; col++) {
        g2d.drawRect(cellW + cellW * row, cellH * col, boardWidth / numRows, cellH);
      }
    }
  }


  /**
   * Handles creating the board as well as square selection.
   * Makes the board accordingly, accounting for scalability (the
   * board can grow and shrink with the window whilst retaining
   * its characteristics).
   * Also handles square selection. Checks if a square has been clicked and
   * highlights it in cyan. If the square is already cyan, meaning it is already
   * selected, it will deselect it, and sets the square color to whatever it should be.
   */
  public void makeBasicBoard() {
    g2d.setColor(Color.GRAY);
    g2d.fillRect(boardWidth / numRows, 0,
            (boardWidth - boardWidth * 3 / boardWidth), cellH * numCols);
    addCards();
    if (selectedCol != -1 && selectedRow != -1) {
      if ((selectedCol != prevCol) || (selectedRow != prevRow)) {
        g2d.setColor(Color.CYAN);
        g2d.fillRect(cellW + cellW * selectedRow, cellH * selectedCol, boardWidth / numRows, cellH);
      } else {
        if (model.getBoard().getSpotAt(selectedCol, selectedRow)
                .getSpotType().equals(SpotType.CARD)) {
          crimson.model.Color currentSelectionColor = model.getBoard()
                  .getSpotAt(selectedCol, selectedRow).getColor();
          if (currentSelectionColor.equals(crimson.model.Color.RED)) {
            g2d.setColor(Color.pink);
          } else if (currentSelectionColor.equals(crimson.model.Color.BLUE)) {
            g2d.setColor(Color.blue);
          } else {
            g2d.setColor(Color.gray);
          }
        } else {
          g2d.setColor(Color.gray);
        }
        g2d.fillRect(cellW + cellW * selectedRow, cellH * selectedCol, boardWidth / numRows, cellH);

        selectedCol = -1;
        selectedRow = -1;
      }
    }
    addCardScores();
    drawBoardLines();
  }

  /**
   * Draws the outlines over the squares.
   */
  public void drawBoardLines() {
    g2d.setColor(Color.black);
    for (int row = 0; row < numRows; row++) {
      for (int col = 0; col < numCols; col++) {
        g2d.drawRect(boardWidth / numRows + boardWidth / numRows * row,
                cellH * col, cellW, cellH);
      }
    }
  }


  /**
   * Adds the row scores to the board.
   * Red on the left and blue on the right.
   * Each side is calculated independently.
   */
  public void addScores() {
    int scoreX;
    int scoreY;
    g2d.setColor(Color.black);
    g2d.setFont(new Font("Ariel", Font.BOLD, 24));
    int rowScore;
    for (int i = 0; i < numCols * 2; i++) {
      if (i % 2 == 0) {
        scoreX = (int) (cellW * 0.5);
        scoreY = cellH / 2 * i + (int) (cellH * 0.5);
        rowScore = model.getBoard().calculateRowScore(i / 2, crimson.model.Color.RED);
      } else {
        scoreX = boardWidth + (int) (cellW * 1.3);
        scoreY = cellH / 2 * i;
        rowScore = model.getBoard().calculateRowScore(i / 2, crimson.model.Color.BLUE);
      }
      g2d.drawString(String.valueOf(rowScore), scoreX, scoreY);
    }
  }

  /**
   * Adds pawns to the board's display.
   * Adds different positions based on the number of
   * pawns on the board spot.
   * For example, one pawn places the pawn in the center of the square,
   * two pawns will place the pawns on the left and right of the square,
   * and three pawns will place the pawns in a triangular formation.
   */
  public void addPawns() {
    for (int row = 0; row < numRows; row++) {
      for (int col = 0; col < numCols; col++) {
        CrimsonBoardSpot currentSpot = model.getBoard().getSpotAt(col, row);
        if (currentSpot.getPawns() > 0) {
          crimson.model.Color spotColor = currentSpot.getColor();
          if (spotColor.equals(crimson.model.Color.RED)) {
            g2d.setColor(Color.pink);
          } else if (spotColor.equals(crimson.model.Color.BLUE)) {
            g2d.setColor(Color.blue);
          }

          if (currentSpot.getPawns() == 1) {
            g2d.fillOval((int) (cellW * 0.4) + cellW + cellW * row,
                    (int) (cellH * 0.4) + cellH * col, 20, 20);
          } else if (currentSpot.getPawns() == 2) {
            g2d.fillOval((int) (cellW * 0.2) + cellW + cellW * row,
                    (int) (cellH * 0.4) + cellH * col, 20, 20);
            g2d.fillOval((int) (cellW * 0.6) + cellW + cellW * row,
                    (int) (cellH * 0.4) + cellH * col, 20, 20);
          } else if (currentSpot.getPawns() >= 3) {
            g2d.fillOval((int) (cellW * 0.2) + cellW + cellW * row,
                    (int) (cellH * 0.5) + cellH * col, 20, 20);
            g2d.fillOval((int) (cellW * 0.6) + cellW + cellW * row,
                    (int) (cellH * 0.5) + cellH * col, 20, 20);
            g2d.fillOval((int) (cellW * 0.4) + cellW + cellW * row,
                    (int) (cellH * 0.2) + cellH * col, 20, 20);
          }

        }
      }
    }
  }

  /**
   * Adds the cards to the player's screen.
   * Format:
   * Card Name
   * Card Cost
   * Card Value
   * Card Influence Diagram
   */
  public void addHand() {
    int cardX;
    int cardY;
    Color cardColor;
    if (playerColor.equals(crimson.model.Color.BLUE)) {
      cardColor = Color.blue;
      handSize = model.getPlayer(playerColor).getHand().size();
    } else {
      cardColor = Color.pink;
      handSize = model.getPlayer(crimson.model.Color.RED).getHand().size();
    }

    for (int i = 0; i < handSize; i++) {
      cardX = i * panelW / handSize;
      cardY = panelH / 2;
      g2d.setColor(cardColor);
      if (selectedCardIndex != -1) {
        if (i == selectedCardIndex) {
          if (selectedCardIndex != prevCard) {
            g2d.setColor(Color.cyan);
          } else {
            selectedCardIndex = -1;
          }
        }
      }
      g2d.fillRect(cardX, cardY, panelW / handSize, panelH / 2);
      g2d.setColor(Color.black);
      g2d.drawRect(cardX, cardY, panelW / handSize, panelH / 2);

      CrimsonCard currentCard = model.getPlayer(playerColor).getHand().get(i);

      g2d.setFont(new Font("Ariel", Font.PLAIN, 18));
      g2d.drawString(currentCard.getName(), cardX + 1, cardY + 25);
      g2d.drawString("Cost: " + currentCard.getCost(), cardX + 1, cardY + 50);
      g2d.drawString("Value: " + currentCard.getPower(), cardX + 1, cardY + 75);
      addInfluence(cardX + 15, cardY + 100, handSize, currentCard);
    }
    g2d.setColor(Color.black);
    g2d.fillRect(0, panelH / 2, panelW, panelH / 100);
  }

  /**
   * Adds a diagram of a card's influence to the card's display.
   *
   * @param influenceX - the starting X coordinate of the influence diagram
   * @param influenceY - the starting Y coordinate of the influence diagram
   * @param handSize   - the amount of cards the player can hold at once
   * @param card       - the current card
   */
  public void addInfluence(int influenceX, int influenceY, int handSize, CrimsonCard card) {
    List<List<String>> influence = getInfluenceList(card);
    int influenceWidth = (boardWidth / handSize);
    int influenceHeight = (int) (panelH * 0.2);
    Color influenceColor;

    for (int row = 0; row < 5; row++) {
      for (int col = 0; col < 5; col++) {
        String currentThing = influence.get(row).get(col);
        if (currentThing.equals("X")) {
          influenceColor = Color.gray;
        } else if (currentThing.equals("C")) {
          influenceColor = Color.YELLOW;
        } else {
          influenceColor = Color.cyan;
        }
        g2d.setColor(influenceColor);
        g2d.fillRect(influenceX + (col * influenceWidth / 5),
                influenceY + (row * influenceHeight / 5), influenceWidth / 5, influenceHeight / 5);
        g2d.setColor(Color.black);
        g2d.drawRect(influenceX + (col * influenceWidth / 5),
                influenceY + (row * influenceHeight / 5), influenceWidth / 5, influenceHeight / 5);
      }
    }
  }

  /**
   * Function to transform the influence of a card into a 2d
   * array, because the single string normally returned isn't usable.
   *
   * @param card - the card whose influence you're getting.
   * @return - A 2d ArrayList containing the influence.
   */
  public List<List<String>> getInfluenceList(CrimsonCard card) {
    List<List<String>> influenceList = new ArrayList<>(List.of(
            List.of("X", "X", "X", "X", "X"),
            List.of("X", "X", "X", "X", "X"),
            List.of("X", "X", "C", "X", "X"),
            List.of("X", "X", "X", "X", "X"),
            List.of("X", "X", "X", "X", "X")));
    for (List<Integer> influence : card.getInfluence()) {
      int influenceCol = 2 - influence.get(0);
      int influenceRow = 2 - influence.get(1);
      List<String> listCopy = new ArrayList<>(influenceList.get(influenceRow));
      listCopy.set(influenceCol, "I");
      influenceList.set(influenceRow, listCopy);
    }
    for (int i = 0; i < 5; i++) {
      influenceList.set(i, influenceList.get(i).reversed());
    }
    return influenceList;
  }


  /**
   * Adds played cards to the board.
   */
  public void addCards() {
    for (int row = 0; row < numRows; row++) {
      for (int col = 0; col < numCols; col++) {
        CrimsonBoardSpot currentSpot = model.getBoard().getSpotAt(col, row);
        if (currentSpot.getSpotType().equals(SpotType.CARD)) {
          if (currentSpot.getColor().equals(crimson.model.Color.RED)) {
            g2d.setColor(Color.pink);
          } else {
            g2d.setColor(Color.blue);
          }
          g2d.fillRect(cellW + cellW * row, cellH * col, cellW, cellH);
          addCardScores();
        }
      }
    }
  }

  /**
   * Adds the score for each card on the board.
   */
  public void addCardScores() {
    for (int row = 0; row < numRows; row++) {
      for (int col = 0; col < numCols; col++) {
        CrimsonBoardSpot currentSpot = model.getBoard().getSpotAt(col, row);
        if (currentSpot.getSpotType().equals(SpotType.CARD)) {

          g2d.setFont(new Font("Ariel", Font.PLAIN, 20));
          g2d.setColor(Color.black);
          g2d.drawString(String.valueOf(currentSpot.getPower()), (int) (cellW * 0.4)
                  + cellW + cellW * row, (int) (cellH * 0.4) + cellH * col);
          if (currentSpot.getColor().equals(crimson.model.Color.RED)) {
            g2d.setColor(Color.pink);
          } else if (currentSpot.getColor().equals(crimson.model.Color.BLUE)) {
            g2d.setColor(Color.blue);
          }
        }
      }
    }
  }
}