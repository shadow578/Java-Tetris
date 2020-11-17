package tetris.core.model;

public abstract class Shape
{	
	/**
	 * the playfield we are placed on
	 */
	final PlayField field;

	/**
	 * the position of the shape
	 */
	int x, y;

	/**
	 * rotation of this shape, in degree (0 - 270)
	 */
	int rotation = 0;

	/**
	 * create a new shape with the given starting position
	 * @param field the play field we are on
	 * @param sx the start x coord
	 * @param sy the start y coord
	 */
	public Shape(PlayField playField, int sx, int sy)
	{
		field = playField;
		x = sx;
		y = sy;
	}

	/**
	 * 
	 * @return the x position of the shape
	 */
	public int getX()
	{
		return x;
	}

	/**
	 * 
	 * @return the y position of the shape
	 */
	public int getY()
	{
		return y;
	}

	/**
	 * 
	 * @return the width of the shape
	 */
	public int getWidth()
	{
		return getBlocks().length;
	}

	/**
	 * 
	 * @return the height of the shape
	 */
	public int getHeight()
	{
		return getBlocks()[0].length;
	}

	/**
	 * get the blocks this shape is made of.
	 * (0/0) will be placed at (getX/getY).
	 * use {@code PlayField.BLANK} for blank blocks.
	 * also, keep the size at the absolute minimum!
	 * 
	 * @return the blocks of this shape
	 */
	public abstract char[][] getBlocks();

	/**
	 * rotate the block 90� clockwise
	 */
	public void rotate()
	{
		rotation += 90;
		if (rotation >= 360)
			rotation = 0;
	}

	/**
	 * try to move the block down by one
	 * @return was the move successfull? if false, we probably collided with something
	 */
	public boolean moveDown()
	{
		// move down and check for collision
		y++;
		if (!field.checkCollision(this))
			return true;

		// we collided, undo movement
		y--;
		return false;
	}

	/**
	 * move the shape horizontally by {@code amount} units.
	 * if {@code amount} is less than 0, move to the left. if more than 0, move to the right.
	 * => so amount= -1 is 1 to the left, and amount= 1 is 1 to the right
	 * @param amount the amount to move
	 * @return was the move successfull? if false, we probably collided with something
	 */
	public boolean moveHorizontal(int amount)
	{
		// move and check for collision
		x += amount;
		if (!field.checkCollision(this))
			return true;

		// we collided! undo movement
		x -= amount;
		return false;
	}
}
