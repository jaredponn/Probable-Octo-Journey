package Game.GameEvents;
/**
 * Damaged focused entity event .
 * @author Jared Pon
 * @version 03/09/19 v1.0
 */

import poj.EngineState;
import Components.*;
import java.util.Optional;
import Game.*;

public class DamageFocusedEntityEvent extends FocusedPlayGameEvent
{

	private int damage;

	/**
	 * constructor
	 */
	public DamageFocusedEntityEvent()
	{
		this(null, 0);
	}

	/**
	 * constructor
	 * @param g: playgame
	 * @param d: damage
	 */
	public DamageFocusedEntityEvent(PlayGame g, int d)
	{
		super(g);
		damage = d;
	}

	/**
	 * set damage
	 * @param d: damage
	 */
	public void setDamage(int d)
	{
		damage = d;
	}

	/**
	 * game event
	 */
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
