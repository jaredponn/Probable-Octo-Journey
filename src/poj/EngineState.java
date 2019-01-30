package poj;

import poj.EntitySet.*;
import poj.Logger.Logger;
import poj.Component.*;
import java.util.Stack;

public class EngineState
{
	private Stack<Integer> freeIndices;
	private Components components;

	private int MAX_ENTITIES = 1000000;

	/* engine init */
	public EngineState()
	{
		components = new Components(MAX_ENTITIES);
		freeIndices = new Stack<Integer>();

		for (int i = 0; i < MAX_ENTITIES; ++i) {
			freeIndices.push(i);
		}
	}

	public EngineState(int max_entities)
	{
		this.MAX_ENTITIES = max_entities;
		components = new Components(MAX_ENTITIES);
		freeIndices = new Stack<Integer>();

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

	/* entity creation / deletion*/
	// gets the next free index for an entity
	public int getFreeIndex()
	{
		return freeIndices.pop();
	}
	// marks index as free so it is reused later
	public void markIndexAsFree(int e)
	{
		freeIndices.push(e);
	}

	public Components getComponents()
	{
		return this.components;
	}

	public <T extends EntitySet> int spawnEntitySet(T set)
	{
		Logger.lassert(set == null,
			       "ERROR passing null arguemnt to spawnEntitySet");
		final int tmp = getFreeIndex();

		set.getComponents().addSetToComponents(components, tmp);
		return tmp;
	}
}
