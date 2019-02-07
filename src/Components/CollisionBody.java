package Components;

import poj.Component.*;
import poj.linear.Rectanglef;
import java.util.ArrayList;

// http://www.jeffreythompson.org/collision-detection/line-circle.php
public class CollisionBody implements Component
{

	ArrayList<Rectanglef> collisionBodies;

	public CollisionBody(Rectanglef... a)
	{
		for (Rectanglef i : a) {
			this.collisionBodies.add(i);
		}
	}

	public final ArrayList<Rectanglef> getCollisionBodies()
	{
		return this.collisionBodies;
	}

	public void print()
	{
		System.out.println();
	}
}
