package tetris.core.renderers.console;

import tetris.core.model.PlayField;
import tetris.core.model.Shape;

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
	 * draw a Y line of the tetris game field
	 * @param field the tetris playfield to draw
	 * @param ms the current moving shape to overlay
	 * @param y the Y value to draw the line of
	 * @param blockWidth how wide a single block is drawn
	 * @param blank the char to use for a blank block
	 * @param blockStatic the char used for a static block
	 * @param blockMoving the char used for a moving block
	 * @return the canvas drawn on
	 */
	public StringCanvas tetrisYLine(PlayField field, Shape ms,
			int y, int blockWidth,
			char blank, char blockStatic, char blockMoving)
	{
		for (int x = 0; x < field.getWidth(); x++)
		{
			// get block from play field to draw
			char block = field.isBlank(x, y) ? blank : blockStatic;

			// check for block of moving shape
			if (ms != null
					&& ms.getX() <= x
					&& ms.getY() <= y
					&& (ms.getX() + ms.getWidth()) > x
					&& (ms.getY() + ms.getHeight()) > y)
			{
				// inside the shape, get block to draw from shape
				int mx = x - ms.getX();
				int my = y - ms.getY();
				char[][] mblocks = ms.getBlocks();
				if (mblocks[mx][my] != PlayField.BLANK)
					block = blockMoving;
			}

			// draw the block
			for (int w = 0; w < blockWidth; w++)
				canvas.append(block);
		}
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
		canvas.append("║");

		// fill the left of the string with spaces
		int spaceToFill = width - str.length();
		for (int i = 0; i < (spaceToFill / 2); i++)
			canvas.append(" ");

		// draw the string
		canvas.append(str);

		// and fill the right of the string with spaces
		if (spaceToFill % 2 != 0)
			spaceToFill++;
		for (int i = 0; i < (spaceToFill / 2); i++)
			canvas.append(" ");

		// ... and end with the right "rail"
		canvas.append("║");
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
		canvas.append("║");

		// ... print the string and fill to width with spaces ...
		canvas.append(str);
		for (int i = 0; i < (width - str.length()); i++)
			canvas.append(" ");

		// ... and end with the right "rail"
		canvas.append("║");
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
		canvas.append("╔");
		for (int i = 0; i < width; i++)
			canvas.append("═");
		canvas.append("╗");
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
		canvas.append("╠");
		for (int i = 0; i < width; i++)
			canvas.append("═");
		canvas.append("╣");
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
		canvas.append("╚");
		for (int i = 0; i < width; i++)
			canvas.append("═");
		canvas.append("╝");
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
		canvas.append("║");
		for (int i = 0; i < width; i++)
			canvas.append(" ");
		canvas.append("║");
		ln();
		return this;
	}

	/**
	 * draw a vertical rail ("║") to the canvas
	 * @return the canvas drawn on
	 */
	public StringCanvas vRail()
	{
		canvas.append("║");
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
			canvas.append(' ');
		return this;
	}

	/**
	 * draw a newline to the canvas
	 * @return the canvas drawn on
	 */
	public StringCanvas ln()
	{
		canvas.append(System.lineSeparator());
		padding(leftPadding);
		return this;
	}
}
