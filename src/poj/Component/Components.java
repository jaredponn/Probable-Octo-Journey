package poj.Component;
/**
 * Components -- wrapper around ComponentsArray to provide more high level /
 * easier iteration through components.
 * Date: February 20, 2019
 * @author  Jared
 * @version  1.00
 */

import poj.EntitySet.EntitySet;
import poj.Component.Component;
import poj.PackedVector;

import java.util.ArrayList;
import java.util.Map;

public class Components extends ComponentsArray
{

	/**
	 * Constructs a Components Array with the specified buffer size
	 * @param  n buffer size
	 */
	public Components(int n)
	{
		super(n);
	}

	/*component deletion and addition */
	/**
	 * add a component at the given index in the sparse vector
	 * @param  ct component type
	 * @param  c value to add
	 * @param  i index to add at
	 */
	public <T extends Component> void addComponentAt(Class<?> ct, T c,
							 int i)
	{
		PackedVector<T> tmp = getComponentPackedVector(ct);
		tmp.add_element_at_sparse_vector(i, c);
	}


	/**
	 * deletes a component at the given index in the sparse vector
	 * @param  ct component type
	 * @param  i index to delete at
	 */
	public <T extends Component> void deleteComponentAt(Class<T> c, int i)
	{
		getComponentPackedVector(c).delete_element_at_sparse_vector(i);
	}

	/**
	 * deletes all components at the given index
	 * @param  i index to delete at
	 */
	public <T extends Component> void deleteAllComponentsAt(int i)
	{
		for (PackedVector<?> pkdvec : m_component_list.values()) {
			pkdvec.delete_element_at_sparse_vector(i);
		}
	}

	/**
	 * deletes all components except the ones specified by the va args
	 * @param  i index to delete at
	 * @param  ns components to not delete
	 */
	public <T extends Component> void
	deleteAllComponentsAtExcept(int i, Class<? extends Component>... ns)
	{
		for (Map.Entry<Class<? extends Component>,
			       PackedVector<? extends Component>> pair :
		     m_component_list.entrySet()) {

			boolean tmp = true;
			for (Class<? extends Component> n : ns) {
				if (pair.getKey() == n)
					tmp = false;
			}
			if (tmp) {
				pair.getValue().delete_element_at_sparse_vector(
					i);
			}
		}
	}


	/* component getters / setters for the sparse vector*/
	/**
	 * returns a pointer to the component of a certain type.
	 * @param  ct component type
	 * @param  i index to get at
	 */
	@SuppressWarnings("unchecked")
	public <T extends Component> T getComponentAt(Class<T> c, int i)
	{
		return (T)getComponentPackedVector(c)
			.get_data_from_sparse_vector(i);
	}

	/**
	 * sets the component of a type at an index to the given value and if
	 * the component has not been added yet for that entity, it will add it
	 * for you.
	 * @param  ct component type
	 * @param  i index to set at
	 * @param  val value to add
	 */
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
	/**
	 * Returns a pointer to the packed data
	 * @param  ct component type
	 * @return  pointer to the packed data
	 */
	@SuppressWarnings("unchecked")
	public <T extends Component> ArrayList<T>
	getRawComponentArrayListPackedData(Class<T> c)
	{
		return (ArrayList<T>)super.getComponentPackedVector(c)
			.get_packed_data();
	}


	/**
	 * checks whether the index has the component
	 * @param  ct component type
	 * @param  i index of entity
	 * @return  boolean -- true if that index contains the component and
	 *         false if it does not
	 */
	public <T extends Component> boolean hasComponent(Class<T> c, int i)
	{
		return getComponentPackedVector(c).get_sparse_vector().get(i)
			!= PackedVector.INVALID_INDEX;
	}


	/* set iteration */
	/**
	 * Invalid entity index
	 */
	public static int INVALID_ENTITY_INDEX = -1;

	/**
	 * gets the initial index of a set
	 * @param  ct component type
	 * @return  int -- either the intial set index or an invalid set index
	 */
	public final <T extends Component> int
	getInitialSetIndex(Class<T> setType)
	{
		ArrayList<Integer> tmp =
			getComponentPackedVector(setType).get_packed_indicies();
		if (tmp.size() == 0)
			return INVALID_ENTITY_INDEX;
		else
			return tmp.get(0);
	}

	/**
	 * gets the last index of the entity set if it exists otherrwise returns
	 * INVALID_ENTITY_INDEX.  e.g. [0,1,2,3] --> gets 3
	 * @param  ct component type
	 * @return  int -- either the last set index or an invalid set index
	 */
	public final <T extends Component> int getLastSetIndex(Class<T> setType)
	{
		ArrayList<Integer> tmp =
			getComponentPackedVector(setType).get_packed_indicies();
		if (tmp.size() == 0)
			return INVALID_ENTITY_INDEX;
		else
			return tmp.get(tmp.size() - 1);
	}

	/**
	 * gets the next set index given a set index.
	 *
	 * @param  ct component type
	 * @param  focus the current "focused" entity
	 * @return  int -- either the next index or an invalid set index
	 */
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

	/**
	 *  Alias for getInitialSetIndex
	 */
	public final <T extends Component> int
	getInitialComponentIndex(Class<T> setType)
	{
		return this.getInitialSetIndex(setType);
	}

	/**
	 *  Alias for getNextSetIndex
	 */
	final public <T extends Component> int
	getNextComponentIndex(Class<T> setType, int focus)
	{
		return this.getNextSetIndex(setType, focus);
	}


	/**
	 * checks if the current int is a valid entity
	 *
	 * @param  focus the current "focused" entity
	 * @return  boolean -- true if it is a valid entity, and false if it is
	 *         not
	 */
	static final public boolean isValidEntity(int focus)
	{
		return focus != INVALID_ENTITY_INDEX;
	}
}
