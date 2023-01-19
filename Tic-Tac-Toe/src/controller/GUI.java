package controller;

import java.util.Optional;

import javafx.application.*;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import model.*;
import view.CSSStyles;
import view.UIConstants;
import view.UIElements;

/**
 * This class contains all the necessary methods to create a graphical user
 * interface for a Tic-Tac-Toe game. It has methods for creating the home scene,
 * single-player prompt scene, multi-player prompt scene, and board scene. Each
 * scene contains various UI elements such as labels, buttons, and combo boxes
 * that allow the user to interact with the game. The GUI class also contains
 * several helper methods from other classes in the package that are used by the
 * scene creation methods to create UI elements.
 */

public class GUI extends Application {
	/**
	 * TicTacToeBoard object that contains the logic and game state for the
	 * Tic-Tac-Toe game.
	 */
	private static TicTacToeBoard board;

	/**
	 * true if user wants to go first in the multi-player game mode, false
	 * otherwise.
	 */
	private static boolean userFirst;

	/**
	 * true if the user has prompted whether or not they want to go first in the
	 * multi-player game mode, false otherwise.
	 */
	private static boolean turnPicked;

	/**
	 * true if the user has prompted whether or not they want to go first in the
	 * multi-player game mode, false otherwise.
	 */
	private static boolean symbolPicked;

	/**
	 * Symbol that player one will be playing with.
	 */
	private static char playerOneSymbol;

	/**
	 * Symbol that player two will be playing with.
	 */
	private static char playerTwoSymbol;

	/**
	 * Stage shared by all scenes in the app.
	 */
	private static Stage mainStage;

	/**
	 * Launches the Tic-Tac-Toe game.
	 * 
	 * @param stage stage shared by all scenes in the app
	 */
	public void start(Stage stage) throws Exception {
		mainStage = stage;
		mainStage.setTitle("Tic-Tac-Toe");
		// game starts with the home scene
		mainStage.setScene(getHomeScene());
		mainStage.show();
	}

	/**
	 * Create a Scene object that represents the home screen of the Tic-Tac-Toe
	 * game. The home screen contains a title label, a prompt label to select a game
	 * mode, and buttons to select either single-player or multi-player game mode.
	 *
	 * @return Scene object representing the home screen of the Tic-Tac-Toe game.
	 */
	public static Scene getHomeScene() {
		// whenever the home scene is launched, these values reset for a new game
		playerOneSymbol = Board.EMPTY;
		playerTwoSymbol = Board.EMPTY;
		turnPicked = false;
		symbolPicked = false;

		// title of the scene
		Label titleLabel = UIElements.getLabel("Tic-Tac-Toe", UIConstants.TITLE_LABEL_FONTSIZE);

		// prompt for the user to select a game mode
		Label modePrompt = UIElements.getLabel("Select game mode: ", UIConstants.PROMPT_LABEL_FONTSIZE);

		// option for single-player mode
		Button singleButton = UIElements.getNextButton();
		singleButton.setPrefSize(UIConstants.BUTTON_GAMEMODE_WIDTH, UIConstants.BUTTON_GAMEMODE_HEIGHT);
		singleButton.setText("Single-Player");
		singleButton.setOnAction(event -> {
			// if selected, set the single-player user prompt screen on the main stage
			mainStage.setScene(getSinglePromptScene());
			mainStage.show();
		});

		// option for multi-player mode
		Button multiButton = UIElements.getNextButton();
		multiButton.setPrefSize(UIConstants.BUTTON_GAMEMODE_WIDTH, UIConstants.BUTTON_GAMEMODE_HEIGHT);
		multiButton.setText("Multi-Player");
		multiButton.setOnAction(event -> {
			// if selected, set the multi-player user prompt screen on the main stage
			mainStage.setScene(getMultiPromptScene());
			mainStage.show();
		});

		// layout will have a vertical layout and the default sizing and style
		VBox homeLayout = new VBox(UIConstants.HOME_SCENE_SPACING);
		homeLayout.setPrefSize(UIConstants.SCENE_WIDTH, UIConstants.SCENE_HEIGHT);
		homeLayout.setStyle(CSSStyles.BLACK_BACKGROUND + CSSStyles.CENTER_ALIGNMENT);
		// add the UI elements to the scene
		homeLayout.getChildren().addAll(titleLabel, modePrompt, singleButton, multiButton);
		// add padding 100 pixels on the bottom to all UI elements in the scene
		homeLayout.setPadding(new Insets(0, 0, 100, 0));

		return new Scene(homeLayout, UIConstants.SCENE_WIDTH, UIConstants.SCENE_HEIGHT);
	}

