package poj.HList;

import poj.HList.HCons;
// Parts of the code was from: ://apocalisp.wordpress.com/2008/10/23/heterogeneous-lists-and-the-limits-of-the-java-type-system/
// And Sandy Macgire's Thinking with TypesTypes
public abstract class HList<T extends HList<T>>
{
	public static final <U, Us extends HList<Us>> HCons<U, Us> hcons(U n,
									 Us ns)
	{
		return new HCons<U, Us>(n, ns);
	}


	public static final HNil hnil()
	{
		return new HNil();
	}

	abstract public Class<?> accept(HVisitor v);
}
