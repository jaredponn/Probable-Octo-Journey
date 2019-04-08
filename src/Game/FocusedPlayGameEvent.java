package Game;

public abstract class FocusedPlayGameEvent extends PlayGameEvent
{

	protected int focus;

	/**
	 * Constructor
	 * @param g: non owning pointer to the game state
	 * @param e: entity to be focused on
	 */
	public FocusedPlayGameEvent()
	{
		super();
	}

	public FocusedPlayGameEvent(PlayGame g)
	{
		super(g);
	}
	public FocusedPlayGameEvent(PlayGame g, int e)
	{
		super(g);
		this.focus = e;
	}


	public void setFocus(int f)
	{
		focus = f;
	}
}
