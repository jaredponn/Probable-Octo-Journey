package poj;

import poj.EntitySet.*;
import poj.Component.*;
import java.util.Stack;

public class Engine
{
	private Components components;
	private Stack<Integer> freeIndices;

	public Engine()
	{
		int MAX_ENTITIES = 100000;
		components = new Components(MAX_ENTITIES);
		freeIndices = new Stack();

		for (int i = 0; i < MAX_ENTITIES; ++i) {
			freeIndices.push(i);
		}
	}

	public <T extends Component> void registerComponent(Class<T> c)
	{
		components.registerComponent(c);
	}

	public <T extends EntitySet> void registerSet(Class<T> s)
	{
		components.registerComponent(s);
	}

	public void runEngine()
	{
	}
}
