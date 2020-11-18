package tetris.core.renderers.console;

import com.diogonunes.jcolor.Attribute;

import tetris.core.model.PlayField;
import tetris.core.model.Renderer;
import tetris.core.model.Shape;

public class ConsoleRenderer extends Renderer
{
	/**
	 * char to use for a blank
	 */
	static final char BLANK = ' ';

	/**
	 * char to use for a static block
	 */
	static final char BLOCK_STATIC = '█';

	/**
	 * char to use for a moving block
	 */
	static final char BLOCK_MOVING = '▓';

	/**
	 * how wide blocks are rendered
	 */
	static final char BLOCK_WIDTH = 2;

	/**
	 * how many chars are added to the left for padding
	 */
	static final int LEFT_PADDING = 3;

	/**
	 * should we render in color?
	 * Only works in good terminals, not eclipse's integrated one
	 */
	static final boolean ENABLE_COLOR_RENDERING = true;

	/**
	 * the play field to render
	 */
	final PlayField field;

	/**
	 * the canvas we draw on
	 */
	final StringCanvas canvas;

	/**
	 * initialize a renderer for the given play field
	 * @param playField the field to render
	 */
	public ConsoleRenderer(PlayField playField)
	{
		super(playField);
		field = playField;
		canvas = new StringCanvas();
	}

	/**
	 * draw the play field
	 * @param ms the currently moving shape
	 * @param score the players score
	 */
	public void draw(Shape ms, double score)
	{
		// clear the canvas
		canvas.setLeftPadding(LEFT_PADDING)
				.clear()

				// first, draw a header line
				.header(getFieldRenderWidth())

				// then draw the score with a separator line
				.stringCentered(String.format("Score: %.2f", score), getFieldRenderWidth())
				.separator(getFieldRenderWidth());

		// then draw the game field
		for (int y = 0; y < field.getHeight(); y++)
		{
			// draw the left rail
			canvas.vRail();

			// then the tetris line
			drawTetrisYLine(ms, y);

			// last the right rail with a line break
			canvas.vRail().ln();
		}

		// draw the footer line
		canvas.footer(getFieldRenderWidth());

		// draw canvas to console
		clearConsole();
		System.out.print(canvas.toString());
	}

	/**
	 * draw the game over screen
	 * @param score the final score the player reached
	 */
	public void drawGameOver(double score)
	{
		// clear the canvas
		canvas.setLeftPadding(LEFT_PADDING)
				.clear()

				// start with a header line
				.header(getFieldRenderWidth())

				// draw a blank line with rails
				.blank(getFieldRenderWidth())

				// then draw "GAME OVER" centered
				.stringCentered("Game Over", getFieldRenderWidth())

				// another blank line with rails
				.blank(getFieldRenderWidth())

				// now draw the score, centered
				.stringLeft(String.format("Score: %.2f", score), getFieldRenderWidth())

				// followed by another blank line
				.blank(getFieldRenderWidth())

				// ... and the footer line
				.footer(getFieldRenderWidth());

		// draw canvas to console
		clearConsole();
		System.out.print(canvas.toString());
	}

	/**
	 * draw a Y line of the tetris game field
	 * @param ms the current moving shape to overlay
	 * @param y the Y value to draw the line of
	 * @return the canvas drawn on
	 */
	void drawTetrisYLine(Shape ms, int y)
	{
		for (int x = 0; x < field.getWidth(); x++)
		{
			// get block from play field to draw (meta for metadata- information and render
			// for what we actuall draw)
			char blockMeta = field.get(x, y);
			char blockRender = blockMeta == PlayField.BLANK ? BLANK : BLOCK_STATIC;

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
				{
					blockMeta = mblocks[mx][my];
					blockRender = BLOCK_MOVING;
				}
			}

			// get color for char
			Attribute color = getColorForMeta(blockMeta);

			// draw the block (colored, maybe?)
			for (int w = 0; w < BLOCK_WIDTH; w++)
				if (ENABLE_COLOR_RENDERING && color != null)
				{
					// draw in color
					canvas.setColorEnabled(true)
							.setForegroundColor(color)
							.append(blockRender + "")
							.setColorEnabled(false);
				}
				else // draw without color
					canvas.append(blockRender + "");
		}
	}

	/**
	 * get the color attributes for a (meta) char
	 * 
	 * b for blue
	 * s for black
	 * c for cyan
	 * g for green
	 * m for magenta
	 * r for red
	 * w for white
	 * y for yellow
	 * 
	 * @param meta the meta char to use (case- insensitive)
	 * @return the color attributes, or null if invalid meta char
	 */
	Attribute getColorForMeta(char meta)
	{
		switch (Character.toLowerCase(meta))
		{
		case 'b':
			return Attribute.BLUE_TEXT();
		case 's':
			return Attribute.BLACK_TEXT();
		case 'c':
			return Attribute.CYAN_TEXT();
		case 'g':
			return Attribute.GREEN_TEXT();
		case 'm':
			return Attribute.MAGENTA_TEXT();
		case 'r':
			return Attribute.RED_TEXT();
		case 'w':
			return Attribute.WHITE_TEXT();
		case 'y':
			return Attribute.YELLOW_TEXT();
		default:
			return null;
		}
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
