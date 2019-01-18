package poj.linear;

import poj.Logger;
import java.util.ArrayList;
import java.util.Collections;
public class MatrixTransformations extends Matrix<Float>
{
	public MatrixTransformations()
	{
		super();
	}


	public void initIdentity(int size)
	{
		ArrayList<Float> tmp = new ArrayList<Float>();
		for (int i = 0; i < size; ++i) {

			for (int j = 0; j < size; ++j) {
				if (i == j) {
					tmp.add(1.f);
				} else {
					tmp.add(0.f);
				}
			}
		}
		setArray(tmp, size);
	}

	public void setTranslationVector3(float xt, float yt)
	{
		this.m_matrix.set(2, xt);
		this.m_matrix.set(5, yt);
	}

	public void setScalingVector3(float xs, float ys)
	{
		this.m_matrix.set(0, xs);
		this.m_matrix.set(4, ys);
	}

	// TODO no rotation and make identity, inherit matrix, then have
	// constructor to create the n*n identity matrix, add rotation, add
	// translation, clear the matrix back to n*n identity
	public final void matrixMultiplication(Matrix<Float> matrixB)
	{
		Logger.lassert(
			(this.cols != matrixB.rows || this.rows == 0
			 || this.cols == 0 || matrixB.rows == 0
			 || matrixB.cols == 0),
			"MAJOR ERROR in matrixMultiplication! A's col number does not match with B's row number");

		ArrayList<Float> tempMatrixC = new ArrayList<Float>(
			Collections.nCopies(this.rows * matrixB.cols, 0.f));
		for (int i = 0; i < this.rows; ++i) {
			for (int j = 0; j < matrixB.cols; ++j) {
				float tempValue = 0;
				for (int m = 0; m < this.cols; ++m) {
					tempValue +=
						this.getDataWithMatrixCord(
							new MatrixCord(i, m))
						* matrixB.getDataWithMatrixCord(
							  new MatrixCord(m, j));
				}
				tempMatrixC.set(i * matrixB.cols + j,
						tempValue);
			}
		}
		this.m_matrix = tempMatrixC;
	}

	public final boolean matrixEquality(Matrix<Float> A, Matrix<Float> B,
					    float EPSILON)
	{
		Logger.lassert(
			(this.m_matrix.size() != B.m_matrix.size()),
			"MAJOR ERROR in matrixEquality!! the two matrix size are not the same");
		for (int i = 0; i < A.m_matrix.size(); ++i) {
			if (AlmostEqualRelative(this.getDataWithIndex(i),
						B.getDataWithIndex(i),
						EPSILON)) {
				return false;
			}
		}
		return true;
	}

	// TODO 	MAKE UNIT TEST FOR THISSSSSSSSS
	public final boolean AlmostEqualRelative(float A, float B,
						 float EPSILON)
	{
		float diff = Math.abs(A - B);
		A = Math.abs(A);
		B = Math.abs(B);
		float largest = (B > A) ? B : A;
		if ((diff <= largest * EPSILON)
		    && (diff >= largest * -EPSILON)) {
			return true;
		}
		return false;
	}


	public final static Vector2f
	matrixVector2fProduct(final Matrix<Float> a, final Vector2f b)
	{
		// if column of matrix A is not 2 (2d vector)
		Logger.lassert(
			(a.cols != 2 || a.rows != 2),
			"MAJOR ERROR in matrixVectorProduct! dimentions of the matrix and the vector does not match!");
		float xnew, ynew;
		// loop through each row of the matrix
		xnew = a.getDataWithIndex(0) * b.x
		       + a.getDataWithIndex(1) * b.y;
		ynew = a.getDataWithIndex(2) * b.x
		       + a.getDataWithIndex(3) * b.y;
		return new Vector2f(xnew, ynew);
	}


	public final static Vector3f
	matrixVector3fProduct(final Matrix<Float> a, final Vector3f b)
	{
		// if column of matrix A is not 2 (2d vector)
		Logger.lassert(
			(a.cols != 3 || a.rows != 3),
			"MAJOR ERROR in matrixVectorProduct! dimentions of the matrix and the vector does not match!");
		float xnew, ynew, znew;
		// loop through each row of the matrix
		xnew = a.getDataWithIndex(0) * b.x + a.getDataWithIndex(1) * b.y
		       + a.getDataWithIndex(2) * b.z;
		ynew = a.getDataWithIndex(3) * b.x + a.getDataWithIndex(4) * b.y
		       + a.getDataWithIndex(5) * b.z;
		znew = a.getDataWithIndex(6) * b.x + a.getDataWithIndex(7) * b.y
		       + a.getDataWithIndex(8) * b.z;
		;
		return new Vector3f(xnew, ynew, znew);
	}
}
