package poj.Render;

/**
 * MinYFirstSortedRenderObjectBuffer -- a priority queue to sort the insertions
 * of render bojects so the lower part of the screen gets rendered first.
 * Date: February 20, 2019
 * @version 1.0
 * @author Jared and code from:
 * https://docs.oracle.com/javase/7/docs/api/java/util/PriorityQueue.html
 */

import java.util.PriorityQueue;
import java.util.Comparator;


class RenderObjectComparator implements Comparator<RenderObject>
{

	/**
	 * compare the render objects
	 * @param a
	 * @param b
	 * @return int -- -1 less than, 0 equal to, 1 greater than
	 */
	public int compare(RenderObject a, RenderObject b)
	{
		int aypos = a.getY() + a.getHeight() / 2;
		int bypos = b.getY() + b.getHeight() / 2;
		if (aypos < bypos) {
			return -1;
		}

		else if (aypos == bypos) {
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

	/**
	 * Constructor
	 */
	public MinYFirstSortedRenderObjectBuffer()
	{
		this(INIT_CAPACITY);
	}

	public MinYFirstSortedRenderObjectBuffer(int capacity)
	{
		super(capacity, comparator);
	}

	/**
	 * Pushes render object to the queue
	 * @param r render boject to render
	 */
	public void pushObject(RenderObject r)
	{
		add(r);
	}
}
