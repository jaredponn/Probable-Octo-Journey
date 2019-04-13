package Resources;

/**
 * Game resouces. Static final resources of the game
 * Date: March 10, 2019
 *@author Jared Pon, Haiyang He, Ramiro Piquer, Alex Stark
 */

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import Components.OctoAnimationBuffer;
import Components.Sound;

import poj.Animation;
import poj.Render.ImageLoader;
import poj.Render.ImageWindow;

public class GameResources
{

	public static BufferedImage TILE_MAP_SINGLE_IMAGE = ImageLoader.load(
		"resources/RamiroGraphics/officialMap/finalmap/MapFinalNoTrees.png");

	public static BufferedImage turret = ImageLoader.load(
		"resources/RamiroGraphics/spritePack/Turrets.png");


	// position constants for the buttons for menu pics:
	// they are calculated based on the position of the buttons relative to
	// the image size
	// clang-format off
	public static int lowResolutionWidth=1366,lowResolutionHeight=768;

	public static float playButtonWidthRatio = 2.6519337016574585f,
			    playButtonHeightRatio = 2.9508196721311477f,
			    playButtonSizeWidthRatio = 4.076433121019108f,
			    playButtonSizeHeightRatio = 6.666666666666667f,
				//how to play button
				howToPlayButtonWidthRatio=2.436548223350254f,
				howToPlayButtonHeightRatio=1.821247892074199f,
			    howToPlayButtonSizeWidthRatio = 5.565217391304348f,
			    howToPlayButtonSizeHeightRatio = 8.459627329192546f,
				//exit button
				exitButtonWidthRatio=2.436548223350254f,
				exitButtonHeightRatio= 1.3740458015267176f,
			    exitButtonSizeWidthRatio = 5.565217391304348f,
			    exitButtonSizeHeightRatio = 8.471748723717228f,
				//back button
				backButtonWidthRatio=2.4427480916030535f,
				backButtonHeightRatio= 1.2399540757749712f,
			    backButtonSizeWidthRatio = 5.550989238216081f,
			    backButtonSizeHeightRatio = 8.485416666666666f,
				//howto howto button
				howToHowToButtonWidthRatio= 3.3566433566433567f,
				howToHowToButtonHeightRatio= 9.436392914653783f,
			    howToHowToButtonSizeWidthRatio = 2.4742268041237114f,
			    howToHowToButtonSizeHeightRatio = 7.859973186354834f;


	// clang-format on
	// Menu pictures:
	// 1920x1080 res menu pics:
	public static BufferedImage octoTitle90 = ImageLoader.load(
		"resources/RamiroGraphics/menu/1920_1080/game_name_main90.png");
	public static BufferedImage mainBg90 = ImageLoader.load(
		"resources/RamiroGraphics/menu/1920_1080/background_main90.png");
	public static BufferedImage playButton90 = ImageLoader.load(
		"resources/RamiroGraphics/menu/1920_1080/playButton90.png");
	public static BufferedImage howToPlayButton90 = ImageLoader.load(
		"resources/RamiroGraphics/menu/1920_1080/howToPlayButton90.png");
	public static BufferedImage howToHowToPlayButton90 = ImageLoader.load(
		"resources/RamiroGraphics/menu/1920_1080/howToHowToPlayButton90.png");
	public static BufferedImage exitButton90 = ImageLoader.load(
		"resources/RamiroGraphics/menu/1920_1080/exitButton90.png");
	public static BufferedImage backButton90 = ImageLoader.load(
		"resources/RamiroGraphics/menu/1920_1080/backButton90.png");

	public static BufferedImage helpBg90 = ImageLoader.load(
		"resources/RamiroGraphics/menu/1920_1080/background_help90.png");
	public static BufferedImage helpButton90 = ImageLoader.load(
		"resources/RamiroGraphics/menu/1920_1080/buttons_help90.png");
	public static BufferedImage helpTopLeft90 = ImageLoader.load(
		"resources/RamiroGraphics/menu/1920_1080/trapHelp90.png");
	public static BufferedImage helpTopRight90 = ImageLoader.load(
		"resources/RamiroGraphics/menu/1920_1080/helpTopRight90.png");
	public static BufferedImage instructionsForHelp90 = ImageLoader.load(
		"resources/RamiroGraphics/menu/1920_1080/instruction_boxes_help90.png");
	public static BufferedImage tombstonesForMain90 = ImageLoader.load(
		"resources/RamiroGraphics/menu/1920_1080/tombstone_main90.png");
	public static ArrayList<BufferedImage> menuImage90 =
		new ArrayList<BufferedImage>() {
			{
				add(octoTitle90);
				add(mainBg90);
				add(tombstonesForMain90);
				add(helpBg90);
				add(instructionsForHelp90);
				add(helpTopLeft90);
				add(helpTopRight90);
			}
		};

