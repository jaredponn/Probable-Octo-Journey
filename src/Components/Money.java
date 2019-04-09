package Components;

import poj.Component.Component;

/**
 * Component for entites that have money
 * @author Alex
 * @version 1.0 - 04/08/19
 */
public class Money implements Component, GUIStringDisplayable
{

	private int cash;

	/**
	 * Basic constructor. Money starts at 0
	 */
	public Money()
	{
		cash = 0;
	}

	/**
	 * Constructor with initial amount of money
	 * @param amount: the amount of starting money
	 */
	public Money(int amount)
	{
		cash = amount;
		if (cash < 0)
			cash = 0;
	}

	/**
	 * Get the amount of money this entity has
	 * @return the current amount of money this entity has
	 */
	public int get()
	{
		return cash;
	}

	/**
	 * Set the amount of money this entity has
	 * @param amount: the final amount of money this entity will have
	 */
	public void set(int amount)
	{
		cash = amount;
		if (cash < 0)
			cash = 0;
	}

	/**
	 * Increase the amount of money this entity has
	 * @param amount: the amount to increase by
	 */
	public void increase(int amount)
	{
		cash += amount;
		if (cash < 0)
			cash = 0;
	}

	/**
	 * Decrease the amount of money this entity has
	 * @param amount: the amount to decrease by
	 */
	public void decrease(int amount)
	{
		increase(-amount);
	}

	public String getFormattedString()
	{
		return "$" + cash;
	}


	/**
	 * Print the amount of money this entity has to console
	 */
	public void print()
	{
		System.out.println("This entity has $" + cash);
	}
}
