package poj.Collisions;
/**
 * DBVT -- dynamic bounding volume tree. A binary AVL tree that sorts the inputs
 * by SA of the bounding boxes that contain the  shapes Date: March 10, 2019
 * @author  Jared Pon
 * @version  1.00
 */

import java.util.ArrayList;

public class DBVT
{
	// branch
	public DBVT left;
	public DBVT right;
	public Rectangle bounds;

	// leaf
	public CollisionShape a;
	public CollisionShape b;

	int height;

	private static final int LEAF_HEIGHT = 1;

	public DBVT()
	{
		this(new Rectangle(0, 0, 0, 0), LEAF_HEIGHT);
	}

	public DBVT(Rectangle b, int h)
	{
		height = h;
		bounds = b;
	}

	public DBVT(Rectangle b)
	{
		this(b, LEAF_HEIGHT); // leafs have a default height of 1
	}


	public int height()
	{
		return height;
	}

	public boolean isLeaf()
	{
		return this.height() == LEAF_HEIGHT;
	}


	// rotates right and returns the new root
	public static DBVT rightRotate(DBVT a)
	{
		DBVT b = a.left;
		DBVT c = b.right;

		b.right = a;
		a.left = c;

		a.height = Math.max(a.right.height, a.left.height) + 1;
		b.height = Math.max(b.right.height, b.left.height) + 1;

		return b;
	}

	// rotates left and returns the new root
	public static DBVT leftRotate(DBVT b)
	{
		DBVT a = b.right;
		DBVT c = a.left;

		a.left = b;
		b.right = c;

		a.height = Math.max(a.right.height, a.left.height) + 1;
		b.height = Math.max(b.right.height, b.left.height) + 1;

		return a;
	}

	public int getBalance()
	{
		return this.left.height - this.right.height;
	}

	private static void insertInLeafAndUpdateBounds(CollisionShape cs)
	{
		cs.getBoundingRectangle();
	}

	// retrusn new root of th esub tree
	public DBVT insert(CollisionShape cs)
	{

		Rectangle r = cs.getBoundingRectangle();

		// base case just add it to the leaf
		if (this.isLeaf()) {
			insertInLeafAndUpdateBounds(cs);
			return this;
		}

		return new DBVT(r);
	}
}
