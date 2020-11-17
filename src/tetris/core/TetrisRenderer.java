package tetris.core;

import tetris.core.model.PlayField;
import tetris.core.model.Renderer;
import tetris.core.model.Shape;

public class TetrisRenderer extends Renderer
{
	/**
	 * char to use for a blank
	 */
	static final char BLANK = ' ';

	/**
	 * char to use for a block
	 */
	static final char BLOCK = '█';

	/**
	 * how wide blocks are rendered
	 */
	static final char BLOCK_WIDTH = 2;

	/**
	 * the play field to render
	 */
	final PlayField field;

	/**
	 * initialize a renderer for the given play field
	 * @param playField the field to render
	 */
	public TetrisRenderer(PlayField playField)
	{
		super(playField);

		field = playField;
	}

	/**
	 * draw the play field
	 * @param ms the currently moving shape
	 * @param score the players score
	 */
	public void draw(Shape ms, double score)
	{
		/*
		 Chars used for rendering, copy&paste: 
		╔══╦══╗
		║  ║  ║
		╠══╬══╣
		║  ║  ║
		╚══╩══╝
		█
		*/

		// render the field to a StringBuilder first
		StringBuilder canvas = new StringBuilder();

		// draw a header line first
		drawHeader(canvas);

		// then the score
		drawScore(canvas, score);

		// draw the game field
		for (int y = 0; y < field.getHeight(); y++)
		{
			// start with the left "rail"
			canvas.append("  ║");

			// then draw tetris game field
			drawTetrisYLine(canvas, y, ms);

			// end with the right "rail" and a new line
			canvas.append("║" + System.lineSeparator());
		}

		// draw a footer line last
		drawFooter(canvas);

		// draw canvas to console
		clearConsole();
		System.out.print(canvas.toString());
	}

	/**
	 * draw a Y line of the tetris game field
	 * @param canvas the canvas to draw to
	 * @param y the Y value to draw the line of
	 * @param ms the current moving shape to overlay
	 */
	void drawTetrisYLine(StringBuilder canvas, int y, Shape ms)
	{
		for (int x = 0; x < field.getWidth(); x++)
		{
			// get block from play field to draw
			char block = field.isBlank(x, y) ? BLANK : BLOCK;

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
					block = BLOCK;
			}

			// draw the block
			for (int w = 0; w < BLOCK_WIDTH; w++)
				canvas.append(block);
		}
	}

	/**
	 * draw the score to the canvas
	 * @param canvas the canvas to draw to
	 * @param score the score to display
	 */
	void drawScore(StringBuilder canvas, double score)
	{
		// format score into a string for rendering
		String scoreStr = String.format("Score: %.2f", score);

		// start with the left "rail"...
		canvas.append("  ║");

		// ... print the score string and fill to width with spaces ...
		canvas.append(scoreStr);
		for (int i = 0; i < (getFieldRenderWidth() - scoreStr.length()); i++)
			canvas.append(" ");

		// ... and end with the right "rail"
		canvas.append("║" + System.lineSeparator());

		// bottom line
		canvas.append("  ╠");
		for (int i = 0; i < getFieldRenderWidth(); i++)
			canvas.append("═");
		canvas.append("╣" + System.lineSeparator());
	}

	/**
	 * draw a header and newline to the canvas
	 * @param canvas the canvas to draw to
	 */
	void drawHeader(StringBuilder canvas)
	{
		canvas.append("  ╔");
		for (int i = 0; i < getFieldRenderWidth(); i++)
			canvas.append("═");
		canvas.append("╗" + System.lineSeparator());
	}

	/**
	 * draw a footer and newline to the canvas
	 * @param canvas the canvas to draw to
	 */
	void drawFooter(StringBuilder canvas)
	{
		canvas.append("  ╚");
		for (int i = 0; i < getFieldRenderWidth(); i++)
			canvas.append("═");
		canvas.append("╝" + System.lineSeparator());
	}

	/**
	 * Clear the console. Only works in CMD and Terminal, not eclipse integrated console
	 */
	void clearConsole()
	{
		System.out.print("\033[H\033[2J" + System.lineSeparator());
		System.out.flush();
	}

	/**
	 * 
	 * @return the rendered width of the game field. Use this as the field may be rendered with double width
	 */
	int getFieldRenderWidth()
	{
		return field.getWidth() * BLOCK_WIDTH;
	}
}
