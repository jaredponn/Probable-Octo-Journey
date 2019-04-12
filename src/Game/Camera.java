package Game;
/**
 * Camera. Alias of the Vector2Matrix transform
 * Date: February 10, 2019
 * @author Jared
 * @version 1.0
 */

import poj.linear.Vector2MatrixTransform;

public class Camera extends Vector2MatrixTransform
{

	/**
	 * construct camera
	 */
	public Camera()
	{
		super();
	}

	/**
	 * pointer copy constructor
	 */
	public Camera(Vector2MatrixTransform n)
	{
		super();
		m_matrix = n.m_matrix;
	}
}
