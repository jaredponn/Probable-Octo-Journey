package poj.GameWindow;

import javax.swing.JFrame;

public class GameWindow extends JFrame
{
	static final long serialVersionUID = 0;

	/**
	 * Constructor for the game window
	 *
	 * @param  s  name of the window
	 */
	public GameWindow(String s)
	{
		super(s);

		this.setResizable(false);
		this.setIgnoreRepaint(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * Default add of game canbas and setting buffer strategy to the game
	 * window
	 *
	 * @param  gc  game canvas
	 */
	public void defaultAddGameCanvasAndSetBufferStrat(GameCanvas gc)
	{
		this.add(gc);
		this.pack();
		this.setVisible(true);
		gc.createBufferStrategy(2);
	}


	/**
	 * Disposes the window
	 */
	public void disposeWindow()
	{
		this.setVisible(false);
		this.dispose();
	}
}
