package EntitySets;
/**
 * PlayerSet. The Player entity set
 * Date: February 10, 2019
 * @author Jared Pon, Haiyang He, Alex Stark, Romiro Piquer
 * @version 1.0
 */
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
			new HasAnimation(GameResources.playerNGunIdleAnimation));
		addComponent(new Movement(GameConfig.PLAYER_SPEED));
		addComponent(new MovementDirection(CardinalDirections.N));
		addComponent(new FacingDirection(CardinalDirections.N));
		// collisions
		addComponent(new PhysicsPCollisionBody(
			GameConfig.PLAYER_COLLISION_BODY));
		addComponent(new PHitBox(GameConfig.PLAYER_HITBOX_BODY));

		addComponent(new HitPoints(GameConfig.PLAYER_HP));
		addComponent(new AttackCycle(GameConfig.PLAYER_ATTACK_CYCLE));
		addComponent(new Ammo(GameConfig.PLAYER_STARTING_AMMO));
	}
}
