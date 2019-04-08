package Game;

import java.util.ArrayList;
import java.util.Collections;

import Components.*;
import Resources.GameResources;
import TileMap.MapLayer;

import poj.Render.*;
import poj.Time.*;
import poj.Render.RenderObject;
import poj.Render.StringRenderObject;
import poj.linear.Vector2f;


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
			g, g.getMap().getLayerEngineState(2), g.buildingBuffer);
		pushTileMapLayerToArrayList(
			g, g.getMap().getLayerEngineState(3), g.entityBuffer);
		pushTileMapLayerToArrayList(
			g, g.getMap().getLayerEngineState(4), g.entityBuffer);


		for (Render r :
		     g.getRawComponentArrayListPackedData(Render.class)) {
			Systems.cullPushRenderComponent(r, g.entityBuffer,
							g.windowWidth,
							g.windowHeight);
		}

		g.guiBuffer.add(new StringRenderObject(g.gameTimer));
		g.guiBuffer.add(new StringRenderObject(g.cashDisplay));
		g.guiBuffer.add(new StringRenderObject(g.healthDisplay));
		g.guiBuffer.add(new StringRenderObject(g.ammoDisplay));
		g.guiBuffer.add(new StringRenderObject(g.killDisplay));
		g.guiBuffer.add(new StringRenderObject(g.mobCountDisplay));
		g.guiBuffer.add(new StringRenderObject(g.damageBonusDisplay));

		Collections.sort(g.entityBuffer, renderObjComp);

		while (g.renderThread.isRendering()) {
			Timer.sleepNMilliseconds(1);
		}
		g.renderThread.swapBuffers();

		g.renderThread.startRendering();

		g.updateRenderWriteToBufferToUnfocusedBuffer();
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
