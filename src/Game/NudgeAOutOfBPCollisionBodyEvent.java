package Game;

import Components.*;
import java.util.Optional;

import Components.*;
import EntitySets.*;
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
	extends FocusedPlayGameEvent
{
	private Class<T> collisionBodyType;

	public NudgeAOutOfBPCollisionBodyEvent(Class<T> collisionBodyType)
	{
		super();
		this.focus1 = -1;
		this.collisionBodyType = collisionBodyType;
	}
	public NudgeAOutOfBPCollisionBodyEvent(PlayGame g,
					       Class<T> collisionBodyType)
	{
		super(g);
		this.focus1 = -1;
		this.collisionBodyType = collisionBodyType;
	}

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
