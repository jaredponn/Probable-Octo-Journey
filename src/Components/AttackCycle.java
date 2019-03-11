package Components;
/**
 * AttackCycle Component.
 *
 * An AttackCycle consistents of a priming pause, attack collisionbox -- recoil
 * pause. It's the cycle an attack goes through before it is exectued. It holds
 * extra state like the direction it was facing when the attack was engaged.
 *
 * The following ascii diagrams should illustrate the states and their names:
 *
 * primingTimems-----attack----------recoilTimems
 * priming---------------------------|--!priming--
 *
 * acct lt primingms AND priming
 * primingTimems------------------|--attack---------------------------recoilTimems
 * priming--------------------------------------------------------|--!priming--
 *                                 acct geq primingms AND priming
 *
 * Date: March 10, 2019
 * @author Jared
 * @version 1.0
 */

import poj.Component.Component;

public class AttackCycle implements Component
{
	// const init values
	protected double primingTimems;
	protected double recoilTimems;

	// mutable state
	protected double accTimems; // cycle state
	protected boolean priming;  // cycle state

	protected CardinalDirections facingAttackDirection;
	protected boolean isAttacking;


	/**
	 * Constructor.
	 *
	 * @param ptms -- priming time in milliseconds
	 * @param atkbox -- array of the attack hit boxes for all 8 cardinal
	 *         directions in order N, E, S, W, NE, NW, SE, SW
	 * @param rtms -- recoil time in milliseconds
	 */
	public AttackCycle(double ptms, double rtms)
	{
		primingTimems = ptms;
		recoilTimems = rtms;

		this.resetCycle();
	}

	public AttackCycle(AttackCycle n)
	{
		primingTimems = n.primingTimems;
		recoilTimems = n.recoilTimems;
	}

	public void resetCycle()
	{
		accTimems = 0d;
		priming = true;
	}


	public void startAttackCycle()
	{
		isAttacking = true;
	}

	public void updateAccTime(double dt)
	{
		accTimems += dt;
	}

	public void endAttackCycle()
	{
		isAttacking = false;
	}

	public void setFacingAttackDirection(CardinalDirections n)
	{
		this.facingAttackDirection = n;
	}

	public CardinalDirections getFacingAttackDirection()
	{
		return this.facingAttackDirection;
	}


	/**
	 * Gets the current attack state.
	 * 0 -- is the priming attack state
	 * 1 -- is the attack hit box state -- NOTE AUTOMATICALLY
	 * UPDATES THE priming VARIABLE
	 * 2 -- is the recoil attack state
	 * 3 -- end of the entire cycle
	 *
	 * -1 -- is no state
	 *
	 * @return   int -- see the above description for what the ints
	 *         mean
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

		if ((accTimems - primingTimems) > recoilTimems && !priming) {
			return 3;
		}

		return -1;
	}

	public boolean isAttacking()
	{
		return this.isAttacking;
	}

	public void print()
	{
		System.out.println("AttackCycle component");
	}
}
