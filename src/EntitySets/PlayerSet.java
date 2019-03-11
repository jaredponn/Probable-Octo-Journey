package EntitySets;
import poj.EntitySet.*;
import Resources.GameResources;
import Resources.GameConfig;
import poj.Render.ImageRenderObject;
import poj.linear.Vector2f;
import Components.*;
import poj.linear.*;
public class PlayerSet extends EntitySet
{
	public PlayerSet()
	{
		super();
		addComponent(new Render(new ImageRenderObject(
			0, 0, GameResources.playerSpriteSheet)));
		addComponent(new WorldAttributes(
			new Vector2f(GameConfig.PLAYER_SPAWNNING_POS),
			GameConfig.PLAYER_WIDTH, GameConfig.PLAYER_HEIGHT));
		addComponent(
			new HasAnimation(GameResources.playerNIdleAnimation));
		addComponent(new Movement(GameConfig.PLAYER_SPEED));
		addComponent(new MovementDirection(CardinalDirections.N));
		addComponent(new FacingDirection(CardinalDirections.N));
		addComponent(new PhysicsPCollisionBody(
			GameConfig.PLAYER_COLLISION_BODY));
		addComponent(new HitPoints(GameConfig.PLAYER_HP));
		addComponent(new AttackCycle(GameConfig.PLAYER_ATTACK_CYCLE));
	}
}
