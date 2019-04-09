package Game.GameEvents;

import java.util.Stack;

public class PlayGameEventStack extends Stack<PlayGameEvent>
{
	private static final long serialVersionUID = 0l;

	public void runGameEventStack()
	{

		while (!this.isEmpty()) {
			PlayGameEvent tmp = this.pop();
			tmp.f();
		}
	}
}
