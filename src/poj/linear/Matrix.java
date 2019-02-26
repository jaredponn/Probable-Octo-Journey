package poj.linear;

import java.util.ArrayList;

import poj.Logger.*;

public class Matrix<T>
{
	// change to protected after done
	public ArrayList<T> m_matrix;
	public int rows;
	public int cols;

	/**
	 * Constructs the Matrix object with type T, will create an empty matrix
	 *
	 */
	public Matrix()
	{
		this.m_matrix = new ArrayList<T>();
	}
	/**
	 * Constructs the Matrix object with type T, with an array, row size and
	 * column size (will return MAJOR ERROR if the row and column size is
	 * smaller than 0 or the row and column size does not match the array
	 * imported )
	 * @param  array	ArrayList<T>
	 * @param  rowSize	integer, rowSize of the matrix
	 * @param  colSize	integer, colSize of the matrix
	 */
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

	/**
	 * Constructs the Matrix object with type T, with an array and row size
	 * (will return MAJOR ERROR if the row and column size is smaller than 0
	 * or the row and column size does not match the array imported )
	 * @param  array	ArrayList<T>
	 * @param  rowSize	integer, rowSize of the matrix
	 */
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

	/**
	 * set the array/value of the matrixof the matrix with a new type T
	 * array, the number of rows and column
	 * *
	 * @param  tmp	ArrayList<T>, the new array
	 * @param  rows	integer, rowSize of the new matrix
	 * @param  cols	integer, colSize of the new matrix
	 * @return      void
	 */
	public void setArray(ArrayList<T> tmp, int rows, int cols)
	{
		this.rows = rows;
		this.cols = cols;
		this.m_matrix = tmp;
	}

	/**
	 * set the array/value of the matrixof the matrix with a new type T
	 * array, the number of rows
	 * @param  tmp	ArrayList<T>, the new array
	 * @param  rows	integer, rowSize of the new matrix
	 * @return      void
	 */
	public void setArray(ArrayList<T> tmp, int rows)
	{
		this.m_matrix = tmp;
	}

	/**
	 * get the index inside Matrix from a matrixCordinate object (will
	 * return MAJOR ERROR if the matrixCord is out of bounds for the matrix)
	 * @param  matrixCord	MatrixCord
	 * * @return      integer, the location of the matrix cord inside the
	 * matrix
	 */

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

	/**
	 * get the matrixCord inside Matrix from a matrix index (will
	 * return MAJOR ERROR if the index is out of bounds for the matrix)
	 * @param  index	integer, the matrix index
	 * * @return      the MatrixCord object that represent the matrix index
	 * */
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

	/**
	 * set data with a matrixCordinate (will
	 * return MAJOR ERROR if the matrixCord is out of bounds for the matrix)
	 * @param  matrixCord	MatrixCord, the cordinate inside the matrix
	 * @param  value	type T, the value that will be set in the matrix
	 *         index
	 * * @return      void
	 * */
	public void setWithMatrixCord(final MatrixCord matrixCord, T value)
	{

		Logger.lassert(
			(matrixCord.row < 0 || matrixCord.col < 0
			 || (matrixCord.row * cols + matrixCord.col)
				    > m_matrix.size() - 1),
			"MAJOR ERROR in setWithMatrixCord! index is out of bounds");
		m_matrix.set(matrixCord.row * cols + matrixCord.col, value);
	}

	/**
	 * set data with index of the matrix(will
	 * return MAJOR ERROR if the index is out of bounds for the matrix)
	 * @param  index	integer, the matrix index of the matrix
	 * @param  value	type T, the value that will be set in the matrix
	 *         index
	 * * @return      void
	 * */
	public void setWithIndex(int index, T value)
	{
		Logger.lassert(
			(index < 0 || index > m_matrix.size() - 1),
			"MAJOR ERROR in setWithIndex! index is out of bounds");
		m_matrix.set(index, value);
	}

	/**
	 * get data with a matrixCordinate (will
	 * return MAJOR ERROR if the index is out of bounds for the matrix)
	 * @param  matrixCord	MatrixCord, the cordinate inside the matrix
	 * * @return      type T of the value at the matrix cordinate
	 * */
	public T getDataWithMatrixCord(final MatrixCord matrixCord)
	{
		Logger.lassert(
			(matrixCord.row < 0 || matrixCord.col < 0
			 || (matrixCord.row * cols + matrixCord.col)
				    > m_matrix.size() - 1),
			"MAJOR ERROR in getDataWithMatrixCord! index is out of bounds");
		return m_matrix.get(matrixCord.row * cols + matrixCord.col);
	}

	/**
	 * get data with the integer representation (row and column) of
	 * a matrixCordinate (will return MAJOR ERROR if the index is out of
	 * bounds for the matrix)
	 * @param  r	integer, the row cordinate of the matrixCord
	 * @param  c	integer, the row cordinate of the matrixCord
	 * * @return      type T of the value at the matrix cordinate
	 * */
	public T getDataWithMatrixCord(int r, int c)
	{
		return getDataWithMatrixCord(new MatrixCord(r, c));
	}
	/**
	 * get data with matrix index (will return MAJOR ERROR if the index is
	 * out of bounds for the matrix)
	 * @param  index	integer, the index inside the matrix
	 * * @return      type T of the value at the matrix cordinate
	 * */
	public T getDataWithIndex(int index)
	{
		Logger.lassert(
			(index < 0 || index > m_matrix.size() - 1),
			"MAJOR ERROR in getDataWithIndex! index is out of bounds");
		return m_matrix.get(index);
	}

	/**
	 * will print the Matrix (for debugging)
	 * * @return      type T of the value at the matrix cordinate
	 * */
	public void log()
	{
		Logger.logMessage(LogLevels.VERBOSE, "Matrix log: ");
		for (int r = 0; r < rows; ++r) {
			String tmp = "";
			for (int c = 0; c < cols; ++c) {
				tmp += getDataWithMatrixCord(r, c).toString()
				       + ", ";
			}
			Logger.logMessage(LogLevels.VERBOSE, tmp);
		}
	}
}