	public static ArrayList<BufferedImage> menuButtonImage90 =
		new ArrayList<BufferedImage>() {
			{
				add(playButton90);
				add(howToPlayButton90);
				add(exitButton90);
				add(howToHowToPlayButton90);
				add(backButton90);
			}
		};
	// clang-format off
	public static float[][] menuButtonValues = new float[][]{
		{playButtonWidthRatio, playButtonHeightRatio, playButtonSizeWidthRatio, playButtonSizeHeightRatio},
		{howToPlayButtonWidthRatio, howToPlayButtonHeightRatio, howToPlayButtonSizeWidthRatio, howToPlayButtonSizeHeightRatio},
		{exitButtonWidthRatio, exitButtonHeightRatio, exitButtonSizeWidthRatio, exitButtonSizeHeightRatio},
		{howToHowToButtonWidthRatio, howToHowToButtonHeightRatio, howToHowToButtonSizeWidthRatio, howToHowToButtonSizeHeightRatio},
		{backButtonWidthRatio, backButtonHeightRatio, backButtonSizeWidthRatio, backButtonSizeHeightRatio}
	};
	// clang-format on

	// 1366x768 res menu pics:
	public static BufferedImage octoTitle38 = ImageLoader.load(
		"resources/RamiroGraphics/menu/1366_768/game_name_main38.png");
	public static BufferedImage mainBg38 = ImageLoader.load(
		"resources/RamiroGraphics/menu/1366_768/background_main38.png");
	public static BufferedImage playButton38 = ImageLoader.load(
		"resources/RamiroGraphics/menu/1366_768/playButton38.png");
	public static BufferedImage howToPlayButton38 = ImageLoader.load(
		"resources/RamiroGraphics/menu/1366_768/howToPlayButton38.png");
	public static BufferedImage howToHowToPlayButton38 = ImageLoader.load(
		"resources/RamiroGraphics/menu/1366_768/howToHowToPlayButton38.png");
	public static BufferedImage exitButton38 = ImageLoader.load(
		"resources/RamiroGraphics/menu/1366_768/exitButton38.png");
	public static BufferedImage backButton38 = ImageLoader.load(
		"resources/RamiroGraphics/menu/1366_768/backButton38.png");

	public static BufferedImage helpBg38 = ImageLoader.load(
		"resources/RamiroGraphics/menu/1366_768/background_help38.png");
	public static BufferedImage helpButton38 = ImageLoader.load(
		"resources/RamiroGraphics/menu/1366_768/buttons_help38.png");
	public static BufferedImage helpTopLeft38 = ImageLoader.load(
		"resources/RamiroGraphics/menu/1366_768/trapHelp38.png");
	public static BufferedImage helpTopRight38 = ImageLoader.load(
		"resources/RamiroGraphics/menu/1366_768/helpTopRight38.png");
	public static BufferedImage instructionsForHelp38 = ImageLoader.load(
		"resources/RamiroGraphics/menu/1366_768/instruction_boxes_help38.png");
	public static BufferedImage tombstonesForMain38 = ImageLoader.load(
		"resources/RamiroGraphics/menu/1366_768/tombstone_main38.png");
	public static ArrayList<BufferedImage> menuImage38 =
		new ArrayList<BufferedImage>() {
			{
				add(octoTitle38);
				add(mainBg38);
				add(tombstonesForMain38);
				add(helpBg38);
				add(instructionsForHelp38);
				add(helpTopLeft38);
				add(helpTopRight38);
			}
		};
	public static ArrayList<BufferedImage> menuButtonImage38 =
		new ArrayList<BufferedImage>() {
			{
				add(playButton38);
				add(howToPlayButton38);
				add(exitButton38);
				add(howToHowToPlayButton38);
				add(backButton38);
			}
		};


	// position constants for the buttons for Game over screen:
	// they are calculated based on the position of the buttons relative to
	// the image size
	// clang-format off
	public static float 
				//back button
				goBackButtonWidthRatio=2.4334600760456273f,
				goBackButtonHeightRatio= 1.25f,
			    goBackButtonSizeWidthRatio = 5.614035087719298f,
			    goBackButtonSizeHeightRatio = 8.307692307692308f,
				//your score display starting position ratio
				goYourScoreWidthRatio=5.217391304347826f,
				goYourScoreHeightRatio=2.082857142857143f,
				goScoreBoardWidthRatioInitially=2.577181208053691f,
				goScoreBoardHeightRatioInitially=3.430983606557377f,
				goScoreBoardGapHeightRatio=15.7f,
				//initials selection ratios
				goInitialSelectionWidthRatio=2.486548223350254f,
				goInitialSelectionHeightRatio=1.3754098360655739f,
				goInitialSelectGapWidthRatio=10.378378378378379f,
				// font size for 1920x1080 resolutions
				goInitialFontSize90=95f,
				goInitialFontSize38=67f,
				goOtherFontSize90=40f,
 				goOtherFontSize38=30f,
				goYourScoreFontSize90=55f,
				goYourScoreFontSize38=45f;


