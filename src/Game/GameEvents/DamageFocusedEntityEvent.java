package Game.GameEvents;

import poj.EngineState;
import Components.*;
import java.util.Optional;
import Game.*;

public class DamageFocusedEntityEvent extends FocusedPlayGameEvent
{

	private int damage;

	public DamageFocusedEntityEvent()
	{
		this(null, 0);
	}

	public DamageFocusedEntityEvent(PlayGame g, int d)
	{
		super(g);
		damage = d;
	}

	public void setDamage(int d)
	{
		damage = d;
	}

	public void f()
	{
		EngineState engineState = super.getPlayGame().getEngineState();

		Optional<HitPoints> hpOpt =
			engineState.getComponentAt(HitPoints.class, focus1);

		if (!hpOpt.isPresent())
			return;
		hpOpt.get().hurt(damage);
	}
}
