package tetris.core.renderers.console;

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
			canvas.vRail()

					// then the tetris line
					.tetrisYLine(field, ms, y, BLOCK_WIDTH, BLANK, BLOCK_STATIC, BLOCK_MOVING)

					// last the right rail with a line break
					.vRail().ln();
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
