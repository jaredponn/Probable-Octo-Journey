package poj;

import poj.PackedVector;
import poj.Logger;
import poj.Component;

import java.util.HashMap;

public class ComponentList
{
	private HashMap<Class<? extends Component>,
			PackedVector<? extends Component>> m_component_list;

	private static int MAX_ENTITIES = 500;

	public ComponentList()
	{
		m_component_list = new HashMap<>();
	}

	public ComponentList(int n)
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

	public <T extends Component> PackedVector<T> getComponent(Class<T> c)
	{

		Logger.lassert(
			!m_component_list.containsKey(c),
			"MAJOR ERROR in ComponentList - Accessing a component that does not exist");

		// TODO surpress this warning somehow
		PackedVector<T> tmp = (PackedVector<T>)m_component_list.get(c);
		return tmp;
	}
}
