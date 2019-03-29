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
	 * @param rtms -- recoil time in milliseconds
	 */
	public AttackCycle(double ptms, double rtms)
	{
		primingTimems = ptms;
		recoilTimems = rtms;

		this.resetCycle();
		this.endAttackCycle();
	}

	public AttackCycle(AttackCycle n)
	{
		primingTimems = n.primingTimems;
		recoilTimems = n.recoilTimems;

		this.resetCycle();
		this.endAttackCycle();
	}

	public void resetCycle()
	{
		accTimems = -Double.MAX_VALUE;
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
	 * 0 -- is the starting attack
	 * 1 -- is the priming attack state
	 * 2 -- is the attack hit box state -- NOTE AUTOMATICALLY
	 * UPDATES THE priming VARIABLE
	 * 3 -- is the recoil attack state
	 * 4 -- end of the entire cycle
	 *
	 *
	 * @return   int -- see the above description for what the ints
	 *         mean
	 */
	public int getAttackState()
	{
		if (accTimems < 0) {
			accTimems = 0;
			return 0;
		}
		if (accTimems < primingTimems && priming) {
			return 1;
		}

		if (accTimems >= primingTimems && priming) {
			priming = !priming;
			return 2;
		}

		if ((accTimems - primingTimems) < recoilTimems && !priming) {
			return 3;
		}

		if ((accTimems - primingTimems) > recoilTimems && !priming) {
			return 4;
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
