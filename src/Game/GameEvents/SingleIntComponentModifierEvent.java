package Game.GameEvents;

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

	public SingleIntComponentModifierEvent()
	{
		this(0, null);
	}

	public SingleIntComponentModifierEvent(int a, Class<T> t)
	{
		super();
		amount = a;
		type = t;
	}

	public void setAmount(int n)
	{
		amount = n;
	}

	public void setType(Class<T> n)
	{
		type = n;
	}

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
