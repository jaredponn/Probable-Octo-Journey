package Game;

/**
 * GameOver game state. UNUSED AND UNIMPLEMENTED
 * Date: March 10, 2019
 * @author Jared
 * @version 1.0
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import Components.PCollisionBody;
import Components.PhysicsPCollisionBody;
import Resources.*;
import App.*;

import poj.Collisions.GJK;
import poj.Collisions.Polygon;
import poj.GameWindow.InputPoller;
import poj.Render.ImageRenderObject;
import poj.Render.StringRenderObject;
import poj.Render.RenderObject;
import poj.Logger.*;
import poj.Render.RenderRect;
import poj.Render.Renderer;
import poj.linear.Vector2f;
import poj.Time.*;

import java.io.*;
import java.util.*;
import java.util.ArrayList;


public class GameOver extends World
{
	protected int newScore;
	protected ArrayList<Integer> scores;
	protected boolean isHighScore;

	protected Queue<RenderObject> renderBuffer;

	protected static final String SCORES_FILE_NAME = "scores.txt";
	protected static final int FONT_SIZE = 32;
	protected static final Font FONT =
		new Font("TimesRoman", Font.BOLD, FONT_SIZE);

	public GameOver(int width, int height, Renderer renderer,
			InputPoller inputPoller, int newScore)
	{
		super(width, height, renderer, inputPoller);
		this.newScore = newScore;
		this.renderBuffer = new LinkedList<RenderObject>();

		this.scores = new ArrayList<Integer>();

		Scanner is = null;
		try {
			is = new Scanner(new File(SCORES_FILE_NAME));
		} catch (FileNotFoundException e) {
			Logger.lassert("error while opening file "
				       + SCORES_FILE_NAME);
		}

		while (is.hasNextLine()) {
			String line = is.nextLine();
			scores.add(Integer.parseInt(line));
		}

		this.isHighScore = true;
		for (Integer i : scores) {
			if (this.newScore <= i) {
				this.isHighScore = false;
				break;
			}
		}

		scores.add(newScore);

		for (Integer i : scores) {
			System.out.println(i);
		}

		Collections.sort(this.scores);
		Collections.reverse(this.scores);

		overWriteScoresTextFile();
	}

	public void runGame()
	{
		poj.Time.Timer.sleepNMilliseconds(10);

		renderBuffer.add(new StringRenderObject(
			"HIGH SCORES", super.windowWidth / 2 - 100,
			4 * FONT_SIZE, Color.darkGray, FONT));

		renderBuffer.add(new StringRenderObject(
			"YOUR SCORE", super.windowWidth / 2 - 90, 5 * FONT_SIZE,
			Color.darkGray, FONT));

		renderBuffer.add(new StringRenderObject(
			"" + newScore, super.windowWidth / 2, 6 * FONT_SIZE,
			Color.darkGray, FONT));

		renderBuffer.add(new StringRenderObject(
			"ZOMBIES SLAIN", super.windowWidth / 2 - 100,
			7 * FONT_SIZE, Color.darkGray, FONT));

		if (isHighScore)
			renderBuffer.add(new StringRenderObject(
				"CONGRATS NEW HIGH SCORE",
				super.windowWidth / 2 - 200, 8 * FONT_SIZE,
				Color.darkGray, FONT));

		renderBuffer.add(new StringRenderObject(
			"OTHER SCORES", super.windowWidth / 2 - 100,
			9 * FONT_SIZE, Color.darkGray, FONT));

		for (int i = 0; i < 5 && i < scores.size(); ++i) {
			renderBuffer.add(new StringRenderObject(
				(i + 1) + ": " + scores.get(i),
				super.windowWidth / 2 - 20,
				(i + 10) * FONT_SIZE, Color.darkGray, FONT));
		}

		renderBuffer.add(new StringRenderObject(
			"PRESS SPACE TO GO BACK TO THE MENU",
			super.windowWidth / 2 - 350,
			super.windowHeight - FONT_SIZE - 20, Color.darkGray,
			FONT));

		this.processInputs();
	}

	public void processInputs()
	{
		if (inputPoller.isKeyDown(KeyEvent.VK_ENTER))
			quit();
	}


	public void render()
	{
		renderer.renderBuffers(renderBuffer);
	}

	public void overWriteScoresTextFile()
	{
		try {
			FileWriter fw =
				new FileWriter(new File(SCORES_FILE_NAME));
			String tmp = "";
			for (Integer i : this.scores) {
				tmp += i + "\n";
			}
			fw.write(tmp);
			fw.close();

		} catch (IOException e) {
			Logger.logMessage(
				"Error overwriting scores text file.");
			e.printStackTrace();
		}
	}
}
