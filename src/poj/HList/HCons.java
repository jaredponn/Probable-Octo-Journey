package poj.HList;

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
