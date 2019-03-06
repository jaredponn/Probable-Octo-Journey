package poj.HList;

/**
 * HVisitor -- used to recover type information of types.
 * Date: February 10, 2019
 * @author  Jared, and parts of the code from the following sources:
 * https://sourcemaking.com/design_patterns/visitor/java/1
 * @version      1.0
 */
public interface HVisitor {

	/**
	 *  Visitors for types
	 */
	HNil hvisit(HNil n);
	<T, Ts extends HList<Ts>> HCons<T, Ts> hvisit(HCons<T, Ts> n);
}
