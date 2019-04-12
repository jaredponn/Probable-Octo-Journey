package Components;
/**
 * generalized double int component
 * @author Jared Pon
 * March 1, 2019
 * v 0.0
 */

public class DoubleIntComponent extends SingleIntComponent
{
	int focus2;


	/**
	 * constructor
	 */
	public DoubleIntComponent()
	{
	}

	/**
	 * constructor
	 * @param a focus1
	 * @param b focus2
	 */
	public DoubleIntComponent(int a, int b)
	{
		super(a);
		focus2 = b;
	}

	/**
	 * add to focus2
	 * @param n int
	 */
	public void addFocus2(int n)
	{
		focus2 += n;
	}

	/**
	 * get focus2
	 * @return n int
	 */
	public int getFocus2()
	{
		return focus2;
	}

	/**
	 * set focus2
	 * @@param n int
	 */
	public void setFocus2(int n)
	{
		focus2 = n;
	}

	/**
	 * print
	 */
	public void print()
	{
		System.out.println("DoubleIntComponent: focus1: " + focus1
				   + ", focus2: " + focus2);
	}
}
