package poj.HList;

/**
 * HTypeVisitor -- a type visitor for the HList. Used to recover type
 * information
 * Date: February 10, 2019
 * @author  Jared, and parts of the code from the following sources:
 * https://sourcemaking.com/design_patterns/visitor/java/1
 * @version      1.0
 */
public class HTypeVisitor implements HVisitor
{

	/**
	 * ID function for HCons
	 *
	 * @param  n  HCons typed object
	 * @return HCons
	 */
	public <T, Ts extends HList<Ts>> HCons<T, Ts> hvisit(HCons<T, Ts> n)
	{
		return n;
	}

	/**
	 * ID function for HNil
	 *
	 * @param  n  HNil typed object
	 * @return HNil
	 */
	public HNil hvisit(HNil n)
	{
		return n;
	}
}
