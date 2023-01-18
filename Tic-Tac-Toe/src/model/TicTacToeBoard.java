package model;

/**
 * This class represents a Tic-Tac-Toe board and implements the Board interface.
 * It allows for the creation of single and multi-player boards. It also
 * provides methods for switching turns and resetting the board and accessing
 * both players, the game state, the board tiles. This class also contains an
 * inner class, Tile, that represents a tile on the board.
 */
public class TicTacToeBoard implements Board {

	/**
	 * Inner class representing a tile on the Tic-Tac-Toe board. The tile has a
	 * symbol which can be either X or O, and can be filled or empty. The class also
	 * provides methods to fill the tile with a symbol, get the symbol of the tile,
	 * check if the tile is empty, and compare two tiles.
	 */
	public class Tile {
		/**
		 * Symbol that is on the tile on the board.
		 */
		private char symbol;

		/**
		 * Constructs an empty tile.
		 */
		public Tile() {
			symbol = EMPTY;
		}

		/**
		 * Fill in tile with specified symbol, increasing the number of filled tiles on
		 * the board.
		 * 
		 * @param symbol the symbol for the tile
		 * @throws IllegalArgumentException if symbol is an invalid symbol
		 */
		public void fillTile(char symbol) {
			if (symbol != X_SYMBOL && symbol != O_SYMBOL) {
				throw new IllegalArgumentException("Valid symbols: X/O");
			}
			this.symbol = symbol;
			// number of filled tiles on the board
			filledTiles++;
		}

		/**
		 * Get the symbol of the tile.
		 * 
		 * @return The symbol of the tile.
		 */
		public char getSymbol() {
			return symbol;
		}

		/**
		 * Check if the tile is empty.
		 * 
		 * @return True if the tile is empty, false otherwise.
		 */
		public boolean isEmpty() {
			return symbol == EMPTY;
		}

		/**
		 * Compare two tiles by their symbol to see if they're equal.
		 * 
		 * @param otherTile the other tile to compare to see if they're equal
		 * @return True if the symbols match, false otherwise.
		 */
		@Override
		public boolean equals(Object otherTile) {
			if (otherTile == this) {
				return true;
			}
			if (!(otherTile instanceof Tile)) {
				return false;
			}

			Tile tile = (Tile) otherTile;
			// compare the symbol of this tile with the symbol of the
			// other tile
			return this.symbol == tile.getSymbol();
		}
	}

	/**
	 * Player who makes first move of the game.
	 */
	private Player playerOne;
	/**
	 * Player who makes second move of the game.
	 */
	private Player playerTwo;
	/**
	 * 2D array of Tiles that represents the game board, as it is a 3x3 array.
	 */
	private Tile gameBoard[][] = new Tile[BOARD_ROWS][BOARD_COLS];
	/**
	 * Player who currently has to make a move.
	 */
	private Player currentPlayer;
	/**
	 * Number of tiles filled on the board by both players.
	 */
	private int filledTiles;

	/**
	 * Construct a multi-player TicTacToeBoard by initializing player one and two
	 * with their respective symbols. Player one and player two are both user
	 * players.
	 * 
	 * @param playerOneSymbol the symbol for player one
	 * @param playerTwoSymbol the symbol for player two
	 * @throws IllegalArgumentException if either symbol is invalid
	 */
	public TicTacToeBoard(char playerOneSymbol, char playerTwoSymbol) {
		if ((playerOneSymbol != X_SYMBOL && playerOneSymbol != O_SYMBOL)
				|| playerTwoSymbol != X_SYMBOL && playerTwoSymbol != O_SYMBOL) {
			throw new IllegalArgumentException("Valid symbols: X/O");
		}
		// initialize players with respective symbols
		playerOne = new UserPlayer(playerOneSymbol);
		playerTwo = new UserPlayer(playerTwoSymbol);
		// initialize game board
		initBoard();
		// set current player about to make move to player one
		currentPlayer = playerOne;
		filledTiles = 0;

	}

	/**
	 * Construct a single-player TicTacToeBoard with the user choosing if they want
	 * to go first or not. Depending on what the user picks, player one and two will
	 * be either a user player or a computer player, initialized with their
	 * respective symbols.
	 * 
	 * @param playerOneSymbol      the symbol for player one
	 * @param computerPlayerSymbol the symbol for the computer player
	 * @param userFirst            true if user wants to go first, false otherwise
	 * @throws IllegalArgumentException if either symbol is invalid
	 */
	public TicTacToeBoard(char playerOneSymbol, char computerPlayerSymbol, boolean userFirst) {
		// initialize players and other fields using "this"
		this(playerOneSymbol, computerPlayerSymbol);
		if (userFirst) {
			// if user wants to go first,
			// then player two is the computer player
			playerTwo = new ComputerPlayer(playerTwo.getSymbol());
		} else {
			// if user wants to go second,
			// player one is computer player and
			// player two is user player.
			// their symbols also get switched when the
			// user goes first
			playerOne = new ComputerPlayer(computerPlayerSymbol);
			playerTwo = new UserPlayer(playerOneSymbol);
			currentPlayer = playerOne;
		}
	}

