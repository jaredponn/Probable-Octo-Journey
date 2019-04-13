package Game;
/**
 * Renderthread
 * Date: February 10, 2019
 * @author Jared
 * @version 1.0
 */

import poj.Render.*;
import poj.Time.Timer;

public class RenderThread extends Thread
{
	protected PlayGameRenderBuffers unfocus;
	protected PlayGameRenderBuffers focus;

	/// reference to the renderer
	protected Renderer renderer;

	protected volatile boolean threadRunning = false;
	protected volatile boolean isRendering = false;

	/**
	 *  constructor
	 *  @param r : renderer
	 */
	public RenderThread(Renderer r)
	{
		unfocus = new PlayGameRenderBuffers();
		focus = new PlayGameRenderBuffers();
		this.renderer = r;
	}

	/**
	 *  starts thread
	 */
	public void startThread()
	{
		System.out.println("Render thread created");
		this.threadRunning = true;
		super.start();
	}

	/**
	 *  end thread
	 */
	public void endThread()
	{
		this.threadRunning = false;
	}


	/**
	 *  start rendering
	 */
	public void startRendering()
	{
		isRendering = true;
	}

	/**
	 *  stop rendering
	 */
	public void stopRendering()
	{
		isRendering = false;
	}

	/**
	 *  swap buffers
	 */
	public void swapBuffers()
	{
		PlayGameRenderBuffers tmp = focus;
		focus = unfocus;
		unfocus = tmp;
	}

	/**
	 *  get unfocused ubfer
	 *  @return unfocused buffesr
	 */
	public PlayGameRenderBuffers getUnfocusedBuffer()
	{
		return unfocus;
	}

	/**
	 *  is rendering?
	 *  @return boolean
	 */
	public boolean isRendering()
	{
		return isRendering;
	}

	/**
	 *  run
	 */
	public void run()
	{
		while (threadRunning) {
			// focus.getBuf(PlayGameRenderBuffers.debugBuf).clear();

			if (isRendering) {
				renderer.renderBufferLists(
					focus.getBuf(PlayGameRenderBuffers
							     .groundBuf),
					focus.getBuf(PlayGameRenderBuffers
							     .entityBuf),
					focus.getBuf(PlayGameRenderBuffers
							     .buildingBuf),
					focus.getBuf(
						PlayGameRenderBuffers.poleBuf),
					focus.getBuf(
						PlayGameRenderBuffers.debugBuf),
					focus.getBuf(
						PlayGameRenderBuffers.guiBuf));

				this.stopRendering();
			}
		}
	}


	/**
	 *  get focused buffer
	 *  @return focusedbuffer
	 */
	protected PlayGameRenderBuffers getFocusedBuffer()
	{
		return focus;
	}

	/**
	 *  get focused buffer size
	 *  @return int -- size
	 */
	protected int getFocusedBufferSize()
	{
		return focus.size();
	}
}
