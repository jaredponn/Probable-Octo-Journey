package Components;

import poj.Logger.*;
import poj.linear.Vector2f;

public enum CardinalDirections {
	N,
	NE,
	NW,
	S,
	SE,
	SW,
	W,
	E;

	public static final float UNIT_DIAGONAL = 0.70710678118f;

	public static void print(CardinalDirections n)
	{
		switch (n) {
		case N:
			Logger.logMessage(LogLevels.VERBOSE,
					  "CardinalDirection value: North");
			break;
		case NE:
			Logger.logMessage(LogLevels.VERBOSE,
					  "CardinalDirection value: NorthEast");
			break;
		case NW:
			Logger.logMessage(LogLevels.VERBOSE,
					  "CardinalDirection value: NorthWest");
			break;
		case S:
			Logger.logMessage(LogLevels.VERBOSE,
					  "CardinalDirection value: South");
			break;
		case SE:
			Logger.logMessage(LogLevels.VERBOSE,
					  "CardinalDirection value: SouthEast");
			break;
		case SW:
			Logger.logMessage(LogLevels.VERBOSE,
					  "CardinalDirection value: SouthWest");
			break;
		case W:
			Logger.logMessage(LogLevels.VERBOSE,
					  "CardinalDirection value: West");
			break;
		case E:
			Logger.logMessage(LogLevels.VERBOSE,
					  "CardinalDirection value: East");
			break;
		}
	}

	public static Vector2f getUnitVector(CardinalDirections n)
	{
		switch (n) {
		case N:
			return new Vector2f(0, -1);
		case NE:
			return new Vector2f(CardinalDirections.UNIT_DIAGONAL,
					    -1);
		case NW:
			return new Vector2f(-CardinalDirections.UNIT_DIAGONAL,
					    -1);
		case S:
			return new Vector2f(0, 1);
		case SE:
			return new Vector2f(CardinalDirections.UNIT_DIAGONAL,
					    1);
		case SW:
			return new Vector2f(-CardinalDirections.UNIT_DIAGONAL,
					    1);
		case E:
			return new Vector2f(1, 0);
		case W:
			return new Vector2f(-1, 0);

		default:
			Logger.lassert(
				true,
				"MAJOR ERROR in CardinalDirections -- an invalid direction has been put in.");
			return new Vector2f(0, 0);
		}
	}

	public static CardinalDirections
	getClosestDirectionFromDirectionVector(Vector2f n)
	{
		// slightly magical function from:
		// https://stackoverflow.com/questions/1437790/how-to-snap-a-directional-2d-vector-to-a-compass-n-ne-e-se-s-sw-w-nw
		int compass = (((int)Math.round(Math.atan2(n.y, n.x)
						/ (2 * Math.PI / 8)))
			       + 8)
			      % 8;

		switch (compass) {
		case 0:
			return CardinalDirections.E;
		case 1:
			return CardinalDirections.SE;

		case 2:
			return CardinalDirections.S;

		case 3:
			return CardinalDirections.SW;

		case 4:
			return CardinalDirections.W;

		case 5:
			return CardinalDirections.NW;
		case 6:
			return CardinalDirections.N;
		case 7:
			return CardinalDirections.NE;
		default:
			Logger.lassert(
				true,
				"MAJOR ERROR in getClosestDirectionFromDirectionVector -- error in calcluating the cardinal direction from the vector ");
			return CardinalDirections.N;
		}
	}
}