	// clang-format on

	// font for gameover and displays inside the game
	public static final String creeperFont =
		"resources/RamiroGraphics/gameOver/creepster/Creepster-Regular.ttf";

	public static Font CREEPER_FONT_HUGE;
	public static Font CREEPER_FONT;
	public static Font CREEPER_FONT_SMALL;

	static
	{
		try {

			CREEPER_FONT_HUGE =
				Font.createFont(
					    Font.TRUETYPE_FONT,
					    new File(
						    "resources/RamiroGraphics/gameOver/creepster/Creepster-Regular.ttf"))
					.deriveFont(64f);
			CREEPER_FONT =
				Font.createFont(
					    Font.TRUETYPE_FONT,
					    new File(
						    "resources/RamiroGraphics/gameOver/creepster/Creepster-Regular.ttf"))
					.deriveFont(32f);
			CREEPER_FONT_SMALL =
				Font.createFont(
					    Font.TRUETYPE_FONT,
					    new File(
						    "resources/RamiroGraphics/gameOver/creepster/Creepster-Regular.ttf"))
					.deriveFont(20f);
		} catch (IOException e) {
			System.out.println(
				"IOException occured when creating the creeper font in gameOver!");
			e.printStackTrace();
		} catch (FontFormatException e) {
			System.out.println(
				"FontFormatException occured when creating the creeper font in gameOver!");
			e.printStackTrace();
		}
	}
	// images for Game over screen:
	// 1920x1080 resolution:
	public static BufferedImage goBg90 = ImageLoader.load(
		"resources/RamiroGraphics/gameOver/1920_1080/background90.png");
	public static BufferedImage goEnterInitials90 = ImageLoader.load(
		"resources/RamiroGraphics/gameOver/1920_1080/enter_initials90 .png");
	public static BufferedImage goArrowKeys90 = ImageLoader.load(
		"resources/RamiroGraphics/gameOver/1920_1080/arrowKeys90.png");
	public static BufferedImage goMiddleThing90 = ImageLoader.load(
		"resources/RamiroGraphics/gameOver/1920_1080/middle_thing90.png");
	public static BufferedImage goScoreBoard90 = ImageLoader.load(
		"resources/RamiroGraphics/gameOver/1920_1080/scores90.png");
	public static BufferedImage goYourScore90 = ImageLoader.load(
		"resources/RamiroGraphics/gameOver/1920_1080/your_score90.png");
	public static BufferedImage goHighScoresLabel90 = ImageLoader.load(
		"resources/RamiroGraphics/gameOver/1920_1080/highScores90.png");
	// buttons:
	public static BufferedImage goBackButton90 = ImageLoader.load(
		"resources/RamiroGraphics/gameOver/1920_1080/backButton90.png");
	// clang-format off
	public static float[] gameOverButtonValue = new float[]{
		goBackButtonWidthRatio, goBackButtonHeightRatio, goBackButtonSizeWidthRatio, goBackButtonSizeHeightRatio
	};
	// clang-format on

	public static ArrayList<BufferedImage> goImage90 =
		new ArrayList<BufferedImage>() {
			{
				add(goBg90);
				add(goMiddleThing90);
				add(goScoreBoard90);
				add(goYourScore90);
				add(goHighScoresLabel90);
				add(goArrowKeys90);
				add(goEnterInitials90);
			}
		};

	// images for Game over screen:
	// 1366x768 resolution:
	public static BufferedImage goBg38 = ImageLoader.load(
		"resources/RamiroGraphics/gameOver/1366_768/background38.png");
	public static BufferedImage goEnterInitials38 = ImageLoader.load(
		"resources/RamiroGraphics/gameOver/1366_768/enter_initials38 .png");
	public static BufferedImage goArrowKeys38 = ImageLoader.load(
		"resources/RamiroGraphics/gameOver/1366_768/arrowKeys38.png");
	public static BufferedImage goMiddleThing38 = ImageLoader.load(
		"resources/RamiroGraphics/gameOver/1366_768/middle_thing38.png");
	public static BufferedImage goScoreBoard38 = ImageLoader.load(
		"resources/RamiroGraphics/gameOver/1366_768/scores38.png");
	public static BufferedImage goYourScore38 = ImageLoader.load(
		"resources/RamiroGraphics/gameOver/1366_768/your_score38.png");
	public static BufferedImage goHighScoresLabel38 = ImageLoader.load(
		"resources/RamiroGraphics/gameOver/1366_768/highScores38.png");
	// buttons:
	public static BufferedImage goBackButton38 = ImageLoader.load(
		"resources/RamiroGraphics/gameOver/1366_768/backButton38.png");
	public static ArrayList<BufferedImage> goImage38 =
		new ArrayList<BufferedImage>() {
			{
				add(goBg38);
				add(goMiddleThing38);
				add(goScoreBoard38);
				add(goYourScore38);
				add(goHighScoresLabel38);
				add(goArrowKeys38);
				add(goEnterInitials38);
			}
		};


