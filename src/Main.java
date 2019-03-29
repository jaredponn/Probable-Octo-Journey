/**
 * Main -- main class of the game.
 v* Date: February 10, 2019
 * @author Jared
 * @version 1.0
 */
import App.App;

import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Main
{
	/**
	 * main
	 * main method of the game -- runs the app
	 * @return      void
	 */
	public static final void main(String[] args)
		throws UnsupportedAudioFileException, IOException,
		       LineUnavailableException
	{
		App app = new App();
		app.runAppLoop();
		app.disposeWindow();
	}
}
