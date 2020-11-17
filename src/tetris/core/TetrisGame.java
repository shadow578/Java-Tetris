package tetris.core;

import java.util.Random;

import tetris.core.model.PlayField;
import tetris.core.model.Renderer;
import tetris.core.model.Shape;
import tetris.core.shapes.ShapeLine;

public class TetrisGame
{
	/**
	 * the target frame- / updaterate to run the game at
	 */
	final int TARGET_FRAMERATE = 5;

	/**
	 * the field to play on
	 */
	final PlayField field;
	
	/**
	 * the renderer used to draw the field
	 */
	final Renderer renderer;

	/**
	 * a random number generator for the game
	 */
	final Random rng = new Random();

	/**
	 * flag to stop running the game
	 */
	boolean endGame = false;

	/**
	 * the currently held shape
	 */
	Shape currentPiece;

	/**
	 * the current score
	 */
	double score = 0;

	/**
	 * init the tetris game with a default game field size of 10x20 blocks
	 */
	public TetrisGame()
	{
		this(10, 20);
	}

	/**
	 * init the tetris game with a game field size of WxH blocks
	 * @param w the width of the play field
	 * @param h the height of the play field
	 */
	public TetrisGame(int w, int h)
	{
		field = new PlayField(w, h);
		renderer = new TetrisRenderer(field);
	}

	/**
	 * start and play the game until finish
	 * @throws InterruptedException when framerate sleep fails for some reason, idk ¯\_(ツ)_/¯
	 */
	public void play() throws InterruptedException
	{
		// TODO: assign a random piece
		currentPiece = new ShapeLine(field, 0, 0);
		
		// run the main game loop
		long sleepTargetMs = 1000 / TARGET_FRAMERATE;
		while (!endGame)
		{
			// run update and measure time taken
			long updateDuration = System.currentTimeMillis();
			onUpdate();
			updateDuration = System.currentTimeMillis() - updateDuration;

			// sleep some ms to reach target framerate stable
			Thread.sleep(Math.max(0, sleepTargetMs - updateDuration));
		}
	}

	/**
	 * update the game. 
	 * called once every frame.
	 */
	void onUpdate()
	{
		// TODO: handle player movement
		
		//rotation yeah
		currentPiece.rotate();

		// move the current piece down by one
		if (!currentPiece.moveDown())
		{
			// collided while moving, place the piece at the current position
			field.placeShape(currentPiece);
			// TODO: get a new piece
		}

		// TODO: check for complete lines and add them to the score

		// draw screen
		renderer.draw(currentPiece);
	}
}
