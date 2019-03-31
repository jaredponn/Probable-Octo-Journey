package Resources;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import Components.Sound;

import poj.Animation;
import poj.Render.ImageLoader;
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
		// ImageLoader.load("resources/RamiroGraphics/spritePack/Turret8.png");
		ImageLoader.load("resources/turret1Crop.png");

	public static BufferedImage octoTitle =
		ImageLoader.load("resources/menu/octo_journey_title.png");
	public static BufferedImage instructionTitle =
		ImageLoader.load("resources/menu/instructions.png");

	public static BufferedImage playButton =
		ImageLoader.load("resources/menu/playButton.png");
	public static int playButtonWidth = 180, playButtonHeight = 100;

	public static BufferedImage howToPlayButton =
		ImageLoader.load("resources/menu/howToPlay.png");
	public static int howToPlayButtonWidth = 200, howToPlayButtonHeight =
							      120;

	public static BufferedImage exitButton =
		ImageLoader.load("resources/menu/exitButton.png");
	public static int exitButtonWidth = 150, exitButtonHeight = 80;
	public static BufferedImage backButton =
		ImageLoader.load("resources/menu/backButton.png");
	public static int backButtonWidth = 150, backButtonHeight = 80;

	// special tiles
	public static BufferedImage tree1 = ImageLoader.load(
		"resources/RamiroGraphics/officialMap/tree1.png");
	public static BufferedImage tree2 = ImageLoader.load(
		"resources/RamiroGraphics/officialMap/tree2.png");
	public static BufferedImage tree3 = ImageLoader.load(
		"resources/RamiroGraphics/officialMap/tree3.png");
	public static BufferedImage tree4 = ImageLoader.load(
		"resources/RamiroGraphics/officialMap/tree4.png");
	public static BufferedImage tree5 = ImageLoader.load(
		"resources/RamiroGraphics/officialMap/tree5.png");

	public static BufferedImage pole1 = ImageLoader.load(
		"resources/RamiroGraphics/officialMap/pole1.png");
	public static BufferedImage pole2 = ImageLoader.load(
		"resources/RamiroGraphics/officialMap/pole2.png");
	public static BufferedImage pole3 = ImageLoader.load(
		"resources/RamiroGraphics/officialMap/pole3.png");
	public static BufferedImage pole4 = ImageLoader.load(
		"resources/RamiroGraphics/officialMap/pole4.png");
	public static BufferedImage stopSign = ImageLoader.load(
		"resources/RamiroGraphics/officialMap/stopSign.png");

	// sounds
	public static String
		gunSoundPath = "resources/sounds/gunSound.wav",
		zombieDeathSoundPath = "resources/sounds/zombieDeathSound.wav",
		zombieDeathSoundPath2 =
			"resources/sounds/zombieDeathSound2.wav",
		zombieDeathSoundPath3 =
			"resources/sounds/zombieDeathSound3.wav",
		zombieDeathSoundPath4 =
			"resources/sounds/zombieDeathSound4.wav",
		emptyClipSoundPath = "resources/sounds/emptyClipSound.wav",
		menuSoundPath = "resources/sounds/menuSound.wav",
		gameBgSoundPath = "resources/sounds/gameBgMusic.wav",
		menuSelectButtonSoundPath =
			"resources/sounds/menuSelectButtonSound.wav",
		healthPickupSoundPath =
			"resources/sounds/healthPickupSound.wav",
		playerHpDropSound1Path = "resources/sounds/playerHpDrop1.wav",
		playerHpDropSound2Path = "resources/sounds/playerHpDrop2.wav",
		playerHpDropSound3Path = "resources/sounds/playerHpDrop3.wav",
		playerHpDropSound4Path = "resources/sounds/playerHpDrop4.wav",
		playerDeathSoundPath1 =
			"resources/sounds/playerDeathSound1.wav",
		playerDeathSoundPath2 =
			"resources/sounds/playerDeathSound2.wav",
		playerDeathSoundPath3 =
			"resources/sounds/playerDeathSound3.wav",
		playerDeathSoundPath4 =
			"resources/sounds/playerDeathSound4.wav";

	public static Sound gunSound, zombieDeathSound, zombieDeathSound2,
		zombieDeathSound3, zombieDeathSound4, emptyClipSound, menuSound,
		gameBgSound, menuSelectButtonSound, healthPickupSound,
		playerHpDropSound1, playerHpDropSound2, playerHpDropSound3,
		playerHpDropSound4, playerDeathSound1, playerDeathSound2,
		playerDeathSound3, playerDeathSound4;
	public static ArrayList<Sound> playerSoundAsset =
					       new ArrayList<Sound>(),
				       zombieSoundAsset =
					       new ArrayList<Sound>();

	static
	{
		try {
			// creating the sounds
			gunSound = new Sound(GameResources.gunSoundPath);
			zombieDeathSound =
				new Sound(GameResources.zombieDeathSoundPath);
			zombieDeathSound2 =
				new Sound(GameResources.zombieDeathSoundPath2);
			zombieDeathSound3 =
				new Sound(GameResources.zombieDeathSoundPath3);
			zombieDeathSound4 =
				new Sound(GameResources.zombieDeathSoundPath4);
			emptyClipSound =
				new Sound(GameResources.emptyClipSoundPath);
			menuSound = new Sound(GameResources.menuSoundPath);
			gameBgSound = new Sound(GameResources.gameBgSoundPath);
			menuSelectButtonSound =
				new Sound(menuSelectButtonSoundPath);
			healthPickupSound = new Sound(healthPickupSoundPath);
			playerHpDropSound1 = new Sound(playerHpDropSound1Path);
			playerHpDropSound2 = new Sound(playerHpDropSound2Path);
			playerHpDropSound3 = new Sound(playerHpDropSound3Path);
			playerHpDropSound4 = new Sound(playerHpDropSound4Path);
			playerDeathSound1 = new Sound(playerDeathSoundPath1);
			playerDeathSound2 = new Sound(playerDeathSoundPath2);
			playerDeathSound3 = new Sound(playerDeathSoundPath3);
			playerDeathSound4 = new Sound(playerDeathSoundPath4);

			// adding the sounds into component's assets
			playerSoundAsset.add(gunSound);
			playerSoundAsset.add(emptyClipSound);
			playerSoundAsset.add(playerHpDropSound1);
			playerSoundAsset.add(playerHpDropSound2);
			playerSoundAsset.add(playerHpDropSound3);
			playerSoundAsset.add(playerHpDropSound4); // 5 index
			playerSoundAsset.add(playerDeathSound1);
			playerSoundAsset.add(playerDeathSound2);
			playerSoundAsset.add(playerDeathSound3);
			playerSoundAsset.add(playerDeathSound4);
		} catch (NullPointerException e) {
			poj.Logger.Logger.logMessage(
				"NullPointerException has occured when loading the sound in (don't know which sound so debugg it yourself')",
				poj.Logger.LogLevels.VERBOSE);
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			poj.Logger.Logger.logMessage(
				"UnsupportedAudioFileException has occured when loading the sound in (don't know which sound so debugg it yourself')",
				poj.Logger.LogLevels.VERBOSE);
			e.printStackTrace();
		} catch (IOException e) {
			poj.Logger.Logger.logMessage(
				"IOException has occured when loading the sound in (don't know which sound so debugg it yourself')",
				poj.Logger.LogLevels.VERBOSE);
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			poj.Logger.Logger.logMessage(
				"LineUnavailableException has occured when loading the sound in (don't know which sound so debugg it yourself')",
				poj.Logger.LogLevels.VERBOSE);
			e.printStackTrace();
		}
	}


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
		ImageLoader.load("resources/RamiroGraphics/officialMap/mapSprities.png");
	public static BufferedImage officialTileSetTest =
		ImageLoader.load("resources/RamiroGraphics/officialMap/mapSpritiesWithLines.png");

	public static String officialMapGround1="resources/RamiroGraphics/officialMap/MapFinal_ground.csv",
		   		officialMapMisc2="resources/RamiroGraphics/officialMap/MapFinal_misc1.csv",
				officialMapCarsAndBuildings3="resources/RamiroGraphics/officialMap/MapFinal_cars and buildings.csv",
				officialMapTreesAndRocks4="resources/RamiroGraphics/officialMap/MapFinal_trees and rocks.csv",
				officialMapLightsAndSigns5="resources/RamiroGraphics/officialMap/MapFinal_lights and signs.csv",
				officialTileSetConfig="resources/RamiroGraphics/officialMap/mapSpritesfinal.json",
				officialMapConfig="resources/RamiroGraphics/officialMap/MapFinal.json";

	public static String NoTreeofficialMapGround1="resources/RamiroGraphics/officialMap/MapFinalNoTrees_ground.csv",
		   		NoTreeofficialMapMisc2="resources/RamiroGraphics/officialMap/MapFinalNoTrees_misc1.csv",
				NoTreeofficialMapCarsAndBuildings3="resources/RamiroGraphics/officialMap/MapFinalNoTrees_cars and buildings.csv",
				NoTreeofficialMapTreesAndRocks4="resources/RamiroGraphics/officialMap/MapFinalNoTrees_trees and rocks.csv",
				NoTreeofficialMapLightsAndSigns5="resources/RamiroGraphics/officialMap/MapFinalNoTrees_lights and signs.csv",
				NoTreeofficialMapEmptyBlockForBuildings="resources/RamiroGraphics/officialMap/MapFinalNoTrees_emptyBlocksFOrBuildings.csv",
				NoTreeofficialTileSetConfig="resources/RamiroGraphics/officialMap/mapSpritesfinal.json",
				NoTreeofficialMapConfig="resources/RamiroGraphics/officialMap/MapFinal.json";

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
	public static BufferedImage cashImage = ImageLoader.load(
		"resources/RamiroGraphics/spritePack/Coin.png");

	// ammo resources
	public static final int AMMO_SPRITE_WIDTH = 16;
	public static final int AMMO_SPRITE_HEIGHT = 16;
	public static BufferedImage ammoImage = ImageLoader.load(
		"resources/RamiroGraphics/spritePack/AmmoPack.png");

	// health resources
	public static final int HEALTH_SPRITE_WIDTH = 16;
	public static final int HEALTH_SPRITE_HEIGHT = 16;
	public static BufferedImage healthImage = ImageLoader.load(
		"resources/RamiroGraphics/spritePack/MedKit.png");

	// health resources
	public static final int POWERUP_SPRITE_WIDTH = 16;
	public static final int POWERUP_SPRITE_HEIGHT = 16;
	public static BufferedImage powerupImage =
		ImageLoader.load("resources/coin.png");


	// player resources
	public static BufferedImage playerSpriteSheet = ImageLoader.load(
		"resources/RamiroGraphics/spritePack/PlayerSheet.png");
	// ImageLoader.load("resources/RamiroGraphics/spritePack/PlayerSheet.png");
	public static BufferedImage enemySpriteSheet = ImageLoader.load(
		"resources/RamiroGraphics/spritePack/ZombieSheet.png");

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
		0, PLAYER_SPRITE_HEIGHT, PLAYER_SPRITE_WIDTH * 12, 0,
		PLAYER_SPRITE_WIDTH * 12, PLAYER_SPRITE_HEIGHT * 4);

	public static Animation playerNMeleeMoveAnimation = new Animation(
		PLAYER_SPRITE_WIDTH, PLAYER_SPRITE_HEIGHT, animationDurationms,
		0, PLAYER_SPRITE_HEIGHT, PLAYER_SPRITE_WIDTH * 4, 0,
		PLAYER_SPRITE_WIDTH * 4, PLAYER_SPRITE_HEIGHT * 4);

	public static Animation playerEGunMoveAnimation = new Animation(
		PLAYER_SPRITE_WIDTH, PLAYER_SPRITE_HEIGHT, animationDurationms,
		0, PLAYER_SPRITE_HEIGHT, PLAYER_SPRITE_WIDTH * 13, 0,
		PLAYER_SPRITE_WIDTH * 13, PLAYER_SPRITE_HEIGHT * 4);

	public static Animation playerEMeleeMoveAnimation = new Animation(
		PLAYER_SPRITE_WIDTH, PLAYER_SPRITE_HEIGHT, animationDurationms,
		0, PLAYER_SPRITE_HEIGHT, PLAYER_SPRITE_WIDTH * 5, 0,
		PLAYER_SPRITE_WIDTH * 5, PLAYER_SPRITE_HEIGHT * 4);

	public static Animation playerSGunMoveAnimation = new Animation(
		PLAYER_SPRITE_WIDTH, PLAYER_SPRITE_HEIGHT, animationDurationms,
		0, PLAYER_SPRITE_HEIGHT, PLAYER_SPRITE_WIDTH * 14, 0,
		PLAYER_SPRITE_WIDTH * 14, PLAYER_SPRITE_HEIGHT * 4);

	public static Animation playerSMeleeMoveAnimation = new Animation(
		PLAYER_SPRITE_WIDTH, PLAYER_SPRITE_HEIGHT, animationDurationms,
		0, PLAYER_SPRITE_HEIGHT, PLAYER_SPRITE_WIDTH * 6, 0,
		PLAYER_SPRITE_WIDTH * 6, PLAYER_SPRITE_HEIGHT * 4);

	public static Animation playerWGunMoveAnimation = new Animation(
		PLAYER_SPRITE_WIDTH, PLAYER_SPRITE_HEIGHT, animationDurationms,
		0, PLAYER_SPRITE_HEIGHT, PLAYER_SPRITE_WIDTH * 15, 0,
		PLAYER_SPRITE_WIDTH * 15, PLAYER_SPRITE_HEIGHT * 4);

	public static Animation playerWMeleeMoveAnimation = new Animation(
		PLAYER_SPRITE_WIDTH, PLAYER_SPRITE_HEIGHT, animationDurationms,
		0, PLAYER_SPRITE_HEIGHT, PLAYER_SPRITE_WIDTH * 7, 0,
		PLAYER_SPRITE_WIDTH * 7, PLAYER_SPRITE_HEIGHT * 4);

	public static Animation playerNEGunMoveAnimation = new Animation(
		PLAYER_SPRITE_WIDTH, PLAYER_SPRITE_HEIGHT, animationDurationms,
		0, PLAYER_SPRITE_HEIGHT, PLAYER_SPRITE_WIDTH * 16, 0,
		PLAYER_SPRITE_WIDTH * 16, PLAYER_SPRITE_HEIGHT * 4);

	public static Animation playerNEMeleeMoveAnimation = new Animation(
		PLAYER_SPRITE_WIDTH, PLAYER_SPRITE_HEIGHT, animationDurationms,
		0, PLAYER_SPRITE_HEIGHT, PLAYER_SPRITE_WIDTH * 8, 0,
		PLAYER_SPRITE_WIDTH * 8, PLAYER_SPRITE_HEIGHT * 4);

	public static Animation playerSEGunMoveAnimation = new Animation(
		PLAYER_SPRITE_WIDTH, PLAYER_SPRITE_HEIGHT, animationDurationms,
		0, PLAYER_SPRITE_HEIGHT, PLAYER_SPRITE_WIDTH * 17, 0,
		PLAYER_SPRITE_WIDTH * 17, PLAYER_SPRITE_HEIGHT * 4);

	public static Animation playerSEMeleeMoveAnimation = new Animation(
		PLAYER_SPRITE_WIDTH, PLAYER_SPRITE_HEIGHT, animationDurationms,
		0, PLAYER_SPRITE_HEIGHT, PLAYER_SPRITE_WIDTH * 9, 0,
		PLAYER_SPRITE_WIDTH * 9, PLAYER_SPRITE_HEIGHT * 4);

	public static Animation playerSWGunMoveAnimation = new Animation(
		PLAYER_SPRITE_WIDTH, PLAYER_SPRITE_HEIGHT, animationDurationms,
		0, PLAYER_SPRITE_HEIGHT, PLAYER_SPRITE_WIDTH * 18, 0,
		PLAYER_SPRITE_WIDTH * 18, PLAYER_SPRITE_HEIGHT * 4);

	public static Animation playerSWMeleeMoveAnimation = new Animation(
		PLAYER_SPRITE_WIDTH, PLAYER_SPRITE_HEIGHT, animationDurationms,
		0, PLAYER_SPRITE_HEIGHT, PLAYER_SPRITE_WIDTH * 10, 0,
		PLAYER_SPRITE_WIDTH * 10, PLAYER_SPRITE_HEIGHT * 4);

	public static Animation playerNWGunMoveAnimation = new Animation(
		PLAYER_SPRITE_WIDTH, PLAYER_SPRITE_HEIGHT, animationDurationms,
		0, PLAYER_SPRITE_HEIGHT, PLAYER_SPRITE_WIDTH * 19, 0,
		PLAYER_SPRITE_WIDTH * 19, PLAYER_SPRITE_HEIGHT * 4);

	public static Animation playerNWMeleeMoveAnimation = new Animation(
		PLAYER_SPRITE_WIDTH, PLAYER_SPRITE_HEIGHT, animationDurationms,
		0, PLAYER_SPRITE_HEIGHT, PLAYER_SPRITE_WIDTH * 11, 0,
		PLAYER_SPRITE_WIDTH * 11, PLAYER_SPRITE_HEIGHT * 4);


	// idle animation
	public static Animation playerNGunIdleAnimation = new Animation(
		PLAYER_SPRITE_WIDTH, PLAYER_SPRITE_HEIGHT, animationDurationms,
		0, PLAYER_SPRITE_HEIGHT, PLAYER_SPRITE_WIDTH * 12,
		PLAYER_SPRITE_HEIGHT * 4, PLAYER_SPRITE_WIDTH * 12,
		PLAYER_SPRITE_HEIGHT * 7);

	public static Animation playerNMeleeIdleAnimation = new Animation(
		PLAYER_SPRITE_WIDTH, PLAYER_SPRITE_HEIGHT, animationDurationms,
		0, PLAYER_SPRITE_HEIGHT, PLAYER_SPRITE_WIDTH * 4,
		PLAYER_SPRITE_HEIGHT * 4, PLAYER_SPRITE_WIDTH * 4,
		PLAYER_SPRITE_HEIGHT * 7);


	public static Animation playerEGunIdleAnimation = new Animation(
		PLAYER_SPRITE_WIDTH, PLAYER_SPRITE_HEIGHT, animationDurationms,
		0, PLAYER_SPRITE_HEIGHT, PLAYER_SPRITE_WIDTH * 13,
		PLAYER_SPRITE_HEIGHT * 4, PLAYER_SPRITE_WIDTH * 13,
		PLAYER_SPRITE_HEIGHT * 7);

	public static Animation playerEMeleeIdleAnimation = new Animation(
		PLAYER_SPRITE_WIDTH, PLAYER_SPRITE_HEIGHT, animationDurationms,
		0, PLAYER_SPRITE_HEIGHT, PLAYER_SPRITE_WIDTH * 5,
		PLAYER_SPRITE_HEIGHT * 4, PLAYER_SPRITE_WIDTH * 5,
		PLAYER_SPRITE_HEIGHT * 7);

	public static Animation playerSGunIdleAnimation = new Animation(
		PLAYER_SPRITE_WIDTH, PLAYER_SPRITE_HEIGHT, animationDurationms,
		0, PLAYER_SPRITE_HEIGHT, PLAYER_SPRITE_WIDTH * 14,
		PLAYER_SPRITE_HEIGHT * 4, PLAYER_SPRITE_WIDTH * 14,
		PLAYER_SPRITE_HEIGHT * 7);

	public static Animation playerSMeleeIdleAnimation = new Animation(
		PLAYER_SPRITE_WIDTH, PLAYER_SPRITE_HEIGHT, animationDurationms,
		0, PLAYER_SPRITE_HEIGHT, PLAYER_SPRITE_WIDTH * 6,
		PLAYER_SPRITE_HEIGHT * 4, PLAYER_SPRITE_WIDTH * 6,
		PLAYER_SPRITE_HEIGHT * 7);

	public static Animation playerWGunIdleAnimation = new Animation(
		PLAYER_SPRITE_WIDTH, PLAYER_SPRITE_HEIGHT, animationDurationms,
		0, PLAYER_SPRITE_HEIGHT, PLAYER_SPRITE_WIDTH * 15,
		PLAYER_SPRITE_HEIGHT * 4, PLAYER_SPRITE_WIDTH * 15,
		PLAYER_SPRITE_HEIGHT * 7);

	public static Animation playerWMeleeIdleAnimation = new Animation(
		PLAYER_SPRITE_WIDTH, PLAYER_SPRITE_HEIGHT, animationDurationms,
		0, PLAYER_SPRITE_HEIGHT, PLAYER_SPRITE_WIDTH * 7,
		PLAYER_SPRITE_HEIGHT * 4, PLAYER_SPRITE_WIDTH * 7,
		PLAYER_SPRITE_HEIGHT * 7);

	public static Animation playerNEGunIdleAnimation = new Animation(
		PLAYER_SPRITE_WIDTH, PLAYER_SPRITE_HEIGHT, animationDurationms,
		0, PLAYER_SPRITE_HEIGHT, PLAYER_SPRITE_WIDTH * 8,
		PLAYER_SPRITE_HEIGHT * 4, PLAYER_SPRITE_WIDTH * 8,
		PLAYER_SPRITE_HEIGHT * 7);

	public static Animation playerNEMeleeIdleAnimation = new Animation(
		PLAYER_SPRITE_WIDTH, PLAYER_SPRITE_HEIGHT, animationDurationms,
		0, PLAYER_SPRITE_HEIGHT, PLAYER_SPRITE_WIDTH * 4,
		PLAYER_SPRITE_HEIGHT * 4, PLAYER_SPRITE_WIDTH * 4,
		PLAYER_SPRITE_HEIGHT * 7);

	public static Animation playerSEGunIdleAnimation = new Animation(
		PLAYER_SPRITE_WIDTH, PLAYER_SPRITE_HEIGHT, animationDurationms,
		0, PLAYER_SPRITE_HEIGHT, PLAYER_SPRITE_WIDTH * 17,
		PLAYER_SPRITE_HEIGHT * 4, PLAYER_SPRITE_WIDTH * 17,
		PLAYER_SPRITE_HEIGHT * 7);

	public static Animation playerSEMeleeIdleAnimation = new Animation(
		PLAYER_SPRITE_WIDTH, PLAYER_SPRITE_HEIGHT, animationDurationms,
		0, PLAYER_SPRITE_HEIGHT, PLAYER_SPRITE_WIDTH * 9,
		PLAYER_SPRITE_HEIGHT * 4, PLAYER_SPRITE_WIDTH * 9,
		PLAYER_SPRITE_HEIGHT * 7);

	public static Animation playerSWGunIdleAnimation = new Animation(
		PLAYER_SPRITE_WIDTH, PLAYER_SPRITE_HEIGHT, animationDurationms,
		0, PLAYER_SPRITE_HEIGHT, PLAYER_SPRITE_WIDTH * 18,
		PLAYER_SPRITE_HEIGHT * 4, PLAYER_SPRITE_WIDTH * 18,
		PLAYER_SPRITE_HEIGHT * 7);

	public static Animation playerSWMeleeIdleAnimation = new Animation(
		PLAYER_SPRITE_WIDTH, PLAYER_SPRITE_HEIGHT, animationDurationms,
		0, PLAYER_SPRITE_HEIGHT, PLAYER_SPRITE_WIDTH * 10,
		PLAYER_SPRITE_HEIGHT * 4, PLAYER_SPRITE_WIDTH * 10,
		PLAYER_SPRITE_HEIGHT * 7);

	public static Animation playerNWGunIdleAnimation = new Animation(
		PLAYER_SPRITE_WIDTH, PLAYER_SPRITE_HEIGHT, animationDurationms,
		0, PLAYER_SPRITE_HEIGHT, PLAYER_SPRITE_WIDTH * 19,
		PLAYER_SPRITE_HEIGHT * 4, PLAYER_SPRITE_WIDTH * 19,
		PLAYER_SPRITE_HEIGHT * 7);

	public static Animation playerNWMeleeIdleAnimation = new Animation(
		PLAYER_SPRITE_WIDTH, PLAYER_SPRITE_HEIGHT, animationDurationms,
		0, PLAYER_SPRITE_HEIGHT, PLAYER_SPRITE_WIDTH * 11,
		PLAYER_SPRITE_HEIGHT * 4, PLAYER_SPRITE_WIDTH * 11,
		PLAYER_SPRITE_HEIGHT * 7);

	//  MELEE ATTACK ANIMATIONS
	public static Animation playerNMeleeAttack = new Animation(
		PLAYER_SPRITE_WIDTH, PLAYER_SPRITE_HEIGHT, animationDurationms,
		0, 0, PLAYER_SPRITE_WIDTH * 0, PLAYER_SPRITE_HEIGHT * 0,
		PLAYER_SPRITE_WIDTH * 0, PLAYER_SPRITE_HEIGHT * 1);

	public static Animation playerEMeleeAttack = new Animation(
		PLAYER_SPRITE_WIDTH, PLAYER_SPRITE_HEIGHT, animationDurationms,
		0, 0, PLAYER_SPRITE_WIDTH * 0, PLAYER_SPRITE_HEIGHT * 1,
		PLAYER_SPRITE_WIDTH * 0, PLAYER_SPRITE_HEIGHT * 2);

	public static Animation playerSMeleeAttack = new Animation(
		PLAYER_SPRITE_WIDTH, PLAYER_SPRITE_HEIGHT, animationDurationms,
		0, 0, PLAYER_SPRITE_WIDTH * 0, PLAYER_SPRITE_HEIGHT * 2,
		PLAYER_SPRITE_WIDTH * 0, PLAYER_SPRITE_HEIGHT * 3);

	public static Animation playerWMeleeAttack = new Animation(
		PLAYER_SPRITE_WIDTH, PLAYER_SPRITE_HEIGHT, animationDurationms,
		0, 0, PLAYER_SPRITE_WIDTH * 0, PLAYER_SPRITE_HEIGHT * 3,
		PLAYER_SPRITE_WIDTH * 0, PLAYER_SPRITE_HEIGHT * 4);

	public static Animation playerNEMeleeAttack = new Animation(
		PLAYER_SPRITE_WIDTH, PLAYER_SPRITE_HEIGHT, animationDurationms,
		0, 0, PLAYER_SPRITE_WIDTH * 1, PLAYER_SPRITE_HEIGHT * 0,
		PLAYER_SPRITE_WIDTH * 1, PLAYER_SPRITE_HEIGHT * 1);

	public static Animation playerSEMeleeAttack = new Animation(
		PLAYER_SPRITE_WIDTH, PLAYER_SPRITE_HEIGHT, animationDurationms,
		0, 0, PLAYER_SPRITE_WIDTH * 1, PLAYER_SPRITE_HEIGHT * 1,
		PLAYER_SPRITE_WIDTH * 1, PLAYER_SPRITE_HEIGHT * 2);

	public static Animation playerSWMeleeAttack = new Animation(
		PLAYER_SPRITE_WIDTH, PLAYER_SPRITE_HEIGHT, animationDurationms,
		0, 0, PLAYER_SPRITE_WIDTH * 1, PLAYER_SPRITE_HEIGHT * 2,
		PLAYER_SPRITE_WIDTH * 1, PLAYER_SPRITE_HEIGHT * 3);

	public static Animation playerNWMeleeAttack = new Animation(
		PLAYER_SPRITE_WIDTH, PLAYER_SPRITE_HEIGHT, animationDurationms,
		0, 0, PLAYER_SPRITE_WIDTH * 1, PLAYER_SPRITE_HEIGHT * 3,
		PLAYER_SPRITE_WIDTH * 1, PLAYER_SPRITE_HEIGHT * 4);

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
		ENEMY_SPRITE_HEIGHT, ENEMY_SPRITE_WIDTH * 4, 0,
		ENEMY_SPRITE_WIDTH * 4, ENEMY_SPRITE_HEIGHT * 4);

	public static Animation enemySEMoveAnimation = new Animation(
		ENEMY_SPRITE_WIDTH, ENEMY_SPRITE_HEIGHT, animationDurationms, 0,
		ENEMY_SPRITE_HEIGHT, ENEMY_SPRITE_WIDTH * 5, 0,
		ENEMY_SPRITE_WIDTH * 5, ENEMY_SPRITE_HEIGHT * 4);

	public static Animation enemySWMoveAnimation = new Animation(
		ENEMY_SPRITE_WIDTH, ENEMY_SPRITE_HEIGHT, animationDurationms, 0,
		ENEMY_SPRITE_HEIGHT, ENEMY_SPRITE_WIDTH * 6, 0,
		ENEMY_SPRITE_WIDTH * 6, ENEMY_SPRITE_HEIGHT * 4);

	public static Animation enemyNWMoveAnimation = new Animation(
		ENEMY_SPRITE_WIDTH, ENEMY_SPRITE_HEIGHT, animationDurationms, 0,
		ENEMY_SPRITE_HEIGHT, ENEMY_SPRITE_WIDTH * 7, 0,
		ENEMY_SPRITE_WIDTH * 7, ENEMY_SPRITE_HEIGHT * 4);

	// attack animation
	public static Animation enemyNAttackAnimation = new Animation(
		ENEMY_SPRITE_WIDTH, ENEMY_SPRITE_HEIGHT, animationDurationms, 0,
		ENEMY_SPRITE_HEIGHT, ENEMY_SPRITE_WIDTH * 8, 0,
		ENEMY_SPRITE_WIDTH * 8, ENEMY_SPRITE_HEIGHT * 13);

	public static Animation enemyEAttackAnimation = new Animation(
		ENEMY_SPRITE_WIDTH, ENEMY_SPRITE_HEIGHT, animationDurationms, 0,
		ENEMY_SPRITE_HEIGHT, ENEMY_SPRITE_WIDTH * 9, 0,
		ENEMY_SPRITE_WIDTH * 9, ENEMY_SPRITE_HEIGHT * 13);

	public static Animation enemySAttackAnimation = new Animation(
		ENEMY_SPRITE_WIDTH, ENEMY_SPRITE_HEIGHT, animationDurationms, 0,
		ENEMY_SPRITE_HEIGHT, ENEMY_SPRITE_WIDTH * 10, 0,
		ENEMY_SPRITE_WIDTH * 10, ENEMY_SPRITE_HEIGHT * 13);

	public static Animation enemyWAttackAnimation = new Animation(
		ENEMY_SPRITE_WIDTH, ENEMY_SPRITE_HEIGHT, animationDurationms, 0,
		ENEMY_SPRITE_HEIGHT, ENEMY_SPRITE_WIDTH * 11, 0,
		ENEMY_SPRITE_WIDTH * 11, ENEMY_SPRITE_HEIGHT * 13);

	public static Animation enemyNEAttackAnimation = new Animation(
		ENEMY_SPRITE_WIDTH, ENEMY_SPRITE_HEIGHT, animationDurationms, 0,
		ENEMY_SPRITE_HEIGHT, ENEMY_SPRITE_WIDTH * 12, 0,
		ENEMY_SPRITE_WIDTH * 12, ENEMY_SPRITE_HEIGHT * 13);

	public static Animation enemySEAttackAnimation = new Animation(
		ENEMY_SPRITE_WIDTH, ENEMY_SPRITE_HEIGHT, animationDurationms, 0,
		ENEMY_SPRITE_HEIGHT, ENEMY_SPRITE_WIDTH * 13, 0,
		ENEMY_SPRITE_WIDTH * 13, ENEMY_SPRITE_HEIGHT * 13);

	public static Animation enemySWAttackAnimation = new Animation(
		ENEMY_SPRITE_WIDTH, ENEMY_SPRITE_HEIGHT, animationDurationms, 0,
		ENEMY_SPRITE_HEIGHT, ENEMY_SPRITE_WIDTH * 14, 0,
		ENEMY_SPRITE_WIDTH * 14, ENEMY_SPRITE_HEIGHT * 13);

	public static Animation enemyNWAttackAnimation = new Animation(
		ENEMY_SPRITE_WIDTH, ENEMY_SPRITE_HEIGHT, animationDurationms, 0,
		ENEMY_SPRITE_HEIGHT, ENEMY_SPRITE_WIDTH * 15, 0,
		ENEMY_SPRITE_WIDTH * 15, ENEMY_SPRITE_HEIGHT * 13);


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
