package poj;

import poj.HList.*;
import poj.Component;

public class EntitySet
{

	// TODO add some perhaps add some timesafety for the component
	private HCons<? extends Component, ?> entitySet;

	public <U extends Component> EntitySet(U c)
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
}
