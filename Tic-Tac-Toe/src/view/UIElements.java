package view;

import model.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.stage.StageStyle;

/**
 * This class contains methods that create and return different elements of the
 * user interface for all scenes in all game states such as labels, buttons and
 * combo-boxes for the Tic-Tac-Toe GUI. The class uses the CSSStyles class to
 * apply CSS styles to the elements.
 */

public class UIElements {
	/**
	 * Create a text label with the specified text and font size. DEFAULT STYLE:
	 * white text fill, Arial font, bold font.
	 *
	 * @param text     the text to be displayed on the label.
	 * @param fontSize the desired font size for the label.
	 * @return A Label with the specified text and font size.
	 */
	public static Label getLabel(String text, int fontSize) {
		Label label = new Label(text);
		// setting the style with the default style plus the specified font size
		label.setStyle(CSSStyles.getFontSize(fontSize) + CSSStyles.WHITE_TEXT_FILL + CSSStyles.ARIAL_FONT
				+ CSSStyles.BOLD_FONT);

		return label;
	}

	/**
	 * Create a text label that displays the score of the specified player in the
	 * Tic-Tac-Toe game. DEFAULT STYLE: UIConstants specified player label text
	 * size, Arial font, bold font.
	 * 
	 * @param player the player whose score is to be displayed.
	 * @param board  the board object that holds the player's score information.
	 * @return A Label containing the specified player's score information.
	 */
	public static Label getScoreLabel(Player player, TicTacToeBoard board) {
		Label scoreLabel;
		// the text fill of the label will depend on if the specified player is
		// player one or two on the board
		String textColor;
		// if the given player is player one on the board
		if (board.getPlayerOne().equals(player)) {
			// create a new label with with the text "player one" with the player's symbol
			// and score,
			// and green text fill style
			scoreLabel = new Label(
					"Player One (" + board.getPlayerOne().getSymbol() + "): " + board.getPlayerOne().getScore());
			textColor = CSSStyles.GREEN_TEXT_FILL;
			// position label above the board on the left
			// by moving it left by one board tile width on the x-axis
			scoreLabel.setTranslateX(UIConstants.BOARD_TILE_WIDTH * -1);
			// if the given player is player two on the board
		} else {
			// create a new label with with the text "player two" with the player's symbol
			// and score,
			// and white text fill style
			scoreLabel = new Label(
					"Player Two (" + board.getPlayerTwo().getSymbol() + "): " + board.getPlayerTwo().getScore());
			textColor = CSSStyles.WHITE_TEXT_FILL;
			// position label above the board on the left
			// by moving it up on the y-axis
			// and to the right by one board tile on the x-axis,
			// aligning it with player one label
			scoreLabel.setTranslateX(UIConstants.BOARD_TILE_WIDTH);
			scoreLabel.setTranslateY(UIConstants.BOARD_SCENE_SPACING * -2.30);
		}
		// setting the style with the default style plus either white or green text fill
		scoreLabel.setStyle(CSSStyles.getFontSize(UIConstants.SCORE_LABEL_FONTSIZE) + textColor + CSSStyles.ARIAL_FONT
				+ CSSStyles.BOLD_FONT);

		return scoreLabel;
	}

	/**
	 * Create a next button. (DEFAULT STYLE: UIConstants specified next button
	 * length and height, UIConstants specified next button font size, gray
	 * background, white text fill, Arial font, bold font.)
	 *
	 * @return A next button, labeled as 'Next'.
	 */
	public static Button getNextButton() {
		Button button = new Button();
		// set text to "Next" and give it the default style
		button.setText("Next");
		button.setPrefSize(UIConstants.NEXT_BUTTON_WIDTH, UIConstants.NEXT_BUTTON_HEIGHT);
		button.setStyle(CSSStyles.getFontSize(UIConstants.NEXT_BUTTON_FONTSIZE) + CSSStyles.WHITE_TEXT_FILL
				+ CSSStyles.ARIAL_FONT + CSSStyles.BOLD_FONT + CSSStyles.GRAY_BACKGROUND);

		return button;
	}

	/**
	 * Create a back button. (DEFAULT STYLE: UIConstants specified back button
	 * length and height, UIConstants specified back button font size, gray
	 * background, white text fill, Arial font, bold font.)
	 * 
	 * @return A back button, labeled as '<'.
	 */
	public static Button getBackButton() {

		Button button = new Button();
		// set text to "<" and set the style with the default style
		button.setText("<");
		button.setPrefSize(UIConstants.BACK_BUTTON_WIDTH, UIConstants.BACK_BUTTON_HEIGHT);
		button.setStyle(CSSStyles.getFontSize(UIConstants.BACK_BUTTON_FONTSIZE) + CSSStyles.WHITE_TEXT_FILL
				+ CSSStyles.ARIAL_FONT + CSSStyles.BOLD_FONT + CSSStyles.GRAY_BACKGROUND);
		// position button on the left side of the screen with equal spacing from the
		// edge of the screen and the button
		button.setTranslateX(((UIConstants.SCENE_WIDTH / 2) - (UIConstants.BACK_BUTTON_WIDTH)) * -1);
		return button;
	}

