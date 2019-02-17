package Resources;
import java.awt.image.*;

import poj.Render.ImageLoader;
import poj.Animation;
import poj.Render.ImageWindow;

public class GameResources
{
	/*
	public static BufferedImage testImage =
		ImageLoader.load("resources/playerspritesheet.png");
	*/

	public static BufferedImage testImage =
		ImageLoader.load("resources/playerspritesheet.png");

	/*
	public static BufferedImage testTile =
		ImageLoader.load("resources/iso-64x64-building.png");
	*/
	public static BufferedImage testTile =
		ImageLoader.load("resources/newmap/tiled_cave_1.png");

	/*
	public static String mapConfig = "resources/map1Config.json",
			     tileSet = "resources/tiles1.json",
			     mapLayer0 = "resources/map1_ground.csv",
			     mapLayer1 = "resources/map1_not_ground.csv",
			     mapLayer2 = "resources/map1_roof.csv";
	*/
	public static String
		// mapConfig = "resources/newmap/testDifSetCombine1.json",
		mapConfig = "resources/newmap2/map.json",
		tileSet = "resources/newmap2/caveTileSet128.json",
		// mapLayer0 = "resources/newmap/testDifSetCombine1.csv",
		mapLayer0 = "resources/newmap2/map.csv";

	// Tile config TODO == haiyang please update this directly with whatever
	// value is in the files. THis is currently hard coded in
	public static final float TILE_SCREEN_WIDTH = 64f / 1.4f;
	public static final float TILE_SCREEN_HEIGHT = 32f / 1.4f;

	public static final float TILE_SCREEN_ROTATION = ((float)Math.PI / 4.f);

	// mapLayer0 =
	//"resources/newmap/testDifSetCombine1_not_ground.csv",
	// mapLayer1 = "resources/newmap/testDifSetCombine1_ground.csv";
	// mapLayer2 = "resources/map1_roof.csv";

	public static Animation testImageAnimation = new Animation(
		new ImageWindow(0, 0, 60, 30), 30, 60, 0, 0, 0, 120, 0);

	// global
	public static int animationDurationms = 30;

	// bullet resources
	public static final int BULLET_SPRITE_WIDTH = 5;
	public static final int BULLET_SPRITE_HEIGHT = 5;
	public static BufferedImage bulletImage =
		ImageLoader.load("resources/5x5bullet.png");

	// player resources
	public static BufferedImage playerSpriteSheet =
		ImageLoader.load("resources/playerspritesheet.png");

	public static final int PLAYER_SPRITE_WIDTH = 48;
	public static final int PLAYER_SPRITE_HEIGHT = 64;

	public static Animation playerNMoveAnimation = new Animation(
		PLAYER_SPRITE_WIDTH, PLAYER_SPRITE_HEIGHT, animationDurationms,
		0, PLAYER_SPRITE_HEIGHT, 0, 0, 0, PLAYER_SPRITE_HEIGHT * 4);
	public static Animation playerEMoveAnimation = new Animation(
		PLAYER_SPRITE_WIDTH, PLAYER_SPRITE_HEIGHT, animationDurationms,
		0, PLAYER_SPRITE_HEIGHT, PLAYER_SPRITE_WIDTH, 0,
		PLAYER_SPRITE_WIDTH, PLAYER_SPRITE_HEIGHT * 4);
	public static Animation playerSMoveAnimation = new Animation(
		PLAYER_SPRITE_WIDTH, PLAYER_SPRITE_HEIGHT, animationDurationms,
		0, PLAYER_SPRITE_HEIGHT, PLAYER_SPRITE_WIDTH * 2, 0,
		PLAYER_SPRITE_WIDTH * 2, PLAYER_SPRITE_HEIGHT * 4);
	public static Animation playerWMoveAnimation = new Animation(
		PLAYER_SPRITE_WIDTH, PLAYER_SPRITE_HEIGHT, animationDurationms,
		0, PLAYER_SPRITE_HEIGHT, PLAYER_SPRITE_WIDTH * 3, 0,
		PLAYER_SPRITE_WIDTH * 3, PLAYER_SPRITE_HEIGHT * 4);

	public static Animation playerNIdleAnimation = new Animation(
		PLAYER_SPRITE_WIDTH, PLAYER_SPRITE_HEIGHT, animationDurationms,
		0, PLAYER_SPRITE_HEIGHT, 0, PLAYER_SPRITE_HEIGHT * 4, 0,
		PLAYER_SPRITE_HEIGHT * 7);
	public static Animation playerEIdleAnimation = new Animation(
		PLAYER_SPRITE_WIDTH, PLAYER_SPRITE_HEIGHT, animationDurationms,
		0, PLAYER_SPRITE_HEIGHT, PLAYER_SPRITE_WIDTH * 1,
		PLAYER_SPRITE_HEIGHT * 4, PLAYER_SPRITE_WIDTH * 1,
		PLAYER_SPRITE_HEIGHT * 7);
	public static Animation playerSIdleAnimation = new Animation(
		PLAYER_SPRITE_WIDTH, PLAYER_SPRITE_HEIGHT, animationDurationms,
		0, PLAYER_SPRITE_HEIGHT, PLAYER_SPRITE_WIDTH * 2,
		PLAYER_SPRITE_HEIGHT * 4, PLAYER_SPRITE_WIDTH * 2,
		PLAYER_SPRITE_HEIGHT * 7);
	public static Animation playerWIdleAnimation = new Animation(
		PLAYER_SPRITE_WIDTH, PLAYER_SPRITE_HEIGHT, animationDurationms,
		0, PLAYER_SPRITE_HEIGHT, PLAYER_SPRITE_WIDTH * 3,
		PLAYER_SPRITE_HEIGHT * 4, PLAYER_SPRITE_WIDTH * 3,
		PLAYER_SPRITE_HEIGHT * 7);
}
