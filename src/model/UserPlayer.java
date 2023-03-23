package model;

/**
 * This class represents a human player in a Tic-Tac-Toe game and implements the
 * Player interface. A player has a symbol and a score, and it provides methods
 * for getting the symbol and score, increasing the score, and making a move on
 * the board.
 */

public class UserPlayer implements Player {
	/**
	 * Symbol that the player will play with.
	 */
	private char symbol;
	/**
	 * Score of the player against the opposing player in the Tic-Tac-Toe game.
	 */
	private int score;

	/**
	 * Construct a user player by initializing its symbol field.
	 * 
	 * @param symbol the symbol for the player
	 */
	public UserPlayer(char symbol) {
		this.symbol = symbol;
		// players start out with a score of zero when initialized
		score = 0;
	}

	/**
	 * Get the symbol of the player.
	 * 
	 * @return The symbol of the player.
	 */
	public char getSymbol() {
		return symbol;
	}

	/**
	 * Get the score of the player.
	 * 
	 * @return The score of the player.
	 */
	public int getScore() {
		return score;
	}

	/**
	 * Increase the score of the player.
	 */
	public void increaseScore() {
		score++;
	}

	/**
	 * Compare two players by their symbol to see if they're equal.
	 * 
	 * @param otherPlayer the other player to compare to see if they're equal
	 * @return True if the symbols match, false otherwise.
	 */
	@Override
	public boolean equals(Object otherPlayer) {
		if (otherPlayer == this) {
			return true;
		}
		if (!(otherPlayer instanceof Player)) {
			return false;
		}

		Player player = (Player) otherPlayer;
		// compare the symbol of this player with the symbol of the
		// other player
		return this.symbol == player.getSymbol();
	}

	/**
	 * Make a move on the board on the specified tile on the board.
	 * 
	 * @param row   the row of the tile to make a move on
	 * @param col   the column of the tile to make a move
	 * @param board the board to make the move on
	 * @return The state of the game after the move, return INVALID_MOVE if the move
	 *         is not valid.
	 */
	public GameState makeMove(int row, int col, Board board) {
		if (board.getTileAt(row, col).isEmpty()) {
			board.getTileAt(row, col).fillTile(getSymbol());
			// return state of game after move is made
			return board.getGameState();
		} else {
			// invalid move if tile is full
			return GameState.INVALID_MOVE;
		}
	}
}
