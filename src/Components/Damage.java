package Components;

import poj.Component.Component;

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
