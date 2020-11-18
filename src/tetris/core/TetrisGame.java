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
	final int TARGET_FRAMERATE = 10;

	/**
	 * how fast pieces fall (amount moved every frame)
	 */
	final double FALL_SPEED = 2.5 / TARGET_FRAMERATE;

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
			System.err.println("Error in keyboard initialization! Exiting game...");
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

		// game ended:
		// disable keyboard input
		if (!keyboard.dispose())
			System.err.println("error disposing keyboard! \nYou may have to force- exit the game.");

		// TODO: show gameover screen
		System.out.printf("%nGame Over%nScore: %.2f", score);
	}

	/**
	 * update the game. 
	 * called once every frame.
	 */
	void onUpdate()
	{
		// handle left/right movement of the current piece
		handlePieceMovement();

		// move the piece down
		handlePieceGravity();

		// check for complete lines and add them to the score
		score += field.removeCompleteLines() * 10;

		// draw screen
		renderer.draw(currentPiece, score);
	}

	/**
	 * handles the falling of the current piece, aswell as the "instant fall" button
	 */
	void handlePieceGravity()
	{
		// do not update the piece when there is none
		if (currentPiece == null)
			return;

		// move the current piece down by one
		if (!currentPiece.moveDown(FALL_SPEED))
		{
			// collided while moving, place the piece at the current position
			onPieceCollided();
		}

		// if user pressed DOWN, move the current piece to it's final position
		if (keyboard.wasPressed(NativeKeyEvent.VC_DOWN))
		{
			// move until a collision happens
			while (currentPiece.moveDown(FALL_SPEED))
				;

			// collided while moving, place the piece at the current position
			onPieceCollided();
		}
	}

	/**
	 * handle keyboard intput of LEFT and RIGHT to move the current piece horizontally
	 * @return did the piece collide while moving?
	 */
	boolean handlePieceMovement()
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
	 * called when the current piece collided with something and should be placed
	 */
	void onPieceCollided()
	{
		// place current piece and get a new one
		field.placeShape(currentPiece);
		getNewPiece();

		// check if the new piece collides
		if (field.checkCollision(currentPiece))
		{
			// new piece instantly collided with something, == game- over
			// exit the main game loop and remove the current piece
			endGame = true;
			currentPiece = null;
		}
	}

	/**
	 * put a new piece into the currentPiece variable
	 */
	void getNewPiece()
	{
		currentPiece = new ShapeLine(field, 0, 0);
	}
}
