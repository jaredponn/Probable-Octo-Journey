package Components;

import poj.Component.Component;

/**
 * Component for entites that have money
 * @author Alex
 * @version 1.0 - 04/08/19
 */
public class Money
	extends SingleIntComponent implements Component, GUIStringDisplayable
{

	/**
	 * Basic constructor. Money starts at 0
	 */
	public Money()
	{
		focus1 = 0;
	}

	/**
	 * Constructor with initial amount of money
	 * @param amount: the amount of starting money
	 */
	public Money(int amount)
	{
		set(amount);
	}

	/**
	 * Get the amount of money this entity has
	 * @return the current amount of money this entity has
	 */
	public int get()
	{
		return getFocus1();
	}

	/**
	 * Set the amount of money this entity has
	 * @param amount: the final amount of money this entity will have
	 */
	public void set(int amount)
	{
		focus1 = amount;
		if (focus1 < 0)
			focus1 = 0;
	}

	/**
	 * Increase the amount of money this entity has
	 * @param amount: the amount to increase by
	 */
	public void increase(int amount)
	{

		set(focus1 + amount);
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
		return "$" + focus1;
	}


	/**
	 * Print the amount of money this entity has to console
	 */
	public void print()
	{
		System.out.println("This entity has $" + focus1);
	}
}
