package Game;

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

	public RenderThread(Renderer r)
	{
		unfocus = new PlayGameRenderBuffers();
		focus = new PlayGameRenderBuffers();
		this.renderer = r;
	}

	public void startThread()
	{
		System.out.println("Render thread created");
		this.threadRunning = true;
		super.start();
	}

	public void endThread()
	{
		this.threadRunning = false;
	}


	public void startRendering()
	{
		isRendering = true;
	}

	public void stopRendering()
	{
		isRendering = false;
	}

	public void swapBuffers()
	{
		PlayGameRenderBuffers tmp = focus;
		focus = unfocus;
		unfocus = tmp;
	}

	public PlayGameRenderBuffers getUnfocusedBuffer()
	{
		return unfocus;
	}

	public boolean isRendering()
	{
		return isRendering;
	}

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


	protected PlayGameRenderBuffers getFocusedBuffer()
	{
		return focus;
	}

	protected int getFocusedBufferSize()
	{
		return focus.size();
	}
}
