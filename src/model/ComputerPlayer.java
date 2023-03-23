package model;

/**
 * This class represents a computer player player in a Tic-Tac-Toe game and
 * extends the UserPlayer class. The computer player uses a five step algorithm
 * to make its moves on the board: 1. Try to fill in tile to win the game. 2. If
 * no tile exists, fill in tile blocking user from winning the game. 3. If no
 * tile exists, fill in tile to get in two in a row. 4. If no tile exists, fill
 * in tile blocking user from getting two in a row. 5. If none of these moves
 * exist, place symbol on a random empty tile. The class overloads the
 * makeMove() method to implement the algorithm.
 */
public class ComputerPlayer extends UserPlayer {
	/**
	 * The coordinates {row, column} of the next move computer player will make. A
	 * computer move is represented by integer arrays of length two.
	 */
	private int[] nextMove;

	/**
	 * Construct a computer player player by initializing its symbol and movesMade
	 * fields.
	 * 
	 * @param symbol the symbol for the player
	 */
	public ComputerPlayer(char symbol) {
		super(symbol);
		// null represents no next move
		nextMove = null;
	}

	/**
	 * Get the next move that the computer player player will make.
	 * 
	 * @return The next move that the computer player player will make will make, or
	 *         null if computer player has no next move.
	 */
	public int[] getNextMoveMade() {
		return nextMove;
	}

	/**
	 * Make the first move of the game. Either fill up a random empty tile, or try
	 * to block user from getting two in a row if possible
	 * 
	 * @param board the board to make the move on
	 * @return The state of the game after the move.
	 */
	public GameState firstMove(Board board) {
		// will make one of two moves randomly in the first turn
		int min = 1, max = 2,
				// formula to generate random number between range inclusive
				random = (int) Math.floor(Math.random() * (max - min + 1) + min);

		switch (random) {
		// try to block user from getting two in a row if random number is 1
		case 1:
			// only block user if their symbol is on the board
			if (board.getFilledTilesNumber() == 1) {
				// get move by calling getTwoInARow with the user symbol
				nextMove = getTwoInARow(board.getWaitingPlayer().getSymbol(), board);
				return makeMove(nextMove[0], nextMove[1], board);
			}
		default:
			// otherwise put symbol on a random empty tile
			return fillRandomTile(board);
		}
	}

	/**
	 * Make a move on the board after the first turn by following the specified
	 * algorithm.
	 * 
	 * @param board the board to make the move on
	 * @return The state of the game after the move.
	 */
	public GameState makeMove(Board board) {
		// call firstMove if board has 0 or 1 filled tiles
		if (board.getFilledTilesNumber() == 0 || board.getFilledTilesNumber() == 1) {
			return firstMove(board);
			// otherwise follow these steps
		} else {
			// try to get winning move by calling getWinningMove and assigning it to
			// nextMove
			if ((nextMove = getWinningMove(getSymbol(), board)) != null) {
				// if move exists call makeMove with coordinates of nextMove
				return makeMove(nextMove[0], nextMove[1], board);
				// try to block user from getting winning move by calling getWinningMove with
				// the user symbol and assigning it to nextMove
			} else if ((nextMove = getWinningMove(board.getWaitingPlayer().getSymbol(), board)) != null) {
				// if move exists call makeMove with coordinates of nextMove
				return makeMove(nextMove[0], nextMove[1], board);
				// try to get two in a row by calling getTwoInARow and assigning it to nextMove
			} else if ((nextMove = getTwoInARow(getSymbol(), board)) != null) {
				// if move exists call makeMove with coordinates of nextMove
				return makeMove(nextMove[0], nextMove[1], board);
				// try to block user from getting to in a row by calling getTwoInARow with user
				// symbol and assigning it to nextMove
			} else if ((nextMove = getTwoInARow(board.getWaitingPlayer().getSymbol(), board)) != null) {
				// if move exists call makeMove with coordinates of nextMove
				return makeMove(nextMove[0], nextMove[1], board);
			} else {
				// place randomly on board if none of these moves exists
				return fillRandomTile(board);
			}
		}

	}

	/**
	 * Utility method for making a random move on the board by filling up a random
	 * empty tile.
	 * 
	 * @param board the board to make the move on
	 * @return The state of the game after the move.
	 */
	private GameState fillRandomTile(Board board) {

		int row = 0, col = 0,
				// range of coordinates allowed for row and col
				min = 0, max = 2;

		do {
			// formula to generate random number between 0-2 inclusive
			// for row and column coordinates
			row = min + (int) (Math.random() * ((max - min) + 1));
			col = min + (int) (Math.random() * ((max - min) + 1));
			// loop runs until retrieve empty tile to fill
		} while (!board.getTileAt(row, col).isEmpty());
		// nextMove is random open coordinates
		nextMove = getMoveCoordinates(row, col);
		// make move with nextMove
		return makeMove(nextMove[0], nextMove[1], board);
	}

