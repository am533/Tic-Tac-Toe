package view;

/**
 * This class defines various CSS styles that are used to set the appearance of
 * user interface (UI) elements in the GUI. These styles include text fill
 * color, font family and weight, background color, and alignment.
 */

public class CSSStyles {
	/**
	 * CSS style for white text fill color. It is used to set the text color to
	 * white for any element that needs to display white text.
	 */
	public final static String WHITE_TEXT_FILL = "-fx-text-fill: white; ";

	/**
	 * CSS style for green text fill color. It is used to set the text color to
	 * green for any element that needs to display green text.
	 */
	public final static String GREEN_TEXT_FILL = "-fx-text-fill: green; ";

	/**
	 * CSS style for Arial font. It is used to set the font to Arial for any element
	 * that needs to use this font.
	 */
	public final static String ARIAL_FONT = "-fx-font-family: ARIAL; ";

	/**
	 * CSS style for bold font weight. It is used to set the font weight to bold for
	 * any element that needs to be bold.
	 */
	public final static String BOLD_FONT = "-fx-font-weight: bold; ";

	/**
	 * CSS style for gray background color. It is used to set the background color
	 * to gray for any element that needs to have a gray background.
	 */
	public final static String GRAY_BACKGROUND = "-fx-background-color: gray; ";

	/**
	 * CSS style for black background color. It is used to set the background color
	 * to black for any element that needs to have a black background.
	 */
	public final static String BLACK_BACKGROUND = "-fx-background-color: black; ";

	/**
	 * CSS style for center alignment. It is used to set the center alignment for
	 * any element that needs to be aligned center of the screen.
	 */
	public final static String CENTER_ALIGNMENT = "-fx-alignment: CENTER";

	/**
	 * CSS style for top center alignment. It is used to set the top center
	 * alignment for any element that needs to be aligned top center of the screen.
	 */
	public final static String TOP_CENTER_ALIGNMENT = "-fx-alignment: TOP_CENTER";

	/**
	 * CSS style for white border color. It is used to set the border color to white
	 * for any element that needs to have a white border.
	 */
	public final static String WHITE_BORDER_COLOR = "-fx-border-color: white; ";

	/**
	 * CSS style for full opacity. Used in the case that a button is disabled, then
	 * the button doesn't become transparent
	 */
	public final static String FULL_OPACITY = "-fx-opacity: 1; ";

	/**
	 * Create a font size CSS style with the given font size. It is used to set the
	 * font size for any element that needs to have that font size
	 * 
	 * @param fontSize the size of the font
	 * @return A string that can be used to set the font size in the GUI.
	 */
	public final static String getFontSize(int fontSize) {
		return "-fx-font-size: " + fontSize + "; ";
	}

}