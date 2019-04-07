/**
 * Engine State -- holds the engine state and a stack to keep track of the free
 * entities.
 * Date: February 20, 2019
 * @author  Jared, and parts of the code were from:
 * https://jaredponn.github.io/posts/2018-11-14-A-Better-Entity-Component-System-Part-1.html
 * @version  1.00
 */

package poj;

import poj.EntitySet.*;
import poj.Logger.Logger;
import poj.Component.*;
import java.util.Stack;

public class EngineState extends Components
{
	private Stack<Integer> freeIndices;

	private static final int DEFAULT_MAX_ENTITIES = 10000;

	/**
	 * Constructor. Constructs an engine with the default max entites
	 */
	public EngineState()
	{
		this(DEFAULT_MAX_ENTITIES);
	}

	/**
	 * Constructs the engine with a specified amount of max entities
	 *
	 * @param  max_entities  max entity number
	 */
	public EngineState(int max_entities)
	{
		super(max_entities);
		freeIndices = new Stack<Integer>();

		for (int i = max_entities - 1; i >= 0; --i) {
			freeIndices.push(i);
		}
	}

	/**
	 * clears the engine state completely
	 *
	 */
	public void clearEngineState()
	{
		m_component_list.clear();
	}


	/**
	 * registers the component -- alias for registerComponent of Components.
	 *
	 * @param  c specified type
	 */
	public <T extends Component> void registerComponent(Class<T> c)
	{
		super.registerComponent(c);
	}


	/**
	 * registers the component -- alias for registerSet of Components.
	 *
	 * @param  c specified type
	 */
	public <T extends EntitySet> void registerSet(Class<T> s)
	{
		super.registerComponent(s);
	}

	/* entity creation / deletion*/
	/**
	 * gets the next free index for an entity
	 * @return the next free index for an entity
	 */
	public int getFreeIndex()
	{
		return freeIndices.pop();
	}

	/**
	 * marks the index to be free so it is reused later
	 * @param e index to mark as free
	 */
	public void markIndexAsFree(int e)
	{
		freeIndices.push(e);
	}


	/**
	 * Deprecated function for backward compatibility.  Gets the components.
	 * @return Components -- the components
	 */
	public Components getComponents()
	{
		return this;
	}


	/**
	 * Alias for get components
	 * @return Components -- the components
	 */
	public Components cpts()
	{
		return getComponents();
	}


	/**
	 * spawns an entity set and all of its required components.
	 * @param set new entity set.
	 * @return int -- index of the entity that was spawned for more
	 */
	public <T extends EntitySet> int spawnEntitySet(T set)
	{
		Logger.lassert(set == null,
			       "ERROR passing null arguemnt to spawnEntitySet");
		final int tmp = getFreeIndex();

		set.getComponents().addSetToComponents(this, tmp);
		return tmp;
	}
}
