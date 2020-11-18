package tetris.core.shapes;

import tetris.core.model.PlayField;
import tetris.core.model.Shape;

/**
 * the "O" or "Block" tetris shape
 */
public class ShapeBlock extends Shape
{
	/**
	 * blocks for 0 deg rotation
	 * the "block" shape does not change between rotations, so this is all we need
	 */
	final char[][] ROT_0 = blocksHelper(
			"##",
			"##");

	public ShapeBlock(PlayField playField, int sx, int sy)
	{
		super(playField, sx, sy);
	}

	@Override
	protected char[][] getBlocksForRotation(int rotation)
	{
		return ROT_0;
	}
}
