import java.io.FileNotFoundException;

public class Main
{

	public static final void main(String[] args)
		throws FileNotFoundException
	{
		App app = new App();
		app.runAppLoop();
		app.disposeWindow();
	}
}
