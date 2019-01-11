package poj;

import poj.PackedVector;
import java.util.HashMap;
import java.util.Stack;

public class ECS
{
	// State
	private HashMap<Class<?>, PackedVector<Class<?>>> m_component_list;
	private Stack<Integer> m_free_indices;

	// Functions
	public void registerComponent()
	{
	}
}
