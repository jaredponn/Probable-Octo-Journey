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
		map.addMapConfig("resources/map1Config.json");

		// map.addMapLayer("resources/testMap.csv");
		map.addMapLayer("resources/map1_ground.csv");
		map.addMapLayer("resources/map1_not_ground.csv");
		map.addMapLayer("resources/map1_roof.csv");
		// map.printMapLayers();
		map.addTileSet("resources/tiles1.json");

		Game g = new Game();
		g.runGameLoop();
		g.disposeWindow();
	}
}
