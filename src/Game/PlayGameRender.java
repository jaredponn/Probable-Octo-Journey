package Game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import Components.*;
import EntitySets.PlayerSet;
import Resources.GameResources;
import TileMap.MapLayer;

import poj.Render.*;
import poj.EngineState;
import poj.Component.*;
import poj.Time.*;
import poj.Render.RenderObject;
import poj.Render.StringRenderObject;
import poj.linear.Vector2f;

import Resources.*;

import java.awt.Color;


public class PlayGameRender
{

	private static RenderObjectComparator renderObjComp =
		new RenderObjectComparator();

	public static void renderPlayGame(PlayGame g)
	{

		// TODO -- this should be moved to the tile map so it is loaded
		// there.
		Vector2f origin =
			new Vector2f(g.map.mapWidth / 2, -g.map.mapHeight / 2);
		origin.matrixMultiply(g.getCam());
		g.groundBuffer.add(new ImageRenderObject(
			(int)origin.x, (int)origin.y,
			GameResources.TILE_MAP_SINGLE_IMAGE));

		// pushTileMapLayerToArrayList(map.getLayerEngineState(0),
		// groundBuffer);
		pushTileMapLayerToArrayList(
			g, g.getMap().getLayerEngineState(1), g.groundBuffer);
		pushTileMapLayerToArrayList(
			g, g.getMap().getLayerEngineState(2), g.entityBuffer);
		pushTileMapLayerToArrayList(
			g, g.getMap().getLayerEngineState(3), g.entityBuffer);
		pushTileMapLayerToArrayList(
			g, g.getMap().getLayerEngineState(4), g.entityBuffer);


		for (Render r :
		     g.getEngineState().getRawComponentArrayListPackedData(
			     Render.class)) {
			Systems.cullPushRenderComponent(r, g.entityBuffer,
							g.windowWidth,
							g.windowHeight);
		}

		addHPBarsToBuffer(g, g.guiBuffer);
		addGameGUIBuffers(g);


		Collections.sort(g.entityBuffer, renderObjComp);

		while (g.renderThread.isRendering()) {
			Timer.sleepNMilliseconds(1);
		}
		g.renderThread.swapBuffers();

		g.renderThread.startRendering();

		g.updateRenderWriteToBufferToUnfocusedBuffer();
	}

	private static void addGameGUIBuffers(PlayGame g)
	{
		int player =
			g.getEngineState().getInitialSetIndex(PlayerSet.class);

		// magic int for how far to place the strings on the left
		int leftAlignVal = 20;
		int rightAlignVal = g.getWindowWidth() - 500;

		// game timer
		g.guiBuffer.add(new StringRenderObject(
			"" + g.getPlayTime(), leftAlignVal,
			GameConfig.HUD_LINE_SPACING * 1, Color.WHITE,
			GameConfig.HUD_FONT));
		// money
		g.guiBuffer.add(new StringRenderObject(
			"Money: "
				+ getGUIStringDisplayableComponent(
					  g.getEngineState(), player,
					  Money.class),
			rightAlignVal, GameConfig.HUD_LINE_SPACING * 20,
			GameConfig.HUD_FONT_COLOR, GameConfig.HUD_FONT));
		// health
		g.guiBuffer.add(new StringRenderObject(
			"Health: "
				+ getGUIStringDisplayableComponent(
					  g.getEngineState(), player,
					  HitPoints.class),
			rightAlignVal, GameConfig.HUD_LINE_SPACING * 3,
			GameConfig.HUD_FONT_COLOR, GameConfig.HUD_FONT));
		// ammo
		g.guiBuffer.add(new StringRenderObject(
			"Ammo: "
				+ getGUIStringDisplayableComponent(
					  g.getEngineState(), player,
					  Ammo.class),
			rightAlignVal, GameConfig.HUD_LINE_SPACING * 4,
			GameConfig.HUD_FONT_COLOR, GameConfig.HUD_FONT));
		// damage bonus
		g.guiBuffer.add(new StringRenderObject(
			"Damage : "
				+ getGUIStringDisplayableComponent(
					  g.getEngineState(), player,
					  Damage.class),
			rightAlignVal, GameConfig.HUD_LINE_SPACING * 5,
			GameConfig.HUD_FONT_COLOR, GameConfig.HUD_FONT));
		// zombies slain
		g.guiBuffer.add(new StringRenderObject(
			"Zombies slain: "
				+ getGUIStringDisplayableComponent(
					  g.getEngineState(), player,
					  KillCount.class),
			rightAlignVal, GameConfig.HUD_LINE_SPACING * 6,
			GameConfig.HUD_FONT_COLOR, GameConfig.HUD_FONT));

		g.guiBuffer.add(new StringRenderObject(
			"Zombies chasing" + g.getMobsSpawned(), 5,
			GameConfig.HUD_LINE_SPACING * 7,
			GameConfig.HUD_FONT_COLOR, GameConfig.HUD_FONT));
	}
	private static void addHPBarsToBuffer(PlayGame g,
					      ArrayList<RenderObject> arr)
	{
		EngineState engineState = g.getEngineState();

		for (int i = engineState.getInitialSetIndex(HitPoints.class);
		     engineState.isValidEntity(i);
		     i = engineState.getNextSetIndex(HitPoints.class, i)) {

			Optional<PHitBox> wcOpt =
				engineState.getComponentAt(PHitBox.class, i);

			if (!wcOpt.isPresent())
				continue;

			PHitBox wc = wcOpt.get();

			HitPoints hp = engineState.unsafeGetComponentAt(
				HitPoints.class, i);

			ArrayList<RenderObject> tmp =
				hp.getRenderObjectGraphics();

			// putting the render oject at the right location
			int maxWidth = tmp.get(0).getWidth();
			for (RenderObject j : tmp) {
				Vector2f npos =
					wc.getCenter().pureMatrixMultiply(
						g.getCam());
				npos.x -= maxWidth / 2;
				npos.y -= 20
					  * wc.getPolygon()
						    .getHeight(); // slightly
								  // magical
								  // constant to
								  // get it to
								  // just render
								  // at the
								  // right spot
				npos.y -= j.getHeight();
				j.setPosition(npos);
			}

			arr.addAll(tmp);
		}
	}

	private static <T extends Component & GUIStringDisplayable>
		String getGUIStringDisplayableComponent(EngineState engineState,
							int focus,
							Class<T> type)
	{

		Optional<T> compOpt = engineState.getComponentAt(type, focus);
		if (compOpt.isPresent())
			return compOpt.get().getFormattedString();
		else
			return "";
	}


	protected static void
	pushTileMapLayerToArrayList(PlayGame g, MapLayer n,
				    ArrayList<RenderObject> q)
	{
		EngineTransforms.pushTileMapLayerToQueue(
			g.getMap(), n, g.windowWidth, g.windowHeight,
			(int)GameResources.TILE_SCREEN_WIDTH,
			(int)GameResources.TILE_SCREEN_HEIGHT, g.getCam(),
			g.getInvCam(), q);
	}
}
