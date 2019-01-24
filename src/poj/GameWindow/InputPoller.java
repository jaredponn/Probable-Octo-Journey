package poj.GameWindow;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

// https://docs.oracle.com/javase/7/docs/api/java/awt/event/KeyEvent.html

public class InputPoller implements KeyListener
{

	private boolean[] key_input_buffer;
	// for somem reason KeyEvent.KEY_LAST isn't really the last key.
	private static int MAX_KEY = 65535;

	public InputPoller()
	{
		key_input_buffer = new boolean[MAX_KEY];

		for (int i = 0; i < MAX_KEY; ++i) {
			key_input_buffer[i] = false;
		}
	}

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

	public boolean isKeyDown(int k)
	{
		return key_input_buffer[k];
	}
}
