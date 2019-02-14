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
		mapConfig = "resources/newmap/mainMap.json",
		tileSet = "resources/newmap/caveTileSet128.json",
		// mapLayer0 = "resources/newmap/testDifSetCombine1.csv",
		mapLayer0 = "resources/newmap/mainMap.csv";
	// mapLayer0 =
	//"resources/newmap/testDifSetCombine1_not_ground.csv",
	// mapLayer1 = "resources/newmap/testDifSetCombine1_ground.csv";
	// mapLayer2 = "resources/map1_roof.csv";

	public static Animation testImageAnimation = new Animation(
		new ImageWindow(0, 0, 60, 30), 30, 60, 0, 0, 0, 120, 0);

	// global
	public static int animationDurationms = 30;

	// player resources
	public static BufferedImage playerSpriteSheet =
		ImageLoader.load("resources/playerspritesheet.png");

	public static Animation playerNMoveAnimation = new Animation(
		(int)GameConfig.PLAYER_WIDTH, (int)GameConfig.PLAYER_HEIGHT,
		animationDurationms, 0, (int)GameConfig.PLAYER_HEIGHT, 0, 0, 0,
		(int)GameConfig.PLAYER_HEIGHT * 4);
	public static Animation playerEMoveAnimation = new Animation(
		(int)GameConfig.PLAYER_WIDTH, (int)GameConfig.PLAYER_HEIGHT,
		animationDurationms, 0, (int)GameConfig.PLAYER_HEIGHT,
		(int)GameConfig.PLAYER_WIDTH, 0, (int)GameConfig.PLAYER_WIDTH,
		(int)GameConfig.PLAYER_HEIGHT * 4);
	public static Animation playerSMoveAnimation = new Animation(
		(int)GameConfig.PLAYER_WIDTH, (int)GameConfig.PLAYER_HEIGHT,
		animationDurationms, 0, (int)GameConfig.PLAYER_HEIGHT,
		(int)GameConfig.PLAYER_WIDTH * 2, 0,
		(int)GameConfig.PLAYER_WIDTH * 2,
		(int)GameConfig.PLAYER_HEIGHT * 4);
	public static Animation playerWMoveAnimation = new Animation(
		(int)GameConfig.PLAYER_WIDTH, (int)GameConfig.PLAYER_HEIGHT,
		animationDurationms, 0, (int)GameConfig.PLAYER_HEIGHT,
		(int)GameConfig.PLAYER_WIDTH * 3, 0,
		(int)GameConfig.PLAYER_WIDTH * 3,
		(int)GameConfig.PLAYER_HEIGHT * 4);

	public static Animation playerNIdleAnimation = new Animation(
		(int)GameConfig.PLAYER_WIDTH, (int)GameConfig.PLAYER_HEIGHT,
		animationDurationms, 0, (int)GameConfig.PLAYER_HEIGHT, 0,
		(int)GameConfig.PLAYER_HEIGHT * 4, 0,
		(int)GameConfig.PLAYER_HEIGHT * 7);
	public static Animation playerEIdleAnimation = new Animation(
		(int)GameConfig.PLAYER_WIDTH, (int)GameConfig.PLAYER_HEIGHT,
		animationDurationms, 0, (int)GameConfig.PLAYER_HEIGHT,
		(int)GameConfig.PLAYER_WIDTH * 1,
		(int)GameConfig.PLAYER_HEIGHT * 4,
		(int)GameConfig.PLAYER_WIDTH * 1,
		(int)GameConfig.PLAYER_HEIGHT * 7);
	public static Animation playerSIdleAnimation = new Animation(
		(int)GameConfig.PLAYER_WIDTH, (int)GameConfig.PLAYER_HEIGHT,
		animationDurationms, 0, (int)GameConfig.PLAYER_HEIGHT,
		(int)GameConfig.PLAYER_WIDTH * 2,
		(int)GameConfig.PLAYER_HEIGHT * 4,
		(int)GameConfig.PLAYER_WIDTH * 2,
		(int)GameConfig.PLAYER_HEIGHT * 7);
	public static Animation playerWIdleAnimation = new Animation(
		(int)GameConfig.PLAYER_WIDTH, (int)GameConfig.PLAYER_HEIGHT,
		animationDurationms, 0, (int)GameConfig.PLAYER_HEIGHT,
		(int)GameConfig.PLAYER_WIDTH * 3,
		(int)GameConfig.PLAYER_HEIGHT * 4,
		(int)GameConfig.PLAYER_WIDTH * 3,
		(int)GameConfig.PLAYER_HEIGHT * 7);
}
