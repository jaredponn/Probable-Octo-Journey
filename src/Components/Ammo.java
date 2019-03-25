package Components;

import poj.Component.Component;

/**
 * component for entities that use ammo
 * @author Alex
 * @version 1.0
 */
public class Ammo implements Component{

	private int ammo;
	
	public Ammo(int amount) {
		this.ammo = amount;
	}
	
	public int get() {
		return this.ammo;
	}
	
	/**
	 * checks if there is at least a certain amount of ammo
	 * @param amount: check if there is at least this much ammo
	 * @return true if there is enough ammo
	 */
	public boolean hasAmmo( int amount ) {
		if (this.ammo >= amount)
			return true;
		else
			return false;
	}
	
	public void setAmmo( int amount ) {
		this.ammo = amount;
	}
	
	public void increaseAmmo( int amount , int maxAmmo ) {
		this.ammo += amount;
		if (this.ammo > maxAmmo)
			this.ammo = maxAmmo;
	}
	
	public void decreaseAmmo( int amount , int maxAmmo ) {
		this.increaseAmmo( -amount , maxAmmo);
	}
	
	public void print() {
		System.out.println("This entity has "+this.ammo+" bullets.");
	}
}
