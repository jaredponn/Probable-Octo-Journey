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

	public static BufferedImage turret =
		ImageLoader.load("resources/turret1Crop.png");

	public static BufferedImage octoTitle =
		ImageLoader.load("resources/menu/octo_journey_title.png");
	public static BufferedImage playButton =
		ImageLoader.load("resources/menu/playButton.png");
	public static int playButtonWidth = 180, playButtonHeight = 100;

	public static BufferedImage howToPlayButton =
		ImageLoader.load("resources/menu/howToPlay.png");
	public static int howToPlayButtonWidth = 200, howToPlayButtonHeight =
							      120;

	public static BufferedImage testTile =
		ImageLoader.load("resources/newmap/tiled_cave_1.png");


	public static String
		mapConfig = "resources/newmap2/map.json",
		tileSet = "resources/newmap2/caveTileSet128.json",
		mapLayer0 = "resources/newmap2/map.csv",
		pathFindTest1Layer = "resources/newmap2/pathFindTest1.csv",

		pathFindTest1Config = "resources/newmap2/pathFindTest1.json",
		pathFindTest1LayerGround =
			"resources/newmap2/pathFindTest1_ground.csv",
		pathFindTest1LayerWall =
			"resources/newmap2/pathFindTest1_wall.csv",

		// demo 1  resources
		demo1Config = "resources/demo1map/demo1.json",
		demo1LayerGround = "resources/demo1map/demo1_ground.csv",
		demo1LayerWall = "resources/demo1map/demo1_wall.csv",

		pathFindTest2Config = "resources/newmap2/pathFindTest2.json",
		pathFindTest2Layer = "resources/newmap2/pathFindTest2.csv",
		pathFindTest3Config = "resources/newmap2/pathFindTest3.json",
		pathFindTest3Layer = "resources/newmap2/pathFindTest3.csv",
		pathFindTest4Config = "resources/newmap2/pathFindTest4.json",
		pathFindTest4Layer = "resources/newmap2/pathFindTest4.csv",
		// 1000,1000 map to test the performance of rendering
		renderPerformanceConf =
			"resources/renderperformancemap/massivemap.json",
		renderPerformanceLayer =
			"resources/renderperformancemap/massivemap.csv";

	// clang-format off

	public static BufferedImage officialTileSet =
		ImageLoader.load("resources/RomiroGraphics/officialTileSet/TILEMAPDONETEST.png");

	public static String officialMapGround1="resources/RomiroGraphics/officialTileSet/TileMap_floor.csv",
		   		officialMapMisc2="resources/RomiroGraphics/officialTileSet/TileMap_any misc items.csv",
				officialMapEverythingElse3="resources/RomiroGraphics/officialTileSet/TileMap_everything else.csv",
				officialMapCars4="resources/RomiroGraphics/officialTileSet/TileMap_cars.csv",
				officialMapRocks5="resources/RomiroGraphics/officialTileSet/TileMap_rocks.csv",
				officialMapTrees6="resources/RomiroGraphics/officialTileSet/TileMap_trees.csv",
				officialTileSetConfig="resources/RomiroGraphics/officialTileSet/MapSprite.json",
				officialMapConfig="resources/RomiroGraphics/officialTileSet/TileMap.json";

	// clang-format on
	public static final float MAGIC_CONSTANT =
		(float)Math.sqrt(2)
		/ 2f; // this constant is important and
		      // makes things "just work" -- rotations
		      // by 45 degrees sqaush the size of things
		      // bit -- this scales it back
	public static final float TILE_SCREEN_ROTATION = (float)Math.PI / 4;

	// TILE DEPENDENT!!
	public static final float TILE_SCREEN_WIDTH = 64;
	public static final float TILE_SCREEN_HEIGHT = 42;

	public static Animation testImageAnimation = new Animation(
		new ImageWindow(0, 0, 60, 30), 30, 60, 0, 0, 0, 120, 0);

	// global
	public static double animationDurationms = 90; // 30

	// bullet resources
	public static final int BULLET_SPRITE_WIDTH = 5;
	public static final int BULLET_SPRITE_HEIGHT = 5;
	public static BufferedImage bulletImage =
		ImageLoader.load("resources/5x5bullet.png");

	// money resources
	public static final int CASH_SPRITE_WIDTH = 16;
	public static final int CASH_SPRITE_HEIGHT = 16;
	public static BufferedImage cashImage =
		ImageLoader.load("resources/coin.png");

	// player resources
	public static BufferedImage playerSpriteSheet =
		ImageLoader.load("resources/RomiroGraphics/playerReduce5.png");
	public static BufferedImage enemySpriteSheet =
		ImageLoader.load("resources/RomiroGraphics/zombieReduce5.png");

	/*
	public static BufferedImage playerSpriteSheet =
		ImageLoader.load("resources/RomiroGraphics/playerReduce5.png");
	public static BufferedImage enemySpriteSheet =
		ImageLoader.load("resources/RomiroGraphics/zombieReduce5.png");*/

	/*
	 * animation is in columns
	 * last 3 rows are idle animation (breath and not breath)
	 * each animation is 126 x 150
	 */
	public static final int PLAYER_SPRITE_WIDTH = (int)(125 * 0.2);
	public static final int PLAYER_SPRITE_HEIGHT = (int)(155 * 0.2);

	// movement animation
	public static Animation playerNGunMoveAnimation = new Animation(
		PLAYER_SPRITE_WIDTH, PLAYER_SPRITE_HEIGHT, animationDurationms,
		0, PLAYER_SPRITE_HEIGHT, PLAYER_SPRITE_WIDTH * 20, 0,
		PLAYER_SPRITE_WIDTH * 20, PLAYER_SPRITE_HEIGHT * 4);

	public static Animation playerNMeleeMoveAnimation = new Animation(
		PLAYER_SPRITE_WIDTH, PLAYER_SPRITE_HEIGHT, animationDurationms,
		0, PLAYER_SPRITE_HEIGHT, PLAYER_SPRITE_WIDTH * 12, 0,
		PLAYER_SPRITE_WIDTH * 12, PLAYER_SPRITE_HEIGHT * 4);

	public static Animation playerEGunMoveAnimation = new Animation(
		PLAYER_SPRITE_WIDTH, PLAYER_SPRITE_HEIGHT, animationDurationms,
		0, PLAYER_SPRITE_HEIGHT, PLAYER_SPRITE_WIDTH * 21, 0,
		PLAYER_SPRITE_WIDTH * 21, PLAYER_SPRITE_HEIGHT * 4);

	public static Animation playerEMeleeMoveAnimation = new Animation(
		PLAYER_SPRITE_WIDTH, PLAYER_SPRITE_HEIGHT, animationDurationms,
		0, PLAYER_SPRITE_HEIGHT, PLAYER_SPRITE_WIDTH * 13, 0,
		PLAYER_SPRITE_WIDTH * 13, PLAYER_SPRITE_HEIGHT * 4);

	public static Animation playerSGunMoveAnimation = new Animation(
		PLAYER_SPRITE_WIDTH, PLAYER_SPRITE_HEIGHT, animationDurationms,
		0, PLAYER_SPRITE_HEIGHT, PLAYER_SPRITE_WIDTH * 22, 0,
		PLAYER_SPRITE_WIDTH * 22, PLAYER_SPRITE_HEIGHT * 4);

	public static Animation playerSMeleeMoveAnimation = new Animation(
		PLAYER_SPRITE_WIDTH, PLAYER_SPRITE_HEIGHT, animationDurationms,
		0, PLAYER_SPRITE_HEIGHT, PLAYER_SPRITE_WIDTH * 14, 0,
		PLAYER_SPRITE_WIDTH * 14, PLAYER_SPRITE_HEIGHT * 4);

	public static Animation playerWGunMoveAnimation = new Animation(
		PLAYER_SPRITE_WIDTH, PLAYER_SPRITE_HEIGHT, animationDurationms,
		0, PLAYER_SPRITE_HEIGHT, PLAYER_SPRITE_WIDTH * 23, 0,
		PLAYER_SPRITE_WIDTH * 23, PLAYER_SPRITE_HEIGHT * 4);

	public static Animation playerWMeleeMoveAnimation = new Animation(
		PLAYER_SPRITE_WIDTH, PLAYER_SPRITE_HEIGHT, animationDurationms,
		0, PLAYER_SPRITE_HEIGHT, PLAYER_SPRITE_WIDTH * 15, 0,
		PLAYER_SPRITE_WIDTH * 15, PLAYER_SPRITE_HEIGHT * 4);

	public static Animation playerNEGunMoveAnimation = new Animation(
		PLAYER_SPRITE_WIDTH, PLAYER_SPRITE_HEIGHT, animationDurationms,
		0, PLAYER_SPRITE_HEIGHT, PLAYER_SPRITE_WIDTH * 4, 0,
		PLAYER_SPRITE_WIDTH * 4, PLAYER_SPRITE_HEIGHT * 4);

	public static Animation playerNEMeleeMoveAnimation = new Animation(
		PLAYER_SPRITE_WIDTH, PLAYER_SPRITE_HEIGHT, animationDurationms,
		0, PLAYER_SPRITE_HEIGHT, PLAYER_SPRITE_WIDTH * 8, 0,
		PLAYER_SPRITE_WIDTH * 8, PLAYER_SPRITE_HEIGHT * 4);

	public static Animation playerSEGunMoveAnimation = new Animation(
		PLAYER_SPRITE_WIDTH, PLAYER_SPRITE_HEIGHT, animationDurationms,
		0, PLAYER_SPRITE_HEIGHT, PLAYER_SPRITE_WIDTH * 5, 0,
		PLAYER_SPRITE_WIDTH * 5, PLAYER_SPRITE_HEIGHT * 4);

	public static Animation playerSEMeleeMoveAnimation = new Animation(
		PLAYER_SPRITE_WIDTH, PLAYER_SPRITE_HEIGHT, animationDurationms,
		0, PLAYER_SPRITE_HEIGHT, PLAYER_SPRITE_WIDTH * 9, 0,
		PLAYER_SPRITE_WIDTH * 9, PLAYER_SPRITE_HEIGHT * 4);

	public static Animation playerSWGunMoveAnimation = new Animation(
		PLAYER_SPRITE_WIDTH, PLAYER_SPRITE_HEIGHT, animationDurationms,
		0, PLAYER_SPRITE_HEIGHT, PLAYER_SPRITE_WIDTH * 6, 0,
		PLAYER_SPRITE_WIDTH * 6, PLAYER_SPRITE_HEIGHT * 4);

	public static Animation playerSWMeleeMoveAnimation = new Animation(
		PLAYER_SPRITE_WIDTH, PLAYER_SPRITE_HEIGHT, animationDurationms,
		0, PLAYER_SPRITE_HEIGHT, PLAYER_SPRITE_WIDTH * 10, 0,
		PLAYER_SPRITE_WIDTH * 10, PLAYER_SPRITE_HEIGHT * 4);

	public static Animation playerNWGunMoveAnimation = new Animation(
		PLAYER_SPRITE_WIDTH, PLAYER_SPRITE_HEIGHT, animationDurationms,
		0, PLAYER_SPRITE_HEIGHT, PLAYER_SPRITE_WIDTH * 7, 0,
		PLAYER_SPRITE_WIDTH * 7, PLAYER_SPRITE_HEIGHT * 4);

	public static Animation playerNWMeleeMoveAnimation = new Animation(
		PLAYER_SPRITE_WIDTH, PLAYER_SPRITE_HEIGHT, animationDurationms,
		0, PLAYER_SPRITE_HEIGHT, PLAYER_SPRITE_WIDTH * 11, 0,
		PLAYER_SPRITE_WIDTH * 11, PLAYER_SPRITE_HEIGHT * 4);


	// idle animation
	public static Animation playerNGunIdleAnimation = new Animation(
		PLAYER_SPRITE_WIDTH, PLAYER_SPRITE_HEIGHT, animationDurationms,
		0, PLAYER_SPRITE_HEIGHT, PLAYER_SPRITE_WIDTH * 20,
		PLAYER_SPRITE_HEIGHT * 4, PLAYER_SPRITE_WIDTH * 20,
		PLAYER_SPRITE_HEIGHT * 7);

	public static Animation playerNMeleeIdleAnimation = new Animation(
		PLAYER_SPRITE_WIDTH, PLAYER_SPRITE_HEIGHT, animationDurationms,
		0, PLAYER_SPRITE_HEIGHT, PLAYER_SPRITE_WIDTH * 12,
		PLAYER_SPRITE_HEIGHT * 4, PLAYER_SPRITE_WIDTH * 12,
		PLAYER_SPRITE_HEIGHT * 7);


	public static Animation playerEGunIdleAnimation = new Animation(
		PLAYER_SPRITE_WIDTH, PLAYER_SPRITE_HEIGHT, animationDurationms,
		0, PLAYER_SPRITE_HEIGHT, PLAYER_SPRITE_WIDTH * 21,
		PLAYER_SPRITE_HEIGHT * 4, PLAYER_SPRITE_WIDTH * 21,
		PLAYER_SPRITE_HEIGHT * 7);

	public static Animation playerEMeleeIdleAnimation = new Animation(
		PLAYER_SPRITE_WIDTH, PLAYER_SPRITE_HEIGHT, animationDurationms,
		0, PLAYER_SPRITE_HEIGHT, PLAYER_SPRITE_WIDTH * 13,
		PLAYER_SPRITE_HEIGHT * 4, PLAYER_SPRITE_WIDTH * 13,
		PLAYER_SPRITE_HEIGHT * 7);

	public static Animation playerSGunIdleAnimation = new Animation(
		PLAYER_SPRITE_WIDTH, PLAYER_SPRITE_HEIGHT, animationDurationms,
		0, PLAYER_SPRITE_HEIGHT, PLAYER_SPRITE_WIDTH * 22,
		PLAYER_SPRITE_HEIGHT * 4, PLAYER_SPRITE_WIDTH * 22,
		PLAYER_SPRITE_HEIGHT * 7);

	public static Animation playerSMeleeIdleAnimation = new Animation(
		PLAYER_SPRITE_WIDTH, PLAYER_SPRITE_HEIGHT, animationDurationms,
		0, PLAYER_SPRITE_HEIGHT, PLAYER_SPRITE_WIDTH * 14,
		PLAYER_SPRITE_HEIGHT * 4, PLAYER_SPRITE_WIDTH * 14,
		PLAYER_SPRITE_HEIGHT * 7);

	public static Animation playerWGunIdleAnimation = new Animation(
		PLAYER_SPRITE_WIDTH, PLAYER_SPRITE_HEIGHT, animationDurationms,
		0, PLAYER_SPRITE_HEIGHT, PLAYER_SPRITE_WIDTH * 23,
		PLAYER_SPRITE_HEIGHT * 4, PLAYER_SPRITE_WIDTH * 23,
		PLAYER_SPRITE_HEIGHT * 7);

	public static Animation playerWMeleeIdleAnimation = new Animation(
		PLAYER_SPRITE_WIDTH, PLAYER_SPRITE_HEIGHT, animationDurationms,
		0, PLAYER_SPRITE_HEIGHT, PLAYER_SPRITE_WIDTH * 15,
		PLAYER_SPRITE_HEIGHT * 4, PLAYER_SPRITE_WIDTH * 15,
		PLAYER_SPRITE_HEIGHT * 7);

	public static Animation playerNEGunIdleAnimation = new Animation(
		PLAYER_SPRITE_WIDTH, PLAYER_SPRITE_HEIGHT, animationDurationms,
		0, PLAYER_SPRITE_HEIGHT, PLAYER_SPRITE_WIDTH * 4,
		PLAYER_SPRITE_HEIGHT * 4, PLAYER_SPRITE_WIDTH * 4,
		PLAYER_SPRITE_HEIGHT * 7);

	public static Animation playerNEMeleeIdleAnimation = new Animation(
		PLAYER_SPRITE_WIDTH, PLAYER_SPRITE_HEIGHT, animationDurationms,
		0, PLAYER_SPRITE_HEIGHT, PLAYER_SPRITE_WIDTH * 4,
		PLAYER_SPRITE_HEIGHT * 4, PLAYER_SPRITE_WIDTH * 4,
		PLAYER_SPRITE_HEIGHT * 7);

	public static Animation playerSEGunIdleAnimation = new Animation(
		PLAYER_SPRITE_WIDTH, PLAYER_SPRITE_HEIGHT, animationDurationms,
		0, PLAYER_SPRITE_HEIGHT, PLAYER_SPRITE_WIDTH * 5,
		PLAYER_SPRITE_HEIGHT * 4, PLAYER_SPRITE_WIDTH * 5,
		PLAYER_SPRITE_HEIGHT * 7);

	public static Animation playerSEMeleeIdleAnimation = new Animation(
		PLAYER_SPRITE_WIDTH, PLAYER_SPRITE_HEIGHT, animationDurationms,
		0, PLAYER_SPRITE_HEIGHT, PLAYER_SPRITE_WIDTH * 9,
		PLAYER_SPRITE_HEIGHT * 4, PLAYER_SPRITE_WIDTH * 9,
		PLAYER_SPRITE_HEIGHT * 7);

	public static Animation playerSWGunIdleAnimation = new Animation(
		PLAYER_SPRITE_WIDTH, PLAYER_SPRITE_HEIGHT, animationDurationms,
		0, PLAYER_SPRITE_HEIGHT, PLAYER_SPRITE_WIDTH * 6,
		PLAYER_SPRITE_HEIGHT * 4, PLAYER_SPRITE_WIDTH * 6,
		PLAYER_SPRITE_HEIGHT * 7);

	public static Animation playerSWMeleeIdleAnimation = new Animation(
		PLAYER_SPRITE_WIDTH, PLAYER_SPRITE_HEIGHT, animationDurationms,
		0, PLAYER_SPRITE_HEIGHT, PLAYER_SPRITE_WIDTH * 10,
		PLAYER_SPRITE_HEIGHT * 4, PLAYER_SPRITE_WIDTH * 10,
		PLAYER_SPRITE_HEIGHT * 7);

	public static Animation playerNWGunIdleAnimation = new Animation(
		PLAYER_SPRITE_WIDTH, PLAYER_SPRITE_HEIGHT, animationDurationms,
		0, PLAYER_SPRITE_HEIGHT, PLAYER_SPRITE_WIDTH * 7,
		PLAYER_SPRITE_HEIGHT * 4, PLAYER_SPRITE_WIDTH * 7,
		PLAYER_SPRITE_HEIGHT * 7);

	public static Animation playerNWMeleeIdleAnimation = new Animation(
		PLAYER_SPRITE_WIDTH, PLAYER_SPRITE_HEIGHT, animationDurationms,
		0, PLAYER_SPRITE_HEIGHT, PLAYER_SPRITE_WIDTH * 11,
		PLAYER_SPRITE_HEIGHT * 4, PLAYER_SPRITE_WIDTH * 11,
		PLAYER_SPRITE_HEIGHT * 7);

	/*
	 * size for player melee attack animation: 185 x 182
	 */
	public static final int ENEMY_SPRITE_WIDTH = PLAYER_SPRITE_WIDTH;
	public static final int ENEMY_SPRITE_HEIGHT = PLAYER_SPRITE_HEIGHT;

	// public static final int ENEMY_SPRITE_WIDTH = (int)(125 * 0.8);
	// public static final int ENEMY_SPRITE_HEIGHT = (int)(155 * 0.8);

	// move direction
	public static Animation enemyNMoveAnimation = new Animation(
		ENEMY_SPRITE_WIDTH, ENEMY_SPRITE_HEIGHT, animationDurationms, 0,
		ENEMY_SPRITE_HEIGHT, ENEMY_SPRITE_WIDTH * 0, 0,
		ENEMY_SPRITE_WIDTH * 0, ENEMY_SPRITE_HEIGHT * 4);

	public static Animation enemyEMoveAnimation = new Animation(
		ENEMY_SPRITE_WIDTH, ENEMY_SPRITE_HEIGHT, animationDurationms, 0,
		ENEMY_SPRITE_HEIGHT, ENEMY_SPRITE_WIDTH * 1, 0,
		ENEMY_SPRITE_WIDTH * 1, ENEMY_SPRITE_HEIGHT * 4);


	public static Animation enemySMoveAnimation = new Animation(
		ENEMY_SPRITE_WIDTH, ENEMY_SPRITE_HEIGHT, animationDurationms, 0,
		ENEMY_SPRITE_HEIGHT, ENEMY_SPRITE_WIDTH * 2, 0,
		ENEMY_SPRITE_WIDTH * 2, ENEMY_SPRITE_HEIGHT * 4);

	public static Animation enemyWMoveAnimation = new Animation(
		ENEMY_SPRITE_WIDTH, ENEMY_SPRITE_HEIGHT, animationDurationms, 0,
		ENEMY_SPRITE_HEIGHT, ENEMY_SPRITE_WIDTH * 3, 0,
		ENEMY_SPRITE_WIDTH * 3, ENEMY_SPRITE_HEIGHT * 4);

	public static Animation enemyNEMoveAnimation = new Animation(
		ENEMY_SPRITE_WIDTH, ENEMY_SPRITE_HEIGHT, animationDurationms, 0,
		ENEMY_SPRITE_HEIGHT, ENEMY_SPRITE_WIDTH * 8, 0,
		ENEMY_SPRITE_WIDTH * 8, ENEMY_SPRITE_HEIGHT * 4);

	public static Animation enemySEMoveAnimation = new Animation(
		ENEMY_SPRITE_WIDTH, ENEMY_SPRITE_HEIGHT, animationDurationms, 0,
		ENEMY_SPRITE_HEIGHT, ENEMY_SPRITE_WIDTH * 9, 0,
		ENEMY_SPRITE_WIDTH * 9, ENEMY_SPRITE_HEIGHT * 4);

	public static Animation enemySWMoveAnimation = new Animation(
		ENEMY_SPRITE_WIDTH, ENEMY_SPRITE_HEIGHT, animationDurationms, 0,
		ENEMY_SPRITE_HEIGHT, ENEMY_SPRITE_WIDTH * 10, 0,
		ENEMY_SPRITE_WIDTH * 10, ENEMY_SPRITE_HEIGHT * 4);

	public static Animation enemyNWMoveAnimation = new Animation(
		ENEMY_SPRITE_WIDTH, ENEMY_SPRITE_HEIGHT, animationDurationms, 0,
		ENEMY_SPRITE_HEIGHT, ENEMY_SPRITE_WIDTH * 11, 0,
		ENEMY_SPRITE_WIDTH * 11, ENEMY_SPRITE_HEIGHT * 4);

	// attack animation
	public static Animation enemyNAttackAnimation = new Animation(
		ENEMY_SPRITE_WIDTH, ENEMY_SPRITE_HEIGHT, animationDurationms, 0,
		ENEMY_SPRITE_HEIGHT, ENEMY_SPRITE_WIDTH * 12, 0,
		ENEMY_SPRITE_WIDTH * 12, ENEMY_SPRITE_HEIGHT * 4);

	public static Animation enemyEAttackAnimation = new Animation(
		ENEMY_SPRITE_WIDTH, ENEMY_SPRITE_HEIGHT, animationDurationms, 0,
		ENEMY_SPRITE_HEIGHT, ENEMY_SPRITE_WIDTH * 13, 0,
		ENEMY_SPRITE_WIDTH * 13, ENEMY_SPRITE_HEIGHT * 4);

	public static Animation enemySAttackAnimation = new Animation(
		ENEMY_SPRITE_WIDTH, ENEMY_SPRITE_HEIGHT, animationDurationms, 0,
		ENEMY_SPRITE_HEIGHT, ENEMY_SPRITE_WIDTH * 14, 0,
		ENEMY_SPRITE_WIDTH * 14, ENEMY_SPRITE_HEIGHT * 4);

	public static Animation enemyWAttackAnimation = new Animation(
		ENEMY_SPRITE_WIDTH, ENEMY_SPRITE_HEIGHT, animationDurationms, 0,
		ENEMY_SPRITE_HEIGHT, ENEMY_SPRITE_WIDTH * 15, 0,
		ENEMY_SPRITE_WIDTH * 15, ENEMY_SPRITE_HEIGHT * 4);

	public static Animation enemyNEAttackAnimation = new Animation(
		ENEMY_SPRITE_WIDTH, ENEMY_SPRITE_HEIGHT, animationDurationms, 0,
		ENEMY_SPRITE_HEIGHT, ENEMY_SPRITE_WIDTH * 4, 0,
		ENEMY_SPRITE_WIDTH * 4, ENEMY_SPRITE_HEIGHT * 4);

	public static Animation enemySEAttackAnimation = new Animation(
		ENEMY_SPRITE_WIDTH, ENEMY_SPRITE_HEIGHT, animationDurationms, 0,
		ENEMY_SPRITE_HEIGHT, ENEMY_SPRITE_WIDTH * 5, 0,
		ENEMY_SPRITE_WIDTH * 5, ENEMY_SPRITE_HEIGHT * 4);

	public static Animation enemySWAttackAnimation = new Animation(
		ENEMY_SPRITE_WIDTH, ENEMY_SPRITE_HEIGHT, animationDurationms, 0,
		ENEMY_SPRITE_HEIGHT, ENEMY_SPRITE_WIDTH * 6, 0,
		ENEMY_SPRITE_WIDTH * 6, ENEMY_SPRITE_HEIGHT * 4);

	public static Animation enemyNWAttackAnimation = new Animation(
		ENEMY_SPRITE_WIDTH, ENEMY_SPRITE_HEIGHT, animationDurationms, 0,
		ENEMY_SPRITE_HEIGHT, ENEMY_SPRITE_WIDTH * 7, 0,
		ENEMY_SPRITE_WIDTH * 7, ENEMY_SPRITE_HEIGHT * 4);


	// death animatoin
	public static Animation enemyNDeathAnimation = new Animation(
		ENEMY_SPRITE_WIDTH, ENEMY_SPRITE_HEIGHT, animationDurationms, 0,
		0, 0, ENEMY_SPRITE_HEIGHT * 4, 0, ENEMY_SPRITE_HEIGHT * 4);

	public static Animation enemyEDeathAnimation = new Animation(
		ENEMY_SPRITE_WIDTH, ENEMY_SPRITE_HEIGHT, animationDurationms, 0,
		0, ENEMY_SPRITE_WIDTH * 1, ENEMY_SPRITE_HEIGHT * 4,
		ENEMY_SPRITE_WIDTH, ENEMY_SPRITE_HEIGHT * 4);

	public static Animation enemySDeathAnimation = new Animation(
		ENEMY_SPRITE_WIDTH, ENEMY_SPRITE_HEIGHT, animationDurationms, 0,
		0, ENEMY_SPRITE_WIDTH * 2, ENEMY_SPRITE_HEIGHT * 4,
		ENEMY_SPRITE_WIDTH * 2, ENEMY_SPRITE_HEIGHT * 4);

	public static Animation enemyWDeathAnimation = new Animation(
		ENEMY_SPRITE_WIDTH, ENEMY_SPRITE_HEIGHT, animationDurationms, 0,
		0, ENEMY_SPRITE_WIDTH * 3, ENEMY_SPRITE_HEIGHT * 4,
		ENEMY_SPRITE_WIDTH * 3, ENEMY_SPRITE_HEIGHT * 4);
}
