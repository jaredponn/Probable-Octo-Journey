package Game.GameEvents;

/**
 * PauseGame -- because the TA wanted it
 * @author Jared Pon
 * @version 03/09/19 v1.0
 */

import java.util.ArrayList;
import Game.PlayGame;
import poj.Time.*;
import poj.Render.*;
import Game.PlayGameProcessInputs;
import Resources.GameConfig;

public class PauseGameEvent extends PlayGameEvent
{

	private static int SLEEP_DURATION = 5;

	/**
	 * constructor
	 * @param g: play game
	 */
	public PauseGameEvent(PlayGame g)
	{
		super(g);
	}

	/**
	 * event
	 */
	public void f()
	{
		PlayGame g = super.getPlayGame();
		g.getRenderThread().stopRendering();

		while (true) {
			ArrayList<RenderObject> tmp =
				new ArrayList<RenderObject>();
			tmp.add(new StringRenderObject(
				"GAME PAUSED -- PRESS P TO UNPAUSE",
				(int)(g.getWindowWidth() * 0.5) - 425,
				(int)(g.getWindowHeight() * 0.5),
				GameConfig.HUD_FONT_COLOR,
				GameConfig.HUD_FONT_HUGE));

			tmp.add(new StringRenderObject(
				"DON'T WORRY - THE ZOMBIES AREN'T KILLING YOU",
				(int)(g.getWindowWidth() * 0.5) - 425,
				(int)(g.getWindowHeight() * 0.5) + 50,
				GameConfig.HUD_FONT_COLOR,
				GameConfig.HUD_FONT));

			tmp.add(new StringRenderObject(
				"ENJOY THE NICE MUSIC :)",
				(int)(g.getWindowWidth() * 0.5) - 425,
				(int)(g.getWindowHeight() * 0.5) + 100,
				GameConfig.HUD_FONT_COLOR,
				GameConfig.HUD_FONT));

			g.getRenderer().renderBufferLists(tmp);

			if (g.getInputPoller().isKeyDown(GameConfig.PAUSE_GAME)
			    && Math.abs(g.getLastCoolDown().get(
				       GameConfig.PAUSE_GAME))
				       == 0d) {
				break;
			}

			Timer.sleepNMilliseconds(SLEEP_DURATION);

			PlayGameProcessInputs.updateDtForKey(
				g, GameConfig.PAUSE_GAME, g.getDt() / 1000);
		}

		g.getRenderThread().startRendering();
	}
}
