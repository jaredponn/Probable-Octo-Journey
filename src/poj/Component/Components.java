package poj.Component;

import poj.EntitySet.EntitySet;
import poj.Component.Component;
import poj.PackedVector;

import java.util.ArrayList;

public class Components extends ComponentsArray
{

	public Components(int n)
	{
		super(n);
	}

	/*component deletion and addition */
	public <T extends Component> void addComponentAt(Class<T> ct, T c,
							 int i)
	{
		PackedVector<T> tmp = getComponentPackedVector(ct);
		tmp.add_element_at_sparse_vector(i, c);
	}

	public <T extends Component> void deleteComponentAt(Class<T> c, int i)
	{
		getComponentPackedVector(c).delete_element_at_sparse_vector(i);
	}


	/* component getters / setters */
	public <T extends Component> T getComponentAt(Class<T> c, int i)
	{
		return getComponentPackedVector(c).get_data_from_sparse_vector(
			i);
	}

	public <T extends Component> void setComponentAt(Class<T> c, int i,
							 T val)
	{
		getComponentPackedVector(c).set_data_at_sparse_vector(i, val);
	}


	public <T extends Component> ArrayList<T>
	getRawComponentArrayListData(Class<T> c)
	{
		return super.getComponentPackedVector(c).get_packed_data();
	}


	/* set iteration */
	public static int INVALID_ENTITY_INDEX = -1;

	// [0,1,2,3] --> gets 3
	public final <T extends Component> int
	getInitialSetElementIndex(Class<T> setType)
	{
		ArrayList<Integer> tmp =
			getComponentPackedVector(setType).get_packed_indicies();
		if (tmp.size() == 0)
			return INVALID_ENTITY_INDEX;
		else
			return getComponentPackedVector(setType)
				.get_packed_indicies()
				.get(0);
	}

	// gets the next entity of a set
	final public <T extends EntitySet> int
	getNextSetElement(Class<T> setType, int focus)
	{
		ArrayList<Integer> stmp =
			getComponentPackedVector(setType).get_sparse_vector();
		ArrayList<Integer> ptmp =
			getComponentPackedVector(setType).get_packed_indicies();

		final int nextpkdfocus = stmp.get(focus) + 1;

		if (ptmp.size() >= nextpkdfocus)
			return INVALID_ENTITY_INDEX;
		else
			return getComponentPackedVector(setType)
				.get_packed_indicies()
				.get(nextpkdfocus);
	}

	final public boolean isValidEntity(int focus)
	{
		return focus != INVALID_ENTITY_INDEX;
	}
}
