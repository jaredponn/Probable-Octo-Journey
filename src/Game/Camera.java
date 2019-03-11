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
	public Camera()
	{
		super();
	}

	public Camera(Vector2MatrixTransform n)
	{
		super();
		m_matrix = n.m_matrix;
	}
}
