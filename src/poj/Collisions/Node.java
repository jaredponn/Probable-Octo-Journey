package poj.Collisions;
/**
 * Node for a dynamic bounding volume tree. A binary AVL tree that sorts the
 * inputs by SA of the bounding boxes that contain the  shapes Date: March 10,
 * 2019
 * @author  Jared Pon
 * @version  1.00
 */
import java.util.ArrayList;

public class Node<T extends CollisionShape>
{
	// LEFT is equal to or less than
	// RIGHT is STRICTLY greater than

	// branch
	public Node<T> left;
	public Node<T> right;
	public Rectangle bounds;

	// leaf
	public LeafData leaf;

	int height;

	private static final int LEAF_HEIGHT = 1;

	/**
	 *  constructor
	 */
	public Node()
	{
		height = LEAF_HEIGHT;
		leaf = new LeafData<T>();
	}


	/**
	 *  gets the height
	 *  @return height
	 */
	public int height()
	{
		return height;
	}

	/**
	 *  checks if the node is a candidate to be a leaf node
	 *  @return boolean - is candidate to be a leaf node
	 */
	public static <T extends CollisionShape> boolean isLeaf(Node<T> node)
	{
		return node == null;
	}

	/**
	 *  right rotation of a tree -- deprecated
	 *  @param a node
	 *  @return new root node
	 */
	public static <T extends CollisionShape> Node<T> rightRotate(Node<T> a)
	{
		Node<T> b = a.left;
		Node<T> c = b.right;

		b.right = a;
		a.left = c;

		a.height = Math.max(a.right.height, a.left.height) + 1;
		b.height = Math.max(b.right.height, b.left.height) + 1;

		return b;
	}

	/**
	 *  left rotation of a tree -- deprecated
	 *  @param a node
	 *  @return new root node
	 */
	public static <T extends CollisionShape> Node<T> leftRotate(Node<T> b)
	{
		Node<T> a = b.right;
		Node<T> c = a.left;

		a.left = b;
		b.right = c;

		a.height = Math.max(a.right.height, a.left.height) + 1;
		b.height = Math.max(b.right.height, b.left.height) + 1;

		return a;
	}


	/**
	 *  gets the balance -- deprecated
	 *  @return balance
	 */
	public int getBalance()
	{
		return this.left.height - this.right.height;
	}

	/**
	 *  recrusive insertion method
	 *  @param node tree to insert in
	 *  @param cs collision shap to insert
	 *  @return root node
	 */
	public static <T extends CollisionShape> Node<T>
	insertRecursive(Node<T> node, T cs)
	{
		// base case -- it is null so create it and add the leaf data in
		if (Node.isLeaf(node)) { // if it is a leaf
			// System.out.println("is empty leaf:");
			Node<T> newNode = new Node<T>();
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

			Rectangle leftRect = Rectangle.getBoundingRectOfRects(
				node.leaf.getLeftBoundingRect(),
				Rectangle.getBoundingRectangle(cs));
			float leftArea = Rectangle.calculateArea(leftRect);

			Rectangle rightRect = Rectangle.getBoundingRectOfRects(
				node.leaf.getRightBoundingRect(),
				Rectangle.getBoundingRectangle(cs));
			float rightArea = Rectangle.calculateArea(rightRect);

			if (rightArea >= leftArea) {

				node.left = insertRecursive(node.left, cs);
				node.left = insertRecursive(
					node.left, (T)node.leaf.leftLeaf);

				node.right = insertRecursive(
					node.right, (T)node.leaf.rightLeaf);
			} else {

				node.right = insertRecursive(node.right, cs);

				node.right = insertRecursive(
					node.right, (T)node.leaf.rightLeaf);

				node.left = insertRecursive(
					node.left, (T)node.leaf.leftLeaf);
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
			Rectangle leftRect = Rectangle.getBoundingRectOfRects(
				node.left.bounds,
				Rectangle.getBoundingRectangle(cs));
			float leftArea = Rectangle.calculateArea(leftRect);

			Rectangle rightRect = Rectangle.getBoundingRectOfRects(
				node.right.bounds,
				Rectangle.getBoundingRectangle(cs));

			float rightArea = Rectangle.calculateArea(rightRect);

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

	/**
	 *  insert
	 *  @param cs collisions shape
	 *  @return root node
	 */
	public void insert(T cs)
	{
		insertRecursive(this, cs);
	}


	/**
	 *  query the collisions recursively
	 *  @param n tree to query collisions
	 *  @param cs collision shape
	 *  @param arr buffer for the queried collisions
	 */
	public static <T extends CollisionShape> void
	queryCollisionsRecursive(Node n, T cs, ArrayList<T> arr)
	{
		Rectangle tmp = cs.getBoundingRectangle();

		if (n.left == null || n.right == null || n.bounds == null) {
			if (n.leaf.leftLeaf != null)
				arr.add((T)n.leaf.leftLeaf);
			if (n.leaf.rightLeaf != null)
				arr.add((T)n.leaf.rightLeaf);

			return;
		}

		if (n.bounds.isCollidingWith(tmp)) {
			if (n.left != null)
				queryCollisionsRecursive(n.left, cs, arr);
			if (n.right != null)
				queryCollisionsRecursive(n.right, cs, arr);
		}
	}

	/**
	 *  query the collisions
	 *  @param cs collision shape
	 *  @param arr buffer for the queried collisions
	 */
	public void queryCollisions(T cs, ArrayList<T> arr)
	{
		queryCollisionsRecursive(this, cs, arr);
	}

	/**
	 *  print
	 */
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
