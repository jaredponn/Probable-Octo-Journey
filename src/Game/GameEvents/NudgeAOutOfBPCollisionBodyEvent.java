package Game.GameEvents;
/**
 * NudgeAOutOfBPCollisionBodyEvent.
 * @author Jared Pon
 * @version 03/09/19 v1.0
 */

import Components.*;
import java.util.Optional;

import Components.*;
import EntitySets.*;
import Game.PlayGame;
import Resources.GameConfig;
import Resources.GameResources;
import TileMap.Map;
import TileMap.MapLayer;

import poj.Render.*;
import poj.GameWindow.*;
import poj.Component.*;
import poj.Collisions.*;
import poj.Logger.Logger;
import poj.Time.*;
import poj.linear.Vector2f;
import poj.EngineState;

public class NudgeAOutOfBPCollisionBodyEvent<T extends PCollisionBody>
	extends BiFocusedPlayGameEvent
{
	private Class<T> collisionBodyType;

	/**
	 * constructor
	 * @param collisionBodyType : type
	 */
	public NudgeAOutOfBPCollisionBodyEvent(Class<T> collisionBodyType)
	{
		super();
		this.focus1 = -1;
		this.collisionBodyType = collisionBodyType;
	}

	/**
	 * constructor
	 * @param g :playgame
	 * @param collisionBodyType : type
	 */
	public NudgeAOutOfBPCollisionBodyEvent(PlayGame g,
					       Class<T> collisionBodyType)
	{
		super(g);
		this.focus1 = -1;
		this.collisionBodyType = collisionBodyType;
	}

	/**
	 * event
	 */
	public void f()
	{

		if (super.focus1 == -1)
			return;

		EngineState engineState = super.getPlayGame().getEngineState();

		Optional<? extends Component> pbodyOpt =
			engineState.getComponentAt(collisionBodyType, focus1);

		if (!pbodyOpt.isPresent())
			return;

		Optional<WorldAttributes> waOpt = engineState.getComponentAt(
			WorldAttributes.class, focus1);

		if (!waOpt.isPresent())
			return;

		PCollisionBody pbody = (PCollisionBody)pbodyOpt.get();

		WorldAttributes wa = waOpt.get();

		wa.add(pbody.calculateThisPenetrationVector());
	}
}
