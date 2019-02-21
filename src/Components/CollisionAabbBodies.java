package Components;

import poj.Component.*;
import poj.Collisions.*;
import poj.linear.*;
import java.util.ArrayList;

// WARNING -- untested and will probably be only used in the final dank boss
// fight. One super godly collisionbodies class
public class CollisionAabbBodies implements Component
{

	private ArrayList<CollisionAabb> collisionBodies;
	private ArrayList<Vector2f>
		distanceDeltas; // all distance deltas are relative to the first
				// CollisionBox added

	public CollisionAabbBodies(CollisionAabb... a)
	{
		collisionBodies = new ArrayList<CollisionAabb>();
		distanceDeltas = new ArrayList<Vector2f>();

		// TODO test if this works
		for (CollisionAabb i : a) {
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

	public CollisionAabbBodies(Vector2f d, CollisionAabb... a)
	{
		collisionBodies = new ArrayList<CollisionAabb>();
		distanceDeltas = new ArrayList<Vector2f>();

		// TODO test if this works
		for (CollisionAabb i : a) {
			this.collisionBodies.add(i);

			// first CollisionBox has a distance delta of 0
			if (distanceDeltas.size() == 0) {
				distanceDeltas.add(new Vector2f(d));
			} else {
				distanceDeltas.add(i.pureGetMin().pureSubtract(
					collisionBodies.get(0).pureGetMin()));
			}
		}
	}

	public CollisionAabbBodies()
	{
		collisionBodies = new ArrayList<CollisionAabb>();
		distanceDeltas = new ArrayList<Vector2f>();
	}

	public CollisionAabbBodies(CollisionAabbBodies n)
	{
		collisionBodies = n.pureGetCollisionBodies();
		distanceDeltas = n.pureGetDistanceDeltas();
	}

	public final ArrayList<CollisionAabb> getCollisionBodies()
	{
		return this.collisionBodies;
	}

	protected final ArrayList<Vector2f> getDistanceDeltas()
	{
		return this.distanceDeltas;
	}

	public final ArrayList<CollisionAabb> pureGetCollisionBodies()
	{
		ArrayList<CollisionAabb> tmp = new ArrayList<CollisionAabb>();
		for (CollisionAabb i : collisionBodies) {
			tmp.add(new CollisionAabb(i));
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
		for (CollisionAabb i : collisionBodies) {
			tmp += i.toString() + " ";
		}
		System.out.println("CollisionBoxBody component: " + tmp);
	}
}
