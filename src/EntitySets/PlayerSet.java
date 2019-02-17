package EntitySets;
import poj.EntitySet.*;
import Resources.GameResources;
import Resources.GameConfig;
import poj.linear.*;
import poj.Render.ImageRenderObject;
import Components.*;
public class PlayerSet extends EntitySet
{
	public PlayerSet()
	{
		super();
		addComponent(new Render(new ImageRenderObject(
			0, 0, GameResources.playerSpriteSheet)));
		addComponent(new WorldAttributes(new Vector2f(2f, 2f),
						 GameConfig.PLAYER_WIDTH,
						 GameConfig.PLAYER_HEIGHT));
		addComponent(
			new HasAnimation(GameResources.playerNIdleAnimation));
		addComponent(new Speed(GameConfig.PLAYER_SPEED));
		addComponent(new MovementDirection(CardinalDirections.N));
		addComponent(new FacingDirection(CardinalDirections.N));
	}
}
