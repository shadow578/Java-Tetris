package tetris.core.model;

public class PlayField
{
	/**
	 * the char representing a blank field. If a field is not blank, it is considered taken
	 */
	public static final char BLANK = ' ';

	/**
	 * the internal play field.
	 * This contains all blocks that are non-moving (static)
	 * [x][y]
	 * 
	 * x is left (0) to right (n) (like in maths)
	 * y is top (0) to bottom (n) (inverse to maths)
	 * => (0/0) is the top-left corner
	 */
	private char[][] staticField;

	/**
	 * the width and height of the play field
	 */
	private int width, height;

	/**
	 * create a play field with the default dimensions of 10x20
	 */
	public PlayField()
	{
		this(10, 20);
	}

	/**
	 * create a play field with the given dimensions
	 * @param w the width of the field
	 * @param h the height of the field
	 */
	public PlayField(int w, int h)
	{
		// init field
		staticField = new char[w][h];
		width = w;
		height = h;

		// clear field
		for (int x = 0; x < w; x++)
			for (int y = 0; y < h; y++)
				set(x, y, BLANK);
	}

	/**
	 * 
	 * @return the width of the field
	 */
	public int getWidth()
	{
		return width;
	}

	/**
	 * 
	 * @return the height of the field
	 */
	public int getHeight()
	{
		return height;
	}

	/**
	 * get the char at the given point
	 * @param x x value of the point to get
	 * @param y y value of the point to get
	 * @return the char at that position
	 */
	public char get(int x, int y)
	{
		return staticField[x][y];
	}

	/**
	 * set the char at the given point
	 * @param x x value of the point to set
	 * @param y y value of the point to set
	 * @param c the char to set
	 */
	public void set(int x, int y, char c)
	{
		staticField[x][y] = c;
	}

	/**
	 * check if a block at the given point is blank
	 * @param x x value of the point to check
	 * @param y y value of the point to check
	 * @return is the block at that point blank?
	 */
	public boolean isBlank(int x, int y)
	{
		return get(x, y) == BLANK;
	}
	
	/**
	 * place a shape on the play field. 
	 * This does NOT do any collision checks. do those beforehand with isColliding().
	 * This, however, does bound checks using isOutOfBounds(). if out of bounds, nothing is placed
	 * @param shape the shape to place
	 */
	public void placeShape(Shape shape)
	{
		// do not place if out of bounds
		if (isOutOfBounds(shape))
			return;

		// get shape details
		int sx = shape.getX();
		int sy = shape.getY();
		int sw = shape.getWidth();
		int sh = shape.getHeight();
		char[][] blocks = shape.getBlocks();

		// add non- blank blocks to field
		for (int x = 0; x < sw; x++)
			for (int y = 0; y < sh; y++)
				if (blocks[x][y] != BLANK)
					set(x + sx, y + sy, blocks[x][y]);
	}

	/**
	 * check if any block of the shape is out of bounds of the play field
	 * @param shape the shape to check
	 * @return is the shape out of bounds?
	 */
	public boolean isOutOfBounds(Shape shape)
	{
		// get shape details
		int sx = shape.getX();
		int sy = shape.getY();
		int sw = shape.getWidth();
		int sh = shape.getHeight();
		char[][] blocks = shape.getBlocks();

		// check if any (non- blank) block is out of bounds
		// abort on the first out- of- bounds block
		for (int x = 0; x < sw; x++)
			for (int y = 0; y < sh; y++)
				if (blocks[x][y] != BLANK
						&& isOutOfBounds(x + sx, y + sy))
					return true; // block is out of bounds!

		// no block was out of bounds
		return false;
	}

	/**
	 * check if the shape is out of bounds of the play field or collides with any static blocks
	 * @param shape the shape to check
	 * @return does the shape collide?
	 */
	public boolean checkCollision(Shape shape)
	{
		// check out of bounds (counts as collision)
		if (isOutOfBounds(shape))
			return true;

		// get shape details
		int sx = shape.getX();
		int sy = shape.getY();
		int sw = shape.getWidth();
		int sh = shape.getHeight();
		char[][] blocks = shape.getBlocks();

		// check if any non- blank shape block overlaps with any non- blank static block
		for (int x = 0; x < sw; x++)
			for (int y = 0; y < sh; y++)
				if (blocks[x][y] != BLANK && !isBlank(x + sx, y + sy))
					return true;

		// no collision found
		return false;
	}
	
	/**
	 * remove all complete lines and mvoe all blocks above down by one
	 * @return the number of full lines removed
	 */
	public int removeCompleteLines()
	{
		// check all lines in reverse order
		int lns = 0;
		for (int y = getHeight() - 1; y >= 0; y--)
		{
			// check this line is complete
			// if there is ANY blank in this line, it cannot be complete
			boolean isLineComplete = true;
			for (int x = 0; x < getWidth(); x++)
				if (isBlank(x, y))
					isLineComplete = false;

			// skip if this line is NOT complete
			if (!isLineComplete)
				continue;

			// the line is complete, move all above lines down by one
			// also count this line into the completed lines removed
			lns++;

			// move lines above this one down by one
			for (int movY = y; movY >= 0; movY--)
				for (int x = 0; x < getWidth(); x++)
					if (movY > 0)
						set(x, movY, get(x, movY - 1)); // not at the topmost line, copy line above down
					else
						set(x, movY, BLANK); // we copy into the topmost line: fill it blank because there are no lines above
												// the topmost line

			// done with this line, but recheck again
			// this is required because we may have moved down a line that is also complete,
			// which would go unchecked if we don't recheck
			y++;
		}

		return lns;
	}

	/**
	 * check if the x/y point is out ouf bounds of the play field
	 * @param x the x coord of the point
	 * @param y the y coord of the point
	 * @return is the point out of bounds?
	 */
	boolean isOutOfBounds(int x, int y)
	{
		return x < 0 || x >= width
				|| y < 0 || y >= height;
	}
}
