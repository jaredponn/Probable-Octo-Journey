package poj.EntitySet;
import poj.HList.*;
import poj.Component.*;

public class EntitySetMemberComponents
{
	private HCons<?, ?> entitySet;

	public <U extends Component> EntitySetMemberComponents(U c)
	{
		entitySet = HList.hcons(c, HList.hnil());
	}

	public <U extends Component> void addComponentToSet(U c)
	{
		entitySet = HList.hcons(c, entitySet);
	}

	public void printSet()
	{
		HTypeVisitor h = new HTypeVisitor();

		HCons<? extends Component, ?> foc = entitySet;

		while (foc.tail().accept(h) != HNil.class) {
			foc.head().print();

			foc = (HCons<? extends Component, ?>)foc.tail();
		}
		foc.head().print();
	}

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

		final T tmp = (T)foc.head().getClass().cast(foc.head());
		final EntitySetType ttmp =
			(EntitySetType)foc.head().getClass().cast(foc.head());
		cs.addComponentAt(ttmp.entityRunTimeTypeRep, ttmp, i);
	}
}
