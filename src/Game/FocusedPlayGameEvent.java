package Game;

public abstract class FocusedPlayGameEvent extends PlayGameEvent
{

	protected int focus1;

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
		this.focus1 = e;
	}


	public void setFocus1(int f)
	{
		focus1 = f;
	}
}
