package Components;

import poj.Component.Component;
import poj.Render.*;
import java.util.ArrayList;
import java.awt.Color;

/**
 * A component for entities that have hit points
 * @author Alex
 * @version 2.0 - 03/31/19
 */
public class HitPoints
	implements Component, GUIStringDisplayable, GUIGraphicDisplayable
{

	private int hp;
	private int maxHP;

	/**
	 * initialize current and max hit points
	 * @param amount: current hit points
	 * @param max: maximum amount of hit points
	 */
	public HitPoints(int amount, int max)
	{
		this.hp = amount;
		this.maxHP = max;
	}

	/**
	 * gets the current amount of hit points
	 * @return current hit points
	 */
	public int getHP()
	{
		return this.hp;
	}

	/**
	 * gets the maximum amount of hit points
	 * @return maximum hit points
	 */
	public int getMax()
	{
		return this.maxHP;
	}

	/**
	 * sets the current hit points
	 * @param amount: the total number of hit points
	 * 				  this entity will have (cannot exceed
	 * maxHP)
	 */
	public void setHP(int amount)
	{
		this.hp = amount;
		if (this.hp > this.maxHP)
			this.hp = this.maxHP;
	}

	/**
	 * increase the current hit points by an amount
	 * @param amount: amount to increase by (cannot exceed maxHP)
	 */
	public void heal(int amount)
	{
		this.hp += amount;
		if (this.hp > this.maxHP)
			this.hp = this.maxHP;
	}

	/**
	 * decrease the current hit points by an amount
	 * @param amount: heals for the negative of this amount
	 * 				  (cannot exceed maxHP)
	 */
	public void hurt(int amount)
	{
		this.heal(-amount);
	}

	public String getFormattedString()
	{
		return hp + "/" + maxHP;
	}


	/**
	 * Static render generator -- please remove this, very bad idea.
	 */
	private static int HP_BAR_MAX_WIDTH = 30;
	private static int HP_BAR_HEIGHT = 5;
	private static Color HP_BAR_BG_COLOR = new Color(244, 170, 66);
	private static Color HP_BAR_HP_COLOR = new Color(244, 74, 65);
	public ArrayList<RenderObject> getRenderObjectGraphics()
	{
		ArrayList<RenderObject> tmp = new ArrayList<RenderObject>();
		tmp.add(new RenderRect(0, 0, HP_BAR_MAX_WIDTH, HP_BAR_HEIGHT,
				       HP_BAR_BG_COLOR));
		tmp.add(new RenderRect(0, 0,
				       (int)((float)HP_BAR_MAX_WIDTH * (float)hp
					     / (float)maxHP),
				       HP_BAR_HEIGHT, HP_BAR_HP_COLOR));
		return tmp;
	}


	/**
	 * prints this entity's current hit points
	 */
	public void print()
	{
		System.out.println("HP: " + this.hp);
	}
}
