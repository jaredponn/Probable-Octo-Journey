package Game;

/**
 * WeaponState: a enum that is used to keep track the player's weapons
 * Date: March 9, 2019
 * @author: Haiyang
 * @version: 1.0
 */
public enum WeaponState {
	Gun,   // gun mode
	Melee; // melee mode
	private static final WeaponState[] vals = values();
	public WeaponState next()
	{
		return vals[(this.ordinal() + 1) % vals.length];
	}

	public WeaponState prev()
	{
		if ((this.ordinal() + 1) % vals.length < 0) {
			return vals[vals.length - 1];
		} else {
			return vals[(this.ordinal() + 1) % vals.length];
		}
	}
	public String currentWeaponState()
	{
		switch (this.ordinal()) {
		case 0:
			return "Gun";
		case 1:
			return "Melee";
		default:
			return "idk";
		}
	}
}
