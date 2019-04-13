package Components;

/**
 * MovementDiretion. MovementDirection component
 *
 * Date: March 10, 2019
 * @author Jared
 * @version 1.0
 */

import poj.Component.Component;
import poj.linear.*;

public class MovementDirection implements Component
{
	private CardinalDirections direction;

	/**
	 * create new movement direction with a cardinal direction
	 * @param n: initial cardinal direction
	 */
	public MovementDirection(CardinalDirections n)
	{
		setDirection(n);
	}

	/**
	 * set the current direction to something new
	 * @param n: the new cardinal direction
	 */
	public void setDirection(CardinalDirections n)
	{
		this.direction = n;
	}

	/**
	 * set the current direction to the closest cardinal
	 * direction to a direction vector
	 * @param n: the velocity vector to set the direction from
	 */
	public void setDirectionFromVectorAndSnapToClosestDirection(Vector2f n)
	{
		this.direction =
			CardinalDirections
				.getClosestDirectionFromDirectionVector(n);
	}

	/**
	 * get the current direction
	 * @return the current movement direction as a cardinal direction
	 */
	public CardinalDirections getDirection()
	{
		return this.direction;
	}

	/**
	 * get the current direction
	 * @return the current movement direction as a vector
	 */
	public Vector2f getDirectionAsUnitVector()
	{
		return CardinalDirections.getUnitVector(this.direction);
	}

	/**
	 * alias of getDirectionAsUnitVector()
	 */
	public Vector2f getUnitVector()
	{
		return CardinalDirections.getUnitVector(this.direction);
	}

	/**
	 * print this direction to console
	 */
	public void print()
	{
		CardinalDirections.print(direction);
	}
}
