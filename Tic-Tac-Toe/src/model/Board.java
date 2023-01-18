package model;

import model.TicTacToeBoard.Tile;

/**
 * This is an interface for a Tic-Tac-Toe board. It defines the methods and
 * constants for a Tic-Tac-Toe board.
 */

public interface Board {
	/**
	 * Number of rows on Tic-Tac-Toe board.
	 */
	final static int BOARD_ROWS = 3;
	/**
	 * Number of columns on Tic-Tac-Toe board.
	 */
	final static int BOARD_COLS = 3;
	/**
	 * One of two playable symbols.
	 */
	final static char X_SYMBOL = 'X';
	/**
	 * One of two playable symbols.
	 */
	final static char O_SYMBOL = 'O';
	/**
	 * Symbol for an empty tile on the Tic-Tac-Toe board.
	 */
	final static char EMPTY = ' ';

	/**
	 * Get player who's going first.
	 * 
	 * @return Player one.
	 */
	public Player getPlayerOne();

	/**
	 * Get player whos's going second.
	 * 
	 * @return Player two.
	 */
	public Player getPlayerTwo();

	/**
	 * Get the player about to make a move.
	 * 
	 * @return The current player.
	 */
	public Player getCurrentPlayer();

	/**
	 * Get the player waiting for their turn.
	 * 
	 * @return The waiting player.
	 */
	public Player getWaitingPlayer();

	/**
	 * Check if it is player one's turn.
	 * 
	 * @return True if it is player one's turn, false otherwise.
	 */
	public boolean isPlayerOneTurn();

	/**
	 * Check if it is player two's turn.
	 * 
	 * @return True if it is player two's turn, false otherwise.
	 */
	public boolean isPlayerTwoTurn();

	/**
	 * Return whether or not the specified player is a computer player or not.
	 * 
	 * @param player player to check for if it's a computer player
	 * @return True if the player is a computer player, false otherwise.
	 */
	public boolean isComputerPlayer(Player player);

	/**
	 * Switch the current and waiting players.
	 */
	public void switchTurns();

	/**
	 * Get the current state of the game.
	 * 
	 * @return The current game state of the board.
	 */
	public GameState getGameState();

	/**
	 * Get the tile at the specified position.
	 * 
	 * @param row the row of the desired tile
	 * @param col the column of the desired tile
	 * @return The tile at the specified position.
	 */
	public Tile getTileAt(int row, int col);

	/**
	 * Get the number of filled tiles on the board.
	 * 
	 * @return The number of filled tiles.
	 */
	public int getFilledTilesNumber();

	/**
	 * Reset the board to its initial state.
	 */
	public void resetBoard();
}