package poj.Render;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

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
