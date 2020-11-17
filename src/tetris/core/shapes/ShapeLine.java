package tetris.core.shapes;

import tetris.core.model.PlayField;
import tetris.core.model.Shape;

/**
 * the "Line" tetris shape
 */
public class ShapeLine extends Shape
{
	/**
	 * blocks for 0� rotation
	 */
	final char[][] ROT_0 = blocksHelper(
			"  # ",
			"  # ",
			"  # ",
			"  # ");

	/**
	 * blocks for 90� rotation
	 */
	final char[][] ROT_90 = blocksHelper(
			"    ",
			"    ",
			"####",
			"    ");

	/**
	 * blocks for 180� rotation
	 */
	final char[][] ROT_180 = blocksHelper(
			" #  ",
			" #  ",
			" #  ",
			" #  ");

	/**
	 * blocks for 270� rotation
	 */
	final char[][] ROT_270 = blocksHelper(
			"    ",
			"####",
			"    ",
			"    ");

	public ShapeLine(PlayField playField, int sx, int sy)
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
