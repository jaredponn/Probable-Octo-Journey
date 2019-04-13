package Components;
/**
 * Single int component
 * Date: March 11, 2019
 * @author Jared Pon
 * @version v1.0
 */

import poj.Component.Component;

public class SingleIntComponent implements Component
{

	int focus1;

	/**
	 * constructor
	 */
	public SingleIntComponent()
	{
	}

	/**
	 * constructor
	 * @param n --  new val
	 */
	public SingleIntComponent(int n)
	{
		focus1 = n;
	}

	/**
	 * adds to focus
	 * @param n --  value to add
	 */
	public void addFocus1(int n)
	{
		focus1 += n;
	}

	/**
	 * gets focus
	 * @return focus1
	 */
	public int getFocus1()
	{
		return focus1;
	}

	/**
	 * sets focus
	 * @param n -- new value
	 */
	public void setFocus1(int n)
	{
		focus1 = n;
	}


	/**
	 * print
	 */
	public void print()
	{
		System.out.println("SingleIntComponent: focus1: " + focus1);
	}
}
