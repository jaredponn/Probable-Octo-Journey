package poj.Collisions;

public class DBVT extends MinBinaryHeap<Node>
{

	public DBVT()
	{
		// initalizes root
		setAt(0, new Node());
	}

	// returns the new root
	/* not worth the effort honestly
	public int insertRecursive(int focus, CollisionShape cs)
	{
		Node node = super.deref(focus);

		// base case -- it is null so create it and add the leaf data in
		if (Node.isLeaf(node)) { // if it is a leaf
			// System.out.println("is empty leaf:");
			Node newNode = new Node();
			newNode.bounds = newNode.leaf.insertInLeaf(cs);
			super.setAt(focus, node);
			return focus;
		}
		// base case -- the leaf is either completely empty or half
		// empty
		else if ((node.left == -1 && node.right == -1)
			 && (node.leaf.leftLeaf == null
			     || node.leaf.rightLeaf == null)) {
			// System.out.println("is half empty leaf:");
			node.bounds = node.leaf.insertInLeaf(cs);
			return focus;
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

				node.left = insertRecursive(
					super.getLeft(focus), cs);
				node.left =
					insertRecursive(super.getLeft(focus),
							node.leaf.leftLeaf);

				node.right =
					insertRecursive(super.getRight(focus),
							node.leaf.rightLeaf);
			} else {
				node.right = insertRecursive(
					super.getRight(focus), cs);

				node.right =
					insertRecursive(super.getRight(focus),
							node.leaf.rightLeaf);

				node.left =
					insertRecursive(super.getLeft(focus),
							node.leaf.leftLeaf);
			}

			// updating bounds
			node.bounds = Rectangle.getBoundingRectOfRects(
				super.deref(node.left).bounds,
				super.deref(node.right).bounds);
			// clearing the leaf
			node.leaf.clearLeaf();
			return focus;

		}
		// inductive step 2 --just on a branch
		else {
			// System.out.println("inductive step");

			Rectangle leftRect = Rectangle.getBoundingRectOfRects(
				super.deref(node.left).bounds,
				Rectangle.getBoundingRectangle(cs));
			float leftArea = Rectangle.calculateArea(leftRect);

			Rectangle rightRect = Rectangle.getBoundingRectOfRects(
				super.deref(node.right).bounds,
				Rectangle.getBoundingRectangle(cs));

			float rightArea = Rectangle.calculateArea(rightRect);

			// left side of the tree will always have LESS THAN or
			// EQUAL TO the surface area of the right side
			if (rightArea >= leftArea) {

				node.left = insertRecursive(
					super.getLeft(focus), cs);
			} else {

				node.right = insertRecursive(
					super.getRight(focus), cs);
			}


			// updating bounds
			node.bounds = Rectangle.getBoundingRectOfRects(
				super.deref(node.left).bounds,
				super.deref(node.right).bounds);

			return focus;
		}
	}

	public void insert(CollisionShape cs)
	{
		insertRecursive(0, cs);
	}*/
}
