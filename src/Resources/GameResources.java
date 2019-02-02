package Resources;
import java.awt.image.*;

import poj.Render.ImageLoader;
import poj.Animation;
import poj.Render.ImageWindow;

public class GameResources
{
	public static BufferedImage testImage =
		ImageLoader.load("resources/playerspritesheet.png");

	public static BufferedImage testTile =
		ImageLoader.load("resources/iso-64x64-building.png");

	public static String mapConfig = "resources/map1Config.json",
			     tileSet = "resources/tiles1.json",
			     mapLayer0 = "resources/map1_ground.csv",
			     mapLayer1 = "resources/map1_not_ground.csv",
			     mapLayer2 = "resources/map1_roof.csv";

	public static Animation testImageAnimation = new Animation(
		new ImageWindow(0, 0, 60, 30), 30, 60, 0, 0, 0, 120, 0);

	// global
	public static int animationDurationms = 30;

	// player resources
	public static int playerHeight = 64;
	public static int playerWidth = 48;
	public static BufferedImage playerSpriteSheet =
		ImageLoader.load("resources/playerspritesheet.png");

	public static Animation playerNMoveAnimation =
		new Animation(playerWidth, playerHeight, animationDurationms, 0,
			      playerHeight, 0, 0, 0, playerHeight * 4);
	public static Animation playerEMoveAnimation = new Animation(
		playerWidth, playerHeight, animationDurationms, 0, playerHeight,
		playerWidth, 0, playerWidth, playerHeight * 4);
	public static Animation playerSMoveAnimation = new Animation(
		playerWidth, playerHeight, animationDurationms, 0, playerHeight,
		playerWidth * 2, 0, playerWidth * 2, playerHeight * 4);
	public static Animation playerWMoveAnimation = new Animation(
		playerWidth, playerHeight, animationDurationms, 0, playerHeight,
		playerWidth * 3, 0, playerWidth * 3, playerHeight * 4);

	public static Animation playerNIdleAnimation = new Animation(
		playerWidth, playerHeight, animationDurationms, 0, playerHeight,
		0, playerHeight * 4, 0, playerHeight * 7);
	public static Animation playerEIdleAnimation =
		new Animation(playerWidth, playerHeight, animationDurationms, 0,
			      playerHeight, playerWidth * 1, playerHeight * 4,
			      playerWidth * 1, playerHeight * 7);
	public static Animation playerSIdleAnimation =
		new Animation(playerWidth, playerHeight, animationDurationms, 0,
			      playerHeight, playerWidth * 2, playerHeight * 4,
			      playerWidth * 2, playerHeight * 7);
	public static Animation playerWIdleAnimation =
		new Animation(playerWidth, playerHeight, animationDurationms, 0,
			      playerHeight, playerWidth * 3, playerHeight * 4,
			      playerWidth * 3, playerHeight * 7);
}
