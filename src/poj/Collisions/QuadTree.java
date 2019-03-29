package poj.Collisions;

/**
 * Quad tree -- quad tree for efficient collision querying.
 *
 * n cross dv cross n == perpendicular vector
 *
 * Date: March 10, 2019
 * @author  Jared Pon and code was taken / translated from / heavily influenced
 * from the following links:
 * https://gamedevelopment.tutsplus.com/tutorials/quick-tip-use-quadtrees-to-detect-likely-collisions-in-2d-space--gamedev-374
 * @version  1.00
 */

import poj.linear.*;
import java.util.ArrayList;


public class QuadTree
{

	private int MAX_OBJ =
		10; // number of objects it can hold before splitting
	private int MAX_LVLS = 8; // deepest sublevel node


	private int curLvl; // current level (0 is teh topmost)
	private ArrayList objects;
	private Rectangle bounds; // 2D space occupied

	/*
	 * -------------
	 * |  2  |  3  |
	 * |     |     |
	 * |-----------|
	 * |  0  |  1  |
	 * |_____|____ |
	 */
	private QuadTree[] nodes; // subnodes


	public QuadTree(int lvl, Rectangle bounds)
	{
		this.curLvl = lvl;
		this.bounds = bounds;
		objects = new ArrayList();
		nodes = new QuadTree[4];
	}


	public void clear()
	{
		objects.clear();

		for (int i = 0; i < nodes.length; ++i) {
			nodes[i].clear();
		}
	}

	private void split()
	{
		float w = bounds.getWidth();
		float h = bounds.getHeight();

		float subW = w / 2;
		float subH = h / 2;

		float x = bounds.getMinX();
		float y = bounds.getMinY();

		nodes[0] = new QuadTree(
			curLvl + 1, new Rectangle(x, y, x + subW, y + subH));

		nodes[1] = new QuadTree(
			curLvl + 1,
			new Rectangle(x + subW, y, x + w, y + subH));

		nodes[2] = new QuadTree(
			curLvl + 1,
			new Rectangle(x, y + subH, x + subW, y + h));

		nodes[3] = new QuadTree(
			curLvl + 1,
			new Rectangle(x + subW, y + subH, x + w, y + h));
	}
}
