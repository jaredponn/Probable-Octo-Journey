package poj.Collisions;
/**
 * DBVT -- dynamic bounding volume tree. A binary AVL tree that sorts the inputs
 * by SA of the bounding boxes that contain the  shapes Date: March 10, 2019
 * @author  Jared Pon
 * @version  1.00
 */

import java.util.ArrayList;

public class DBVT extends Tree
{
	public DBVT right;
	public DBVT left;

	private static int MAX_OBJ =
		2; // number of objects it can hold before splitting

	public DBVT(CollisionShape cs)
	{
		super.height = 1; // leafs have height of 1
		super.bounds = cs.getBoundingRectangle();
		super.objects = null;
	}


	public static int height(DBVT node)
	{
		return (node == null) ? 0 : node.height;
	}

	// rotates right and returns the new root
	public static DBVT rightRotate(DBVT a)
	{
		DBVT b = a.left;
		DBVT c = b.right;

		b.right = a;
		a.left = c;

		a.height = Math.max(height(a.right), height(a.left)) + 1;
		b.height = Math.max(height(b.right), height(b.left)) + 1;

		return b;
	}

	// rotates left and returns the new root
	public static DBVT leftRotate(DBVT b)
	{
		DBVT a = b.right;
		DBVT c = a.left;

		a.left = b;
		b.right = c;

		a.height = Math.max(height(a.right), height(a.left)) + 1;
		b.height = Math.max(height(b.right), height(b.left)) + 1;

		return a;
	}

	public static int getBalance(DBVT n)
	{
		return height(n.left) - height(n.right);
	}

	public static DBVT insert(DBVT node, CollisionShape cs)
	{

		Rectangle r = cs.getBoundingRectangle();

		// base case just add it to the leaf
		if (node == null)
			return new DBVT(cs);
		// if (r.getArea() < node)

		return node;
	}
}
