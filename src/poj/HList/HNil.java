package poj.HList;


/**
 * HNil -- Part of the HList implementation.
 * Date: February 10, 2019
 * @author  Jared, and parts of the code from the following sources:
 * https://apocalisp.wordpress.com/2008/10/23/heterogeneous-lists-and-the-limits-of-the-java-type-system/
 * Sandy Macgire *Thinking with Types*
 * @version      1.0
 */
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
