package Game.GameEvents;
/**
 * PlayGameEventStack.
 * @author Jared Pon
 * @version 03/09/19 v1.0
 */

import java.util.Stack;

public class PlayGameEventStack extends Stack<PlayGameEvent>
{
	private static final long serialVersionUID = 0l;

	/**
	 * runs the game event stack
	 */
	public void runGameEventStack()
	{

		while (!this.isEmpty()) {
			PlayGameEvent tmp = this.pop();
			tmp.f();
		}
	}
}
