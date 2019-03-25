package Game;

public interface EntityAttackSetHandler {
	public PlayGameEvent primerHandler(PlayGame g, int focus);
	public PlayGameEvent attackHandler(PlayGame g, int focus);
	public PlayGameEvent recoilHandler(PlayGame g, int focus);
}
