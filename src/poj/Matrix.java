package poj;
import poj.Logger;
import poj.LogLevels;
import poj.MatrixCord;
import java.util.ArrayList;

public class Matrix<T>
{

	public ArrayList<T> m_matrix;
	public int rows;
	public int cols;

	public Matrix(final ArrayList<T> array, int rowSize, int colSize)
	{
		if (array.size() <= 0 || rowSize <= 0 || colSize <= 0) {
			Logger.logMessage(
				"MAJOR ERROR IN Matrix! The row or col is 0!!",
				LogLevels.MAJOR_CRITICAL);
		}
		if ((rowSize * colSize) != array.size()) {
			Logger.logMessage(
				"MAJOR ERROR in Matrix! the row*col size does not match the arrayList size!",
				LogLevels.MAJOR_CRITICAL);
		}
		m_matrix = array;
		this.rows = rowSize;
		this.cols = colSize;
	}

	public Matrix(final ArrayList<T> array, int rowSize)
	{
		if (array.size() <= 0 || rowSize <= 0) {
			Logger.logMessage(
				"MAJOR ERROR IN Matrix! The row or col is 0!!",
				LogLevels.MAJOR_CRITICAL);
		}
		if ((array.size() % rowSize) != 0) {
			Logger.logMessage(
				"MAJOR ERROR in Matrix! arrayList size / rowSize have REMAINDER!",
				LogLevels.MAJOR_CRITICAL);
		}
		m_matrix = array;
		this.rows = rowSize;
		this.cols = array.size() / rowSize;
	}
	// TODO construct array with row and col size, but WHAT ABOUT THE
	// TYPE?????
	/*
	public Matrix( int rowSize, int colSize){

	}
	*/
	// 0 based indexing!!
	public int getIndexFromMatrixCord(final MatrixCord matrixCord)
	{
		if (matrixCord.row < 0 || matrixCord.col < 0
		    || (matrixCord.row * cols + matrixCord.col)
			       > m_matrix.size() - 1) {
			Logger.logMessage(
				"MAJOR ERROR in getIndexFromMatrixCord! index is out of bounds",
				LogLevels.MAJOR_CRITICAL);
		}
		return matrixCord.row * cols + matrixCord.col;
	}

	public MatrixCord getMatrixCordFromIndex(int index)
	{
		if (index < 0 || index > m_matrix.size() - 1) {
			Logger.logMessage(
				"MAJOR ERROR in getMatrixCordFromIndex! index is out of bounds",
				LogLevels.MAJOR_CRITICAL);
		}
		MatrixCord matrixCord = new MatrixCord();
		matrixCord.row = index / cols;
		matrixCord.col = index % cols;
		return matrixCord;
	}

	public void setWithMatrixCord(final MatrixCord matrixCord, T value)
	{

		if (matrixCord.row < 0 || matrixCord.col < 0
		    || (matrixCord.row * cols + matrixCord.col)
			       > m_matrix.size() - 1) {
			Logger.logMessage(
				"MAJOR ERROR in setWithMatrixCord! index is out of bounds",
				LogLevels.MAJOR_CRITICAL);
		}
		m_matrix.set(matrixCord.row * cols + matrixCord.col, value);
	}

	public void setWithIndex(int index, T value)
	{
		if (index < 0 || index > m_matrix.size() - 1) {
			Logger.logMessage(
				"MAJOR ERROR in setWithIndex! index is out of bounds",
				LogLevels.MAJOR_CRITICAL);
		}
		m_matrix.set(index, value);
	}

	public T getDataWithMatrixCord(final MatrixCord matrixCord)
	{
		if (matrixCord.row < 0 || matrixCord.col < 0
		    || (matrixCord.row * cols + matrixCord.col)
			       > m_matrix.size() - 1) {
			Logger.logMessage(
				"MAJOR ERROR in getDataWithMatrixCord! index is out of bounds",
				LogLevels.MAJOR_CRITICAL);
		}
		return m_matrix.get(matrixCord.row * cols + matrixCord.col);
	}
	public T getDataWithIndex(int index)
	{
		if (index < 0 || index > m_matrix.size() - 1) {
			Logger.logMessage(
				"MAJOR ERROR in getDataWithIndex! index is out of bounds",
				LogLevels.MAJOR_CRITICAL);
		}
		return m_matrix.get(index);
	}
}

