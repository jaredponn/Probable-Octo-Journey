package Game;

/**
 * GameOver
 * Date: February 10, 2019
 * @author Haiyang He, Jared Pon, Alex Stark
 * @version 1.0
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

import Components.PCollisionBody;
import Components.Render;
import EntitySets.MenuButton;
import Resources.GameConfig;
import Resources.GameResources;

import poj.Collisions.GJK;
import poj.Collisions.Polygon;
import poj.GameWindow.InputPoller;
import poj.Logger.Logger;
import poj.Render.ImageRenderObject;
import poj.Render.RenderObject;
import poj.Render.RenderRect;
import poj.Render.Renderer;
import poj.Render.StringRenderObject;
import poj.linear.Vector2f;

public class GameOver extends World
{
	protected int newScore;
	protected ArrayList<ScoreTuple> scores = new ArrayList<ScoreTuple>();
	protected boolean isHighScore;

	protected GJK gjk = new GJK();

	protected Queue<RenderObject> renderBuffer;
	protected Queue<RenderObject> backgroundBuffer =
		new LinkedList<RenderObject>();
	protected Queue<RenderObject> buttonsBuffer =
		new LinkedList<RenderObject>();
	protected Queue<RenderObject> collisioBoxBuffer =
		new LinkedList<RenderObject>();
	protected Queue<PCollisionBody> buttonHitBoxBuffer =
		new LinkedList<PCollisionBody>();

	protected static final String SCORES_FILE_NAME = "scores.txt";

	protected static Font FONT;

	protected ArrayList<RenderObject> menuImageROBuffer =
		new ArrayList<RenderObject>();
	protected ArrayList<PCollisionBody> buttonHitBoxROBuffer =
		new ArrayList<PCollisionBody>();
	protected BufferedImage backButton;
	protected ArrayList<Render> buttonRenderLayer;


	// array of integers that are cast to a string depicting 
	// the set of initials the player has chosen to
	// save with their score
	protected ArrayList<Integer> initials = new ArrayList<Integer>() {
		{
			add(GameConfig.A_INTEGER);
			add(GameConfig.A_INTEGER);
			add(GameConfig.A_INTEGER);
		}
	};
	protected ArrayList<StringRenderObject> initialLetters =
		new ArrayList<StringRenderObject>();

	// the currently selected slot in the set of initials
	protected int currentInitial = 0;

	protected Scanner is = null;
	// TODO: DO OTHER FONT SIZES!!!
	protected float initialFontSize = 0, yourScoreFontSize = 0;


	/**
	 *  constructor
	 * @param width :width
	 * @param height :height
	 * @param renderer :renderer  --should be a reference
	 * @param inputPoller :inputPoller  --should be a reference
	 * @param newscore :new score
	 */
	public GameOver(int width, int height, Renderer renderer,
			InputPoller inputPoller, int newScore)
	{
		super(width, height, renderer, inputPoller);
		this.newScore = newScore;
		this.renderBuffer = new LinkedList<RenderObject>();


		// try to find high score file
		try {
			Scanner findFile =
				new Scanner(new File(SCORES_FILE_NAME));
			findFile.close();
		} catch (FileNotFoundException e) {
			System.out.println("Cannot find file: "
					   + SCORES_FILE_NAME);
			// create the file if one does not exist
			try {
				PrintWriter writer = new PrintWriter(
					new BufferedWriter(new FileWriter(
						SCORES_FILE_NAME)));
				writer.write("" + 0 + "\nAAA");
				for (int i = 0; i < 9; i++) {
					writer.write("\n" + 0 + "\nAAA");
				}
				writer.close();
			} catch (IOException ioe) {
				Logger.lassert("Cannot create file: "
					       + SCORES_FILE_NAME);
			}
		}

		// save info from high score file to an array
		// of ScoreTuple objects
		try {
			is = new Scanner(new File(SCORES_FILE_NAME));
			String thisName = "";
			int thisScore = 0;
			while (is.hasNextLine()) {
				thisScore = Integer.parseInt(is.nextLine());
				thisName = is.nextLine();
				scores.add(new ScoreTuple(thisScore, thisName));
			}
			is.close();
		} catch (IOException ioe) {
			Logger.lassert("Cannot load high score info");
		} catch (Exception e) {
			Logger.lassert("Cannot load high score info");
		}

		// congratulate the player if they achieved a new high score
		this.isHighScore = true;
		for (ScoreTuple t : scores) {
			if (this.newScore <= t.getScore()) {
				this.isHighScore = false;
				break;
			}
		}

		// sort the high scores into descending order
		Collections.sort(scores);
		Collections.reverse(scores);

		// deep copies the coolDown keys
		for (int i = 0; i < GameConfig.COOL_DOWN_KEYS.size(); ++i) {
			coolDownMax.set(GameConfig.COOL_DOWN_KEYS.get(i).fst,
					GameConfig.COOL_DOWN_KEYS.get(i).snd);
		}

		registerComponents();
		registerEntitySets();
		/*
		 * Note: the order of index for the menuImageROBuffer is inside
		 * gameresources
		 */
		// if the resolution is 1920x1080
		if (this.windowWidth == 1920 && this.windowHeight == 1080) {
			// create the imageRenderObject from the bufferedImages
			for (int i = 0; i < GameResources.goImage90.size();
			     ++i) {
				menuImageROBuffer.add(new ImageRenderObject(
					0, 0, GameResources.goImage90.get(i)));
			}
			// make the menuButton pointer point to the 1920x1080
			// res vector
			backButton = GameResources.goBackButton90;
			this.initialFontSize =
				GameResources.goInitialFontSize90;
			this.yourScoreFontSize =
				GameResources.goYourScoreFontSize90;

			FONT = GameResources.CREEPER_FONT.deriveFont(
				GameResources
					.goOtherFontSize90); // copies the
							     // pointer for the
							     // manual font
							     // "creeper" with
							     // font size 40f

		}
		// any other resolution will be using the default resolution
		// images: 1366x768 (an OK assumption since modern computers
		// have common resolution ratio of  1366x768)
		else {
			// create the imageRenderObject from the bufferedImages
			for (int i = 0; i < GameResources.goImage38.size();
			     ++i) {
				menuImageROBuffer.add(new ImageRenderObject(
					0, 0, GameResources.goImage38.get(i)));
			}
			// make the menuButton pointer point to the 1920x1080
			// res vector
			backButton = GameResources.goBackButton38;
			this.initialFontSize =
				GameResources.goInitialFontSize38;

			this.yourScoreFontSize =
				GameResources.goYourScoreFontSize38;

			FONT = GameResources.CREEPER_FONT.deriveFont(
				GameResources
					.goOtherFontSize38); // copies the
							     // pointer for the
							     // manual font
							     // "creeper" with
							     // font size 40f
		}

		/*
		initialRender = new StringRenderObject(
			initialsToStringSelection(),
			(int)(this.windowWidth
			      / GameResources.goInitialSelectionWidthRatio),
			(int)(this.windowHeight
			      / GameResources.goInitialSelectionHeightRatio),
			Color.BLACK,
			GameResources.CREEPER_FONT.deriveFont(initialFontSize));
			*/

		GraphicsEnvironment ge =
			GraphicsEnvironment.getLocalGraphicsEnvironment();
		ge.registerFont(FONT);

		// add the initial letters for the player initial
		addInitialLetters();

		// add the menu buttons
		addMenuButtons();
	}

	/**
	 *  add intial letters
	 */
	public void addInitialLetters()
	{
		for (int i = 0; i < 3; ++i) {
			initialLetters.add(new StringRenderObject(
				"A",
				(int)(super.windowWidth
				      / GameResources
						.goInitialSelectionWidthRatio)
					+ (int)(super.windowWidth
						/ GameResources
							  .goInitialSelectGapWidthRatio)
						  * i,
				(int)(super.windowHeight
				      / GameResources
						.goInitialSelectionHeightRatio),
				Color.BLACK,
				GameResources.CREEPER_FONT.deriveFont(
					this.initialFontSize)));
		}
	}

	// reset the initial character string render object each frame
	/**
	 *  clear the intial colors
	 */
	public void clearInitialColors()
	{
		for (int i = 0; i < initialLetters.size(); ++i) {
			initialLetters.get(i).setColor(Color.BLACK);
		}
	}

	/**
	 *  add menu buttons
	 */
	public void addMenuButtons()
	{
		// clang-format off
		//registers all of the button entity sets with its render and collision box 
			super.engineState.spawnEntitySet(new MenuButton(this.windowWidth, this.windowHeight, GameResources.gameOverButtonValue[0], GameResources.gameOverButtonValue[1], GameResources.gameOverButtonValue[2], GameResources.gameOverButtonValue[3],this.backButton));
		// clang-format on
		// store the render components for the buttons
		buttonRenderLayer = super.engineState.getComponents()
					    .getRawComponentArrayListPackedData(
						    Render.class);
		buttonHitBoxROBuffer =
			super.engineState.getComponents()
				.getRawComponentArrayListPackedData(
					PCollisionBody.class);
	}

	/**
	 *  register components
	 */
	public void registerComponents()
	{
		super.engineState.registerComponent(PCollisionBody.class);
		super.engineState.registerComponent(Render.class);
	}

	/**
	 *  register entity sets
	 */
	public void registerEntitySets()
	{
		super.engineState.registerSet(MenuButton.class);
	}

	/**
	 *  run game
	 */
	public void runGame()
	{
		poj.Time.Timer.sleepNMilliseconds(20);

		PlayGameProcessInputs.updateCoolDownKeys(this);


		this.processInputs();
	}

	/**
	 *  process inputs
	 */
	public void processInputs()
	{
		if (inputPoller.isKeyDown(KeyEvent.VK_ENTER))
			super.quit();

		// clear the colors of the initials
		clearInitialColors();


		// check if the user clicked back button
		if (inputPoller.isLeftMouseButtonDown()) {
			// get the mouse position
			Vector2f mousePosition = inputPoller.getMousePosition();
			PCollisionBody mouseHitBox = new PCollisionBody(
				new Vector2f(0f, 0f), new Vector2f(0f, 0f),
				mousePosition);

			// if use clicks back button
			if (Systems.arePCollisionBodiesColliding(
				    gjk, mouseHitBox,
				    this.buttonHitBoxROBuffer.get(0))) {
				// plays the sound
				GameResources.menuSelectButtonSound.play();
				super.quit();
			}
		}

		// Choose slot
		if (inputPoller.isKeyDown(GameConfig.ARROW_LEFT)
		    && Math.abs(lastCoolDown.get(GameConfig.ARROW_LEFT)) == 0) {
			if (currentInitial > 0)
				currentInitial--;
			PlayGameProcessInputs.updateDtForKey(
				this, GameConfig.ARROW_LEFT,
				-PlayGame.coolDownMax.get(
					GameConfig.ARROW_LEFT));
		}
		if (inputPoller.isKeyDown(GameConfig.ARROW_RIGHT)
		    && Math.abs(lastCoolDown.get(GameConfig.ARROW_RIGHT))
			       == 0) {
			if (currentInitial < 2)
				currentInitial++;
			PlayGameProcessInputs.updateDtForKey(
				this, GameConfig.ARROW_RIGHT,
				-PlayGame.coolDownMax.get(
					GameConfig.ARROW_RIGHT));
		}

		/// set the color of the current initial to orange
		initialLetters.get(currentInitial)
			.setStr(initialsToStringSelection(currentInitial));
		initialLetters.get(currentInitial).setColor(Color.ORANGE);

		// Choose letter
		if (inputPoller.isKeyDown(GameConfig.ARROW_DOWN)
		    && Math.abs(lastCoolDown.get(GameConfig.ARROW_DOWN)) == 0) {
			if (initials.get(currentInitial)
			    < GameConfig.Z_INTEGER) {
				Integer initial = initials.get(currentInitial);
				initials.set(currentInitial, initial + 1);

			} else {
				initials.set(currentInitial,
					     GameConfig.A_INTEGER);
			}
			// initialRender.setStr(
			// initialsToStringSelection(currentInitial));
			PlayGameProcessInputs.updateDtForKey(
				this, GameConfig.ARROW_DOWN,
				-PlayGame.coolDownMax.get(
					GameConfig.ARROW_DOWN));
		}
		if (inputPoller.isKeyDown(GameConfig.ARROW_UP)
		    && Math.abs(lastCoolDown.get(GameConfig.ARROW_UP)) == 0) {
			if (initials.get(currentInitial)
			    > GameConfig.A_INTEGER) {
				Integer initial = initials.get(currentInitial);
				initials.set(currentInitial, initial - 1);
			} else {
				initials.set(currentInitial,
					     GameConfig.Z_INTEGER);
			}
			PlayGameProcessInputs.updateDtForKey(
				this, GameConfig.ARROW_UP,
				-PlayGame.coolDownMax.get(GameConfig.ARROW_UP));
		}
	}

	/**
	 *  add game over picture
	 */
	public void addGameOverPictureRenderBuffer()
	{
		for (int i = 0; i < menuImageROBuffer.size(); ++i) {
			backgroundBuffer.add(menuImageROBuffer.get(i));
		}
		// only have 1 button
		buttonsBuffer.add(buttonRenderLayer.get(0).getGraphic());
		buttonHitBoxBuffer.add(this.buttonHitBoxROBuffer.get(0));
		pCollisionBodyDebugRenderer(this.buttonHitBoxROBuffer.get(0),
					    collisioBoxBuffer, Color.BLUE);
	}

	/**
	 *  render
	 */
	public void render()
	{
		addGameOverPictureRenderBuffer();

		// adding the text
		renderBuffer.add(new StringRenderObject(
			"" + newScore,
			(int)(super.windowWidth
			      / GameResources.goYourScoreWidthRatio),
			(int)(super.windowHeight
			      / GameResources.goYourScoreHeightRatio),
			Color.RED,
			GameResources.CREEPER_FONT.deriveFont(
				this.yourScoreFontSize)));

		// list of previous high scores
		for (int i = 0; i < 5 && i < scores.size(); ++i) {
			renderBuffer.add(new StringRenderObject(
				scores.get(i).getName() + ":    "
					+ scores.get(i).getScore(),
				(int)(super.windowWidth
				      / GameResources
						.goScoreBoardWidthRatioInitially),
				(int)(super.windowHeight
				      / GameResources
						.goScoreBoardHeightRatioInitially)
					+ (int)(super.windowHeight
						/ GameResources
							  .goScoreBoardGapHeightRatio)
						  * i,
				Color.BLACK, FONT));
		}

		for (int i = 0; i < initialLetters.size(); ++i) {
			backgroundBuffer.add(initialLetters.get(i));
		}


		renderer.renderBuffers(backgroundBuffer, buttonsBuffer,
				       renderBuffer, collisioBoxBuffer);


		// if the loop will quit
		if (super.quit) {
			quit();
			overWriteScoresTextFile();
		}
	}

	/**
	 *  over write scores to file
	 */
	public void overWriteScoresTextFile()
	{
		try {
			FileWriter fw =
				new FileWriter(new File(SCORES_FILE_NAME));
			String tmp = "";
			for (int i = 0; i < scores.size(); i++) {
				if (i != 0)
					tmp += "\n" + scores.get(i).getScore()
					       + "\n" + scores.get(i).getName();
				else
					tmp += scores.get(i).getScore() + "\n"
					       + scores.get(i).getName();
			}

			fw.write(tmp);
			fw.close();

		} catch (IOException e) {
			Logger.logMessage(
				"Error overwriting scores text file.");
			e.printStackTrace();
		}
	}

	/**
	 *  initials to string
	 *  @return String of initials
	 */
	protected String initialsToString()
	{
		String stringOfInitials = "";
		for (int i : initials) {
			String thisInitial = Character.toString((char)i);
			stringOfInitials = stringOfInitials + thisInitial;
		}
		return stringOfInitials;
	}


	/**
	 *  intials to string selection
	 *  @param index : index of string
	 *  @return String of initials
	 */
	protected String initialsToStringSelection(int index)
	{
		String stringOfInitials = "";
		String thisInitial = Character.toString(
			(char)((initials.get(index).intValue())));
		stringOfInitials = stringOfInitials + thisInitial;
		return stringOfInitials;
	}


	/**
	 *  quit
	 */
	public void quit()
	{

		// add new score to the list of high scores
		scores.add(new ScoreTuple(newScore, initialsToString()));

		// re-sort scores into descending order
		Collections.sort(scores);
		Collections.reverse(scores);

		// truncate list of high score back to 10
		while (scores.size() > 10) {
			scores.remove(10);
		}
	}


	/**
	 *  debug renderer
	 *  @param pc : collision body
	 *  @param q : render buf
	 *  @param r : color
	 */
	public void pCollisionBodyDebugRenderer(final PCollisionBody pc,
						Queue<RenderObject> q, Color r)
	{
		Polygon p = pc.getPolygon();
		Vector2f[] pts = p.pts();

		for (int i = 0; i < p.getSize(); ++i) {
			q.add(new RenderRect((int)pts[i].x, (int)pts[i].y, 5, 5,
					     r));
		}
	}

	/**
	 * data type used to store high scores and their
	 * associated player initials
	 * @author Alex
	 */
	private class ScoreTuple implements Comparable<ScoreTuple>
	{
		private String name;
		private int score;

		/**
		 *  Score tuple
		 *  @param defScore : score
		 *  @param string : name
		 */
		public ScoreTuple(int defScore, String defName)
		{
			name = defName;
			score = defScore;
		}

		/**
		 *  get name
		 *  @return string
		 */
		public String getName()
		{
			return name;
		}


		/**
		 *  get score
		 *  @return int
		 */
		public int getScore()
		{
			return score;
		}

		/**
		 *  print
		 */
		public void print()
		{
			System.out.println("Name: " + name
					   + " | Score: " + score);
		}

		/**
		 *  compare to
		 *  @param ScoreTuple : score tuple
		 *  @return - int
		 */
		@Override public int compareTo(ScoreTuple o)
		{
			if (this.getScore() > o.getScore())
				return 1;
			else if (this.getScore() < o.getScore())
				return -1;
			else
				return 0;
		}
	}
}
