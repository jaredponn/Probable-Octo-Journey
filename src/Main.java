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
