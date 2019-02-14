package poj.linear;

public class Vector2MatrixTransform extends MatrixTransformations
{
	public final static int DIAGONAL_LENGTH = 3;

	public static final float EPSILON = 0.00000001f;

	public Vector2MatrixTransform()
	{
		super(DIAGONAL_LENGTH);
	}

	public void setTranslationForVector2(float xt, float yt)
	{
		this.m_matrix.set(2, xt);
		this.m_matrix.set(5, yt);
	}

	public void setScalingForVector2(float xs, float ys)
	{
		this.m_matrix.set(0, xs);
		this.m_matrix.set(4, ys);
	}


	/** rotation with respect to X axis:*/
	public void setRotationForVector2XaxisCC(float theta)
	{
		this.m_matrix.set(0, (float)Math.cos(theta));
		this.m_matrix.set(1, -(float)Math.sin(theta));
		this.m_matrix.set(3, (float)Math.sin(theta));
		this.m_matrix.set(4, (float)Math.cos(theta));
	}

	/** rotation with respect to X axis:*/
	public void setShearForVector2XaxisCC(float xshear, float yshear)
	{
		this.m_matrix.set(1, xshear);
		this.m_matrix.set(3, yshear);
	}

	// composes the current vector with a rotation.
	public void composeWithRotationForVector2XaxisCC(float theta)
	{
		Vector2MatrixTransform tmp = new Vector2MatrixTransform();
		tmp.setRotationForVector2XaxisCC(theta);
		this.compose(tmp);
	}

	// refactor this in the future so it's generalized
	// if the det is 0, will throw a random error
	public Vector2MatrixTransform unsafePureInverse()
	{
		Vector2MatrixTransform adj = pureGetMatrixOfAdjugate();
		final float determinant = det();

		for (int i = 0;
		     i < Vector2MatrixTransform.DIAGONAL_LENGTH
				 * Vector2MatrixTransform.DIAGONAL_LENGTH;
		     ++i) {
			adj.setWithIndex(i,
					 adj.getDataWithIndex(i) / determinant);
		}

		return adj;
	}


	// also bad someone please refactor this
	private Vector2MatrixTransform pureGetMatrixOfAdjugate()
	{
		Vector2MatrixTransform tmp = new Vector2MatrixTransform();
		tmp.setWithIndex(
			0, det2By2(getDataWithIndex(4), getDataWithIndex(5),
				   getDataWithIndex(7), getDataWithIndex(8)));
		tmp.setWithIndex(
			1, -det2By2(getDataWithIndex(3), getDataWithIndex(5),
				    getDataWithIndex(6), getDataWithIndex(8)));
		tmp.setWithIndex(
			2, det2By2(getDataWithIndex(3), getDataWithIndex(4),
				   getDataWithIndex(6), getDataWithIndex(7)));
		tmp.setWithIndex(
			3, -det2By2(getDataWithIndex(1), getDataWithIndex(2),
				    getDataWithIndex(7), getDataWithIndex(8)));
		tmp.setWithIndex(
			4, det2By2(getDataWithIndex(0), getDataWithIndex(2),
				   getDataWithIndex(6), getDataWithIndex(8)));
		tmp.setWithIndex(
			5, -det2By2(getDataWithIndex(0), getDataWithIndex(1),
				    getDataWithIndex(6), getDataWithIndex(7)));
		tmp.setWithIndex(
			6, det2By2(getDataWithIndex(1), getDataWithIndex(2),
				   getDataWithIndex(4), getDataWithIndex(5)));
		tmp.setWithIndex(
			7, -det2By2(getDataWithIndex(0), getDataWithIndex(2),
				    getDataWithIndex(3), getDataWithIndex(5)));
		tmp.setWithIndex(
			8, det2By2(getDataWithIndex(0), getDataWithIndex(1),
				   getDataWithIndex(3), getDataWithIndex(4)));

		tmp.unsafeSwap(1, 3);
		tmp.unsafeSwap(2, 6);
		tmp.unsafeSwap(5, 7);
		return tmp;
	}

	// someone please refactor this
	public float det()
	{
		return getDataWithIndex(0)
			* det2By2(getDataWithIndex(4), getDataWithIndex(5),
				  getDataWithIndex(7), getDataWithIndex(8))
			- getDataWithIndex(1)
				  * det2By2(getDataWithIndex(3),
					    getDataWithIndex(5),
					    getDataWithIndex(6),
					    getDataWithIndex(8))
			+ getDataWithIndex(2)
				  * det2By2(getDataWithIndex(3),
					    getDataWithIndex(4),
					    getDataWithIndex(6),
					    getDataWithIndex(7));
	}

	// a,b
	// c,d
	public static float det2By2(float a, float b, float c, float d)
	{

		return a * d - b * c;
	}

	// refactor this in the future it's generalized
	public boolean isInvertible()
	{
		return Math.abs(det() - 0) < EPSILON;
	}
}
