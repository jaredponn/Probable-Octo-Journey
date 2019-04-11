package Components;

import poj.Component.Component;

/**
 * component for entities that apply damage to things
 * @author Alex
 * @version 1.0
 */
public class Damage extends SingleIntComponent implements Component
{

	/**
	 * initialize the amount of damage this entity deals
	 */
	public Damage()
	{
	}

	/**
	 * initialize the amount of damage this entity deals
	 * @param dmg: the amount of damage this deals
	 */
	public Damage(int dmg)
	{
		super(dmg);
	}


	/**
	 * get the amount of damage this entity deals
	 * @return the amount of damage this entity deals
	 */
	public int getDamage()
	{
		return getFocus1();
	}

	/**
	 * set the amount of damage this entity deals
	 * @param dmg: the new amount of damage this entity will deal
	 */
	public void setDamage(int dmg)
	{
		setFocus1(dmg);
	}

	/**
	 * increase/decrease the amount of damage this entity deals
	 * @param mod: the amount to increase/decrease damage by
	 */
	public void addDamage(int mod)
	{
		setDamage(getFocus1() + mod);
	}

	/**
	 * prints the amount of damage this entity deals
	 */
	public void print()
	{
		System.out.println("This entity deals " + focus1 + " damage.");
	}
}
