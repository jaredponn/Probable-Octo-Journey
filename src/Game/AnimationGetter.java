package Game;

import poj.Animation;
import EntitySets.*;
import poj.Component.Component;
import poj.Logger.Logger;

import Resources.GameResources;
import Components.CardinalDirections;

public class AnimationGetter
{

	public static Animation
	queryAnimationSprite(Class<? extends Component> c,
			     CardinalDirections dir, int flag)
	{
		if (c == PlayerSet.class)
			return queryPlayerSprite(dir, flag);
		else if (c == MobSet.class)
			return queryEnemySprite(dir, flag);
		else {
			Logger.logMessage(
				"error in animation getter -- invalid class");
			return GameResources.enemyNMoveAnimation;
		}
	}

	public static Animation queryEnemySprite(CardinalDirections dir,
						 int flag)
	{
		// flag = 0, idle position,
		// flag = 1, move direction
		// flag = 10, melee attack
		// flag = 30, death animation

		switch (dir) {
		case N:
			if (flag == 0) {
			} else if (flag == 1) {
				return new Animation(
					GameResources.enemyNMoveAnimation);
			} else if (flag == 10) {
				return new Animation(
					GameResources.enemyNAttackAnimation);

			} else if (flag == 30) {
				return new Animation(
					GameResources.enemyNDeathAnimation);
			}
		case NE:
			if (flag == 0) {
			} else if (flag == 1) {
				return new Animation(
					GameResources.enemyNEMoveAnimation);
			} else if (flag == 10) {
				return new Animation(
					GameResources.enemyNEAttackAnimation);
			} else if (flag == 30) {
				return new Animation(
					GameResources.enemyNDeathAnimation);
			}
		case NW:
			if (flag == 0) {
			} else if (flag == 1) {
				return new Animation(
					GameResources.enemyNWMoveAnimation);
			} else if (flag == 10) {
				return new Animation(
					GameResources.enemyNWAttackAnimation);
			} else if (flag == 30) {
				return new Animation(
					GameResources.enemyWDeathAnimation);
			}
		case S:
			if (flag == 0) {
			} else if (flag == 1) {
				return new Animation(
					GameResources.enemySMoveAnimation);
			} else if (flag == 10) {
				return new Animation(
					GameResources.enemySAttackAnimation);
			} else if (flag == 30) {
				return new Animation(
					GameResources.enemySDeathAnimation);
			}
		case SE:
			if (flag == 0) {
			} else if (flag == 1) {
				return new Animation(
					GameResources.enemySEMoveAnimation);
			} else if (flag == 10) {
				return new Animation(
					GameResources.enemySEAttackAnimation);
			} else if (flag == 30) {
				return new Animation(
					GameResources.enemyEDeathAnimation);
			}
		case SW:
			if (flag == 0) {
			} else if (flag == 1) {
				return new Animation(
					GameResources.enemySWMoveAnimation);
			} else if (flag == 10) {
				return new Animation(
					GameResources.enemySWAttackAnimation);
			} else if (flag == 30) {
				return new Animation(
					GameResources.enemySDeathAnimation);
			}
		case W:
			if (flag == 0) {
			} else if (flag == 1) {
				return new Animation(
					GameResources.enemyWMoveAnimation);
			} else if (flag == 10) {
				return new Animation(
					GameResources.enemyWAttackAnimation);
			} else if (flag == 30) {
				return new Animation(
					GameResources.enemyWDeathAnimation);
			}
		case E:
			if (flag == 0) {
			} else if (flag == 1) {
				return new Animation(
					GameResources.enemyEMoveAnimation);
			} else if (flag == 10) {
				return new Animation(
					GameResources.enemyEAttackAnimation);
			} else if (flag == 30) {
				return new Animation(
					GameResources.enemyEDeathAnimation);
			}
		default:
			System.out.println(
				"Error with get enemy sprite -- please put in a valid sprite");
			return new Animation(GameResources.enemyNMoveAnimation);
		}
	}

