package poj.EntitySet;
import poj.HList.*;
import poj.Component.*;

/**
 * Abstraction like member variables to add them to the Components
 */
public class EntitySetMemberComponents
{
	private HCons<?, ?> entitySet;


	/**
	 * Constructor. Initial constructor -- constructs the
	 * EntitySetMemberComponents with the given Component and the HNil
	 * component
	 */
	public <U extends Component> EntitySetMemberComponents(U c)
	{
		entitySet = HList.hcons(c, HList.hnil());
	}

	/**
	 * Adds a component to the set
	 *
	 * @param  c component to add to the set
	 */
	public <U extends Component> void addComponentToSet(U c)
	{
		entitySet = HList.hcons(c, entitySet);
	}

	@SuppressWarnings("unchecked")

	/**
	 * prints the entire set -- useful for debugging
	 */
	public <T extends Component> void printSet()
	{
		HTypeVisitor h = new HTypeVisitor();

		HCons<?, ?> foc = entitySet;

		while (foc.tail().accept(h) != HNil.class) {

			((T)(foc.head())).print();

			foc = (HCons<?, ?>)foc.tail();
		}

		((T)(foc.head())).print();
	}

	/**
	 * adds all the components of this set in its HList to the Components
	 * @param cs Components to add to
	 * @param i index to add it to in Components
	 */
	@SuppressWarnings("unchecked")
	public <T extends Component> void addSetToComponents(Components cs,
							     int i)
	{
		HTypeVisitor h = new HTypeVisitor();

		HCons<?, ?> foc = entitySet;

		while (foc.tail().accept(h) != HNil.class) {

			final T tmp = (T)foc.head().getClass().cast(foc.head());
			cs.addComponentAt(tmp.getClass(), tmp, i);

			foc = (HCons<?, ?>)foc.tail();
		}

		// final T tmp = (T)foc.head().getClass().cast(foc.head());
		final EntitySetType ttmp =
			(EntitySetType)foc.head().getClass().cast(foc.head());
		cs.addComponentAt(ttmp.entityRunTimeTypeRep, ttmp, i);
	}
}
