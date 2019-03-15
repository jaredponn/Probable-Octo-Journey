package Game;

public interface PlayGameEvent {

	/**
	 * Interface for a game event
	 * @param g: godly game state
	 * @param es: VA args fo entites to do transforms on
	 */
	public void f(PlayGame g, int... es);
}
