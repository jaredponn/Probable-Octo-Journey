package poj.Collisions;

/**
 * Min binary heap .
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

	public MinBinaryHeap()
	{
		arr = new ArrayList<T>(DEFAULT_BUF_SIZE);
	}

	public int getLeft(int i)
	{
		return (2 * i) + 1;
	}

	public int getRight(int i)
	{
		return (2 * i) + 2;
	}

	public int getParent(int i)
	{
		return (i - 1) / 2;
	}

	public T deref(int i)
	{
		return arr.get(i);
	}

	public void setAt(int i, T val)
	{
		arr.set(i, val);
	}
}
