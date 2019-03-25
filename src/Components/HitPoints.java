package Components;

import Resources.GameConfig;

/**
 * HitPoints. HitPoints Component
 *
 * Date: March 10, 2019
 * @author Alex Stark
 * @version 1.0
 */

import poj.Component.Component;

public class HitPoints implements Component
{

	private int hp;

	public HitPoints(int amount)
	{
		this.hp = amount;
	}

	public int getHP()
	{
		return this.hp;
	}

	public void setHP(int amount)
	{
		this.hp = amount;
	}

	public void heal(int amount)
	{
		this.hp += amount;
		if (this.hp > GameConfig.PLAYER_MAX_HP)
			this.hp = GameConfig.PLAYER_MAX_HP;
	}

	public void hurt(int amount)
	{
		this.heal(-amount);
	}

	public void print()
	{
		System.out.println("HP: " + this.hp);
	}
}
