package poj.Render;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.geom.AffineTransform;

import java.util.Queue;
import java.util.LinkedList;

import poj.Logger;
import poj.LogLevels;

// Most of the code here was driven by the following articles:
// https://docs.oracle.com/javase/7/docs/api/java/awt/image/BufferStrategy.html
// https://docs.oracle.com/javase/tutorial/extra/fullscreen/bufferstrategy.html
// https://www.gamedev.net/articles/programming/general-and-gameplay-programming/java-games-active-rendering-r2418/

public class Renderer
{
	private BufferStrategy bufferStrat;

	private GraphicsContext graphicsContext;

	// off screen drawing surface
	private BufferedImage bufferedImage;

	// data buffer
	private Queue<RenderObject> renderBuffer;

	// bg color related vars
	private Color backgroundColor;
	private int width;
	private int height;

	public Renderer(GameCanvas gc)
	{
		this.graphicsContext = new GraphicsContext();
		this.bufferStrat = gc.getBufferStrategy();
		setBufferedImageFromCanvas(gc);
		this.width = gc.getWidth();
		this.height = gc.getHeight();

		this.renderBuffer = new LinkedList<RenderObject>();
	}

	public void setBufferedImageFromCanvas(GameCanvas gc)
	{
		bufferedImage =
			graphicsContext.graphicsConfig.createCompatibleImage(
				gc.getWidth(), gc.getHeight());
		width = gc.getWidth();
		height = gc.getHeight();
	}

	public void pushRenderObject(RenderObject r)
	{
		renderBuffer.add(r);
	}

	public void setClearColor(Color c)
	{
		backgroundColor = c;
	}

	public void render()
	{
		Graphics g = null;
		Graphics2D g2d = null;

		// clear the color
		g2d = bufferedImage.createGraphics();
		g2d.setColor(this.backgroundColor);
		g2d.fillRect(0, 0, this.width, this.height);

		do {
			do {
				while (!this.renderBuffer.isEmpty()) {
					final RenderObject t =
						renderBuffer.remove();
					// deprecated
					if (t.getRenderObjectType()
					    == RenderRect.class) {
						renderRect((RenderRect)t, g2d);
					}

					else if (t.getRenderObjectType()
						 == ImageRender.class) {
						renderImageRender(
							(ImageRender)t, g2d);
					} else if (t.getRenderObjectType()
						   == RenderString.class) {
						renderStr((RenderString)t, g2d);

					} else {
						Logger.logMessage(
							"Error in renderer -- unknown render object type",
							LogLevels
								.MINOR_CRITICAL);
					}
				}

				g = bufferStrat.getDrawGraphics();
				g.drawImage(bufferedImage, 0, 0, null);

				g.dispose();
				g2d.dispose();

			} while (bufferStrat.contentsRestored());

			bufferStrat.show();

		} while (bufferStrat.contentsLost());
	}

	private void renderImageRender(ImageRender n, Graphics2D g2d)
	{
		g2d.drawImage(
			n.getImage().getSubimage(
				n.getImageWindow().getX(),
				n.getImageWindow().getY(),
				n.getImageWindow().getWidth(),
				n.getImageWindow().getHeight()),
			new AffineTransform(1f, 0f, 0f, 1f, n.getX(), n.getY()),
			null);
	}

	// deprecated
	private void renderRect(RenderRect n, Graphics2D g2d)
	{
		g2d.setColor(n.getColor());
		g2d.drawRect(n.getX(), n.getY(), n.getWidth(), n.getHeight());
	}

	private void renderStr(RenderString n, Graphics2D g2d)
	{
		g2d.setColor(n.getColor());
		g2d.setFont(n.getFont());
		g2d.drawString(n.getStr(), n.getX(), n.getY());
	}
}
