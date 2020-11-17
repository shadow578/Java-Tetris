package tetris.core;

import java.util.Random;

import org.jnativehook.keyboard.NativeKeyEvent;

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
	 * keyboard hook helper to read key down events
	 */
	final KeyboardHelper keyboard;

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
		keyboard = new KeyboardHelper();
	}

	/**
	 * start and play the game until finish
	 * @throws InterruptedException when framerate sleep fails for some reason, idk ¯\_(ツ)_/¯
	 */
	public void play() throws InterruptedException
	{
		// init keyboard hooks
		if (!keyboard.init())
		{
			System.out.println("Error in keyboard initialization! Exiting game...");
			return;
		}

		// put the first piece
		getNewPiece();

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
		// handle left/right movement of the current piece
		handleKeyInput();

		// move the current piece down by one
		if (!currentPiece.moveDown())
		{
			// collided while moving, place the piece at the current position
			field.placeShape(currentPiece);
			getNewPiece();
		}

		// check for complete lines and add them to the score
		score += field.removeCompleteLines() * 10;
		
		// draw screen
		renderer.draw(currentPiece, score);
	}

	/**
	 * handle keyboard intput of LEFT and RIGHT to move the current piece horizontally
	 * @return did the piece collide while moving?
	 */
	boolean handleKeyInput()
	{
		// ignore keyboard input when no piece is falling
		if (currentPiece == null)
			return false;

		// check rotate (up arrow and r)
		if (keyboard.isDown(NativeKeyEvent.VC_UP) || keyboard.isDown(NativeKeyEvent.VC_R))
			currentPiece.rotate();

		// check LEFT movement (left arrow and a)
		int move = 0;
		if (keyboard.isDown(NativeKeyEvent.VC_LEFT) || keyboard.isDown(NativeKeyEvent.VC_A))
			move = -1;

		// check RIGHT movement (right arrow and d)
		if (keyboard.isDown(NativeKeyEvent.VC_RIGHT) || keyboard.isDown(NativeKeyEvent.VC_D))
			move = 1;

		// move the current piece
		return !currentPiece.moveHorizontal(move);
	}

	/**
	 * put a new piece into the currentPiece variable
	 */
	void getNewPiece()
	{
		currentPiece = new ShapeLine(field, 0, 0);
	}
}
