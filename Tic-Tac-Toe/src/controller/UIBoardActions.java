package controller;

import view.*;
import model.*;

import javafx.animation.PauseTransition;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

/**
 * This class contains utility methods for handling user interaction with the
 * user interface (UI) Tic-Tac-Toe board. It handles score keeping, turn
 * switching, player moves, notifying the user of a win or tie, and updating the
 * board with the computer's based off of tiles on the board the user(s) click.
 */
public class UIBoardActions {
	/**
	 * The user interface TicTacToe board which will get updated and display the
	 * moves of the players after every move is made. null represents that the user
	 * hasn't created a UI board yet.
	 */
	private static GridPane UIBoard = null;
	/**
	 * Defines the logic, state, and data associated with the board that will be
	 * used to update the UI board, switch turns, update scores, and notify the user
	 * of a tie or win.
	 */
	private static TicTacToeBoard board;
	/**
	 * Displays player one's symbol and score and appears green whenever it is
	 * player one's turn. It alters between green and white text fill, and gets its
	 * score updated based on user and/or computer interaction with the UI board.
	 */
	private static Label playerOneLabel;
	/**
	 * Displays player two's symbol and score and appears green whenever it is
	 * player one's turn. It alters between green and white text fill, and gets its
	 * score updated based on user and/or computer interaction with the UI board.
	 */
	private static Label playerTwoLabel;

	/**
	 * Create a GridPane as a UI board representation of the Tic-Tac-Toe board using
	 * the UIElements class, and define what to do when a user clicks a tile on the
	 * board.
	 * 
	 * @param initPlayerOneLabel label displaying the name, symbol, and score of
	 *                           player one. this label is associated with this
	 *                           instance of a UI board.
	 * @param initPlayerTwoLabel Label displaying the name, symbol, and score of
	 *                           player two. this label is associated with this
	 *                           instance of a UI board.
	 * @param initBoard          TicTacToeBoard object that contains the logic and
	 *                           game state for the Tic-Tac-Toe game. this
	 *                           TicTacToeBoad is associated with this instance of a
	 *                           UI board.
	 * @return A UI GridPane representation of the TicTacToeBoard board
	 */
	public static GridPane getUIBoard(TicTacToeBoard initBoard, Label initPlayerOneLabel, Label initPlayerTwoLabel) {
		UIBoard = UIElements.getUIBoardLayout(initBoard);
		board = initBoard;
		playerOneLabel = initPlayerOneLabel;
		playerTwoLabel = initPlayerTwoLabel;
		for (int row = 0; row < Board.BOARD_ROWS; row++) {
			for (int col = 0; col < Board.BOARD_COLS; col++) {
				Button UIBoardTile = (Button) UIBoard.getChildren().get((row * 3) + col);
				final int ROW = row;
				final int COL = col;
				// set action for when tile is clicked
				UIBoardTile.setOnAction(event -> {
					// UserPlayer object used to represent user making a move on the UI board
					UserPlayer user = new UserPlayer(initBoard.getCurrentPlayer().getSymbol());
					// GameState variable set to game state after tile is clicked by user
					GameState gameState = user.makeMove(ROW, COL, initBoard);
					// follow these steps if move made is a valid move
					if (gameState != GameState.INVALID_MOVE) {
						// copy over symbol from corresponding
						// tile on TicTacToeBoard after move is made
						UIBoardTile.setText(String.valueOf(initBoard.getTileAt(ROW, COL).getSymbol()));
						// after move is made and turn is switched, check if there is
						// a winner or a tie
						checkWinner(gameState);
						// if no win or tie happens, and the current player is a computer player player,
						// call computerTurn for the computer player to make a move
						if (initBoard.isComputerPlayer(initBoard.getCurrentPlayer())) {
							computerTurn();
						}
					}
				});

			}
		}
		return UIBoard;
	}

	/**
	 * Simulate the computer player's move on the Tic-Tac-Toe game. Update the UI
	 * GridPane board with the new computer move, and update the player labels after
	 * a move. Additionally, check if there is a winner or a tie after the move is
	 * made.
	 */
	public static void computerTurn() {
		if (UIBoard != null) {
			// disable all buttons when the computer player is going
			for (Node node : UIBoard.getChildren()) {
				if (node instanceof Button) {
					Button button = (Button) node;
					button.setDisable(true);
				}
			}
			// pause for one second (to simulate computer player thinking) and then make a
			// move
			PauseTransition pause = new PauseTransition(Duration.seconds(1));
			// execute after pause
			pause.setOnFinished(e -> {
				// ComputerPlayer object used to represent computer making a move on the UI
				// board
				ComputerPlayer compPlayer = (ComputerPlayer) board.getCurrentPlayer();
				// GameState variable set to game state after tile is clicked by computer player
				GameState gameState = compPlayer.makeMove(board);
				// coordinates {row, col} of the next move computer will make on the UI board
				int nextMove[] = compPlayer.getNextMoveMade();
				// button on UIBoard representing tile filled after computer makes its move
				Button filled = (Button) UIBoard.getChildren().get((nextMove[0] * 3) + nextMove[1]);
				// copy over symbol from corresponding
				// tile on TicTacToeBoard after move is made
				filled.setText(String.valueOf(board.getTileAt(nextMove[0], nextMove[1]).getSymbol()));
				// after move is made and turn is switched, check if there is
				// a winner or a tie
				checkWinner(gameState);

				// re-enable buttons if game state isn't a tie or a win
				for (Node node : UIBoard.getChildren()) {
					if (node instanceof Button) {
						((Button) node).setDisable(false);
					}
				}
			});
			pause.play();
		}
	}

	/**
	 * Helper method used after move is made on the board. If the current game state
	 * is a win or a tie, then create and display an appropriate end-of-game prompt.
	 * Also update the score in the label of the winning player, if there is one.
	 * 
	 * @param gameState the current GameState of the game, to determine if the game
	 *                  has ended in a win or a tie
	 */
	private static void checkWinner(GameState gameState) {
		if (gameState == GameState.WIN || gameState == GameState.TIE) {
			String result;
			if (gameState == GameState.WIN) {
				// winning result text depends on the current player (last player who went)
				// update winning player's score label by setting it to the new score
				// to match their new score before end of game prompt
				if (board.isPlayerOneTurn()) {
					result = "PLAYER ONE WINS!";
					playerOneLabel.setText("Player One (" + board.getPlayerOne().getSymbol() + "): "
							+ board.getPlayerOne().getScore());
				} else {
					result = "PLAYER TWO WINS!";
					playerTwoLabel.setText("Player Two (" + board.getPlayerTwo().getSymbol() + "): "
							+ board.getPlayerTwo().getScore());
				}

			} else {
				result = "TIE";
			}
			// display end of game prompt
			GUI.displayEndOfGamePrompt(result);
		} else {
			// if last move didn't end the game, switch the turns and update the player
			// score labels
			board.switchTurns();
			updatePlayerLabels();
		}
	}

	/**
	 * Helper method to update the color of the player labels to reflect the current
	 * player's turn.
	 */
	private static void updatePlayerLabels() {
		// change the current player's label to green
		// change the waiting player's label to white
		if (board.isPlayerOneTurn()) {
			playerOneLabel.setTextFill(Color.GREEN);
			playerTwoLabel.setTextFill(Color.WHITE);
		} else {
			playerTwoLabel.setTextFill(Color.GREEN);
			playerOneLabel.setTextFill(Color.WHITE);
		}

	}
}
