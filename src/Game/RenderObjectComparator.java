package Game;

import poj.Render.*;
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
		int aypos = a.getY() + (int)(a.getHeight() / 1.13f)
			    + a.getRenderSortModifier();

		int bypos = b.getY() + (int)(b.getHeight() / 1.13f)
			    + b.getRenderSortModifier();

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

