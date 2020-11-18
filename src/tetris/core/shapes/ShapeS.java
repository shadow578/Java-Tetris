package tetris.core.shapes;

import tetris.core.model.PlayField;
import tetris.core.model.Shape;

/**
 * the "S" tetris shape
 */
public class ShapeS extends Shape
{
	/**
	 * blocks for 0 deg rotation
	 */
	final char[][] ROT_0 = blocksHelper(
			" ##",
			"## ",
			"   ");

	/**
	 * blocks for 90 deg rotation
	 */
	final char[][] ROT_90 = blocksHelper(
			" # ",
			" ##",
			"  #");

	/**
	 * blocks for 180 deg rotation
	 */
	final char[][] ROT_180 = blocksHelper(
			"   ",
			" ##",
			"## ");

	/**
	 * blocks for 270 deg rotation
	 */
	final char[][] ROT_270 = blocksHelper(
			"#  ",
			"## ",
			" # ");

	public ShapeS(PlayField playField, int sx, int sy)
	{
		super(playField, sx, sy);
	}

	@Override
	protected char[][] getBlocksForRotation(int rotation)
	{
		if (rotation == 0)
			return ROT_0;
		else if (rotation == 90)
			return ROT_90;
		else if (rotation == 180)
			return ROT_180;
		else
			return ROT_270;
	}
}
