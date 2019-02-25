package poj.Component;

import poj.PackedVector;
import poj.Component.Component;
import poj.Logger.Logger;

import java.util.HashMap;

public class ComponentsArray
{
	private HashMap<Class<? extends Component>,
			PackedVector<? extends Component>> m_component_list;
	private int MAX_ENTITIES;

	/**
	 * Constructs the component array with all packed vectors having a given
	 * capacity
	 *
	 * @param  n  capacity for the packed vectors
	 */
	public ComponentsArray(int n)
	{
		Logger.lassert(
			n < 0,
			"MAJOR ERROR in ComponentList - please input a positive integer in the constructor ;");

		m_component_list = new HashMap<>();
		MAX_ENTITIES = n;
	}


	/**
	 * Registers a component in the ComponentsArray. Makes it available for
	 * use later -- creates the key value pair and allocates the memoty for
	 * the packed vector
	 *
	 * @param  c  type of the component to register
	 */
	public <T extends Component> void registerComponent(Class<T> c)
	{
		Logger.lassert(
			m_component_list.containsKey(c),
			"MAJOR ERROR in ComponentList - do not create 2 components of the same type.");

		m_component_list.put(c, new PackedVector<T>(MAX_ENTITIES));
	}


	/**
	 * gets the packed vector of a given type
	 *
	 * @param  c  type of the component to get
	 * @return  PackedVector<c> of type c -- program crashes if that type
	 *         has not been registered yet
	 */
	@SuppressWarnings("unchecked")
	protected <T extends Component> PackedVector<T>
	getComponentPackedVector(Class<?> c)
	{
		Logger.lassert(
			!m_component_list.containsKey(c),
			"MAJOR ERROR in ComponentList - Accessing a component that does not exist. Trying to get component of type (if it is an enum it just won't work): "
				+ c.toString());

		PackedVector<T> tmp = (PackedVector<T>)m_component_list.get(c);
		return tmp;
	}
}
