package poj.HList;

/**
 * HCons -- Part of the implementation of HList
 * Date: February 10, 2019
 * @author  Jared, and large portions of the code from the following sources:
 * https://apocalisp.wordpress.com/2008/10/23/heterogeneous-lists-and-the-limits-of-the-java-type-system/
 * Sandy Macgire *Thinking with Types*
 * @version      1.0
 */
public class HCons<U, Us extends HList<Us>> extends HList<HCons<U, Us>>
{
	private U head;
	private Us tail;

	/**
	 * HCons constructor
	 *
	 * @param  n head of the lsit
	 * @param  ns  tail of the list
	 */
	public HCons(U n, Us ns)
	{
		head = n;
		tail = ns;
	}

	/**
	 * Gets the head of the lsit
	 *
	 * @return  head -- of the lsit
	 */
	final public U head()
	{
		return head;
	}

	/**
	 * Gets the tail of the lsit
	 *
	 * @return  tail -- of the lsit
	 */
	final public Us tail()
	{
		return tail;
	}

	/**
	 * Type visitor to recover type information
	 *
	 * @param v -- HVisitor to input
	 */
	public Class<?> accept(HVisitor v)
	{
		return v.hvisit(this).getClass();
	}
}
