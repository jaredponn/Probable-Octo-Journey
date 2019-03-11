package Components;

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
	}

	public void hurt(int amount)
	{
		this.hp -= amount;
	}

	public void print()
	{
		System.out.println("HP: " + this.hp);
	}
}
