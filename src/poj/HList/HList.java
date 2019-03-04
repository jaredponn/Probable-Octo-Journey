package poj.HList;

import poj.HList.HCons;

// Parts of the code was from:
// https://apocalisp.wordpress.com/2008/10/23/heterogeneous-lists-and-the-limits-of-the-java-type-system/
// And Sandy Macgire's Thinking with Types
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
