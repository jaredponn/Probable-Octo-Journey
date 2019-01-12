package poj;

import poj.PackedVector;
import poj.Logger;
import poj.Component;

import java.util.HashMap;

public class ComponentList
{
	private HashMap<Class<?>, PackedVector<? extends Component>>
		m_component_list;

	private static int MAX_ENTITIES = 500;

	public ComponentList()
	{
		m_component_list = new HashMap();
	}

	public ComponentList(int n)
	{
		if (n < 0) {
			Logger.logMessage(
				"MAJOR ERROR in ComponentList - please input a positive integer in the constructor ;",
				LOG_LEVEL.MAJOR_CRITICAL);
		}
		m_component_list = new HashMap();
		MAX_ENTITIES = n;
	}

	public <T extends Component> void registerComponent(Class<?> c)
	{
		if (m_component_list.containsKey(c)) {
			Logger.logMessage(
				"MAJOR ERROR in ComponentList - do not create 2 components of the same type.",
				LOG_LEVEL.MAJOR_CRITICAL);
		}

		m_component_list.put(c, new PackedVector<T>(MAX_ENTITIES));
	}

	public PackedVector<?> getComponent(Class<?> c)
	{
		if (!m_component_list.containsKey(c)) {
			Logger.logMessage(
				"MAJOR ERROR in ComponentList - Accessing a component that does not exist",
				LOG_LEVEL.MAJOR_CRITICAL);
		}
		return m_component_list.get(c);
	}
}