	/**
	 * Create a combo box that is used to prompt the user to select a symbol for the
	 * game. (DEFAULT STYLE: UIConstants specified combo-box length and height,
	 * UIConstats specified combo-box font size, Arial font.)
	 * 
	 * @param text the text to be displayed as the prompt for the ComboBox.
	 * @return A ComboBox with the given text, containing options for the user to
	 *         select a symbol for the game.
	 */
	public static ComboBox<String> getSymbolPromptCombobox(String text) {
		ComboBox<String> comboBox = new ComboBox<String>();
		// set the text of the prompt and add 'X' and 'O' as options
		comboBox.setPromptText(text);
		comboBox.getItems().addAll(String.valueOf(Board.X_SYMBOL), String.valueOf(Board.O_SYMBOL));
		// set the style with the default style
		comboBox.setPrefSize(UIConstants.COMBOBOX_WIDTH, UIConstants.COMBOBOX_HEIGHT);
		comboBox.setStyle(CSSStyles.getFontSize(UIConstants.COMBOBOX_FONTSIZE) + CSSStyles.ARIAL_FONT);

		return comboBox;
	}

	/**
	 * Create a radio button A radio button is a control that allows the user to
	 * make a single selection from a toggle group of other radio buttons. (DEFAULT
	 * STYLE: UIConstants specified radio button font size, white text fill, Arial
	 * font, bold font.)
	 * 
	 * @param text        the text displayed on the radio button
	 * @param toggleGroup the group in which the radio button belongs to
	 * @return A RadioButton with the given text.
	 */
	public static RadioButton getRadioButton(String text, ToggleGroup toggleGroup) {
		RadioButton radioButton = new RadioButton(text);
		// now the radio button is apart of the specified toggle group
		radioButton.setToggleGroup(toggleGroup);
		// set the style with the default style
		radioButton.setStyle(CSSStyles.getFontSize(UIConstants.RADIO_BUTTON_FONTSIZE) + CSSStyles.WHITE_TEXT_FILL
				+ CSSStyles.ARIAL_FONT + CSSStyles.BOLD_FONT);
		// position radio button to have spacing above
		// next button at bottom of screen
		radioButton.setTranslateY((UIConstants.USER_SELECTION_SCENE_SPACING) * -1);

		return radioButton;
	}

	/**
	 * Create a button representing a tile on the board. (DEFAULT STYLE: UIConstants
	 * specified board tile length and height, UIConstants specified font size, full
	 * opacity [when button is disabled during computer's turn, it doesn't become
	 * transparent], white text fill, white border color, Arial font, bold font, and
	 * black background color.)
	 * 
	 * @return A button representing a tile on the board.
	 */
	public static Button getBoardTile() {
		Button button = new Button();
		// set the style with the default style for a board tile
		button.setPrefSize(UIConstants.BOARD_TILE_WIDTH, UIConstants.BOARD_TILE_HEIGHT);
		button.setStyle(CSSStyles.getFontSize(UIConstants.BOARD_TILE_FONTSIZE) + CSSStyles.FULL_OPACITY
				+ CSSStyles.WHITE_TEXT_FILL + CSSStyles.WHITE_BORDER_COLOR + CSSStyles.ARIAL_FONT + CSSStyles.BOLD_FONT
				+ CSSStyles.BLACK_BACKGROUND);

		return button;
	}

	/**
	 * Create a GridPane as a form of representation for the Tic-Tac-Toe game.
	 * (DEFAULT STYLE: black background and center alignment on the screen)
	 * 
	 * @param board the Tic-Tac-Toe board that the GridPane will represent
	 * @return UI representation of the Tic-Tac-Toe game in the form of a 3x3
	 *         GridPane
	 */
	public static GridPane getUIBoardLayout(TicTacToeBoard board) {
		// UIBoard represented by a 3x3 UI GridPane
		GridPane UIBoard = new GridPane();
		// set the board's default style and size.
		UIBoard.setPrefSize(UIConstants.BOARD_WIDTH, UIConstants.BOARD_HEIGHT);
		UIBoard.setStyle(CSSStyles.CENTER_ALIGNMENT);
		UIBoard.setTranslateY(UIConstants.BOARD_SCENE_SPACING * -2);
		for (int UIBoardCol = 0; UIBoardCol < Board.BOARD_COLS; UIBoardCol++) {
			for (int UIBoardRow = 0; UIBoardRow < Board.BOARD_ROWS; UIBoardRow++) {
				// separate row and column variables for aligning the
				// TicTacToeBoard's 2D array with the UI's GridPane,
				// allowing for tiles to be copied and moves to be made on the GridPane
				final int ticTacToeBoardRow = UIBoardCol;
				final int ticTacToeBoardCol = UIBoardRow;
				// create button that represents a board tile at the current index of the UI
				// Board
				Button UIBoardTile = UIElements.getBoardTile();
				// set the UI tile symbol to the TicTacToeBoard symbol at the corresponding tile
				UIBoardTile.setText(String.valueOf(board.getTileAt(ticTacToeBoardRow, ticTacToeBoardCol).getSymbol()));
				// notice use of ticTacToeBoardRow/Col for accessing board tiles
				// and UIBoardRow/Col for accessing UIBoard tiles
				UIBoard.add(UIBoardTile, UIBoardRow, UIBoardCol);
			}
		}
		return UIBoard;
	}

	/**
	 * Create an alert to display the end of game result.
	 * 
	 * @param result The result of the game, whether it was a win, loss, or draw.
	 * @return an alert object that displays the end of game result and prompts the
	 *         user to play again.
	 */
	public static Alert getEndOfGamePrompt(String result) {
		// create an alert to display the end of game result
		Alert alert = new Alert(AlertType.INFORMATION);
		// disable ability for user to 'x' out of the alert
		alert.initStyle(StageStyle.UNDECORATED);
		alert.setHeaderText("Game over");
		// prompt the user in the alert, asking if they want to play again or not
		alert.setContentText(result);
		return alert;
	}

}
