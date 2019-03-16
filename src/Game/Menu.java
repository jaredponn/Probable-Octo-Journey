package Game;

import java.util.LinkedList;
import java.util.Queue;

import Resources.GameResources;

import poj.Collisions.GJK;
import poj.GameWindow.InputPoller;
import poj.Render.ImageRenderObject;
import poj.Render.RenderObject;
import poj.Render.Renderer;

public class Menu
{
	private Renderer renderer;
	private InputPoller inputPoller;
	protected Queue<RenderObject> titleBuffer =
		new LinkedList<RenderObject>();
	protected Queue<RenderObject> buttons = new LinkedList<RenderObject>();
	protected GJK gjk;
	private ImageRenderObject title;
	private int width, height;
	private ImageRenderObject playButton;
	private MenuState curMenuState = MenuState.mainMenu;

	public Menu(int width, int height, Renderer renderer,
		    InputPoller inputPoller)
	{
		this.width = width;
		this.height = height;
		this.renderer = renderer;
		this.inputPoller = inputPoller;

		title = new ImageRenderObject(0, 0, GameResources.octoTitle);
		playButton =
			new ImageRenderObject(width / 2 - 70, height / 2 - 50,
					      GameResources.playButton);
	}

	public void processInputs()
	{
	}

	public void runGame()
	{
		switch (curMenuState) {
		case mainMenu:
			setMainMenu();
			break;
		case playing:
		case instructions:
		}
		render();
	}

	public void render()
	{
		renderer.renderBuffers(titleBuffer, buttons);
	}
	public void spawnWorld(){};
	public void clearWorld(){};

	public void registerComponents(){};
	public void registerEntitySets(){};

	public void setMainMenu()
	{
		titleBuffer.add(title);
		buttons.add(playButton);
	}
}

enum MenuState {
	mainMenu,
	playing,
	instructions;
}
