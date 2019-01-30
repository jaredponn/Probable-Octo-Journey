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
import TileMap.Map;
import java.io.FileNotFoundException;

import java.awt.*;
import java.awt.event.KeyEvent;
import Resources.GameResources;
import Game.App;

public class Main
{

	public static final void main(String[] args)
		throws FileNotFoundException
	{

		/*
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
		*/

		App g = new App();
		g.runAppLoop();
		g.disposeWindow();
	}
}
