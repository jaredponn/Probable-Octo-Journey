package Resources;

import java.awt.event.KeyEvent;
import poj.linear.*;
import Components.*;
import poj.Collisions.*;


// IMPORTANT: Everything in this document should be in screen coordinates. That
// is, the height and width of things are expressed as ratios of the tile size.
public class GameConfig
{

	// player config
	// They need to be switched because of how the tiles are viewed. Not
	// going to lie, this is really strange and if someone wants to take the
	// time to figure this out, please let me know
	public static final float PLAYER_SPEED = 0.003f;
	public static final float PLAYER_WIDTH =
		GameResources.PLAYER_SPRITE_WIDTH
		/ GameResources.TILE_SCREEN_WIDTH;
	public static final int PLAYER_DIFFUSION_VALUE = (int)Math.pow(2, 12);
	public static final float PLAYER_HEIGHT =
		GameResources.PLAYER_SPRITE_HEIGHT
		/ GameResources.TILE_SCREEN_HEIGHT;
	public static final Vector2f PLAYER_SPAWNNING_POS =
		new Vector2f(0f, 0f);
	public static final CircleCollisionBody PLAYER_COLLISION_BODY =
		new CircleCollisionBody(
			new CollisionCircle(0, 0, PLAYER_WIDTH));


	// bullet config
	public static final float BULLET_SPEED = 0.02f;
	public static final float BULLET_WIDTH =
		GameResources.BULLET_SPRITE_WIDTH
		/ GameResources.TILE_SCREEN_WIDTH;
	public static final float BULLET_HEIGHT =
		GameResources.BULLET_SPRITE_HEIGHT
		/ GameResources.TILE_SCREEN_HEIGHT;

	// mob config
	public static final float MOB_SPEED = 0.6f * PLAYER_SPEED;
	// public static final float MOB_SPEED = 0f;
	public static final float MOB_HEIGHT = PLAYER_HEIGHT;
	public static final float MOB_WIDTH = PLAYER_WIDTH;
	public static final float MOB_HP = 100;
	public static final CircleCollisionBody MOB_COLLISION_BODY =
		new CircleCollisionBody(new CollisionCircle(0, 0, MOB_WIDTH));

	// construct config
	public static final float CONSTRUCT_HEIGHT = 64;
	public static final float CONSTRUCT_WIDTH = 48;
	public static final float CONSTRUCT_HP = 100;

	// input config
	public static final int SWITCH_WEAPONS = KeyEvent.VK_X;
	public static final int ATTACK_KEY = KeyEvent.VK_SPACE;
	public static final int BUILD_TOWER = KeyEvent.VK_Q;
	public static final int BUILD_TRAP = KeyEvent.VK_E;
}