	// special tiles for tilemap
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

	public static BufferedImage fountainBuilding = ImageLoader.load(
		"resources/RamiroGraphics/officialMap/fountain.png");
	public static BufferedImage gasStationBuilding = ImageLoader.load(
		"resources/RamiroGraphics/officialMap/gasStationBuilding.png");
	public static BufferedImage schoolBuilding = ImageLoader.load(
		"resources/RamiroGraphics/officialMap/schoolBuilding.png");
	public static BufferedImage blueBuilding = ImageLoader.load(
		"resources/RamiroGraphics/officialMap/blueBuilding.png");
	public static BufferedImage brownBuilding = ImageLoader.load(
		"resources/RamiroGraphics/officialMap/brownBuilding.png");
	public static BufferedImage pinkBuilding = ImageLoader.load(
		"resources/RamiroGraphics/officialMap/pinkBuilding.png");


	// sounds
	public static String
		gunSoundPath = "resources/sounds/gunSound.wav",
		zombieDeathSound1Path = "resources/sounds/zombieDeathSound.wav",
		zombieDeathSound2Path =
			"resources/sounds/zombieDeathSound2.wav",
		zombieDeathSound3Path =
			"resources/sounds/zombieDeathSound3.wav",
		zombieDeathSound4Path =
			"resources/sounds/zombieDeathSound4.wav",
		zombieSpawnSound1Path =
			"resources/sounds/zombieSpawnSound1.wav",
		zombieSpawnSound2Path =
			"resources/sounds/zombieSpawnSound2.wav",
		zombieSpawnSound3Path =
			"resources/sounds/zombieSpawnSound3.wav",
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
		playerDeathSound1Path =
			"resources/sounds/playerDeathSound1.wav",
		playerDeathSound2Path =
			"resources/sounds/playerDeathSound2.wav",
		playerDeathSound3Path =
			"resources/sounds/playerDeathSound3.wav",
		playerDeathSound4Path =
			"resources/sounds/playerDeathSound4.wav",
		playerMeleeSound1Path =
			"resources/sounds/playerMeleeSound1.wav",
		playerMeleeSound2Path =
			"resources/sounds/playerMeleeSound2.wav",
		playerMeleeSound3Path =
			"resources/sounds/playerMeleeSound3.wav",
		winSoundPath = "resources/sounds/winMusic.wav",
		loseSoundPath = "resources/sounds/loseMusic.wav",
		bossSoundPath = "resources/sounds/bossMusic.wav",
		bossAlertSoundPath = "resources/sounds/bossAlert.wav",
		bossSpawnSoundPath = "resources/sounds/bossSpawn.wav";


	public static Sound gunSound, zombieDeathSound1, zombieDeathSound2,
		zombieDeathSound3, zombieDeathSound4, zombieSpawnSound1,
		zombieSpawnSound2, zombieSpawnSound3, emptyClipSound, menuSound,
		gameBgSound, menuSelectButtonSound, healthPickupSound,
		playerHpDropSound1, playerHpDropSound2, playerHpDropSound3,
		playerHpDropSound4, playerDeathSound1, playerDeathSound2,
		playerDeathSound3, playerDeathSound4, winSound, loseSound,
		bossSound, bossAlertSound, bossSpawnSound;
	public static ArrayList<String> playerSoundAsset =
						new ArrayList<String>(),
					zombieSoundAsset =
						new ArrayList<String>();

	// used for playing background music
	// public static ArrayList<Sound> musicSoundAsset = new
	// ArrayList<Sound>();

