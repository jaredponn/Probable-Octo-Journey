package poj.linear;

public class Ray
{
	Vector3f origin;
	Vector3f direction;
	public Ray(Vector3f origin, Vector3f direction)
	{
		this.origin = origin;
		this.direction = direction;
	}


	final public Vector3f getOrigin()
	{
		return this.origin;
	}

	final public Vector3f getDirection()
	{
		return this.direction;
	}

	final public Vector3f pointAtParameter(float t)
	{
		Vector3f tmpo = Vector3f.deepCopyVector3f(this.origin);
		Vector3f tmpd = Vector3f.deepCopyVector3f(this.direction);

		return tmpo.add(tmpd.elemMul(t));
	}
}
