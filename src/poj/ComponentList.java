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
		if (n < 0) {
			Logger.logMessage(
				"MAJOR ERROR in ComponentList - please input a positive integer in the constructor ;",
				LogLevels.MAJOR_CRITICAL);
		}
		m_component_list = new HashMap<>();
		MAX_ENTITIES = n;
	}

	public <T extends Component> void registerComponent(Class<T> c)
	{
		if (m_component_list.containsKey(c)) {
			Logger.logMessage(
				"MAJOR ERROR in ComponentList - do not create 2 components of the same type.",
				LogLevels.MAJOR_CRITICAL);
		}

		m_component_list.put(c, new PackedVector<T>(MAX_ENTITIES));
	}

	public <T extends Component> PackedVector<T> getComponent(Class<T> c)
	{
		if (!m_component_list.containsKey(c)) {
			Logger.logMessage(
				"MAJOR ERROR in ComponentList - Accessing a component that does not exist",
				LogLevels.MAJOR_CRITICAL);
		}
		// TODO surpress this warning somehow
		PackedVector<T> tmp = (PackedVector<T>)m_component_list.get(c);
		return tmp;
	}
}
