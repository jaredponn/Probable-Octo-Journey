package EntitySets;
import poj.EntitySet.*;
import Resources.GameResources;
import Resources.GameConfig;
import poj.Render.ImageRenderObject;
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
			// GameConfig.PLAYER_SPAWNNING_POS,
			new Vector2f(1f, 7f), GameConfig.PLAYER_WIDTH,
			GameConfig.PLAYER_HEIGHT));

		addComponent(
			new HasAnimation(GameResources.playerNIdleAnimation));
		addComponent(new Movement(GameConfig.PLAYER_SPEED));
		addComponent(new MovementDirection(CardinalDirections.N));
		addComponent(new FacingDirection(CardinalDirections.N));
	}
}
