package Components;

import poj.Component.Component;
import poj.linear.*;

// Same thing as Movement direction -- unfortunately java does not allow easy
// strong typedefing (newtype in Haskell)
public class FacingDirection implements Component
{

	private CardinalDirections direction;

	public FacingDirection(CardinalDirections n)
	{
		setDirection(n);
	}

	public void setDirection(CardinalDirections n)
	{
		this.direction = n;
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
