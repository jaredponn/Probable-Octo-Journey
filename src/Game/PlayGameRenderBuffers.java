package Game;

import java.util.ArrayList;
import poj.Render.*;

public class PlayGameRenderBuffers
{

	protected ArrayList<ArrayList<RenderObject>> buffers;

	protected static final int groundBuf = 0;
	protected static final int entityBuf = 1;
	protected static final int buildingBuf = 2;
	protected static final int poleBuf = 3;
	protected static final int debugBuf = 4;
	protected static final int guiBuf = 5;

	protected static final int DEFAULT_BUF_SIZE = 1000; // overkill
	protected static final int DEFAULT_NUM_BUFFERS = guiBuf + 1;

	protected PlayGameRenderBuffers()
	{
		buffers = new ArrayList<ArrayList<RenderObject>>(
			DEFAULT_NUM_BUFFERS);

		for (int i = 0; i < DEFAULT_NUM_BUFFERS; ++i) {
			buffers.add(
				new ArrayList<RenderObject>(DEFAULT_BUF_SIZE));
		}
	}

	protected ArrayList<RenderObject> getBuf(int i)
	{
		return buffers.get(i);
	}

	protected int size()
	{
		int acc = 0;

		for (ArrayList<RenderObject> arr : buffers) {
			acc += arr.size();
		}
		return acc;
	}
}
