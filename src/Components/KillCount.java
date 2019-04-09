package Components;

import poj.Component.Component;

/**
 * Component for entities that track their kills
 * @author Alex
 * @version 1.0 04/08/19
 */
public class KillCount implements Component {
	private int kills;
	
	/**
	 * Basic constructor. Kills start at 0
	 */
	public KillCount() {
		kills = 0;
	}
	
	/**
	 * Constructor with initial kills
	 * @param amount: the amount of kills this entity has
	 */
	public KillCount(int amount) {
		kills = amount;
	}
	
	/**
	 * Get the number of kills this entity has
	 * @return the number of kills this entity has
	 */
	public int get() {
		return kills;
	}
	
	/**
	 * Increase the number if kills this entity has
	 */
	public void increase() {
		kills++;
		if (kills < 0)
			kills = 0;
	}
	
	/**
	 * Set the number of kills this entity has
	 * @param amount: the final number of kills this entity has
	 */
	public void set(int amount) {
		kills = amount;
		if (kills < 0)
			kills = 0;
	}
	
	/**
	 * Print this number of kills this entity has to the console
	 */
	public void print() {
		System.out.println("This entity has "+kills+" kills");
	}

}
