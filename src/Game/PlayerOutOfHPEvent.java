package Game;

public class PlayerOutOfHPEvent extends FocusedPlayGameEvent
{

	public PlayerOutOfHPEvent(PlayGame g)
	{
		super(g);
	}

	public void f()
	{
		System.out.println("Player is dead");
	}
}
