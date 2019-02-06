package poj.test;
import static org.junit.Assert.*;
import org.junit.Test;
import poj.linear.*;
import Components.CardinalDirections;

public class CardinalDirectionTests
{
	@Test public void getClosestDirectionFromDirectionVectorTests()
	{

		assertEquals(CardinalDirections
				     .getClosestDirectionFromDirectionVector(
					     new Vector2f(0, 1)),
			     CardinalDirections.S);

		assertEquals(CardinalDirections
				     .getClosestDirectionFromDirectionVector(
					     new Vector2f(0, -1)),
			     CardinalDirections.N);

		assertEquals(CardinalDirections
				     .getClosestDirectionFromDirectionVector(
					     new Vector2f(-1, 0)),
			     CardinalDirections.W);

		assertEquals(CardinalDirections
				     .getClosestDirectionFromDirectionVector(
					     new Vector2f(1, 0)),
			     CardinalDirections.E);

		assertEquals(CardinalDirections
				     .getClosestDirectionFromDirectionVector(
					     CardinalDirections.getUnitVector(
						     CardinalDirections.NE)),
			     CardinalDirections.NE);

		assertEquals(CardinalDirections
				     .getClosestDirectionFromDirectionVector(
					     CardinalDirections.getUnitVector(
						     CardinalDirections.NW)),
			     CardinalDirections.NW);

		assertEquals(CardinalDirections
				     .getClosestDirectionFromDirectionVector(
					     CardinalDirections.getUnitVector(
						     CardinalDirections.SE)),
			     CardinalDirections.SE);

		assertEquals(CardinalDirections
				     .getClosestDirectionFromDirectionVector(
					     CardinalDirections.getUnitVector(
						     CardinalDirections.SW)),
			     CardinalDirections.SW);


		assertEquals(CardinalDirections
				     .getClosestDirectionFromDirectionVector(
					     new Vector2f(0.1f, 1000f)),
			     CardinalDirections.S);

		assertEquals(CardinalDirections
				     .getClosestDirectionFromDirectionVector(
					     new Vector2f(30f, 30f)),
			     CardinalDirections.SE);

		assertEquals(CardinalDirections
				     .getClosestDirectionFromDirectionVector(
					     new Vector2f(30f, 40)),
			     CardinalDirections.SE);

		assertEquals(CardinalDirections
				     .getClosestDirectionFromDirectionVector(
					     new Vector2f(30f, 40)),
			     CardinalDirections.SE);
	}
}

