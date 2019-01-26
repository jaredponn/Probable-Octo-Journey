import Game.Game;

public class Main
{

	public static final void main(String[] args)
	{
		Game g = new Game();
		g.initGame();
		g.runGameLoop();
		g.disposeWindow();
	}
}