	/**
	 * Utility method that will get the next move that forms two in a row for the
	 * specified symbol, if it exists.
	 * 
	 * @param symbol the symbol to attempt to make two in a row of
	 * @param board  the board to make the move on
	 * @return The move that forms two in a row for the symbol, or null if no such
	 *         move is found.
	 */
	private int[] getTwoInARow(char symbol, Board board) {
		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 3; col++) {
				// runs when the current tile is equal to the specified symbol
				if (board.getTileAt(row, col).getSymbol() == symbol) {
					// checks for two in a row when we are on the first column
					if (col == 0) {
						// if we are on the top left
						if (row == 0) {
							// if the middle and bottom left tiles are empty
							if (board.getTileAt(row + 1, col + 1).isEmpty()
									&& board.getTileAt(row + 2, col + 2).isEmpty()) {
								// return the coordinates of the middle tile
								return getMoveCoordinates(row + 1, col + 1);
							}
							// if we are on the bottom left
						} else if (row == 2) {
							// if the middle and top right tiles are empty
							if (board.getTileAt(row - 1, col + 1).isEmpty()
									&& board.getTileAt(row - 2, col + 2).isEmpty()) {
								// return the coordinate of the middle tile
								return getMoveCoordinates(row - 1, col + 1);
							}
						}
						// otherwise if the column to the right and two to the right are empty
						if (board.getTileAt(row, col + 1).isEmpty() && board.getTileAt(row, col + 2).isEmpty()) {
							// return the coordinates of the right tile
							return getMoveCoordinates(row, col + 1);
						}
						// if we are on the middle column
					} else if (col == 1) {
						// if the column to the left and right are empty
						if (board.getTileAt(row, col - 1).isEmpty() && board.getTileAt(row, col + 1).isEmpty()) {
							// return the coordinates of the tile to the left
							return getMoveCoordinates(row, col - 1);
						}
						// if we are on the rightmost column
					} else if (col == 2) {
						// if we are on the top right
						if (row == 0) {
							// if the middle and bottom left tiles are empty
							if (board.getTileAt(row + 1, col - 1).isEmpty()
									&& board.getTileAt(row + 2, col - 2).isEmpty()) {
								// return the coordinates of the middle tile
								return getMoveCoordinates(row + 1, col - 1);
							}
							// if we are on the bottom right
						} else if (row == 2) {
							// if the middle and top left tiles are empty
							if (board.getTileAt(row - 1, col - 1).isEmpty()
									&& board.getTileAt(row - 2, col - 2).isEmpty()) {
								// return the coordinates of the middle tile
								return getMoveCoordinates(row - 1, col - 1);
							}
						}
						// otherwise if the tile to the left and two to the left are empty
						if (board.getTileAt(row, col - 1).isEmpty() && board.getTileAt(row, col - 2).isEmpty()) {
							// return column to the left
							return getMoveCoordinates(row, col - 1);
						}
					}
					// if we are on the first row
					if (row == 0) {
						// if the tile below and two below are empty
						if (board.getTileAt(row + 1, col).isEmpty() && board.getTileAt(row + 2, col).isEmpty()) {
							// return the tile one below
							return getMoveCoordinates(row + 1, col);
						}
						// if we are on the middle row
					} else if (row == 1) {
						// if the tile above and below are empty
						if (board.getTileAt(row - 1, col).isEmpty() && board.getTileAt(row + 1, col).isEmpty()) {
							// return the coordinates of the tile above
							return getMoveCoordinates(row - 1, col);
						}
						// if we are on the last tow
					} else if (row == 2) {
						// if the row above and two above are empty
						if (board.getTileAt(row - 2, col).isEmpty() && board.getTileAt(row - 1, col).isEmpty()) {
							// return the coordinates of the tile above
							return getMoveCoordinates(row - 1, col);
						}
					}
				}
			}
		}
		// return null if no two in a row move exist
		return null;
	}

	/**
	 * Utility method that will get the winning move for the specified symbol on the
	 * board, if it exists.
	 * 
	 * @param symbol the symbol to get the winning move for
	 * @param board  the board to get the winning move on
	 * @return The winning move, or null if no winning move is available.
	 */
	private int[] getWinningMove(char symbol, Board board) {
		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 3; col++) {

				if (board.getTileAt(row, col).getSymbol() == symbol) {
					// start checking for winning moves at top left of the board
					if (row == 0 && col == 0) {
						// if middle tile is the same and bottom right is empty
						if (board.getTileAt(row + 1, col + 1).getSymbol() == symbol
								&& board.getTileAt(row + 2, col + 2).isEmpty()) {
							// return coordinates of bottom right
							return getMoveCoordinates(row + 2, col + 2);
							// if bottom right is the same and middle is empty
						} else if (board.getTileAt(row + 2, col + 2).getSymbol() == symbol
								&& board.getTileAt(row + 1, col + 1).isEmpty()) {
							// return coordinates of the middle
							return getMoveCoordinates(row + 1, col + 1);
						}
					}
					// check for winning moves at the top right of the board
					if (row == 0 && col == 2) {
						// if middle tile is the same and bottom left is empty
						if (board.getTileAt(row + 1, col - 1).getSymbol() == symbol
								&& board.getTileAt(row + 2, col - 2).isEmpty()) {
							// return coordinates of bottom left
							return getMoveCoordinates(row + 2, col - 2);
							// if bottom left tile is the same and middle is empty
						} else if (board.getTileAt(row + 2, col - 2).getSymbol() == symbol
								&& board.getTileAt(row + 1, col - 1).isEmpty()) {
							// return coordinates of middle
							return getMoveCoordinates(row + 1, col - 1);
						}
					}
					// check for winning move from the middle of the board
					if (row == 1 && col == 1) {
						// if the bottom left tile is the same and the top right is empty
						if (board.getTileAt(row - 1, col - 1).getSymbol() == symbol
								&& board.getTileAt(row + 1, col + 1).isEmpty()) {
							// return coordinates of the top right
							return getMoveCoordinates(row + 1, col + 1);
							// if the bottom right tile is the same and the top left is empty
						} else if (board.getTileAt(row - 1, col + 1).getSymbol() == symbol
								&& board.getTileAt(row + 1, col - 1).isEmpty()) {
							// return coordinates of the top left
							return getMoveCoordinates(row + 1, col - 1);
							// if the top right tile is the same and the bottom left is empty
						} else if (board.getTileAt(row + 1, col + 1).getSymbol() == symbol
								&& board.getTileAt(row - 1, col - 1).isEmpty()) {
							// return coordinates of the bottom left
							return getMoveCoordinates(row - 1, col - 1);
							// if the top left tile is the same and the bottom right is empty
						} else if (board.getTileAt(row + 1, col - 1).getSymbol() == symbol
								&& board.getTileAt(row - 1, col + 1).isEmpty()) {
							// return coordinates of the bottom right
							return getMoveCoordinates(row - 1, col + 1);
						}
					}
					// check for vertical winning moves from the first row
					if (row == 0) {
						// if the tile below is the same and the tile two below is empty
						if (board.getTileAt(row + 1, col).getSymbol() == symbol
								&& board.getTileAt(row + 2, col).isEmpty()) {
							// return the coordinates for the tile two below
							return getMoveCoordinates(row + 2, col);
							// if the tile below is empty and the tile two below is the same
						} else if (board.getTileAt(row + 2, col).getSymbol() == symbol
								&& board.getTileAt(row + 1, col).isEmpty()) {
							// return the coordinates of the tile below
							return getMoveCoordinates(row + 1, col);

						}
					}

					// check for vertical winning moves from the middle row
					if (row == 1) {
						// if the tile above is the same and the tile below is empty
						if (board.getTileAt(row + 1, col).getSymbol() == symbol
								&& board.getTileAt(row - 1, col).isEmpty()) {
							// return coordinates of the tile below
							return getMoveCoordinates(row - 1, col);
						}
					}
					// check for horizontal winning moves from the first column
					if (col == 0) {
						// if the tile to the right is the same and the tile two to the right is empty
						if (board.getTileAt(row, col + 1).getSymbol() == symbol
								&& board.getTileAt(row, col + 2).isEmpty()) {
							// return the coordinates for the tile two to the right
							return getMoveCoordinates(row, col + 2);
							// if the tile two to the right is the same and the tile to the right is empty
						} else if (board.getTileAt(row, col + 2).getSymbol() == symbol
								&& board.getTileAt(row, col + 1).isEmpty()) {
							// return the coordinates for the tile to the right
							return getMoveCoordinates(row, col + 1);

						}
					}
					// check for horizontal winning moves from the middle column
					if (col == 1) {
						// if the tile to the right is the same and the tile to the left is empty
						if (board.getTileAt(row, col + 1).getSymbol() == symbol
								&& board.getTileAt(row, col - 1).isEmpty()) {
							// return the coordinates of the tile to the left
							return getMoveCoordinates(row, col - 1);
						}
					}

				}
			}
		}
		// return null if no winning move exists
		return null;
	}

	/**
	 * Return a move on the board represented by the the given coordinates (row and
	 * column values).
	 * 
	 * @param row the row value to put in the array
	 * @param col the column value to put in the array
	 * @return An int array containing the given row and col values represented as
	 *         {row, column}.
	 */
	private int[] getMoveCoordinates(int row, int col) {
		int[] move = new int[2];
		// move array represented as {row, col}
		move[0] = row;
		move[1] = col;
		return move;
	}

}