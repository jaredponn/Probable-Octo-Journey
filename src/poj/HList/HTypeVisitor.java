package poj.HList;

// The type visitor idea was taken from:
// https://sourcemaking.com/design_patterns/visitor/java/1
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
