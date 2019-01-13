package poj;
import poj.Logger;
import java.util.ArrayList;

public class Matrix<T>
{

	private ArrayList<T> m_matrix;
	private int rows;
	private int cols;

	public Matrix(ArrayList<T> array, int rowSize, int colSize)
	{
		if ((rowSize * colSize) != array.size()) {
			Logger.logMessage(
				"MAJOR ERROR in Matrix! the row*col size does not match the arrayList size!",
				LOG_LEVEL.MAJOR_CRITICAL);
		}
		m_matrix = array;
		this.rows = rowSize;
		this.cols = colSize;
	}

	public Matrix(ArrayList<T> array, int rowSize)
	{
		if ((array.size() % rowSize) != 0) {
			Logger.logMessage(
				"MAJOR ERROR in Matrix! arrayList size / rowSize have REMAINDER!",
				LOG_LEVEL.MAJOR_CRITICAL);
		}
		m_matrix = array;
		this.rows = rowSize;
		this.cols = array.size() / rowSize;
	}

	// 0 based indexing!!
	public int getIndexFromMatrixCord(MatrixCord matrixCord)
	{
		if (matrixCord.row < 0 || matrixCord.col < 0
		    || (matrixCord.row * rows + matrixCord.col)
			       > m_matrix.size() - 1) {
			Logger.logMessage(
				"MAJOR ERROR in getIndexFromMatrixCord! index is out of bounds",
				LOG_LEVEL.MAJOR_CRITICAL);
		}
		return matrixCord.row * rows + matrixCord.col;
	}

	public MatrixCord getMatrixCordFromIndex(int index)
	{
		if (index < 0 || index > m_matrix.size() - 1) {
			Logger.logMessage(
				"MAJOR ERROR in getMatrixCordFromIndex! index is out of bounds",
				LOG_LEVEL.MAJOR_CRITICAL);
		}
		MatrixCord matrixCord = new MatrixCord();
		matrixCord.row = index / rows;
		matrixCord.col = index % cols;
		return matrixCord;
	}

	public void setWithMatrixCord(MatrixCord matrixCord, T value)
	{

		if (matrixCord.row < 0 || matrixCord.col < 0
		    || (matrixCord.row * rows + matrixCord.col)
			       > m_matrix.size() - 1) {
			Logger.logMessage(
				"MAJOR ERROR in setWithMatrixCord! index is out of bounds",
				LOG_LEVEL.MAJOR_CRITICAL);
		}
		m_matrix.set(matrixCord.row * rows + matrixCord.col, value);
	}

	public void setWithIndex(int index, T value)
	{
		if (index < 0 || index > m_matrix.size() - 1) {
			Logger.logMessage(
				"MAJOR ERROR in setWithIndex! index is out of bounds",
				LOG_LEVEL.MAJOR_CRITICAL);
		}
		m_matrix.set(index, value);
	}

	public T getDataWithMatrixCord(MatrixCord matrixCord)
	{
		if (matrixCord.row < 0 || matrixCord.col < 0
		    || (matrixCord.row * rows + matrixCord.col)
			       > m_matrix.size() - 1) {
			Logger.logMessage(
				"MAJOR ERROR in getDataWithMatrixCord! index is out of bounds",
				LOG_LEVEL.MAJOR_CRITICAL);
		}
		return m_matrix.get(matrixCord.row * rows + matrixCord.col);
	}
	public T getDataWithIndex(int index)
	{
		if (index < 0 || index > m_matrix.size() - 1) {
			Logger.logMessage(
				"MAJOR ERROR in getDataWithIndex! index is out of bounds",
				LOG_LEVEL.MAJOR_CRITICAL);
		}
		return m_matrix.get(index);
	}
}

class MatrixCord
{
	public int row;
	public int col;
}