	/**
	 * Create a scene for the single-player user symbol select screen. This scene
	 * prompts the user to select their symbol and whether or not to go first. It
	 * also allows the user to return to the home screen with the back button and
	 * start the game with the next button if the prompts are answered.
	 *
	 * @return Scene multiPromptScene, the scene for the multi-player symbol prompt
	 *         screen
	 */
	public static Scene getSinglePromptScene() {
		// title for the scene
		Label titleLabel = UIElements.getLabel("Single-Player", UIConstants.TITLE_LABEL_FONTSIZE);
		// move title up by one space
		titleLabel.setTranslateY((UIConstants.USER_SELECTION_SCENE_SPACING) * -1);

		// create a back button
		Button backButton = UIElements.getBackButton();
		backButton.setOnAction(event -> {
			// when back button is clicked, set the home scene as the current scene
			mainStage.setScene(getHomeScene());
			mainStage.show();
		});
		// move back button up half a space
		backButton.setTranslateY((UIConstants.USER_SELECTION_SCENE_SPACING * 0.5));

		// create a symbol prompt label and move it up one space
		Label symbolPromptLabel = UIElements.getLabel("Choose your symbol: ", UIConstants.PROMPT_LABEL_FONTSIZE);
		symbolPromptLabel.setTranslateY((UIConstants.USER_SELECTION_SCENE_SPACING) * -1);

		// create a combo box for letting the user choose a symbol
		ComboBox<String> symbolChoiceComboBox = UIElements.getSymbolPromptCombobox("Choose Symbol");
		symbolChoiceComboBox.setOnAction(event -> {
			// whatever is selected is player one's symbol, and player two's is the opposite
			// symbol
			playerOneSymbol = symbolChoiceComboBox.getSelectionModel().getSelectedItem().charAt(0);
			playerTwoSymbol = (playerOneSymbol == Board.X_SYMBOL) ? Board.O_SYMBOL : Board.X_SYMBOL;

		});
		// move combo box up by 1.5 spaces to create smaller spacing between the prompt
		// and the combo box
		symbolChoiceComboBox.setTranslateY((UIConstants.USER_SELECTION_SCENE_SPACING) * -1.5);

		// create a label that prompts the user if they want to go first
		Label userFirstPromptLabel = UIElements.getLabel("Do you want to go first?: ",
				UIConstants.PROMPT_LABEL_FONTSIZE);
		// move prompt label up by one space
		userFirstPromptLabel.setTranslateY((UIConstants.USER_SELECTION_SCENE_SPACING) * -1);

		// yes and no button options will be apart of the same toggle group
		// so that only one can be picked at a time
		ToggleGroup toggleGroup = new ToggleGroup();
		RadioButton yesButton = UIElements.getRadioButton("Yes", toggleGroup);
		yesButton.setOnAction(event -> {
			// if user picks yes, turnPicked is true and userFirst is true
			turnPicked = true;
			userFirst = true;

		});

		RadioButton noButton = UIElements.getRadioButton("No", toggleGroup);
		noButton.setOnAction(event -> {
			// if user picks no, turnPicked is true and userFirst is false
			turnPicked = true;
			userFirst = false;
		});

		// create next button
		Button nextButton = UIElements.getNextButton();
		nextButton.setOnAction(event -> {
			// true if user selected their symbol, false otherwise
			symbolPicked = (playerOneSymbol != Board.EMPTY || playerTwoSymbol != Board.EMPTY);
			// if clicked and both the symbol choice choice to go first are made,
			// make a new multi-player board with the specified symbols assigned to each
			// player
			// and the user choice of if they want to go first or not
			if (symbolPicked && turnPicked) {
				board = new TicTacToeBoard(playerOneSymbol, playerTwoSymbol, userFirst);
				mainStage.setScene(getBoardScene());
			} else {
				// otherwise generate an error message with what user forgot
				String message;
				if (!symbolPicked && !turnPicked) {
					message = "Please fill out the prompts";
				} else if (symbolPicked) {
					message = "First move choice missing";
				} else {
					message = "Symbol not selected";
				}
				// display an alert with error message
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Error");
				alert.setContentText(message);

				alert.showAndWait();
			}

		});
		// layout will have a vertical layout and the default sizing and style
		VBox singlePromptLayout = new VBox(UIConstants.USER_SELECTION_SCENE_SPACING);
		singlePromptLayout.setPrefSize(UIConstants.SCENE_WIDTH, UIConstants.SCENE_HEIGHT);
		singlePromptLayout.setStyle(CSSStyles.BLACK_BACKGROUND + CSSStyles.TOP_CENTER_ALIGNMENT);
		// add the UI elements to the scene
		singlePromptLayout.getChildren().addAll(backButton, titleLabel, symbolPromptLabel, symbolChoiceComboBox,
				userFirstPromptLabel, yesButton, noButton, nextButton);

		return new Scene(singlePromptLayout, UIConstants.SCENE_WIDTH, UIConstants.SCENE_HEIGHT);

	}

