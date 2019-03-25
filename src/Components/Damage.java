package Components;

import poj.Component.Component;

/**
 * component for entities that apply damage to things
 * @author Alex
 * @version 1.0
 */
public class Damage implements Component{
	
	private int damage;
	
	public Damage (int dmg ) {
		this.damage = dmg;
	}
	
	public int getDamage() {
		return this.damage;
	}
	
	public void setDamage( int dmg ) {
		this.damage = dmg;
	}
	
	public void changeDamage( int mod ) {
		this.damage += mod;
	}
	
	public void print() {
		System.out.println("This entity deals " +this.damage+ " damage.");
	}

}
