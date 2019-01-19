package poj.linear;

import poj.Logger;
import java.util.ArrayList;

public class Matrix<T>
{
	// change to protected after done
	public ArrayList<T> m_matrix;
	public int rows;
	public int cols;

	public Matrix()
	{
		this.m_matrix = new ArrayList<T>();
	}
	public Matrix(final ArrayList<T> array, int rowSize, int colSize)
	{
		Logger.lassert(
			(array.size() <= 0 || rowSize <= 0 || colSize <= 0),
			"MAJOR ERROR IN Matrix! The row or col is 0!!");
		Logger.lassert(
			((rowSize * colSize) != array.size()),
			"MAJOR ERROR in Matrix! the row*col size does not match the arrayList size!");
		this.m_matrix = array;
		this.rows = rowSize;
		this.cols = colSize;
	}

	public Matrix(final ArrayList<T> array, int rowSize)
	{
		Logger.lassert((array.size() <= 0 || rowSize <= 0),
			       "MAJOR ERROR IN Matrix! The row or col is 0!!");
		Logger.lassert(
			((array.size() % rowSize) != 0),
			"MAJOR ERROR in Matrix! arrayList size / rowSize have REMAINDER!");
		this.m_matrix = array;
		this.rows = rowSize;
		this.cols = array.size() / rowSize;
	}

	public void setArray(ArrayList<T> tmp, int size)
	{
		this.rows = size;
		this.cols = size;
		this.m_matrix = tmp;
	}
	// 0 based indexing!!

	public int getIndexFromMatrixCord(final MatrixCord matrixCord)
	{
		Logger.lassert(
			(matrixCord.row < 0 || matrixCord.col < 0
			 || (matrixCord.row * cols + matrixCord.col)
				    > m_matrix.size() - 1),
			"MAJOR ERROR in getIndexFromMatrixCord! index is out of bounds");
		return matrixCord.row * cols + matrixCord.col;
	}

	public MatrixCord getMatrixCordFromIndex(int index)
	{
		Logger.lassert(
			(index < 0 || index > m_matrix.size() - 1),
			"MAJOR ERROR in getMatrixCordFromIndex! index is out of bounds");
		MatrixCord matrixCord = new MatrixCord();
		matrixCord.row = index / cols;
		matrixCord.col = index % cols;
		return matrixCord;
	}

	public void setWithMatrixCord(final MatrixCord matrixCord, T value)
	{

		Logger.lassert(
			(matrixCord.row < 0 || matrixCord.col < 0
			 || (matrixCord.row * cols + matrixCord.col)
				    > m_matrix.size() - 1),
			"MAJOR ERROR in setWithMatrixCord! index is out of bounds");
		m_matrix.set(matrixCord.row * cols + matrixCord.col, value);
	}

	public void setWithIndex(int index, T value)
	{
		Logger.lassert(
			(index < 0 || index > m_matrix.size() - 1),
			"MAJOR ERROR in setWithIndex! index is out of bounds");
		m_matrix.set(index, value);
	}

	public T getDataWithMatrixCord(final MatrixCord matrixCord)
	{
		Logger.lassert(
			(matrixCord.row < 0 || matrixCord.col < 0
			 || (matrixCord.row * cols + matrixCord.col)
				    > m_matrix.size() - 1),
			"MAJOR ERROR in getDataWithMatrixCord! index is out of bounds");
		return m_matrix.get(matrixCord.row * cols + matrixCord.col);
	}
	public T getDataWithIndex(int index)
	{
		Logger.lassert(
			(index < 0 || index > m_matrix.size() - 1),
			"MAJOR ERROR in getDataWithIndex! index is out of bounds");
		return m_matrix.get(index);
	}
}

