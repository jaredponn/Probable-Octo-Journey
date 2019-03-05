package poj.GameWindow;

import java.awt.*;

/**
 * GraphicsContext -- POD data type to contain all the graphics related
 * contexts. Date: February 20, 2019
 * @author  Jared
 * @version  1.0
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
