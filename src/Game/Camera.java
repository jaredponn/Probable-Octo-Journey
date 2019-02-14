package Game;

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
