package Game.GameEvents;

/**
 * SingleIntComponentModifierEvent.
 * @author Jared Pon
 * @version 03/09/19 v1.0
 */

import Resources.GameConfig;
import poj.EngineState;
import Components.*;
import EntitySets.*;

import java.util.Optional;

public class SingleIntComponentModifierEvent<T extends SingleIntComponent>
	extends BiFocusedPlayGameEvent
{
	private int amount;
	private Class<T> type;


	/**
	 * constructor
	 */
	public SingleIntComponentModifierEvent()
	{
		this(0, null);
	}


	/**
	 * constructor
	 * @param a : amount
	 * @param t : type
	 */
	public SingleIntComponentModifierEvent(int a, Class<T> t)
	{
		super();
		amount = a;
		type = t;
	}

	/**
	 * constructor
	 * @param n : amount
	 */
	public void setAmount(int n)
	{
		amount = n;
	}

	/**
	 * constructor
	 * @param n : type
	 */
	public void setType(Class<T> n)
	{
		type = n;
	}

	/**
	 * event
	 */
	public void f()
	{
		EngineState engineState = super.getPlayGame().getEngineState();

		Optional<T> mOpt = engineState.getComponentAt(type, focus1);

		if (!mOpt.isPresent())
			return;

		mOpt.get().addFocus1(amount);

		engineState.deleteAllComponentsAt(focus2);
	}
}
