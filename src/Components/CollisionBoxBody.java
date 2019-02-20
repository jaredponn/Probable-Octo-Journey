package Components;

import poj.Component.*;
import poj.Collisions.*;
import poj.linear.*;
import poj.Render.RenderRect;
import java.util.ArrayList;

// http://www.jeffreythompson.org/collision-detection/line-circle.php
public class CollisionBoxBody implements Component
{

	private ArrayList<CollisionBox> collisionBodies;
	private ArrayList<Vector2f>
		distanceDeltas; // all distance deltas are relative to the first
				// CollisionBox added

	public CollisionBoxBody(CollisionBox... a)
	{
		collisionBodies = new ArrayList<CollisionBox>();
		distanceDeltas = new ArrayList<Vector2f>();

		// TODO test if this works
		for (CollisionBox i : a) {
			this.collisionBodies.add(i);

			// first CollisionBox has a distance delta of 0
			if (distanceDeltas.size() == 0) {
				distanceDeltas.add(new Vector2f(0, 0));
			} else {
				distanceDeltas.add(i.pureGetMin().pureSubtract(
					collisionBodies.get(0).pureGetMin()));
			}
		}
	}

	public CollisionBoxBody()
	{
		collisionBodies = new ArrayList<CollisionBox>();
		distanceDeltas = new ArrayList<Vector2f>();
	}

	public CollisionBoxBody(CollisionBoxBody n)
	{
		collisionBodies = n.pureGetCollisionBodies();
		distanceDeltas = n.pureGetDistanceDeltas();
	}

	public final ArrayList<CollisionBox> getCollisionBodies()
	{
		return this.collisionBodies;
	}

	protected final ArrayList<Vector2f> getDistanceDeltas()
	{
		return this.distanceDeltas;
	}

	public final ArrayList<CollisionBox> pureGetCollisionBodies()
	{
		ArrayList<CollisionBox> tmp = new ArrayList<CollisionBox>();
		for (CollisionBox i : collisionBodies) {
			tmp.add(new CollisionBox(i));
		}
		return tmp;
	}

	public final ArrayList<Vector2f> pureGetDistanceDeltas()
	{

		ArrayList<Vector2f> tmp = new ArrayList<Vector2f>();
		for (Vector2f i : distanceDeltas) {
			tmp.add(new Vector2f(i));
		}
		return tmp;
	}


	public final void setCollisionBodyPositionFromTopLeft(Vector2f topleft)
	{

		for (int i = 0; i < collisionBodies.size(); ++i) {
			collisionBodies.get(i).setTopLeftAndUpdateAllPoints(
				topleft.pureAdd(distanceDeltas.get(i)));
		}
	}

	public void print()
	{
		String tmp = "";
		for (CollisionBox i : collisionBodies) {
			tmp += i.toString() + " ";
		}
		System.out.println("CollisionBoxBody component: " + tmp);
	}
}
