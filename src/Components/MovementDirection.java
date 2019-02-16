package Components;

import poj.Component.Component;
import poj.linear.*;

public class MovementDirection implements Component
{
	private CardinalDirections direction;

	public MovementDirection(CardinalDirections n)
	{
		setDirection(n);
	}

	public void setDirection(CardinalDirections n)
	{
		this.direction = n;
	}

	public void setDirectionFromVectorAndSnapToClosestDirection(Vector2f n)
	{
		this.direction =
			CardinalDirections
				.getClosestDirectionFromDirectionVector(n);
	}

	public CardinalDirections getDirection()
	{
		return this.direction;
	}

	public Vector2f getDirectionAsUnitVector()
	{
		return CardinalDirections.getUnitVector(this.direction);
	}

	public Vector2f getUnitVector()
	{
		return CardinalDirections.getUnitVector(this.direction);
	}

	public void print()
	{
		CardinalDirections.print(direction);
	}
}
