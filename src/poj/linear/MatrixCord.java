package poj.linear;
/**
 * MatrixCord -- matrix coordinate
 *
 * date March 10, 2019
 * @author Haiyang He
 * @version 1.0
 */

public class MatrixCord
{

	public int row;
	public int col;
	/**
	 * Constructs the MatrixCord object with integer row and column
	 * @param  row	integer, row of the matrixCordinate
	 * @param  col	integer, column of the matrixCordinate
	 */
	public MatrixCord(int row, int col)
	{
		this.row = row;
		this.col = col;
	}
	/**
	 * Constructs the MatrixCord object
	 *  @return      void
	 */
	public MatrixCord()
	{
	}
}
