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

	public ComponentsArray(int n)
	{
		Logger.lassert(
			n < 0,
			"MAJOR ERROR in ComponentList - please input a positive integer in the constructor ;");

		m_component_list = new HashMap<>();
		MAX_ENTITIES = n;
	}

	public <T extends Component> void registerComponent(Class<T> c)
	{
		Logger.lassert(
			m_component_list.containsKey(c),
			"MAJOR ERROR in ComponentList - do not create 2 components of the same type.");

		m_component_list.put(c, new PackedVector<T>(MAX_ENTITIES));
	}

	protected <T extends Component> PackedVector<T>
	getComponentPackedVector(Class<?> c)
	{
		Logger.lassert(
			!m_component_list.containsKey(c),
			"MAJOR ERROR in ComponentList - Accessing a component that does not exist. Trying to get component of type (if it is an enum it just won't work): "
				+ c.toString());

		// TODO surpress this warning somehow
		PackedVector<T> tmp = (PackedVector<T>)m_component_list.get(c);
		return tmp;
	}
}
