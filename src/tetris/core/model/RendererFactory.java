package tetris.core.model;

public abstract class RendererFactory
{
	/**
	 * build a renderer for a given playfield
	 * @param field the playfield to render
	 * @return the renderer built
	 */
	public abstract Renderer build(PlayField field);
}
