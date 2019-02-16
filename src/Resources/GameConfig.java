package Resources;

import java.awt.event.KeyEvent;


// IMPORTANT: Everything in this document should be in screen coordinates. That
// is, the height and width of things are expressed as ratios of the tile size.
public class GameConfig
{


	// player config
	public static final float PLAYER_SPEED = 0.01f;
	public static final float PLAYER_WIDTH = 0.75f;
	public static final float PLAYER_HEIGHT = 1f;

	// bullet config
	public static final float BULLET_SPEED = 1f;
	public static final float BULLET_WIDTH = 0.1f;
	public static final float BULLET_HEIGHT = 0.1f;

	// mob config
	public static final float MOB_VELOCITY = 0.9f * PLAYER_SPEED;
	public static final float MOB_HEIGHT = PLAYER_WIDTH;
	public static final float MOB_WIDTH = PLAYER_HEIGHT;
	public static final float MOB_HP = 100;

	// construct config
	public static final float CONSTRUCT_HEIGHT = 64;
	public static final float CONSTRUCT_WIDTH = 48;
	public static final float CONSTRUCT_HP = 100;

	// input config
	public static final int SWITCH_WEAPONS = KeyEvent.VK_X;
	public static final int ATTACK_KEY = KeyEvent.VK_F;
	public static final int BUILD_TOWER = KeyEvent.VK_Q;
	public static final int BUILD_TRAP = KeyEvent.VK_E;
}
