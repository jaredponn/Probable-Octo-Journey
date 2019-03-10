package poj.HList;

/**
 * HList -- implementation of a list that takes different types.
 * Date: February 10, 2019
 * @author  Jared, and large portions of the code from the following sources:
 * https://apocalisp.wordpress.com/2008/10/23/heterogeneous-lists-and-the-limits-of-the-java-type-system/
 * Sandy Macgire *Thinking with Types*
 * @version      1.0
 */

import poj.HList.HCons;

public abstract class HList<T extends HList<T>>
{

	/**
	 * Alias for new HCons
	 *
	 * @param  n head
	 * @param  ns tail
	 * @return      HList of the list
	 */
	public static final <U, Us extends HList<Us>> HCons<U, Us> hcons(U n,
									 Us ns)
	{
		return new HCons<U, Us>(n, ns);
	}


	/**
	 * Alias for new HNil
	 *
	 * @return      HNil
	 */
	public static final HNil hnil()
	{
		return new HNil();
	}

	/**
	 * Type visitor to recover type information
	 *
	 * @return      type information of the visited object
	 */
	abstract public Class<?> accept(HVisitor v);
}