	public static Animation queryPlayerSprite(CardinalDirections dir,
						  int flag)
	{
		// flag = 0, gun idle position,
		// flag = 1, gun move direction
		// flag = 2, melee attack idle
		// flag = 3, melee attack move

		// flag = 10, melee attack
		switch (dir) {
		case N:
			if (flag == 0) {
				return GameResources.playerNGunIdleAnimation;
			} else if (flag == 1) {
				return GameResources.playerNGunMoveAnimation;
			} else if (flag == 2) {
				return GameResources.playerNMeleeIdleAnimation;
			} else if (flag == 3) {
				return GameResources.playerNMeleeMoveAnimation;
			} else if (flag == 10) {

				return GameResources.playerNMeleeAttack;
			}
		case NE:
			if (flag == 0) {
				return GameResources.playerNEGunIdleAnimation;
			} else if (flag == 1) {
				return GameResources.playerNEGunMoveAnimation;
			} else if (flag == 2) {
				return GameResources.playerNEMeleeIdleAnimation;
			} else if (flag == 3) {
				return GameResources.playerNEMeleeMoveAnimation;

			} else if (flag == 10) {
				return GameResources.playerNEMeleeAttack;
			}
		case NW:
			if (flag == 0) {
				return GameResources.playerNWGunIdleAnimation;
			} else if (flag == 1) {
				return GameResources.playerNWGunMoveAnimation;
			} else if (flag == 2) {
				return GameResources.playerNWMeleeIdleAnimation;
			} else if (flag == 3) {
				return GameResources.playerNWMeleeMoveAnimation;
			} else if (flag == 10) {
				return GameResources.playerNWMeleeAttack;
			}
		case S:
			if (flag == 0) {
				return GameResources.playerSGunIdleAnimation;
			} else if (flag == 1) {
				return GameResources.playerSGunMoveAnimation;
			} else if (flag == 2) {
				return GameResources.playerSMeleeIdleAnimation;
			} else if (flag == 3) {
				return GameResources.playerSMeleeMoveAnimation;
			} else if (flag == 10) {
				return GameResources.playerSMeleeAttack;
			}
		case SE:
			if (flag == 0) {
				return GameResources.playerSEGunIdleAnimation;
			} else if (flag == 1) {
				return GameResources.playerSEGunMoveAnimation;
			} else if (flag == 2) {
				return GameResources.playerSEMeleeIdleAnimation;
			} else if (flag == 3) {
				return GameResources.playerSEMeleeMoveAnimation;
			} else if (flag == 10) {
				return GameResources.playerSEMeleeAttack;
			}
		case SW:
			if (flag == 0) {
				return GameResources.playerSWGunIdleAnimation;
			} else if (flag == 1) {
				return GameResources.playerSWGunMoveAnimation;
			} else if (flag == 2) {
				return GameResources.playerSWMeleeIdleAnimation;
			} else if (flag == 3) {
				return GameResources.playerSWMeleeMoveAnimation;
			} else if (flag == 10) {
				return GameResources.playerSWMeleeAttack;
			}
		case W:
			if (flag == 0) {
				return GameResources.playerWGunIdleAnimation;
			} else if (flag == 1) {
				return GameResources.playerWGunMoveAnimation;

			} else if (flag == 2) {
				return GameResources.playerWMeleeIdleAnimation;
			} else if (flag == 3) {
				return GameResources.playerWMeleeMoveAnimation;
			} else if (flag == 10) {
				return GameResources.playerWMeleeAttack;
			}
		case E:
			if (flag == 0) {
				return GameResources.playerEGunIdleAnimation;
			} else if (flag == 1) {
				return GameResources.playerEGunMoveAnimation;
			} else if (flag == 2) {
				return GameResources.playerEMeleeIdleAnimation;
			} else if (flag == 3) {
				return GameResources.playerEMeleeMoveAnimation;
			} else if (flag == 10) {
				return GameResources.playerEMeleeAttack;
			}
		default:
			return GameResources.playerNGunIdleAnimation;
		}
	}
}
