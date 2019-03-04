package poj.GameWindow;

import java.awt.*;

/**
 * POD data type to contain all the graphics related contexts
 */
public class GraphicsContext
{
	public GraphicsEnvironment graphicsEnv;
	public GraphicsDevice graphicsDevice;
	public GraphicsConfiguration graphicsConfig;

	public GraphicsContext()
	{
		graphicsEnv = GraphicsEnvironment.getLocalGraphicsEnvironment();
		graphicsDevice = graphicsEnv.getDefaultScreenDevice();
		graphicsConfig = graphicsDevice.getDefaultConfiguration();
	}
}
