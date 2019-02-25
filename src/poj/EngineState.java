package poj;

import poj.EntitySet.*;
import poj.Logger.Logger;
import poj.Component.*;
import java.util.Stack;

public class EngineState extends Components
{
	private Stack<Integer> freeIndices;

	private static final int DEFAULT_MAX_ENTITIES = 1000000;

	/**
	 * Constructor. Constructs an engine with the default max entites
	 *
	 * @param  url  an absolute URL giving the base location of the image
	 * @param  name the location of the image, relative to the url argument
	 * @return      the image at the specified URL
	 * @see         Image
	 */
	public EngineState()
	{
		this(DEFAULT_MAX_ENTITIES);
	}

	public EngineState(int max_entities)
	{
		super(max_entities);
		freeIndices = new Stack<Integer>();

		for (int i = max_entities; i >= 0; --i) {
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
