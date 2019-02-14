package poj;

import poj.EntitySet.*;
import poj.Logger.Logger;
import poj.Component.*;
import java.util.Stack;

public class EngineState extends Components
{
	private Stack<Integer> freeIndices;

	private static final int DEFAULT_MAX_ENTITIES = 1000000;

	/* engine init */
	public EngineState()
	{
		super(DEFAULT_MAX_ENTITIES);
		freeIndices = new Stack<Integer>();

		for (int i = 0; i < DEFAULT_MAX_ENTITIES; ++i) {
			freeIndices.push(i);
		}
	}

	public EngineState(int max_entities)
	{
		super(max_entities);
		freeIndices = new Stack<Integer>();

		for (int i = 0; i < max_entities; ++i) {
			freeIndices.push(i);
		}
	}

	public <T extends Component> void registerComponent(Class<T> c)
	{
		super.registerComponent(c);
	}

	public <T extends EntitySet> void registerSet(Class<T> s)
	{
		super.registerComponent(s);
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
		return this;
	}

	public Components cpts()
	{
		return getComponents();
	}

	public <T extends EntitySet> int spawnEntitySet(T set)
	{
		Logger.lassert(set == null,
			       "ERROR passing null arguemnt to spawnEntitySet");
		final int tmp = getFreeIndex();

		set.getComponents().addSetToComponents(this, tmp);
		return tmp;
	}
}
