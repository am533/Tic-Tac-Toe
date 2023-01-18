package model;

import model.TicTacToeBoard.Tile;

/**
 * This enum represents the possible states of a Tic-Tac-Toe game: a win for one
 * of the players, a tie, a game in progress, and an invalid move. It also
 * contains two methods to check if a player has won the game or if the game
 * ended in a tie.
 */

public enum GameState {

	/**
	 * Represent the state of game in which one of the players won.
	 */
	WIN,
	/**
	 * Represent the state of game in which the game ended in a tie.
	 */
	TIE,
	/**
	 * Represent the state of game in which the game is in progress.
	 */
	IN_PROGRESS,
	/**
	 * Represent the state of game in which the move just made is not valid.
	 */
	INVALID_MOVE;

	/**
	 * Check if a player has won the game.
	 * 
	 * @param board the board to check to see if there's a winner
	 * @return True if a player has won the game, false otherwise.
	 */
	public static boolean winningMoveMade(Board board) {
		boolean won = true;

		for (int row = 0; row < Board.BOARD_ROWS; row++) {
			for (int col = 0; col < Board.BOARD_COLS; col++) {
				Tile currTile = board.getTileAt(row, col);
				// check for horizontal win whenever loop is at column one
				if (col == 0 && !currTile.isEmpty()) {
					if (currTile.equals(board.getTileAt(row, col + 1))
							&& currTile.equals(board.getTileAt(row, col + 2))) {
						return won;
					}
				}
				// check for vertical win whenever loop is at row one
				if (row == 0 && !currTile.isEmpty()) {
					if (currTile.equals(board.getTileAt(row + 1, col))
							&& currTile.equals(board.getTileAt(row + 2, col))) {
						return won;
					}
				}
				// check for diagonal win from top left whenever loop is at
				// row one and column one
				if (row == 0 && col == 0 && !currTile.isEmpty()) {
					if (currTile.equals(board.getTileAt(row + 1, col + 1))
							&& currTile.equals(board.getTileAt(row + 2, col + 2))) {
						return won;
					}
				}
				// check check for diagonal win from top right whenever loop is
				// at row one and column three
				if (row == 0 && col == 2 && !board.getTileAt(row, col).isEmpty()) {
					// if tile equals middle tile and bottom left tile, player has won
					if (currTile.equals(board.getTileAt(row + 1, col - 1))
							&& currTile.equals(board.getTileAt(row + 2, col - 2))) {
						return won;
					}
				}
			}
		}
		// return false if no such move exists
		return !won;
	}

	/**
	 * Check if two players tied with each other.
	 * 
	 * @param board board to check to see if game ended with a tie
	 * @return True If the two players tied, false otherwise.
	 */

	public static boolean isTie(Board board) {
		return !winningMoveMade(board) && board.getFilledTilesNumber() == 9;
	}

}