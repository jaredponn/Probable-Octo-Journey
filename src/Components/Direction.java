package Components;

import poj.Component.Component;
import poj.linear.*;

/*
   TODO  -- not working
*/

enum Directions implements Component {
	N {
		public void print()
		{
			System.out.println("North");
		}
	},

	E {
		public void print()
		{
			System.out.println("East");
		}
	},

	S {
		public void print()
		{
			System.out.println("South");
		}
	},


	W {
		public void print()
		{
			System.out.println("West");
		}
	};
}


public class Direction implements Component
{
	private Directions direction;

	public static final float UNIT_DIAGONAL = 0.70710678118f;

	public void print()
	{
	}

	public static Vector2f getDirectionUnitVector(Direction d)
	{
		/*
		switch (d) {
		case N:
			return new Vector2f(0.f, -1.f);
		case E:
			return new Vector2f(1.f, 0.f);
		case W:
			return new Vector2f(-1.f, 0.f);
		case S:
			return new Vector2f(0.f, 1.f);
		default:
			return new Vector2f(0, 0);
		}*/

		return new Vector2f(0, 0);
	}
}