	static
	{
		try {
			// game sounds
			menuSound = new Sound(GameResources.menuSoundPath);
			gameBgSound = new Sound(GameResources.gameBgSoundPath);
			winSound = new Sound(GameResources.winSoundPath);
			loseSound = new Sound(GameResources.loseSoundPath);
			bossSound = new Sound(GameResources.bossSoundPath);
			bossAlertSound =
				new Sound(GameResources.bossAlertSoundPath);
			bossSpawnSound =
				new Sound(GameResources.bossSpawnSoundPath);

			// musicSoundAsset.add(menuSound);
			// musicSoundAsset.add(gameBgSound);
			/*
			healthPickupSound = new
			Sound(healthPickupSoundPath);
			*/
			// adding the sounds into player assets
			playerSoundAsset.add(gunSoundPath);
			playerSoundAsset.add(emptyClipSoundPath);
			playerSoundAsset.add(playerHpDropSound1Path);
			playerSoundAsset.add(playerHpDropSound2Path);
			playerSoundAsset.add(playerHpDropSound3Path);
			playerSoundAsset.add(playerHpDropSound4Path); // 5 index
			playerSoundAsset.add(playerDeathSound1Path);
			playerSoundAsset.add(playerDeathSound2Path);
			playerSoundAsset.add(playerDeathSound3Path);
			playerSoundAsset.add(playerDeathSound4Path); // 9 index
			playerSoundAsset.add(playerMeleeSound1Path);
			playerSoundAsset.add(playerMeleeSound2Path);
			playerSoundAsset.add(playerMeleeSound3Path);

			// adding the sounds into zombie assets
			zombieSoundAsset.add(zombieSpawnSound1Path);
			zombieSoundAsset.add(zombieSpawnSound2Path);
			zombieSoundAsset.add(zombieSpawnSound3Path); // 2 index
			zombieSoundAsset.add(zombieDeathSound1Path);
			zombieSoundAsset.add(zombieDeathSound2Path);
			zombieSoundAsset.add(zombieDeathSound3Path);
			zombieSoundAsset.add(zombieDeathSound4Path);
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


	// clang-format off

	public static BufferedImage officialTileSetGoodFountain =
		ImageLoader.load("resources/RamiroGraphics/officialMap/finalmap/withfountain.png");

	public static String NoTreeofficialMapGround1="resources/RamiroGraphics/officialMap/finalmap/MapFinalNoTreesWithFountainOnTiles_ground.csv",
		   		NoTreeofficialMapMisc2="resources/RamiroGraphics/officialMap/finalmap/MapFinalNoTreesWithFountainOnTiles_misc1.csv",
				NoTreeofficialMapCarsAndBuildings3="resources/RamiroGraphics/officialMap/finalmap/MapFinalNoTreesWithFountainOnTiles_cars and buildings.csv",
				NoTreeofficialMapTreesAndRocks4="resources/RamiroGraphics/officialMap/finalmap/MapFinalNoTreesWithFountainOnTiles_trees and rocks.csv",
				NoTreeofficialMapLightsAndSigns5="resources/RamiroGraphics/officialMap/finalmap/MapFinalNoTreesWithFountainOnTiles_lights and signs.csv",
				NoTreeofficialMapEmptyBlockForBuildings="resources/RamiroGraphics/officialMap/finalmap/MapFinalNoTreesWithFountainOnTiles_emptyBlocksFOrBuildings.csv",
				NoTreeofficialMapimageMarkForBuildings="resources/RamiroGraphics/officialMap/finalmap/MapFinalNoTreesWithFountainOnTiles_imageMarkForBuildings.csv",
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

	// power-up resources
	public static final int POWERUP_SPRITE_WIDTH = 16;
	public static final int POWERUP_SPRITE_HEIGHT = 16;
	public static BufferedImage powerupImage = ImageLoader.load(
		"resources/RamiroGraphics/spritePack/ArrowPowerup.png");


	// player resources
	public static BufferedImage playerSpriteSheet = ImageLoader.load(
		"resources/RamiroGraphics/spritePack/PlayerSheet.png");
	// ImageLoader.load("resources/RamiroGraphics/spritePack/PlayerSheet.png");
	public static BufferedImage enemySpriteSheet = ImageLoader.load(
		"resources/RamiroGraphics/spritePack/ZombieSheet.png");

	public static BufferedImage bossSpriteSheet = ImageLoader.load(
		"resources/RamiroGraphics/spritePack/Boss.png");

	public static BufferedImage trapImage = ImageLoader.load(
		"resources/RamiroGraphics/spritePack/Trap.png");

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

	public static OctoAnimationBuffer playerMeleeMoveAnimation =
		new OctoAnimationBuffer(
			playerNMeleeMoveAnimation, playerNEMeleeMoveAnimation,
			playerNWMeleeMoveAnimation, playerSMeleeMoveAnimation,
			playerSEMeleeMoveAnimation, playerSWMeleeMoveAnimation,
			playerWMeleeMoveAnimation, playerEMeleeMoveAnimation);

	public static OctoAnimationBuffer playerGunMoveAnimation =
		new OctoAnimationBuffer(
			playerNGunMoveAnimation, playerNEGunMoveAnimation,
			playerNWGunMoveAnimation, playerSGunMoveAnimation,
			playerSEGunMoveAnimation, playerSWGunMoveAnimation,
			playerWGunMoveAnimation, playerEGunMoveAnimation);

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
		0, PLAYER_SPRITE_HEIGHT, PLAYER_SPRITE_WIDTH * 16,
		PLAYER_SPRITE_HEIGHT * 4, PLAYER_SPRITE_WIDTH * 16,
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

	public static OctoAnimationBuffer playerMeleeIdleAnimation =
		new OctoAnimationBuffer(
			playerNMeleeIdleAnimation, playerNEMeleeIdleAnimation,
			playerNWMeleeIdleAnimation, playerSMeleeIdleAnimation,
			playerSEMeleeIdleAnimation, playerSWMeleeIdleAnimation,
			playerWMeleeIdleAnimation, playerEMeleeIdleAnimation);

	public static OctoAnimationBuffer playerGunIdleAnimation =
		new OctoAnimationBuffer(
			playerNGunIdleAnimation, playerNEGunIdleAnimation,
			playerNWGunIdleAnimation, playerSGunIdleAnimation,
			playerSEGunIdleAnimation, playerSWGunIdleAnimation,
			playerWGunIdleAnimation, playerEGunIdleAnimation);

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


	public static OctoAnimationBuffer playerMeleeAttackAnimation =
		new OctoAnimationBuffer(playerNMeleeAttack, playerNEMeleeAttack,
					playerNWMeleeAttack, playerSMeleeAttack,
					playerSEMeleeAttack,
					playerSWMeleeAttack, playerWMeleeAttack,
					playerEMeleeAttack);

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

	public static OctoAnimationBuffer enemyMoveAnimation =
		new OctoAnimationBuffer(
			enemyNMoveAnimation, enemyNEMoveAnimation,
			enemyNWMoveAnimation, enemySMoveAnimation,
			enemySEMoveAnimation, enemySWMoveAnimation,
			enemyWMoveAnimation, enemyEMoveAnimation);

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


	public static OctoAnimationBuffer enemyAttackAnimation =
		new OctoAnimationBuffer(
			enemyNAttackAnimation, enemyNEAttackAnimation,
			enemyNWAttackAnimation, enemySAttackAnimation,
			enemySEAttackAnimation, enemySWAttackAnimation,
			enemyWAttackAnimation, enemyEAttackAnimation);
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

	public static OctoAnimationBuffer enemyDeathAnimation =
		new OctoAnimationBuffer(
			enemyNDeathAnimation, enemyNDeathAnimation,
			enemyWDeathAnimation, enemySDeathAnimation,
			enemyEDeathAnimation, enemySDeathAnimation,
			enemyWDeathAnimation, enemyEDeathAnimation);

	// boss resources

	public static final int BOSS_SPRITE_WIDTH = PLAYER_SPRITE_WIDTH * 5;
	public static final int BOSS_SPRITE_HEIGHT = PLAYER_SPRITE_HEIGHT * 5;

	// public static final int ENEMY_SPRITE_WIDTH = (int)(125 * 0.8);
	// public static final int ENEMY_SPRITE_HEIGHT = (int)(155 * 0.8);

	// move direction
	public static Animation bossNMoveAnimation = new Animation(
		BOSS_SPRITE_WIDTH, BOSS_SPRITE_HEIGHT, animationDurationms, 0,
		BOSS_SPRITE_HEIGHT, BOSS_SPRITE_WIDTH * 0, 0,
		BOSS_SPRITE_WIDTH * 0, BOSS_SPRITE_HEIGHT * 4);

	public static Animation bossEMoveAnimation = new Animation(
		BOSS_SPRITE_WIDTH, BOSS_SPRITE_HEIGHT, animationDurationms, 0,
		BOSS_SPRITE_HEIGHT, BOSS_SPRITE_WIDTH * 1, 0,
		BOSS_SPRITE_WIDTH * 1, BOSS_SPRITE_HEIGHT * 4);


	public static Animation bossSMoveAnimation = new Animation(
		BOSS_SPRITE_WIDTH, BOSS_SPRITE_HEIGHT, animationDurationms, 0,
		BOSS_SPRITE_HEIGHT, BOSS_SPRITE_WIDTH * 2, 0,
		BOSS_SPRITE_WIDTH * 2, BOSS_SPRITE_HEIGHT * 4);

	public static Animation bossWMoveAnimation = new Animation(
		BOSS_SPRITE_WIDTH, BOSS_SPRITE_HEIGHT, animationDurationms, 0,
		BOSS_SPRITE_HEIGHT, BOSS_SPRITE_WIDTH * 3, 0,
		BOSS_SPRITE_WIDTH * 3, BOSS_SPRITE_HEIGHT * 4);

	public static Animation bossNEMoveAnimation = new Animation(
		BOSS_SPRITE_WIDTH, BOSS_SPRITE_HEIGHT, animationDurationms, 0,
		BOSS_SPRITE_HEIGHT, BOSS_SPRITE_WIDTH * 4, 0,
		BOSS_SPRITE_WIDTH * 4, BOSS_SPRITE_HEIGHT * 4);

	public static Animation bossSEMoveAnimation = new Animation(
		BOSS_SPRITE_WIDTH, BOSS_SPRITE_HEIGHT, animationDurationms, 0,
		BOSS_SPRITE_HEIGHT, BOSS_SPRITE_WIDTH * 5, 0,
		BOSS_SPRITE_WIDTH * 5, BOSS_SPRITE_HEIGHT * 4);

	public static Animation bossSWMoveAnimation = new Animation(
		BOSS_SPRITE_WIDTH, BOSS_SPRITE_HEIGHT, animationDurationms, 0,
		BOSS_SPRITE_HEIGHT, BOSS_SPRITE_WIDTH * 6, 0,
		BOSS_SPRITE_WIDTH * 6, BOSS_SPRITE_HEIGHT * 4);

	public static Animation bossNWMoveAnimation = new Animation(
		BOSS_SPRITE_WIDTH, BOSS_SPRITE_HEIGHT, animationDurationms, 0,
		BOSS_SPRITE_HEIGHT, BOSS_SPRITE_WIDTH * 7, 0,
		BOSS_SPRITE_WIDTH * 7, BOSS_SPRITE_HEIGHT * 4);

	public static OctoAnimationBuffer bossMoveAnimation =
		new OctoAnimationBuffer(bossNMoveAnimation, bossNEMoveAnimation,
					bossNWMoveAnimation, bossSMoveAnimation,
					bossSEMoveAnimation,
					bossSWMoveAnimation, bossWMoveAnimation,
					bossEMoveAnimation);

	// attack animation
	public static Animation bossNAttackAnimation = new Animation(
		BOSS_SPRITE_WIDTH, BOSS_SPRITE_HEIGHT, animationDurationms, 0,
		BOSS_SPRITE_HEIGHT, BOSS_SPRITE_WIDTH * 8, 0,
		BOSS_SPRITE_WIDTH * 8, BOSS_SPRITE_HEIGHT * 13);

	public static Animation bossEAttackAnimation = new Animation(
		BOSS_SPRITE_WIDTH, BOSS_SPRITE_HEIGHT, animationDurationms, 0,
		BOSS_SPRITE_HEIGHT, BOSS_SPRITE_WIDTH * 9, 0,
		BOSS_SPRITE_WIDTH * 9, BOSS_SPRITE_HEIGHT * 13);

	public static Animation bossSAttackAnimation = new Animation(
		BOSS_SPRITE_WIDTH, BOSS_SPRITE_HEIGHT, animationDurationms, 0,
		BOSS_SPRITE_HEIGHT, BOSS_SPRITE_WIDTH * 10, 0,
		BOSS_SPRITE_WIDTH * 10, BOSS_SPRITE_HEIGHT * 13);

	public static Animation bossWAttackAnimation = new Animation(
		BOSS_SPRITE_WIDTH, BOSS_SPRITE_HEIGHT, animationDurationms, 0,
		BOSS_SPRITE_HEIGHT, BOSS_SPRITE_WIDTH * 11, 0,
		BOSS_SPRITE_WIDTH * 11, BOSS_SPRITE_HEIGHT * 13);

	public static Animation bossNEAttackAnimation = new Animation(
		BOSS_SPRITE_WIDTH, BOSS_SPRITE_HEIGHT, animationDurationms, 0,
		BOSS_SPRITE_HEIGHT, BOSS_SPRITE_WIDTH * 12, 0,
		BOSS_SPRITE_WIDTH * 12, BOSS_SPRITE_HEIGHT * 13);

	public static Animation bossSEAttackAnimation = new Animation(
		BOSS_SPRITE_WIDTH, BOSS_SPRITE_HEIGHT, animationDurationms, 0,
		BOSS_SPRITE_HEIGHT, BOSS_SPRITE_WIDTH * 13, 0,
		BOSS_SPRITE_WIDTH * 13, BOSS_SPRITE_HEIGHT * 13);

	public static Animation bossSWAttackAnimation = new Animation(
		BOSS_SPRITE_WIDTH, BOSS_SPRITE_HEIGHT, animationDurationms, 0,
		BOSS_SPRITE_HEIGHT, BOSS_SPRITE_WIDTH * 14, 0,
		BOSS_SPRITE_WIDTH * 14, BOSS_SPRITE_HEIGHT * 13);

	public static Animation bossNWAttackAnimation = new Animation(
		BOSS_SPRITE_WIDTH, BOSS_SPRITE_HEIGHT, animationDurationms, 0,
		BOSS_SPRITE_HEIGHT, BOSS_SPRITE_WIDTH * 15, 0,
		BOSS_SPRITE_WIDTH * 15, BOSS_SPRITE_HEIGHT * 13);


	public static OctoAnimationBuffer bossAttackAnimation =
		new OctoAnimationBuffer(
			bossNAttackAnimation, bossNEAttackAnimation,
			bossNWAttackAnimation, bossSAttackAnimation,
			bossSEAttackAnimation, bossSWAttackAnimation,
			bossWAttackAnimation, bossEAttackAnimation);
	// death animatoin
	public static Animation bossNDeathAnimation = new Animation(
		BOSS_SPRITE_WIDTH, BOSS_SPRITE_HEIGHT, animationDurationms, 0,
		0, 0, BOSS_SPRITE_HEIGHT * 4, 0, BOSS_SPRITE_HEIGHT * 4);

	public static Animation bossEDeathAnimation = new Animation(
		BOSS_SPRITE_WIDTH, BOSS_SPRITE_HEIGHT, animationDurationms, 0,
		0, BOSS_SPRITE_WIDTH * 1, BOSS_SPRITE_HEIGHT * 4,
		BOSS_SPRITE_WIDTH, BOSS_SPRITE_HEIGHT * 4);

	public static Animation bossSDeathAnimation = new Animation(
		BOSS_SPRITE_WIDTH, BOSS_SPRITE_HEIGHT, animationDurationms, 0,
		0, BOSS_SPRITE_WIDTH * 2, BOSS_SPRITE_HEIGHT * 4,
		BOSS_SPRITE_WIDTH * 2, BOSS_SPRITE_HEIGHT * 4);

	public static Animation bossWDeathAnimation = new Animation(
		BOSS_SPRITE_WIDTH, BOSS_SPRITE_HEIGHT, animationDurationms, 0,
		0, BOSS_SPRITE_WIDTH * 3, BOSS_SPRITE_HEIGHT * 4,
		BOSS_SPRITE_WIDTH * 3, BOSS_SPRITE_HEIGHT * 4);

	public static OctoAnimationBuffer bossDeathAnimation =
		new OctoAnimationBuffer(
			bossNDeathAnimation, bossNDeathAnimation,
			bossWDeathAnimation, bossSDeathAnimation,
			bossEDeathAnimation, bossSDeathAnimation,
			bossWDeathAnimation, bossEDeathAnimation);


	/// turret resources
	public static final int TURRET_SPRITE_WIDTH = 250 / 4;
	public static final int TURRET_SPRITE_HEIGHT = 150 / 2;

	// public static final int TURRET_SPRITE_WIDTH = 250 / 4;
	// public static final int TURRET_SPRITE_HEIGHT = 150 / 2;


	public static Animation turretNAttackAnimation = new Animation(
		TURRET_SPRITE_WIDTH, TURRET_SPRITE_HEIGHT, animationDurationms,
		0, 0, TURRET_SPRITE_WIDTH * 3, 0, TURRET_SPRITE_WIDTH * 3, 0);

	public static Animation turretEAttackAnimation = new Animation(
		TURRET_SPRITE_WIDTH, TURRET_SPRITE_HEIGHT, animationDurationms,
		0, 0, TURRET_SPRITE_WIDTH * 0, 0, TURRET_SPRITE_WIDTH * 0, 0);

	public static Animation turretSAttackAnimation = new Animation(
		TURRET_SPRITE_WIDTH, TURRET_SPRITE_HEIGHT, animationDurationms,
		0, 0, TURRET_SPRITE_WIDTH * 2, 0, TURRET_SPRITE_WIDTH * 2, 0);

	public static Animation turretWAttackAnimation = new Animation(
		TURRET_SPRITE_WIDTH, TURRET_SPRITE_HEIGHT, animationDurationms,
		0, 0, TURRET_SPRITE_WIDTH * 0, TURRET_SPRITE_HEIGHT * 1,
		TURRET_SPRITE_WIDTH * 0, TURRET_SPRITE_HEIGHT * 1);

	public static Animation turretNEAttackAnimation = new Animation(
		TURRET_SPRITE_WIDTH, TURRET_SPRITE_HEIGHT, animationDurationms,
		0, 0, TURRET_SPRITE_WIDTH * 3, TURRET_SPRITE_HEIGHT * 1,
		TURRET_SPRITE_WIDTH * 3, TURRET_SPRITE_HEIGHT * 1);

	public static Animation turretSEAttackAnimation = new Animation(
		TURRET_SPRITE_WIDTH, TURRET_SPRITE_HEIGHT, animationDurationms,
		0, 0, TURRET_SPRITE_WIDTH * 1, 0, TURRET_SPRITE_WIDTH * 1, 0);

	public static Animation turretSWAttackAnimation = new Animation(
		TURRET_SPRITE_WIDTH, TURRET_SPRITE_HEIGHT, animationDurationms,
		0, 0, TURRET_SPRITE_WIDTH * 3, 0, TURRET_SPRITE_WIDTH * 3, 0);

	public static Animation turretNWAttackAnimation = new Animation(
		TURRET_SPRITE_WIDTH, TURRET_SPRITE_HEIGHT, animationDurationms,
		0, 0, TURRET_SPRITE_WIDTH * 1, TURRET_SPRITE_HEIGHT * 1,
		TURRET_SPRITE_WIDTH * 1, TURRET_SPRITE_HEIGHT * 1);

	public static OctoAnimationBuffer turretAttackAnimation =
		new OctoAnimationBuffer(
			turretNAttackAnimation, turretNEAttackAnimation,
			turretNWAttackAnimation, turretSAttackAnimation,
			turretSEAttackAnimation, turretSWAttackAnimation,
			turretWAttackAnimation, turretEAttackAnimation);
}
