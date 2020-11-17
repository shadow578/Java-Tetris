package tetris.core.shapes;

import tetris.core.model.PlayField;
import tetris.core.model.Shape;

public class ShapeLine extends Shape
{

	public ShapeLine(PlayField playField, int sx, int sy)
	{
		super(playField, sx, sy);
	}

	@Override
	public char[][] getBlocks()
	{
		return new char[][]
		{
				{ '#' },
				{ '#' },
				{ '#' },
				{ '#' }
		};
	}

}
