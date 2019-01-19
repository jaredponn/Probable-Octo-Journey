package poj.Render;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import java.util.Stack;

public class Renderer
{
	private BufferStrategy bufferStrat;

	private GraphicsContext graphicsContext;

	// off screen drawing surface
	private BufferedImage bufferedImage;

	// data buffers
	private Stack<RenderRect> rectBuffer;

	public Renderer(GameCanvas gc)
	{
		graphicsContext = new GraphicsContext();
		bufferStrat = gc.getBufferStrategy();
		setBufferedImageFromCanvas(gc);

		rectBuffer = new Stack<RenderRect>();
	}

	public void setBufferedImageFromCanvas(GameCanvas gc)
	{
		bufferedImage =
			graphicsContext.graphicsConfig.createCompatibleImage(
				gc.getWidth(), gc.getHeight());
	}

	public void pushRenderRect(RenderRect r)
	{
		rectBuffer.push(r);
	}

	public void render()
	{
		// Objects needed for rendering...
		Graphics graphics = null;
		Graphics2D g2d = null;
		Color background = Color.BLACK;

		while (true) {
			// prepare rendering for next frame
			g2d = bufferedImage.createGraphics();
			g2d.setColor(background);
			g2d.fillRect(0, 0, 639, 479);

			// render a single frame
			do {
				do {
					// render to grahpics
					g2d.setColor(Color.RED);
					g2d.drawString("yo", 2, 3);
					g2d.drawRect(100, 100, 300, 300);


					graphics =
						bufferStrat.getDrawGraphics();
					graphics.drawImage(bufferedImage, 0, 0,
							   null);


					// dispose trhe graphics
					graphics.dispose();
					g2d.dispose();
				} while (bufferStrat.contentsRestored());

				bufferStrat.show();

			} while (bufferStrat.contentsLost());
		}
	}
}
