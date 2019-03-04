package poj.Render;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.IndexColorModel;
import java.awt.image.RescaleOp;
import java.awt.geom.AffineTransform;

import java.util.Queue;
import java.util.LinkedList;

import poj.GameWindow.GameCanvas;
import poj.GameWindow.GraphicsContext;
import poj.Logger.LogLevels;
import poj.Logger.Logger;

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

	/**
	 * Renderer constructor
	 *
	 * @param  gc game canvas
	 */
	public Renderer(GameCanvas gc)
	{
		this.graphicsContext = new GraphicsContext();
		this.bufferStrat = gc.getBufferStrategy();
		setBufferedImageFromCanvas(gc);
		this.width = gc.getWidth();
		this.height = gc.getHeight();

		this.renderBuffer = new LinkedList<RenderObject>();
	}

	/**
	 * Sets the buffered image for an off the screen drawing surface
	 *
	 * @param  gc game canvas
	 */
	public void setBufferedImageFromCanvas(GameCanvas gc)
	{
		bufferedImage =
			graphicsContext.graphicsConfig.createCompatibleImage(
				gc.getWidth(), gc.getHeight());
		width = gc.getWidth();
		height = gc.getHeight();
	}

	/**
	 * Push render object to the internal queue. WARNING DEPRECATED
	 *
	 * @param  r RenderObject
	 */
	public void pushRenderObject(RenderObject r)
	{
		renderBuffer.add(r);
	}

	/**
	 * Sets the clear color
	 *
	 * @param  Color c
	 */
	public void setClearColor(Color c)
	{
		backgroundColor = c;
	}

	/**
	 * Sets the render buffer
	 *
	 * @param  n render buffer
	 */
	public void setRenderBuffer(Queue<RenderObject> n)
	{
		renderBuffer = n;
	}


	/**
	 * Renders its own render buffer
	 */
	public void render()
	{
		renderBuffers(this.renderBuffer);
	}


	/**
	 * Renders an arbitrary render buffers
	 */
	public void renderBuffers(Queue<RenderObject>... renderBuffers)
	{
		Graphics g = null;
		Graphics2D g2d = null;

		// clear the color
		g2d = bufferedImage.createGraphics();
		g2d.setColor(this.backgroundColor);
		g2d.fillRect(0, 0, this.width, this.height);

		do {
			do {
				for (Queue<RenderObject> rb : renderBuffers) {
					while (!rb.isEmpty()) {
						final RenderObject t =
							rb.remove();
						// deprecated
						if (t.getRenderObjectType()
						    == RenderRect.class) {
							renderRect(
								(RenderRect)t,
								g2d);
						} else if (
							t.getRenderObjectType()
							== ImageRenderObject
								   .class) {
							renderImageRenderObject(
								(ImageRenderObject)
									t,
								g2d);
						} else if (
							t.getRenderObjectType()
							== StringRenderObject
								   .class) {
							renderStringRenderObject(
								(StringRenderObject)
									t,
								g2d);

						} else {
							Logger.logMessage(
								"Error in renderer -- unknown render object type",
								LogLevels
									.MINOR_CRITICAL);
						}
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

	/**
	 * Renders objects with dim. WARNING LONG DEPRECATED
	 */
	private void renderImageRenderObjectWithDim(ImageRenderObject n,
						    Graphics2D g2d)
	{

		// doesn't copy
		BufferedImage subimage = n.getImage().getSubimage(
			n.getImageWindow().getX(), n.getImageWindow().getY(),
			n.getImageWindow().getWidth(),
			n.getImageWindow().getHeight());

		// dest
		BufferedImage copyimage = new BufferedImage(
			n.getImage().getColorModel(),
			n.getImage().getRaster().createCompatibleWritableRaster(
				n.getImageWindow().getWidth(),
				n.getImageWindow().getHeight()),
			n.getImage().isAlphaPremultiplied(), null);


		// copy
		subimage.copyData(copyimage.getRaster());

		// transformations
		n.getRescaleOp().filter(copyimage, copyimage);

		// drawing
		g2d.drawImage(
			copyimage,
			new AffineTransform(1f, 0f, 0f, 1f, n.getX(), n.getY()),
			null);
	}

	/**
	 * Renders an Image Render object.
	 * @param n Image to render
	 * @param g2d graphics object
	 */
	private void renderImageRenderObject(ImageRenderObject n,
					     Graphics2D g2d)
	{

		// doesn't copy
		BufferedImage subimage = n.getImage().getSubimage(
			n.getImageWindow().getX(), n.getImageWindow().getY(),
			n.getImageWindow().getWidth(),
			n.getImageWindow().getHeight());

		// drawing
		g2d.drawImage(
			subimage,
			new AffineTransform(1f, 0f, 0f, 1f, n.getX(), n.getY()),
			null);
	}

	/**
	 * Renders a renderrect
	 * @param n render rect
	 * @param g2d graphics object
	 */
	private void renderRect(RenderRect n, Graphics2D g2d)
	{
		g2d.setColor(n.getColor());
		g2d.drawRect(n.getX(), n.getY(), n.getWidth(), n.getHeight());
	}

	/**
	 * Renders a stringrenderobject
	 * @param n stringrenderobject
	 * @param g2d graphics object
	 */
	private void renderStringRenderObject(StringRenderObject n,
					      Graphics2D g2d)
	{
		g2d.setColor(n.getColor());
		g2d.setFont(n.getFont());
		g2d.drawString(n.getStr(), n.getX(), n.getY());
	}

	/**
	 * Debug renderer --- DEPRECTATED
	 * @param n stringrenderobject
	 * @param g2d graphics object
	 */
	public void debugRender()
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
					} else if (t.getRenderObjectType()
						   == ImageRenderObject.class) {
						renderDebugImageRenderObject(
							(ImageRenderObject)t,
							g2d);
					} else if (t.getRenderObjectType()
						   == StringRenderObject
							      .class) {
						renderStringRenderObject(
							(StringRenderObject)t,
							g2d);

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

	private void renderDebugImageRenderObject(ImageRenderObject n,
						  Graphics2D g2d)
	{

		g2d.setColor(n.getDebugBorderColor());
		g2d.drawRect(n.getX(), n.getY(), n.getImageWindow().getWidth(),
			     n.getImageWindow().getHeight());
		g2d.drawImage(
			n.getImage().getSubimage(
				n.getImageWindow().getX(),
				n.getImageWindow().getY(),
				n.getImageWindow().getWidth(),
				n.getImageWindow().getHeight()),
			new AffineTransform(1f, 0f, 0f, 1f, n.getX(), n.getY()),
			null);
	}
}
