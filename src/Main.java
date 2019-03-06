/**
 * Main -- main class of the game.
 * Date: February 10, 2019
 * @author Jared
 * @version 1.0
 */
public class Main
{
	/**
	 * main
	 * main method of the game -- runs the app
	 * @return      void
	 */
	public static final void main(String[] args)
	{
		App app = new App();
		app.runAppLoop();
		app.disposeWindow();
	}
}
