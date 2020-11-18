package tetris.core;

import java.util.Random;

import tetris.core.model.PlayField;
import tetris.core.model.Shape;
import tetris.core.shapes.*;

public final class ShapeRegistry
{
	/**
	 * get a new, random shape and place it onto the play field
	 * @param random random number generator to get a random shape
	 * @param playField the field to place the shape onto
	 * @param sx the x position of the shape
	 * @param sy the y position of the shape
	 * @return a new, random shape that is placed at the specified position onto the play field
	 */
	public static Shape getRandomShape(Random random, PlayField playField, int sx, int sy)
	{
		switch (random.nextInt(10))
		{
		case 0:
		default: // default to a line piece
			return new ShapeLine(playField, sx, sy);
		case 1:
			return new ShapeBlock(playField, sx, sy);
		case 2:
			return new ShapeJ(playField, sx, sy);
		case 3:
			return new ShapeL(playField, sx, sy);
		case 4:
			return new ShapeS(playField, sx, sy);
		case 5:
			return new ShapeT(playField, sx, sy);
		case 6:
			return new ShapeZ(playField, sx, sy);
		}
	}
}
