package Game;

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

/**
 * GameOver game state.
 * @author Jared and Alex
 * @version 2.0 - 04/03/19
 */
public class GameOver extends World
{
	protected int newScore;
	protected ArrayList<ScoreTuple> scores = new ArrayList<ScoreTuple>();
	protected boolean isHighScore;

	protected Queue<RenderObject> renderBuffer;

	protected static final String SCORES_FILE_NAME = "scores.txt";
	protected static final int FONT_SIZE = 32;
	protected static final Font FONT =
		new Font("TimesRoman", Font.BOLD, FONT_SIZE);
	
	protected ArrayList<Integer> initials = new ArrayList<Integer>() {
		{
			add(65);
			add(65);
			add(65);
		}
	};
	protected int currentInitial = 0;
	protected StringRenderObject initialRender = new StringRenderObject(
				initialsToString(),30,30,Color.DARK_GRAY,FONT);
	
	protected Scanner is = null;
	

	public GameOver(int width, int height, Renderer renderer,
			InputPoller inputPoller, int newScore)
	{
		super(width, height, renderer, inputPoller);
		this.newScore = newScore;
		this.renderBuffer = new LinkedList<RenderObject>();
		
		try {
			is = new Scanner(new File(SCORES_FILE_NAME));
			is.close();
		} catch (FileNotFoundException e) {
			System.out.println("Cannot find file: " + SCORES_FILE_NAME);
			try {
				PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(SCORES_FILE_NAME)));
				writer.write(""+ 0 + "\nAAA");
				for ( int i = 0 ; i < 9 ; i++ ) {
	            	writer.write("\n"+ 0+"\nAAA");
	            }
	            writer.close();
			}
			catch (IOException ioe) {
				Logger.lassert("Cannot create file: "
					       + SCORES_FILE_NAME);
			}
		}
		
		try {
			is = new Scanner(new File(SCORES_FILE_NAME));
			String thisName = "";
			int thisScore = 0;
			while (is.hasNextLine()) {
				thisScore = Integer.parseInt(is.nextLine());
				thisName = is.nextLine();
				scores.add(new ScoreTuple(thisScore , thisName));
			}
			is.close();
		}
		catch (IOException ioe) {
			Logger.lassert("Cannot load high score info");
		}
		
		this.isHighScore = true;
		for (ScoreTuple t : scores) {
			if (this.newScore <= t.getScore()) {
				this.isHighScore = false;
				break;
			}
		}
		
		Collections.sort(scores);
		Collections.reverse(scores);
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
				scores.get(i).getName() +": " + scores.get(i).getScore(),
				super.windowWidth / 2 - 20,
				(i + 10) * FONT_SIZE, Color.darkGray, FONT));
		}

		renderBuffer.add(new StringRenderObject(
			"PRESS ENTER TO GO BACK TO THE MENU",
			super.windowWidth / 2 - 350,
			super.windowHeight - FONT_SIZE - 20, Color.darkGray,
			FONT));

		renderBuffer.add(initialRender);

		this.processInputs();
	}

	public void processInputs()
	{
		if (inputPoller.isKeyDown(KeyEvent.VK_ENTER))
			quit();
		
		// Choose letter
		if (inputPoller.isKeyDown(KeyEvent.VK_UP) ) {
			if (initials.get(currentInitial) < 90) {
				Integer initial = initials.get(currentInitial);
				initials.set(currentInitial, initial + 1);
			}
			else {
				initials.set(currentInitial, 65);
			}
			initialRender.setStr(initialsToString());
		}
		if (inputPoller.isKeyDown(KeyEvent.VK_DOWN) ) {
			if (initials.get(currentInitial) > 65) {
				Integer initial = initials.get(currentInitial);
				initials.set(currentInitial, initial - 1);
			}
			else {
				initials.set(currentInitial , 90 );
			}
			initialRender.setStr(initialsToString());
		}
		// Choose slot
		if (inputPoller.isKeyDown(KeyEvent.VK_LEFT)) {
			if (currentInitial > 0)
				currentInitial--;
		}
		if (inputPoller.isKeyDown(KeyEvent.VK_RIGHT)) {
			if (currentInitial < 2)
				currentInitial++;
		}
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
			for (int i = 0 ; i < scores.size() ; i ++) {
				if (i != 0)
					tmp += "\n" + scores.get(i).getScore() + "\n" + scores.get(i).getName();
				else
					tmp += scores.get(i).getScore() + "\n" + scores.get(i).getName();
			}
			fw.write(tmp);
			fw.close();

		} catch (IOException e) {
			Logger.logMessage(
				"Error overwriting scores text file.");
			e.printStackTrace();
		}
	}
	
	protected String initialsToString() {
		String stringOfInitials = "";
		for (int i : initials) {
			String thisInitial = Character.toString((char) i);
			stringOfInitials = stringOfInitials + thisInitial;
		}
		return stringOfInitials;
	}
	
	public void quit() {
		
		scores.add(new ScoreTuple(newScore,initialsToString()));

		Collections.sort(scores);
		Collections.reverse(scores);
		
		while (scores.size() > 10) {
			scores.remove(10);
		}

		overWriteScoresTextFile();
		super.quit();
	}
	
	private class ScoreTuple implements Comparable<ScoreTuple>{
		 private String name;
		 private int score;
		 
		 public ScoreTuple( int defScore , String defName ) {
			 name = defName;
			 score = defScore;
		 }
		 
		 public String getName() {
			 return name;
		 }
		 
		 public int getScore() {
			 return score;
		 }
		 
		 public void print() {
			 System.out.println("Name: "+name + " | Score: "+score);
		 }
		 
		 @Override
		 public int compareTo(ScoreTuple o) {
			 if (this.getScore() > o.getScore() )
				 return 1;
			 else if (this.getScore() < o.getScore() )
				 return -1;
			 else
				 return 0;
		 }
	}
}
