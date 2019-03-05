package poj.GameWindow;

/**
 * GameWindow -- the window for the game
 * Date: February 20, 2019
 * @author  Jared and parts of the code were taken from:
 * https://examples.javacodegeeks.com/desktop-java/swing/jframe/java-jframe-example/
 * https://javatutorial.net/swing-jframe-basics-create-jframe
 * https://www.javatpoint.com/java-awt-canvas
 * @version  1.0
 */

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
