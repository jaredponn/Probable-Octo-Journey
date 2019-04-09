package poj.Collisions;
/**
 * DBVT -- dynamic bounding volume tree. A binary AVL tree that sorts the inputs
 * by SA of the bounding boxes that contain the  shapes Date: March 10, 2019
 * @author  Jared Pon
 * @version  1.00
 */
import java.util.ArrayList;

public class Node
{
	// LEFT is equal to or less than
	// RIGHT is STRICTLY greater than

	// branch
	public Node left;
	public Node right;
	public Rectangle bounds;

	// leaf
	public LeafData leaf;

	int height;

	private static final int LEAF_HEIGHT = 1;

	public Node()
	{
		height = LEAF_HEIGHT;
		leaf = new LeafData();
	}

	public int height()
	{
		return height;
	}

	public static boolean isLeaf(Node node)
	{
		return node == null;
	}

	// rotates right and returns the new root
	public static Node rightRotate(Node a)
	{
		Node b = a.left;
		Node c = b.right;

		b.right = a;
		a.left = c;

		a.height = Math.max(a.right.height, a.left.height) + 1;
		b.height = Math.max(b.right.height, b.left.height) + 1;

		return b;
	}

	// rotates left and returns the new root
	public static Node leftRotate(Node b)
	{
		Node a = b.right;
		Node c = a.left;

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

	private void swapLeftAndRight()
	{
		Node tmp = this.right;
		this.right = left;
		this.left = tmp;

		leaf.swapLeftAndRight();
	}


	// returns the new root
	public static Node insertRecursive(Node node, CollisionShape cs)
	{
		// base case -- it is null so create it and add the leaf data in
		if (Node.isLeaf(node)) { // if it is a leaf
			// System.out.println("is empty leaf:");
			Node newNode = new Node();
			newNode.bounds = newNode.leaf.insertInLeaf(cs);
			return newNode;
		}
		// base case -- the leaf is either completely empty or half
		// empty
		else if ((node.left == null && node.right == null)
			 && (node.leaf.leftLeaf == null
			     || node.leaf.rightLeaf == null)) {
			// System.out.println("is half empty leaf:");
			node.bounds = node.leaf.insertInLeaf(cs);
			return node;
		}
		// inductive step 1 -- leaf is filled and push downwards
		else if (node.leaf.leftLeaf != null
			 && node.leaf.rightLeaf != null) {
			// System.out.println("filled leaf inductive step ");

			Rectangle leftRect = Rectangle.getBoundingRectOfRects(
				node.leaf.getLeftBoundingRect(),
				Rectangle.getBoundingRectangle(cs));
			float leftArea = Rectangle.calculateArea(leftRect);

			Rectangle rightRect = Rectangle.getBoundingRectOfRects(
				node.leaf.getRightBoundingRect(),
				Rectangle.getBoundingRectangle(cs));
			float rightArea = Rectangle.calculateArea(rightRect);

			// left side of the tree will always have LESS THAN or
			// EQUAL TO the surface area of the right side
			if (rightArea >= leftArea) {

				node.left = insertRecursive(node.left, cs);
				node.left = insertRecursive(node.left,
							    node.leaf.leftLeaf);

				node.right = insertRecursive(
					node.right, node.leaf.rightLeaf);
			} else {

				node.right = insertRecursive(node.right, cs);

				node.right = insertRecursive(
					node.right, node.leaf.rightLeaf);

				node.left = insertRecursive(node.left,
							    node.leaf.leftLeaf);
			}


			// updating bounds
			node.bounds = Rectangle.getBoundingRectOfRects(
				node.left.bounds, node.right.bounds);
			// clearing the leaf
			node.leaf.clearLeaf();
			return node;

		}
		// inductive step 2 --just on a branch
		else {
			// System.out.println("inductive step");

			Rectangle leftRect = Rectangle.getBoundingRectOfRects(
				node.left.bounds,
				Rectangle.getBoundingRectangle(cs));
			float leftArea = Rectangle.calculateArea(leftRect);

			Rectangle rightRect = Rectangle.getBoundingRectOfRects(
				node.right.bounds,
				Rectangle.getBoundingRectangle(cs));

			float rightArea = Rectangle.calculateArea(rightRect);

			// left side of the tree will always have LESS THAN or
			// EQUAL TO the surface area of the right side
			if (rightArea >= leftArea) {

				node.left = insertRecursive(node.left, cs);
			} else {

				node.right = insertRecursive(node.right, cs);
			}


			// updating bounds
			node.bounds = Rectangle.getBoundingRectOfRects(
				node.left.bounds, node.right.bounds);

			return node;
		}
	}

	public void insert(CollisionShape cs)
	{
		insertRecursive(this, cs);
	}


	public void queryCollisions(CollisionShape cs,
				    ArrayList<CollisionShape> arr)
	{
		insertRecursive(this, cs);
	}

	public void print()
	{
		System.out.println("NODE -- start print");
		System.out.println("Left: " + left);
		System.out.println("Right: " + right);
		System.out.println("Bounds: " + bounds);
		System.out.println("leafLeft: " + leaf.leftLeaf);
		System.out.println("leafRight: " + leaf.rightLeaf);
		System.out.println("NODE -- end print");
	}
}
