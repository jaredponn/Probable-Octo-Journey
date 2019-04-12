package Components;

import poj.Component.Component;

/**
 * component for entities that use ammo
 * @author Alex
 * @version 2.0 - 03/31/19
 */
public class Ammo
	extends DoubleIntComponent implements Component, GUIStringDisplayable
{

	/**
	 * initialize current and max ammo amounts
	 * @param amount: current amount of ammo
	 * @param defMax: maximum amount of ammo
	 */
	public Ammo(int amount, int defMax)
	{
		super(amount, defMax);
	}

	/**
	 * get the current amount of ammo
	 * @return: the current amount of ammo
	 */
	public int get()
	{
		return getFocus1();
	}

	/**
	 * get the maximum amount of ammo
	 * @return: the maximum amount of ammo
	 */
	public int getMax()
	{
		return getFocus2();
	}

	/**
	 * checks if there is at least a certain amount of ammo
	 * @param amount: check if there is at least this much ammo
	 * @return true if there is enough ammo
	 */
	public boolean hasAmmo()
	{
		return focus1 > 0;
	}

	/**
	 * sets the current amount to a specified amount
	 * @param amount: the total amount of current ammo
	 */
	public void setAmmo(int amount)
	{
		setFocus1(amount);

		if (focus1 > focus2)
			setFocus1(focus2);
	}

	/**
	 * increase the current ammo by a specified amount (cannot exceed max)
	 * @param amount: the amount to increase current ammo by
	 */
	public void increaseAmmo(int amount)
	{
		setAmmo(focus1 + amount);
	}

	/**
	 * Alias of increaseAmmo()
	 */
	public void addFocus1(int n)
	{
		increaseAmmo(n);
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

	/**
	 * Returns a string formatted to be displayed on screen
	 * "<currentAmmo> / <maxAmmo>"
	 */
	public String getFormattedString()
	{
		return focus1 + "/" + focus2;
	}

	/**
	 * prints the total amount of ammo this entity has
	 */
	public void print()
	{
		System.out.println("This entity has " + focus1 + " bullets.");
	}
}
