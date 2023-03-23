package model;

/**
 * This is an interface for a player in a Tic-Tac-Toe game. It defines the
 * methods for a player.
 */

public interface Player {
	/**
	 * Get the symbol of the player.
	 * 
	 * @return The symbol of the player.
	 */
	public char getSymbol();

	/**
	 * Get the score of the player.
	 * 
	 * @return The score of the player.
	 */
	public int getScore();

	/**
	 * Increase the score of the player.
	 */
	public void increaseScore();

	/**
	 * Compare two players by their symbol to see if they're equal.
	 * 
	 * @param otherPlayer the other player to compare to
	 * @return true if the symbols match, false otherwise.
	 */
	@Override
	public boolean equals(Object otherPlayer);

}