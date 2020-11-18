package tetris.core.shapes;

import tetris.core.model.PlayField;
import tetris.core.model.Shape;

/**
 * the "O" or "Block" tetris shape
 */
public class ShapeBlock extends Shape
{
	/**
	 * the meta char to use for this shape
	 */
	final char META = 'y';
	
	/**
	 * blocks for 0 deg rotation
	 * the "block" shape does not change between rotations, so this is all we need
	 */
	final char[][] ROT_0 = blocksHelper(META,
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
