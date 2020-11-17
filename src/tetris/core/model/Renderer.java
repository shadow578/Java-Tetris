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
	 * draw the play field
	 * @param movingShape the current dynamic (moving) shape to draw on top of the game field
	 */
	public abstract void draw(Shape movingShape);
}
