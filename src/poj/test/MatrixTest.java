package poj.test;
import poj.Matrix;
import poj.MatrixCord;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;
public class MatrixTest
{

	@Test public void trivialTest()
	{
		ArrayList<Integer> a = new ArrayList<Integer>();
		ArrayList<Integer> b = new ArrayList<Integer>();
		for (int i = 0; i < 6; ++i) {
			a.add(i);
			b.add(i);
		}
		Matrix<Integer> matrixA = new Matrix<Integer>(a, 2, 3);
		Matrix<Integer> matrixB = new Matrix<Integer>(b, 2);
		assertEquals(matrixA.rows, 2);
		assertEquals(matrixA.cols, 3);
		assertEquals(matrixB.rows, 2);
		assertEquals(matrixB.cols, 3);
	}

	@Test public void getIndexFromMatrixCord()
	{

		ArrayList<Integer> a = new ArrayList<Integer>();
		ArrayList<Integer> b = new ArrayList<Integer>();
		for (int i = 0; i < 6; ++i) {
			a.add(i);
			b.add(i);
		}
		Matrix<Integer> matrixA = new Matrix<Integer>(a, 2, 3);
		MatrixCord testCord = new MatrixCord();
		testCord.row = 1;
		testCord.col = 1;
		assertEquals(Integer.valueOf(
				     matrixA.getIndexFromMatrixCord(testCord)),
			     Integer.valueOf(4));
	}
	@Test public void getMatrixCordFromIndex()
	{

		ArrayList<Integer> a = new ArrayList<Integer>();
		ArrayList<Integer> b = new ArrayList<Integer>();
		for (int i = 0; i < 6; ++i) {
			a.add(i);
			b.add(i);
		}
		Matrix<Integer> matrixA = new Matrix<Integer>(a, 2, 3);
		Matrix<Integer> matrixB = new Matrix<Integer>(b, 2);
		MatrixCord testCord;
		testCord = matrixA.getMatrixCordFromIndex(5);
		assertEquals(Integer.valueOf(testCord.row), Integer.valueOf(1));
		assertEquals(Integer.valueOf(testCord.col), Integer.valueOf(2));
	}

	@Test public void setStuff()
	{

		ArrayList<Integer> a = new ArrayList<Integer>();
		for (int i = 0; i < 6; ++i) {
			a.add(0);
		}
		Matrix<Integer> matrixA = new Matrix<Integer>(a, 2, 3);
		MatrixCord testCord = new MatrixCord();
		testCord.row = 1;
		testCord.col = 1;
		matrixA.setWithMatrixCord(testCord, 1);
		matrixA.setWithIndex(2, 2);
		assertEquals(Integer.valueOf(matrixA.m_matrix.get(4)),
			     Integer.valueOf(1));
		assertEquals(Integer.valueOf(matrixA.m_matrix.get(2)),
			     Integer.valueOf(2));
		testCord.row = 2;
		testCord.col = 1;
		// matrixA.setWithMatrixCord(testCord, 1);
	}
	@Test public void getStuff()
	{

		ArrayList<Integer> a = new ArrayList<Integer>();
		for (int i = 0; i < 6; ++i) {
			a.add(0);
		}
		Matrix<Integer> matrixA = new Matrix<Integer>(a, 2, 3);
		MatrixCord testCord = new MatrixCord();
		testCord.row = 1;
		testCord.col = 1;
		matrixA.setWithMatrixCord(testCord, 1);
		matrixA.setWithIndex(2, 2);
		assertEquals(Integer.valueOf(
				     matrixA.getDataWithMatrixCord(testCord)),
			     Integer.valueOf(1));
		assertEquals(Integer.valueOf(matrixA.getDataWithIndex(2)),
			     Integer.valueOf(2));
		testCord.row = 2;
		testCord.col = 1;
		// matrixA.setWithMatrixCord(testCord, 1); }
	}
}
