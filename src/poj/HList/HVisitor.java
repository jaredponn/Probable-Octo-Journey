package poj.HList;


public interface HVisitor {

	/**
	 *  Visitors for types
	 */
	HNil hvisit(HNil n);
	<T, Ts extends HList<Ts>> HCons<T, Ts> hvisit(HCons<T, Ts> n);
}