	/**
	 * Get player who's going first.
	 * 
	 * @return Player one.
	 */
	@Override
	public Player getPlayerOne() {
		return playerOne;
	}

	/**
	 * Get player whos's going second.
	 * 
	 * @return Player two.
	 */
	@Override
	public Player getPlayerTwo() {
		return playerTwo;
	}

	/**
	 * Get the player about to make a move.
	 * 
	 * @return The current player.
	 */
	@Override
	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	/**
	 * Get the player waiting for their turn.
	 * 
	 * @return The waiting player.
	 */
	@Override
	public Player getWaitingPlayer() {
		// returns playerTwo if current player is
		// equal to playerOne and vice versa
		return getCurrentPlayer().equals(playerOne) ? playerTwo : playerOne;
	}

	/**
	 * Check if it is player one's turn.
	 * 
	 * @return True if it is player one's turn, false otherwise.
	 */
	@Override
	public boolean isPlayerOneTurn() {
		return getCurrentPlayer().equals(playerOne);
	}

	/**
	 * Check if it is player two's turn.
	 * 
	 * @return True if it is player two's turn, false otherwise.
	 */
	@Override
	public boolean isPlayerTwoTurn() {
		return getCurrentPlayer().equals(playerTwo);
	}

	/**
	 * Return whether or not the specified player is a computer player or not.
	 * 
	 * @param player player to check for if it's a computer player
	 * @return True if the player is a computer player, false otherwise.
	 */
	@Override
	public boolean isComputerPlayer(Player player) {
		return player instanceof ComputerPlayer;
	}

	/**
	 * Switch the current and waiting players.
	 */
	@Override
	public void switchTurns() {
		// waiting player depends on the current player,
		// so change currentPlayer reference
		if (getCurrentPlayer().equals(playerOne)) {
			// switch current player to player two if player one is current player
			currentPlayer = playerTwo;
		} else {
			// otherwise, switch current player to player one
			currentPlayer = playerOne;
		}

	}

	/**
	 * Get the current state of the game.
	 * 
	 * @return The current game state of the board.
	 */
	@Override
	public GameState getGameState() {
		GameState gameState;
		if (GameState.winningMoveMade(this)) {
			gameState = GameState.WIN;
			// increase score of player who made winning move
			getCurrentPlayer().increaseScore();
		} else if (GameState.isTie(this)) {
			gameState = GameState.TIE;
		} else {
			// if there's no tie or win, then the game state
			// is IN_PROGRESS
			gameState = GameState.IN_PROGRESS;
		}
		return gameState;
	}

	/**
	 * Get the tile at the specified position.
	 * 
	 * @param row The row of the desired tile
	 * @param col The column of the desired tile
	 * @return The tile at the specified position.
	 */
	@Override
	public Tile getTileAt(int row, int col) {
		return gameBoard[row][col];
	}

	/**
	 * Reset the board to its initial state.
	 */
	@Override
	public void resetBoard() {
		// re-initialize board
		initBoard();
		// set filled tiles back to zero
		filledTiles = 0;
	}

	/**
	 * Get the number of filled tiles on the board.
	 * 
	 * @return The number of filled tiles.
	 */
	@Override
	public int getFilledTilesNumber() {
		return filledTiles;
	}

	/**
	 * Helper method that initialize the board with a new blank tile in all the rows
	 * and columns.
	 */
	private void initBoard() {
		for (int row = 0; row < BOARD_ROWS; row++) {
			for (int col = 0; col < BOARD_COLS; col++) {
				// new Tile() initializes a blank tile at
				// current row and column on the board
				gameBoard[row][col] = new Tile();
			}
		}

	}

	// for testing purposes
	// string representation of the board
	@Override
	public String toString() {
		String board = "";
		for (int row = 0; row < Board.BOARD_ROWS; row++) {
			for (int col = 0; col < Board.BOARD_COLS; col++) {
				board += getTileAt(row, col).getSymbol();
				if (col != Board.BOARD_COLS - 1) {
					board += "|";
				}
			}
			board += "\n";
			if (row != Board.BOARD_ROWS - 1) {
				board += "-+-+-\n";
			}
		}
		return board;
	}

}
