import Components.Render;
import Components.Physics;

import poj.EngineState;
import poj.GameWindow.*;
import poj.Render.*;
import poj.Time.*;
import poj.linear.*;
import poj.Animation;
import Components.*;
import EntitySets.*;
import poj.EngineState;
import TileMap.MapRender;
import java.io.FileNotFoundException;

import java.awt.*;
import java.awt.event.KeyEvent;
import Resources.GameResources;
import Game.Game;

public class Main
{

	public static final void main(String[] args)
		throws FileNotFoundException
	{

		MapRender map = new MapRender();
		map.addMapConfig(GameResources.mapConfig);

		// map.addMapLayer("resources/testMap.csv");
		map.addMapLayer(GameResources.mapLayer0);
		map.addMapLayer(GameResources.mapLayer1);
		map.addMapLayer(GameResources.mapLayer2);
		// map.printMapLayers();
		map.addTileSet(GameResources.tileSet);
		// TODO add ECS for map!!!!
		EngineState mapECS = new EngineState();
		mapECS.registerComponent(Render.class);


		Game g = new Game();
		g.runGameLoop();
		g.disposeWindow();
	}
}
