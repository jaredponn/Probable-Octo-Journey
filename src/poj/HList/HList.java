package poj.HList;

import poj.HList.HCons;

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
