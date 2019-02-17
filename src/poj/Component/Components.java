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
	public <T extends Component> void addComponentAt(Class<?> ct, T c,
							 int i)
	{
		PackedVector<T> tmp = getComponentPackedVector(ct);
		tmp.add_element_at_sparse_vector(i, c);
	}


	public <T extends Component> void deleteComponentAt(Class<T> c, int i)
	{
		getComponentPackedVector(c).delete_element_at_sparse_vector(i);
	}


	/* component getters / setters for the sparse vector*/
	public <T extends Component> T getComponentAt(Class<T> c, int i)
	{
		return (T)getComponentPackedVector(c)
			.get_data_from_sparse_vector(i);
	}

	// sets the component at an idnex to the value. If the component has not
	// been added yet for that enttiy, it will add it for you
	public <T extends Component> void setComponentAt(Class<?> c, int i,
							 T val)
	{
		if (getComponentPackedVector(c).get_sparse_vector().get(i)
		    == PackedVector.INVALID_INDEX) {
			this.addComponentAt(c, val, i);
			return;
		} else {
			getComponentPackedVector(c).set_data_at_sparse_vector(
				i, val);
		}
	}


	// getting the packed data
	public <T extends Component> ArrayList<T>
	getRawComponentArrayListPackedData(Class<T> c)
	{
		return (ArrayList<T>)super.getComponentPackedVector(c)
			.get_packed_data();
	}

	public <T extends Component> boolean hasComponent(Class<T> c, int i)
	{
		return getComponentPackedVector(c).get_sparse_vector().get(i)
			!= PackedVector.INVALID_INDEX;
	}


	/* set iteration */
	public static int INVALID_ENTITY_INDEX = -1;

	// [0,1,2,3] --> gets 3
	public final <T extends Component> int
	getInitialSetIndex(Class<T> setType)
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
	final public <T extends Component> int getNextSetIndex(Class<T> setType,
							       int focus)
	{
		ArrayList<Integer> stmp =
			getComponentPackedVector(setType).get_sparse_vector();
		ArrayList<Integer> ptmp =
			getComponentPackedVector(setType).get_packed_indicies();

		final int nextpkdfocus = stmp.get(focus) + 1;

		if (ptmp.size() <= nextpkdfocus)
			return INVALID_ENTITY_INDEX;
		else
			return ptmp.get(nextpkdfocus);
	}

	//  gets the initial entity for a component
	public final <T extends Component> int
	getInitialComponentIndex(Class<T> setType)
	{
		return this.getInitialSetIndex(setType);
	}


	//  gets the next entity for a component
	final public <T extends Component> int
	getNextComponentIndex(Class<T> setType, int focus)
	{
		return this.getNextSetIndex(setType, focus);
	}

	static final public boolean isValidEntity(int focus)
	{
		return focus != INVALID_ENTITY_INDEX;
	}
}
