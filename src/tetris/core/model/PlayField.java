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
}
