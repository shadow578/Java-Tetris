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
	static final char BLOCK = '#';

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
	 */
	public void draw(Shape ms)
	{
		// render the field to a StringBuilder first
		StringBuilder canvas = new StringBuilder();
		for (int y = 0; y < field.getHeight(); y++)
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
				canvas.append(block);
			}

			// continue in new line
			canvas.append(System.lineSeparator());
		}

		// draw canvas to console
		clearConsole();
		System.out.print(canvas.toString());
	}

	/**
	 * Clear the console. Only works in CMD and Terminal, not eclipse integrated console
	 */
	void clearConsole()
	{
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}
}
