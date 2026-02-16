**Overview:**
This codebase aims to construct a playable game of Sanguine, a card game.
The game is played between two players, player red and player blue, and continues
until both of them pass their turns sequentially or the board is fully filled.

**Quick Start:**
Our game is programmed to start via command line inputs.
The way I've gone about that is through command line arguments that you directly pass in.
The format (as directed) is: 
board rows, board columns, red file path, blue file path, red strategy, blue strategy

The strategies to pick from are Human (if you want to play), Easy (easy AI), and Hard (hard AI).

For convenience, I've configured it to automatically run a default game against the AI if there are no
arguments passed in.

Example code to run:
String[] args = new String[]{"5 7 docs/example.deck docs/example.deck human easy"};
Sanguine.main(args);


**How to Play:**
You play as the red player, playing against either an AI or a second human player. You can see your cards,
as well as their stats, at the botton of your window. Each card has an INFLUENCE, a PAWN COST, and a VALUE.
The INFLUENCE is the pawns it'll place around the board, branching off of itself. The COST is the number of pawns
it needs to be placed upon. And the VALUE is the amount it'll add to your score.

Use the influence of your cards to spread pawns across the board, which you'll use to place more cards down, and get a
higher score than your opponent.

The game ends when neither player can move. Press 'P' to pass your turn if you have no further moves. If both players pass, the game ends.


**Key Components:**

The model drives the majority of the systems within the code. It has a variety of components, each one
an individual piece of the wonderful majesty that is the sanguine card game.

The view is what the user sees the model through. It takes a model and converts it into a string.


**Key Subcomponents:**

Model - CrimsonBoardSpot:
The board spots are what make up the board. Each spot can contain either a card, pawns, or nothing.
Each spot also stores its own power value, which is used to calculate the scores.

Model - CrimsonBoard:
The board is composed of rows and columns of _board spots_. The number of  rows and columns is
determined by user input. The board is responsible for adding cards to a boardspot,
and is also responsible for calculating the score of the game.

Model - CrimsonCard:
The cards are what the user actually uses to play the game. Each card has a name, a cost, a color,
a value (which we referred to as 'power' for ease of access), and an influence.
The influence of a card is the spaces within a board that it adds pawns to. The influence is stored in
a list of coordinate directions relative to the card itself.

Model - SpotType:
Stores the possible values that a boardSpot can contain:
'EMPTY', if the board spot is empty.
'PAWNS', if the board spot contains one or more pawns.
'CARD', if the board spot contains a card.

Model - Color:
Stores the possible colors that a card, player, or board spot can be:
Red, Blue, and Blank (if a board spot is empty).

View - CrimsonTextualView:
Displays the current state of the game as a string. Converts the board to string form,
then displays the current number of cards in the user's hand, as well as details about each card.
Currently displays the cards vertically, but I may switch to horizontal in future parts of the project.

gui - BasicBoard/Contents:
This is the board the player sees. It is a representation of the textual view that is given
graphics. It displays the board, scores, and cards of the player.

strategies:



**Source Organization:**

src/main/java/crimson/artificialplayer:
* ArtificialCrimsonPlayer
* BasicArtificialCrimsonPlayer
* CheckInfluenceSpreadNeededStrategy
* CrimsonStrategy
* EnsureMostPlayableStrategy
* IncreaseLowPowerRowStrategy
* SpreadInfluenceStrategy
* StrategyMove
* SweeperCrimsonStrategy
* TryTwoStrategy
* WinRowPowerStrategy

src/main/java/crimson/filescanner:
* ConfigFileScanner
* ConfigReader

src/main/java/crimson/gui:
* BasicWindow
* Contents

src/main/java/crimson/model:
* BasicCrimsonBoard
* BasicCrimsonBoardSpot
* BasicCrimsonCard
* BasicCrimsonGame
* BasicCrimsonPlayer
* Color
* CrimsonBoard
* CrimsonBoardSpot
* CrimsonCard
* CrimsonGame
* CrimsonPlayer
* SpotType

src/main/java/crimson/view:
* CrimsonTextualView
* CrimsonView
* ViewActions

java/crimson:
* Sanguine

src/test/java/crimson:
* CrimsonTests.java
                   

Part 3 Stuff:

Our GUI has been built and updated to display a board for each player at once.
It also implements several strategies that can be picked at the creation of the game.
