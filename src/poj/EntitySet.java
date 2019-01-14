package poj;

import poj.HList.*;
import poj.Component;

public class EntitySet<T extends HList<T>>
{

	// TODO add some perhaps add some timesafety for the component
	private HList<?> entitySet;

	public EntitySet()
	{
		HList<?> a = HList.hnil();
		// entitySet =
	}

	public <U extends Component> void addComponentToSet(U c)
	{
		entitySet = hcons(c, entitySet);
	}
}
