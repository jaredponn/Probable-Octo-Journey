package poj.Collisions;

/**
 * Min binary heap . DEPRECATED
 * Root is at arr[0].
 * Parent is at: arr[(i-1)/2]
 * left is at: arr[(2*i)+1]
 * right is at: arr[(2*i)+2]
 * https://www.geeksforgeeks.org/binary-heap/
 * @author  Jared Pon
 * @version  1.00
 */
import java.util.ArrayList;

public class MinBinaryHeap<T>
{

	public ArrayList<T> arr;

	public static int DEFAULT_BUF_SIZE = 10000;

	/**
	 * Constructor
	 */
	public MinBinaryHeap()
	{
		arr = new ArrayList<T>(DEFAULT_BUF_SIZE);
	}


	/**
	 * gets the left index of given index
	 * @param i index
	 * @return left index
	 */
	public int getLeft(int i)
	{
		return (2 * i) + 1;
	}

	/**
	 * gets the right index of given index
	 * @param i index
	 * @return right index
	 */
	public int getRight(int i)
	{
		return (2 * i) + 2;
	}

	/**
	 * gets the parent index of given index
	 * @param i index
	 * @return parent index
	 */
	public int getParent(int i)
	{
		return (i - 1) / 2;
	}

	/**
	 * dereferences the type (array gey)
	 * @param i index
	 * @return data
	 */
	public T deref(int i)
	{
		return arr.get(i);
	}

	/**
	 * set at
	 * @param i index
	 * @param val  data
	 */
	public void setAt(int i, T val)
	{
		arr.set(i, val);
	}
}
