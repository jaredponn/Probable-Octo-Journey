package Game;

public interface EntityAttackSetHandler {
	public PlayGameEvent startingHandler(PlayGame g, int focus);
	public default PlayGameEvent primerHandler(PlayGame g, int focus)
	{
		return new SetSpeedToZeroEvent(g, focus);
	}
	public PlayGameEvent attackHandler(PlayGame g, int focus);

	public default PlayGameEvent recoilHandler(PlayGame g, int focus)
	{
		return new SetSpeedToZeroEvent(g, focus);
	}
	public PlayGameEvent endAttackHandler(PlayGame g, int focus);
}
