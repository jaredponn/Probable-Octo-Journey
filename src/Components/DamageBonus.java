package Components;

import poj.Component.Component;

/**
 * Component for entities that gain bonus damage
 * @author Alex
 * @version 1.0 - 04/08/19
 */
public class DamageBonus implements Component {
	private int bonus;
	
	/**
	 * Basic constructor. Damage bonus starts at 0
	 */
	public DamageBonus() {
		bonus = 0;
	}
	
	/**
	 * Constructor with initial damage bonus
	 * @param amount: initial damage bonus
	 */
	public DamageBonus(int amount) {
		bonus = amount;
		if (bonus < 0)
			bonus = 0;
	}
	
	/**
	 * Get the current damage bonus
	 * @return the current damage bonus
	 */
	public int get() {
		return bonus;
	}
	
	/**
	 * Increase the damage bonus by an amount
	 * @param amount: the amount to increase by
	 */
	public void increase(int amount) {
		bonus += amount;
		if (bonus < 0)
			bonus = 0;
	}
	
	/**
	 * Decrease the damage bonus by an amount
	 * @param amount: the amount to decrease by
	 */
	public void decrease(int amount) {
		increase(-amount);
	}
	
	/**
	 * Set the damage bonus to a specific amount
	 * @param amount: the final damage bonus for this entity
	 */
	public void set(int amount) {
		bonus = amount;
		if (bonus < 0)
			bonus = 0;
	}
	
	/**
	 * Print the current damage bonus for this entity to console
	 */
	public void print() {
		System.out.println("This entity does "+bonus+" bonus damage");
	}
}
