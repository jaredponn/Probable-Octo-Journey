package poj.Render;

import java.util.PriorityQueue;
import java.util.Comparator;

// docs: https://docs.oracle.com/javase/7/docs/api/java/util/PriorityQueue.html
class RenderObjectComparator implements Comparator<RenderObject>
{
	public int compare(RenderObject a, RenderObject b)
	{
		if (a.getY() < b.getY()) {
			return -1;
		}

		else if (a.getY() == b.getY()) {
			return 0;
		}
		// a is greater than b
		else {
			return 1;
		}
	}
}

public class MinYFirstSortedRenderObjectBuffer
	extends PriorityQueue<RenderObject>
{
	private static final long serialVersionUID = 0l;

	private static final int INIT_CAPACITY = 5000;
	private static final Comparator<RenderObject> comparator =
		new RenderObjectComparator();

	public MinYFirstSortedRenderObjectBuffer()
	{
		super(INIT_CAPACITY, comparator);
	}

	// push object should be used after the camera
	public void pushObject(RenderObject r)
	{
		add(r);
	}
}
