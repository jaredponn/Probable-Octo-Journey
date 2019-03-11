package Components;
/**
 * BasicAttack Component.
 *
 * A basic attack consistents of a priming pause, attack collisionbox -- recoil
 * pause.
 *
 * so: primingTimems ----- attackHitBox ---- recoilTimems
 *
 */


import poj.Collisions.*;

public class BasicAttack
{

	private double primingTimems;
	private Polygon attackHitBox;
	private double recoilTimems;

	private double accTimems;

	public BasicAttack(double ptms, Polygon atkbox, double rtms)
	{
		primingTimems = ptms;
		attackHitBox = atkbox;
		recoilTimems = rtms;

		accTimems = 0d;
	}


	public int getAttackState()
	{
		if (accTimems < primingTimems) {
			return 0;
		}

		if (accTimems >= primingTimems) {
			return 0;
		}
		return 0;
	}
}
