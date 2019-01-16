package poj;

import poj.EntitySet.*;
import poj.ComponentsArray;
import java.util.Stack;

public class Engine
{
	private ComponentsArray componentList;
	private Stack<Integer> freeIndices;

	public Engine()
	{
		int MAX_ENTITIES = 100000;
		componentList = new ComponentsArray(MAX_ENTITIES);
		freeIndices = new Stack();

		for (int i = 0; i < MAX_ENTITIES; ++i) {
			freeIndices.push(i);
		}
	}

	public <T extends Component> void registerComponent(T c)
	{
		componentList.registerComponent(c.getClass());
	}

	public void registerComponent(Class<? extends Component> c)
	{
		componentList.registerComponent(c);
	}
}
