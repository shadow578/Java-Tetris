package tetris.core.model;

public abstract class Renderer
{

	/**
	 * initialize a renderer for the given play field
	 * @param playField the field to render
	 */
	public Renderer(PlayField playField)
	{
	}
	
	/**
	 * draw the game
	 * @param movingShape the current dynamic (moving) shape to draw on top of the game field
	 * @param currentScore the player's current score
	 */
	public abstract void draw(Shape movingShape, double currentScore);
	
	/**
	 * draw the game over screen
	 * @param score the final score the player reached
	 */
	public abstract void drawGameOver(double score);
}
