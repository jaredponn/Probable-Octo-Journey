package Game;

public class TurretOutOfHPEvent extends FocusedPlayGameEvent
{
	public TurretOutOfHPEvent(PlayGame g)
	{
		super(g);
	}

	public void f()
	{
		getPlayGame().getEngineState().deleteAllComponentsAt(focus);
	}
}