	/**
	 * Create a scene for the multi-player user symbol select screen. This scene
	 * prompts both players to select their symbols. It also allows the user to
	 * return to the home screen with the back button and start the game with the
	 * next button if the prompts are answered.
	 *
	 * @return Scene multiPromptScene, the scene for the multi-player symbol prompt
	 *         screen
	 */
	public static Scene getMultiPromptScene() {
		// title label for the scene
		Label titleLabel = UIElements.getLabel("Multi-Player", UIConstants.TITLE_LABEL_FONTSIZE);
		// move title label up 8.5 spaces to the top
		titleLabel.setTranslateY((UIConstants.USER_SELECTION_SCENE_SPACING * 8.5) * -1);

		// create a back button
		Button backButton = UIElements.getBackButton();
		backButton.setOnAction(event -> {
			// when clicked, set the current scene to the home scene
			mainStage.setScene(getHomeScene());
			mainStage.show();
		});
		// move back button up 1.5 spaces to the top of the screen
		backButton.setTranslateY((UIConstants.USER_SELECTION_SCENE_SPACING * 1.25) * -1);

		// create a prompt that asks both players to choose their symbols
		Label goFirstPrompt = UIElements.getLabel("Choose your symbols: ", UIConstants.PROMPT_LABEL_FONTSIZE);

		// create a combo box for letting player one choose a symbol
		ComboBox<String> playerOneSymbolComboBox = UIElements.getSymbolPromptCombobox("Player one choose symbol");

		// create a combo box for letting player two choose a symbol
		ComboBox<String> playeTwoSymbolComboBox = UIElements.getSymbolPromptCombobox("Player two choose symbol");

		playerOneSymbolComboBox.setOnAction(event -> {
			// if selection is made, assign player one symbol to selected symbol
			// and player two symbol to the opposite symbol, along with the player two combo
			// box
			playerOneSymbol = playerOneSymbolComboBox.getSelectionModel().getSelectedItem().charAt(0);
			if (playerTwoSymbol == Board.EMPTY || playerOneSymbol == playerTwoSymbol) {
				playerTwoSymbol = (playerOneSymbol == Board.X_SYMBOL) ? Board.O_SYMBOL : Board.X_SYMBOL;
			}
			playeTwoSymbolComboBox.getSelectionModel().select(String.valueOf(playerTwoSymbol));

		});
		playeTwoSymbolComboBox.setOnAction(event -> {
			// if selection is made, assign player two symbol to selected symbol
			// and player one symbol to the opposite symbol, along with the player one combo
			// box
			playerTwoSymbol = playeTwoSymbolComboBox.getSelectionModel().getSelectedItem().charAt(0);
			if (playerOneSymbol == Board.EMPTY || playerOneSymbol == playerTwoSymbol) {
				playerOneSymbol = (playerTwoSymbol == Board.X_SYMBOL) ? Board.O_SYMBOL : Board.X_SYMBOL;
			}
			playerOneSymbolComboBox.getSelectionModel().select(String.valueOf(playerOneSymbol));

		});

		// create a next button
		Button nextButton = UIElements.getNextButton();
		nextButton.setOnAction(event -> {
			// if clicked and both symbol choices are made, make a new single-player
			// board with the specified symbols assigned to each player
			if (playerOneSymbol != Board.EMPTY || playerTwoSymbol != Board.EMPTY) {
				board = new TicTacToeBoard(playerOneSymbol, playerTwoSymbol);
				mainStage.setScene(getBoardScene());
			} else {
				// otherwise generate an error message with what user forgot
				Alert alert = new Alert(Alert.AlertType.ERROR);

				alert.setTitle("Error");
				alert.setContentText("Symbols not selected");

				// display alert with the message
				alert.showAndWait();
			}
		});

		// layout will have a vertical layout and the default sizing and style
		VBox multiPromptLayout = new VBox(UIConstants.USER_SELECTION_SCENE_SPACING);
		multiPromptLayout.setPrefSize(UIConstants.SCENE_WIDTH, UIConstants.SCENE_HEIGHT);
		multiPromptLayout.setStyle(CSSStyles.BLACK_BACKGROUND + CSSStyles.CENTER_ALIGNMENT);
		// add the UI elements to the scene
		// title label added second to last so that there's extra spacing between
		// prompts and next button
		multiPromptLayout.getChildren().addAll(backButton, goFirstPrompt, playerOneSymbolComboBox,
				playeTwoSymbolComboBox, titleLabel, nextButton);
		return new Scene(multiPromptLayout, UIConstants.SCENE_WIDTH, UIConstants.SCENE_HEIGHT);

	}

