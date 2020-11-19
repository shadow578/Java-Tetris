package tetris.core.renderers.console;

import tetris.core.model.PlayField;
import tetris.core.model.Renderer;
import tetris.core.model.RendererFactory;

public class ConsoleRendererFactory extends RendererFactory
{
	/**
	 * should the renderer render in color?
	 */
	boolean enableColor = false;
	
	@Override
	public Renderer build(PlayField field)
	{
		ConsoleRenderer renderer = new ConsoleRenderer(field);
		renderer.enableColorRendering = enableColor;
		return renderer;
	}
	
	/**
	 * enable or disable color rendering
	 * @param enable should color rendering be enabled or disable?
	 * @return the factory instance
	 */
	public ConsoleRendererFactory setColorEnabled(boolean enable) {
		enableColor = enable;
		return this;
	}
}