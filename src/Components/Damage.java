package Components;

import poj.Component.Component;

/**
 * component for entities that apply damage to things
 * @author Alex
 * @version 1.0
 */
public class Damage implements Component{
	
	private int damage;
	
	/**
	 * initialize the amount of damage this entity deals
	 * @param dmg: the amount of damage this deals
	 */
	public Damage (int dmg ) {
		this.damage = dmg;
	}
	
	/**
	 * get the amount of damage this entity deals
	 * @return the amount of damage this entity deals
	 */
	public int getDamage() {
		return this.damage;
	}
	
	/**
	 * set the amount of damage this entity deals
	 * @param dmg: the new amount of damage this entity will deal
	 */
	public void setDamage( int dmg ) {
		this.damage = dmg;
	}
	
	/**
	 * increase/decrease the amount of damage this entity deals
	 * @param mod: the amount to increase/decrease damage by
	 */
	public void changeDamage( int mod ) {
		this.damage += mod;
	}
	
	/**
	 * prints the amount of damage this entity deals
	 */
	public void print() {
		System.out.println("This entity deals " +this.damage+ " damage.");
	}

}