	/**
	 * Create a Scene representing the Tic-Tac-Toe game. The scene includes a title
	 * label indicating whether the game mode is single-player or multi-player, a
	 * back button that allows the user to navigate back to the appropriate user
	 * prompt scene, and the UI representation of the Tic-Tac-Toe board as a 3x3
	 * GridPane. If the current player going is a computer player, the computer will
	 * make a move on the board.
	 * 
	 * @return Scene representing the Tic-Tac-Toe board
	 */
	public static Scene getBoardScene() {
		// text of title depends on what game mode the user picked
		String titleText = (board.isComputerPlayer(board.getCurrentPlayer())
				|| board.isComputerPlayer(board.getWaitingPlayer())) ? "Single-Player" : "Multi-Player";
		// title label for the scene
		Label titleLabel = UIElements.getLabel(titleText, UIConstants.TITLE_LABEL_FONTSIZE);
		// move title label up one space
		titleLabel.setTranslateY(UIConstants.BOARD_SCENE_SPACING * -1);

		// create a back button
		Button backButton = UIElements.getBackButton();
		backButton.setOnAction(event -> {
			// if clicked, set either single or multi-player user prompt scene as the
			// current scene
			// depending on what game mode is being played
			if (titleText.equals("Single-Player")) {
				mainStage.setScene(getSinglePromptScene());
			} else {
				mainStage.setScene(getMultiPromptScene());
			}
			mainStage.show();
		});
		// move back button up half a space to the top of the scene
		backButton.setTranslateY((UIConstants.USER_SELECTION_SCENE_SPACING * 0.5));

		// create the player labels
		// player label display whether it's player one or two, the player's symbol, and
		// their current score.
		Label playerOneScoreLabel = UIElements.getScoreLabel(board.getPlayerOne(), board);
		Label playerTwoScoreLabel = UIElements.getScoreLabel(board.getPlayerTwo(), board);

		// create UI representation of the TicTacToeBoard as a 3x3 GridPane
		GridPane UIBoard = UIBoardActions.getUIBoard(board, playerOneScoreLabel, playerTwoScoreLabel);

		// if the current player is a computer player, call computer turn for
		// the computer player to make a move on the board
		if (board.isComputerPlayer(board.getCurrentPlayer())) {
			UIBoardActions.computerTurn();
		}

		// layout will have a vertical layout and the default sizing and style
		VBox display = new VBox(UIConstants.BOARD_SCENE_SPACING);
		display.setPrefSize(UIConstants.SCENE_WIDTH, UIConstants.SCENE_HEIGHT);
		display.setStyle(CSSStyles.BLACK_BACKGROUND + CSSStyles.TOP_CENTER_ALIGNMENT);
		// add the UI elements to the scene
		display.getChildren().addAll(backButton, titleLabel, playerOneScoreLabel, playerTwoScoreLabel, UIBoard);

		return new Scene(display, UIConstants.SCENE_WIDTH, UIConstants.SCENE_HEIGHT);

	}

	/**
	 * Displays an alert with a message indicating the outcome of the game and gives
	 * the option to play again or go back to the home scene.
	 * 
	 * @param result the outcome of the game, either "PLAYER ONE WINS!", "PLAYER TWO
	 *               WINS!", or "TIE"
	 */
	public static void displayEndOfGamePrompt(String result) {

		// use Platform.runLater to ensure that the game over alert is displayed
		// on the JavaFX application thread,
		// preventing errors caused by updating the UI from a background thread.
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				// create an alert to display the end of game result
				Alert alert = new Alert(AlertType.INFORMATION);
				// disable ability for user to 'x' out of the alert
				alert.initStyle(StageStyle.UNDECORATED);
				alert.setHeaderText("Game over");
				// prompt the user in the alert, asking if they want to play again or not
				alert.setContentText(result);
				// Add options for the user to choose from ("Play again" or "Home")
				ButtonType playAgainOption = new ButtonType("Play again");
				ButtonType homeOption = new ButtonType("Home");
				alert.getButtonTypes().setAll(playAgainOption, homeOption);
				// Show the alert and wait for the user's response
				Optional<ButtonType> response = alert.showAndWait();
				if (response.get() == playAgainOption) {
					// If user chooses "Play again", reset the game board
					// and set the current scene to the board scene
					board.resetBoard();
					// switch back to player one if needed
					if (board.isPlayerTwoTurn()) {
						board.switchTurns();
						mainStage.setScene(getBoardScene());
					} else {
						mainStage.setScene(getBoardScene());
					}
				} else {
					// If user chooses "Home", return to the home scene
					mainStage.setScene(getHomeScene());
				}
			}
		});
	}

	/**
	 * Main method to launch the Tic-Tac-Toe game GUI.
	 * 
	 * @param args command line arguments
	 */
	public static void main(String args[]) {
		// launch the application and start execution at the start() method
		launch(args);
	}

}