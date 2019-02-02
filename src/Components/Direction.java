package Components;

import poj.Component.Component;

public enum Direction implements Component {

	NORTH {
		public void print()
		{
			System.out.println("North");
		}
	},

	EAST {
		public void print()
		{
			System.out.println("East");
		}
	},

	SOUTH {
		public void print()
		{
			System.out.println("South");
		}
	},

	WEST {
		public void print()
		{
			System.out.println("West");
		}
	}
}
