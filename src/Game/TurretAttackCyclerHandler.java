package Game;

import java.awt.Color;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import Components.AnimationWindowAssets;
import Components.CardinalDirections;
import Components.HasAnimation;
import Components.Movement;
import Components.PCollisionBody;
import Components.PHitBox;
import Components.PhysicsPCollisionBody;
import Components.SoundEffectAssets;
import Components.WorldAttributes;
import EntitySets.Bullet;
import EntitySets.MobSet;
import EntitySets.PlayerSet;
import Game.GameEvents.FocusedPlayGameEvent;
import Game.GameEvents.PlayGameEvent;
import Resources.GameConfig;

import poj.EngineState;
import poj.GameWindow.InputPoller;
import poj.linear.Vector2f;

public class TurretAttackCyclerHandler implements EntityAttackSetHandler
{


	class TurretAttackStartingHandler extends FocusedPlayGameEvent
	{

		TurretAttackStartingHandler(PlayGame g, int focus)
		{
			super(g, focus);
		}

		public void f()
		{
			EngineState engineState =
				super.getPlayGame().getEngineState();

			CombatFunctions.turretTargeting(engineState, focus1);
		}
	}

	public PlayGameEvent startingHandler(PlayGame g, int focus)
	{
		return new TurretAttackStartingHandler(g, focus);
	}

	public PlayGameEvent primerHandler(PlayGame g, int focus)
	{
		return null;
	}
	public PlayGameEvent attackHandler(PlayGame g, int focus)
	{
		return null;
	}

	public PlayGameEvent recoilHandler(PlayGame g, int focus)
	{
		return null;
	}
	public PlayGameEvent endAttackHandler(PlayGame g, int focus)
	{
		return null;
	}
}
