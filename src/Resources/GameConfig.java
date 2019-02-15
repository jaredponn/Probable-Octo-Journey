package Resources;

import java.awt.event.KeyEvent;

public class GameConfig
{

	// player config
	public static final float PLAYER_VELOCITY = 100.f;
	public static final float PLAYER_HEIGHT = 64f;
	public static final float PLAYER_WIDTH = 48f;

	// mob config
	public static final float MOB_VELOCITY = 0.9f * PLAYER_VELOCITY;
	public static final float MOB_HEIGHT = 64;
	public static final float MOB_WIDTH = 48;
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
