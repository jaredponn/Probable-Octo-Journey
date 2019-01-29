package poj;

import poj.linear.Rectanglef;
import poj.linear.Vector2f;

public class CollisionRect extends Rectanglef
{
	public CollisionRect()
	{
		super();
	}

	public CollisionRect(Vector2f topleft, Vector2f botright)
	{
		super(topleft, botright);
	}

	// if the edges are touching, then the objects are considered to be
	// colliding
	public static boolean isColliding(CollisionRect a, CollisionRect b)
	{
		return (a.getTopLeft().getX() <= b.getBotRight().getX()
			&& a.getBotRight().getX() >= b.getTopLeft().getX())
			&& (a.getTopLeft().getY() <= b.getBotRight().getY()
			    && a.getBotRight().getY() >= b.getTopLeft().getY());
	}
}
