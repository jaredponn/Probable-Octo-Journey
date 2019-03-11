package Components;
/**
 * BasicAttack Component.
 *
 * A basic attack consistents of a priming pause, attack collisionbox -- recoil
 * pause.
 *
 * The following ascii diagrams should illustrate the states and their names:
 *
 * primingTimems-----attackHitBox----recoilTimems
 * priming---------------------------|--!priming--
 *
 * acct lt primingms AND priming
 * primingTimems------------------|--attackHitBox---------------------recoilTimems
 * priming--------------------------------------------------------|--!priming--
 *                                 acct geq primingms AND priming
 *
 * Date: March 10, 2019
 * @author Jared
 * @version 1.0
 */


import poj.Collisions.*;
import poj.Logger.*;

public class BasicAttack
{

	private double primingTimems;
	private Polygon attackHitBoxes[];
	private double recoilTimems;

	private double accTimems;
	private boolean priming;


	/**
	 * Constructor.
	 *
	 * @param ptms -- priming time in milliseconds
	 * @param atkbox -- array of the attack hit boxes for all 8 cardinal
	 *         directions in order N, E, S, W, NE, NW, SE, SW
	 * @param rtms -- recoil time in milliseconds
	 */
	public BasicAttack(double ptms, Polygon atkboxes[], double rtms)
	{
		primingTimems = ptms;
		attackHitBoxes = atkboxes;
		recoilTimems = rtms;

		this.resetBasicAttack();
	}

	public void resetBasicAttack()
	{
		accTimems = 0d;
		priming = true;
	}

	public void addToAccTime(double dt)
	{
		accTimems += dt;
	}


	/**
	 * Gets the current attack state.
	 * 0 -- is the priming attack state
	 * 1 -- is the attack hit box state -- NOTE AUTOMATICALLY UPDATES THE
	 * priming VARIABLE
	 * 2 -- is the recoil attack state 3 -- is no state
	 *
	 * @return   int -- see the above description for what the ints mean
	 */
	public int getAttackState()
	{
		if (accTimems < primingTimems && priming) {
			return 0;
		}

		if (accTimems >= primingTimems && priming) {
			priming = !priming;
			return 1;
		}

		if ((accTimems - primingTimems) < recoilTimems && !priming) {
			return 2;
		}

		return 3;
	}


	public Polygon getAttackBox(CardinalDirections d)
	{
		switch (d) {
		case N:
			return attackHitBoxes[0];
		case E:
			return attackHitBoxes[1];
		case S:
			return attackHitBoxes[2];
		case W:
			return attackHitBoxes[3];
		case NE:
			return attackHitBoxes[4];
		case NW:
			return attackHitBoxes[5];
		case SE:
			return attackHitBoxes[6];
		case SW:
			return attackHitBoxes[7];

		default:
			Logger.lassert("Java is awful and language.");
			return new Polygon();
		}
	}
}
