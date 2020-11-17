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
	double x, y;

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
		return (int)x;
	}

	/**
	 * 
	 * @return the y position of the shape
	 */
	public int getY()
	{
		return (int)y;
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
	public char[][] getBlocks()
	{
		return getBlocksForRotation(rotation);
	}

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
	 * @param howMuch by how much to move down. used to change the speed we move at
	 * @return was the move successfull? if false, we probably collided with something
	 */
	public boolean moveDown(double howMuch)
	{
		// move down and check for collision
		y += howMuch;
		if (!field.checkCollision(this))
			return true;

		// we collided, undo movement
		y -= howMuch;
		return false;
	}

	/**
	 * move the shape horizontally by {@code amount} units.
	 * if {@code amount} is less than 0, move to the left. if more than 0, move to the right.
	 * => so amount= -1 is 1 to the left, and amount= 1 is 1 to the right
	 * @param amount the amount to move
	 * @return was the move successfull? if false, we probably collided with something
	 */
	public boolean moveHorizontal(double amount)
	{
		// move and check for collision
		x += amount;
		if (!field.checkCollision(this))
			return true;

		// we collided! undo movement
		x -= amount;
		return false;
	}

	/**
	 * get the shape's blocks for a given rotation
	 * @param rotation the rotation of the shape, in degree (0 - 270)
	 * @return the blocks for a given rotation
	 */
	protected abstract char[][] getBlocksForRotation(int rotation);

	/**
	 * helper to construct a char[][] blocks array from strings
	 * Make sure that all strings have the same length!
	 * 
	 * Usage Example:
	 * {@code blocksHelper("  # ", "  # ", "  # ", "  # ")}
	 * 
	 * @param lines the y lines of the shape
	 * @return the shape's char[][] blocks
	 */
	protected char[][] blocksHelper(String... lines)
	{
		// prepare blocks array
		int w = lines[0].length();
		int h = lines.length;
		char[][] blocks = new char[w][h];

		// copy chars into blocks array
		for (int x = 0; x < w; x++)
			for (int y = 0; y < h; y++)
				blocks[x][y] = lines[y].charAt(x);

		return blocks;
	}
}
