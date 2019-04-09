package Components;

import poj.Component.Component;

/**
 * component for entities that use ammo
 * @author Alex
 * @version 2.0 - 03/31/19
 */
public class Ammo implements Component, GUIStringDisplayable
{

	private int ammo;
	private int max;

	/**
	 * initialize current and max ammo amounts
	 * @param amount: current amount of ammo
	 * @param defMax: maximum amount of ammo
	 */
	public Ammo(int amount, int defMax)
	{
		this.ammo = amount;
		this.max = defMax;
	}

	/**
	 * get the current amount of ammo
	 * @return: the current amount of ammo
	 */
	public int get()
	{
		return this.ammo;
	}

	/**
	 * get the maximum amount of ammo
	 * @return: the maximum amount of ammo
	 */
	public int getMax()
	{
		return this.max;
	}

	/**
	 * checks if there is at least a certain amount of ammo
	 * @param amount: check if there is at least this much ammo
	 * @return true if there is enough ammo
	 */
	public boolean hasAmmo(int amount)
	{
		if (this.ammo >= amount)
			return true;
		else
			return false;
	}

	/**
	 * sets the current amount to a specified amount
	 * @param amount: the total amount of current ammo
	 */
	public void setAmmo(int amount)
	{
		this.ammo = amount;
	}

	/**
	 * increase the current ammo by a specified amount (cannot exceed max)
	 * @param amount: the amount to increase current ammo by
	 */
	public void increaseAmmo(int amount)
	{
		this.ammo += amount;
		if (this.ammo > this.max)
			this.ammo = this.max;
	}

	/**
	 * decrease the current ammo by a specified amount
	 * @param amount: increases ammo by the negative of this value (cannot
	 *         exceed max)
	 */
	public void decreaseAmmo(int amount)
	{
		this.increaseAmmo(-amount);
	}

	public String getFormattedString()
	{
		return ammo + "/" + max;
	}

	/**
	 * prints the total amount of ammo this entity has
	 */
	public void print()
	{
		System.out.println("This entity has " + this.ammo
				   + " bullets.");
	}
}
