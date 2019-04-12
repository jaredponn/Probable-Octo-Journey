package poj.linear;
/**
 * Vector2 matrix transform
 *
 * date March 10, 2019
 * @author Haiyang He
 * @version 1.0
 */

public class Vector2MatrixTransform extends MatrixTransformations
{
	public final static int DIAGONAL_LENGTH = 3;

	public static final float EPSILON = 0.00000001f;

	/**
	 * Constructs the Vector2MatrixTransform object, which extends
	 * MatrixTransformations, and calls the MatrixTransformations
	 * constructor to initialize an 3x3 identity matrix.
	 */
	public Vector2MatrixTransform()
	{
		super(DIAGONAL_LENGTH);
	}

	/**
	 * set the translation vector for the vector2f
	 * @param  xt	float, x translation value
	 * @param  yt	float, y translation value
	 *  @return      void
	 */
	public void setTranslationForVector2(float xt, float yt)
	{
		this.m_matrix.set(2, xt);
		this.m_matrix.set(5, yt);
	}

	/**
	 * set the scaling vector for the vector2f
	 * @param  xs	float, x scaling value
	 * @param  ys	float, y scaling value
	 *  @return      void
	 */
	public void setScalingForVector2(float xs, float ys)
	{
		this.m_matrix.set(0, xs);
		this.m_matrix.set(4, ys);
	}

	/**
	 * will swap the x and y values inside vector2f, with unsafeSwap.
	 *  @return      void
	 */
	public void setSwapXAndY()
	{
		unsafeSwap(0, 3);
		unsafeSwap(1, 4);
		unsafeSwap(2, 5);
	}

	/**
	 * Will make a temporary vector, swap x and y values inside it, and then
	 * multiply it with the current vector2f
	 *  @return      void
	 */
	public void composeSetSwapXAndY()
	{
		Vector2MatrixTransform tmp = new Vector2MatrixTransform();
		tmp.setSwapXAndY();
		this.compose(tmp);
	}

	/**
	 * Will make a temporary vector, set the scaling value for it, and then
	 * multiply it with the current vector2f
	 *  @return      void
	 */
	public void composeSetScalingForVector2(float xs, float ys)
	{
		Vector2MatrixTransform tmp = new Vector2MatrixTransform();
		tmp.setScalingForVector2(xs, ys);
		this.compose(tmp);
	}


	/**
	 * Will do a rotation of the vector2f with respect to X axis
	 *   @return      void
	 */
	public void setRotationForVector2XaxisCC(float theta)
	{
		this.m_matrix.set(0, (float)Math.cos(theta));
		this.m_matrix.set(1, -(float)Math.sin(theta));
		this.m_matrix.set(3, (float)Math.sin(theta));
		this.m_matrix.set(4, (float)Math.cos(theta));
	}

	/**
	 * Will do a shear of the vector2f with respect to X axis
	 *   @return      void
	 */
	public void setShearForVector2XaxisCC(float xshear, float yshear)
	{
		this.m_matrix.set(1, xshear);
		this.m_matrix.set(3, yshear);
	}

	/**
	 * Will make a temporary vector, set the rotation  value for it, and
	 then
	 * multiply it with the current vector2f

	 *   @return      void
	 */
	// composes the current vector with a rotation.
	public void composeWithRotationForVector2XaxisCC(float theta)
	{
		Vector2MatrixTransform tmp = new Vector2MatrixTransform();
		tmp.setRotationForVector2XaxisCC(theta);
		this.compose(tmp);
	}

	/**
	 * will calculate the inverse of a 3x3  matrix unsafely (does
	 * not check for errors, and if the determinant is 0 it will throw a
	 * random error)
	 *   @return      Vector2MatrixTransform, the inverse matrix
	 */
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


	/**
	 * will calculate the adjugate of a 3x3  matrix
	 *    @return      Vector2MatrixTransform, the adjugate matrix
	 */
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

	/**
	 * will calculate the determinant of a 3x3  matrix
	 *    @return      float, the determinant of the matrix
	 */
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

	/**
	 * will calculate the determinant of a specified 2x2  matrix
	 * @param  a	float, cordinate (0,0) of the matrix
	 * @param  b	float, cordinate (0,1) of the matrix
	 * @param  c	float, cordinate (1,0) of the matrix
	 * @param  d	float, cordinate (1,1) of the matrix
	 *    @return      float, the determinant of the matrix
	 */
	// a,b
	// c,d
	public static float det2By2(float a, float b, float c, float d)
	{

		return a * d - b * c;
	}

	/**
	 * return if the 3x3 matrix is isInvertible
	 *    @return      boolean, true if the matrix is invertible, and false
	 * otherwise
	 */
	// refactor this in the future it's generalized
	public boolean isInvertible()
	{
		return !(Math.abs(det() - 0) < EPSILON);
	}
}
