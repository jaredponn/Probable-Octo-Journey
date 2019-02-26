package poj.HList;

public class HNil extends HList<HNil>
{

	/**
	 * HNil constructor
	 */
	public HNil()
	{
	}

	/**
	 * HVisitor to recover type information
	 * @param v HVisitor
	 * @return Java's run time rep of the type
	 */
	public Class<?> accept(HVisitor v)
	{
		return v.hvisit(this).getClass();
	}
}

