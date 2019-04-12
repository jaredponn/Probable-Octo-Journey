package poj.Collisions;

/**
 * LeafData. Leaf data type for node tree
 *
 * date March 10, 2019
 * @author Jared Pon
 * @version 1.0
 */

// leaf data
public class LeafData<T extends CollisionShape>
{
	public T leftLeaf;
	public T rightLeaf;


	/**
	 * Inserts in leaf.  returns the new bounding rectangle of the leafs
	 *
	 * @param cs : collision shape
	 */
	public Rectangle insertInLeaf(T cs)
	{
		// Compleetley empty -- so fill the right side up
		if (this.leftLeaf == null && this.rightLeaf == null) {
			this.rightLeaf = cs;
		} else if (this.leftLeaf == null
			   && this.rightLeaf
				      != null) { // right side should be filled
						 // so fill the left side up

			// new collision shape has greater area than the right
			// node
			if (cs.getBoundingRectangle().getArea()
			    > this.rightLeaf.getBoundingRectangle().getArea()) {
				this.leftLeaf = this.rightLeaf;
				this.rightLeaf = cs;

			}
			// new collision shape has less area than the right node
			else {
				this.leftLeaf = cs;
			}
		}

		return Rectangle.getBoundingRectOfRects(
			this.getLeftBoundingRect(),
			this.getRightBoundingRect());
	}


	/**
	 * Gets the left bounding rectangle
	 *
	 * @return bounding rect
	 */
	public Rectangle getLeftBoundingRect()
	{
		if (leftLeaf == null)
			return null;
		else
			return leftLeaf.getBoundingRectangle();
	}

	/**
	 * Gets the right bounding rectangle
	 *
	 * @return bounding rect
	 */
	public Rectangle getRightBoundingRect()
	{
		if (rightLeaf == null)
			return null;
		else
			return rightLeaf.getBoundingRectangle();
	}

	/**
	 * Clears the leaf node
	 */
	public void clearLeaf()
	{
		leftLeaf = null;
		rightLeaf = null;
	}


	/**
	 * swaps the left and right leafs
	 */
	public void swapLeftAndRight()
	{
		T tmp = leftLeaf;
		leftLeaf = rightLeaf;
		rightLeaf = tmp;
	}

	/**
	 * checks if the leaf is full
	 * @return boolean if the leaf is full
	 */
	public boolean leafIsFull()
	{
		return rightLeaf != null && leftLeaf != null;
	}
}
