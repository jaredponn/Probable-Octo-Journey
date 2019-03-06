package poj.GameWindow;

/**
 * InputPoller  -- a way to poll the input events.
 * Date: February 20, 2019
 * @author  Jared and code from:
 * https://docs.oracle.com/javase/7/docs/api/java/awt/event/KeyEvent.html
 * @version  1.0
 */

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import java.awt.event.MouseWheelListener;
import java.awt.event.MouseWheelEvent;

import poj.linear.Vector2f;

public class InputPoller implements KeyListener, MouseListener,
				    MouseMotionListener, MouseWheelListener
{

	// for some reason KeyEvent.KEY_LAST isn't really the last key and this
	// constant seems to work well. This constant is from this StackOverflow
	// post:
	// https://stackoverflow.com/questions/31357664/java-get-maximum-keyid-from-keyevent
	public static int MAX_KEY = 65535;

	private boolean[] key_input_buffer;

	private int mouseX = 0;
	private int mouseY = 0;

	private boolean left_mouse_is_down = false;
	private boolean right_mouse_is_down = false;

	private int mouse_wheel_notches = 0;

	/**
	 * InputPoller constructor -- constructs the object
	 */
	public InputPoller()
	{
		key_input_buffer = new boolean[MAX_KEY];

		for (int i = 0; i < MAX_KEY; ++i) {
			key_input_buffer[i] = false;
		}
	}

	/**
	 * Tests to see if the key is down. Key code can be found at:
	 * https://docs.oracle.com/javase/7/docs/api/java/awt/event/KeyEvent.html
	 *
	 * @param  k key to check if it is down
	 * @return      boolean to check if it is down
	 */
	public boolean isKeyDown(int k)
	{
		return key_input_buffer[k];
	}

	/**
	 * gets the mouse x position
	 * @return      int of the mouse x position
	 */
	public int getMouseX()
	{
		return this.mouseX;
	}

	/**
	 * gets the mouse y position
	 * @return      int of the mouse y position
	 */
	public int getMouseY()
	{
		return this.mouseY;
	}

	/**
	 * gets the mouse position
	 * @return      Vector2f of the x and y mouse position
	 */
	public Vector2f getMousePosition()
	{
		return new Vector2f(this.getMouseX(), this.getMouseY());
	}


	/**
	 * Tests if the left mouse button is down
	 * @return      boolean -- if the mouse button is down returns true,
	 *         otherwise false.
	 */
	public boolean isLeftMouseButtonDown()
	{
		return this.left_mouse_is_down;
	}

	/**
	 * Tests if the right mouse button is down
	 * @return      boolean -- if the mouse button is down returns true,
	 *         otherwise false.
	 */
	public boolean isRightMouseButtonDown()
	{
		return this.right_mouse_is_down;
	}

	/**
	 * Returns number of notches the mouse wheel was rotated -- down
	 * (towards the user) is positive, and away (up) is negative
	 * WARNING -> Java's mouse api is not amazing and this will
	 * always be the last value of the mouse scroll. To get around
	 * this, ensure you call "setMouseWheelNotches(0)" at the end of
	 * every frame *
	 * @return      int -- the number of whell notches
	 */
	public int getMouseWheelNotches()
	{
		return this.mouse_wheel_notches;
	}


	/**
	 * sets the number of wheel notches to a certain value
	 * @param      n int of the number of wheel notches to set
	 */
	public void setMouseWheelNotches(int n)
	{
		this.mouse_wheel_notches = n;
	}

	/**
	 * Overrided methods
	 boiler plate of various key and mouse listeners:
	 */

	@Override public void keyTyped(KeyEvent e)
	{
	}

	@Override public void keyPressed(KeyEvent e)
	{
		key_input_buffer[e.getKeyCode()] = true;
	}

	@Override public void keyReleased(KeyEvent e)
	{
		key_input_buffer[e.getKeyCode()] = false;
	}

	@Override public void mouseClicked(MouseEvent e)
	{
		updateMousePosition(e);
	}

	@Override public void mousePressed(MouseEvent e)
	{
		setInputPollerMouseButtons(e, true);
		updateMousePosition(e);
	}

	@Override public void mouseReleased(MouseEvent e)
	{
		setInputPollerMouseButtons(e, false);
		updateMousePosition(e);
	}
	@Override public void mouseMoved(MouseEvent e)
	{
		updateMousePosition(e);
	}

	@Override public void mouseDragged(MouseEvent e)
	{
		updateMousePosition(e);
	}

	@Override public void mouseExited(MouseEvent e)
	{
		updateMousePosition(e);
	}

	@Override public void mouseEntered(MouseEvent e)
	{
		updateMousePosition(e);
	}

	@Override public void mouseWheelMoved(MouseWheelEvent e)
	{
		this.mouse_wheel_notches = e.getWheelRotation();
	}

	/**
	 * Updates the mouse position
	 * @param e -- mouse event
	 */
	private void updateMousePosition(MouseEvent e)
	{

		mouseX = e.getX();
		mouseY = e.getY();
	}

	/**
	 * Sets the input poller mouse buttons
	 * @param e mouse event
	 * @param n boolean
	 */
	private void setInputPollerMouseButtons(MouseEvent e, boolean n)
	{
		if (e.getButton() == MouseEvent.BUTTON1) {
			left_mouse_is_down = n;
		} else if (e.getButton() == MouseEvent.BUTTON3) {

			right_mouse_is_down = n;
		}
	}
}
