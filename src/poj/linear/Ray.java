package poj.linear;
/**
 * Ray.
 *
 * date March 10, 2019
 * @author Haiyang He
 * @version 1.0
 */

public class Ray
{
	Vector3f origin;
	Vector3f direction;
	/**
	 * Constructs the Ray object with Vector3f origin and Vector3f direction
	 * @param  origin	Vector3f, origin vector
	 *  @param  direction	Vector3f, direction vector
	 */
	public Ray(Vector3f origin, Vector3f direction)
	{
		this.origin = origin;
		this.direction = direction;
	}


	/**
	 * returns the origin vector
	 *  @return      Vector3f, origin vector
	 */
	final public Vector3f getOrigin()
	{
		return this.origin;
	}

	/**
	 * returns the direction vector
	 *  @return      Vector3f, direction vector
	 */
	final public Vector3f getDirection()
	{
		return this.direction;
	}


	/*
	final public Vector3f pointAtParameter(float t)
	{
		Vector3f tmpo = Vector3f.deepCopyVector3f(this.origin);
		Vector3f tmpd = Vector3f.deepCopyVector3f(this.direction);

		return tmpo.add(tmpd.elemMul(t));
	}
	*/
}
