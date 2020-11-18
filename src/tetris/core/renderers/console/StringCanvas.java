package tetris.core.renderers.console;

import com.diogonunes.jcolor.Ansi;
import com.diogonunes.jcolor.Attribute;

public class StringCanvas
{
	/*
	 Chars used for rendering, copy&paste: 
	╔══╦══╗
	║  ║  ║
	╠══╬══╣
	║  ║  ║
	╚══╩══╝
	█▒▓
	*/

	/**
	 * the internal string builder drawn to
	 */
	StringBuilder canvas = new StringBuilder();

	/**
	 * how much padding is added to the left of each line
	 */
	int leftPadding = 0;

	/**
	 * is color rendering enabled?
	 */
	boolean enableColors = false;

	/**
	 * the current foreground color to render in
	 */
	Attribute currentFGColor;

	/**
	 * clear the canvas
	 * @return the cleared canvas
	 */
	public StringCanvas clear()
	{
		canvas = new StringBuilder();
		padding(leftPadding);
		return this;
	}

	/**
	 * set how much padding is added to the left of each line
	 * @param padding how many chars of padding to add
	 * @return the canvas object
	 */
	public StringCanvas setLeftPadding(int padding)
	{
		if (padding < 0)
			padding = 0;

		leftPadding = padding;
		return this;
	}

	/**
	 * 
	 * @return the internal string builder used as a canvas
	 */
	public StringBuilder getStringBuilder()
	{
		return canvas;
	}

	/**
	 * get the string that was drawn
	 */
	@Override
	public String toString()
	{
		return canvas.toString();
	}

	/**
	 * enable or disable colored text rendering
	 * @param enabled enable or disable color rendering using JColor?
	 * @return the canvas object
	 */
	public StringCanvas setColorEnabled(boolean enabled)
	{
		enableColors = enabled;
		return this;
	}

	/**
	 * set the foreground color to render in. call with null to reset
	 * @param fg thet color to render in
	 * @return the canvas object
	 */
	public StringCanvas setForegroundColor(Attribute fg)
	{
		currentFGColor = fg;
		return this;
	}

	/**
	 * append a string to the canvas, colored in the current foreground color
	 * @param s the string to append
	 * @return the canvas drawn on
	 */
	public StringCanvas append(String s)
	{
		if (!enableColors || currentFGColor == null)
			canvas.append(s);
		else
			canvas.append(Ansi.colorize(s, currentFGColor));
		return this;
	}

	/**
	 * draw a centered string to the canvas
	 * @param str the string to draw
	 * @param width the width to draw the box at
	 * @return the canvas drawn on
	 */
	public StringCanvas stringCentered(String str, int width)
	{
		// start with the left "rail"...
		append("║");

		// fill the left of the string with spaces
		int spaceToFill = width - str.length();
		for (int i = 0; i < (spaceToFill / 2); i++)
			append(" ");

		// draw the string
		append(str);

		// and fill the right of the string with spaces
		if (spaceToFill % 2 != 0)
			spaceToFill++;
		for (int i = 0; i < (spaceToFill / 2); i++)
			append(" ");

		// ... and end with the right "rail"
		append("║");
		ln();
		return this;
	}

	/**
	 * draw a left aligned string to the canvas
	 * @param str the string to draw
	 * @param width the width to draw the box at
	 * @return the canvas drawn on
	 */
	public StringCanvas stringLeft(String str, int width)
	{
		// start with the left "rail"...
		append("║");

		// ... print the string and fill to width with spaces ...
		append(str);
		for (int i = 0; i < (width - str.length()); i++)
			append(" ");

		// ... and end with the right "rail"
		append("║");
		ln();
		return this;
	}

	/**
	 * draw a header and newline to the canvas
	 * @param the width to draw the header with
	 * @return the canvas drawn on
	 */
	public StringCanvas header(int width)
	{
		append("╔");
		for (int i = 0; i < width; i++)
			append("═");
		append("╗");
		ln();
		return this;
	}

	/**
	 * draw a separator and newline to the canvas
	 * @param the width to draw the separator with
	 * @return the canvas drawn on
	 */
	public StringCanvas separator(int width)
	{
		append("╠");
		for (int i = 0; i < width; i++)
			append("═");
		append("╣");
		ln();
		return this;
	}

	/**
	 * draw a footer and newline to the canvas
	 * @param the width to draw the footer with
	 * @return the canvas drawn on
	 */
	public StringCanvas footer(int width)
	{
		append("╚");
		for (int i = 0; i < width; i++)
			append("═");
		append("╝");
		ln();
		return this;
	}

	/**
	 * draw a blank line width "rails" left and right aswell as a newline to the canvas
	 * @param the width to draw the line with
	 * @return the canvas drawn on
	 */
	public StringCanvas blank(int width)
	{
		append("║");
		for (int i = 0; i < width; i++)
			append(" ");
		append("║");
		ln();
		return this;
	}

	/**
	 * draw a vertical rail ("║") to the canvas
	 * @return the canvas drawn on
	 */
	public StringCanvas vRail()
	{
		append("║");
		return this;
	}

	/**
	 * draw a padding consisting of spaces to the canvas
	 * @param width how wide the spacer should be drawn
	 * @return the canvas drawn on
	 */
	public StringCanvas padding(int width)
	{
		for (int i = 0; i < width; i++)
			append(" ");
		return this;
	}

	/**
	 * draw a newline to the canvas
	 * @return the canvas drawn on
	 */
	public StringCanvas ln()
	{
		append(System.lineSeparator());
		padding(leftPadding);
		return this;
	}
}
