package poj.GameWindow;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import java.awt.event.MouseWheelListener;
import java.awt.event.MouseWheelEvent;

// https://docs.oracle.com/javase/7/docs/api/java/awt/event/KeyEvent.html

public class InputPoller implements KeyListener, MouseListener,
				    MouseMotionListener, MouseWheelListener
{

	// for somem reason KeyEvent.KEY_LAST isn't really the last key.
	private static int MAX_KEY = 65535;

	private boolean[] key_input_buffer;

	private int mouseX = 0;
	private int mouseY = 0;

	private boolean left_mouse_is_down = false;
	private boolean right_mouse_is_down = false;

	private int mouse_wheel_notches = 0;

	public InputPoller()
	{
		key_input_buffer = new boolean[MAX_KEY];

		for (int i = 0; i < MAX_KEY; ++i) {
			key_input_buffer[i] = false;
		}
	}

	// tests if a key is down
	// key codes can be found here:
	// https://docs.oracle.com/javase/7/docs/api/java/awt/event/KeyEvent.html
	public boolean isKeyDown(int k)
	{
		return key_input_buffer[k];
	}
	public int getMouseX()
	{
		return this.mouseX;
	}

	public int getMouseY()
	{
		return this.mouseY;
	}

	public boolean isLeftMouseButtonDown()
	{
		return this.left_mouse_is_down;
	}

	public boolean isRightMouseButtonDown()
	{
		return this.right_mouse_is_down;
	}

	// returns numver of notches the ouse wheel was rotated. down (towards
	// th user) is positive, and away (up) is negative
	// WARNING -> Java's mouse api is not amazing and this will always be
	// the last value of the mouse scroll. To get around this, ensure you
	// call "setMouseWheelNotches(0)" at the end of every rame
	public int getMouseWheelNotches()
	{
		return this.mouse_wheel_notches;
	}

	public void setMouseWheelNotches(int n)
	{
		this.mouse_wheel_notches = n;
	}

	/* boiler plate of various key and mouse listeners:*/

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

	private void updateMousePosition(MouseEvent e)
	{

		mouseX = e.getX();
		mouseY = e.getY();
	}

	private void setInputPollerMouseButtons(MouseEvent e, boolean n)
	{
		if (e.getButton() == MouseEvent.BUTTON1) {
			left_mouse_is_down = n;
		} else if (e.getButton() == MouseEvent.BUTTON3) {

			right_mouse_is_down = n;
		}
	}
}
