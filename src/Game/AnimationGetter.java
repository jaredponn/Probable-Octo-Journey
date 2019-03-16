package Game;

import poj.Animation;
import Resources.GameResources;
import Components.CardinalDirections;

public class AnimationGetter
{

	public static Animation queryEnemySprite(CardinalDirections dir,
						 int flag)
	{
		// flag = 0, idle position,
		// flag = 1, move direction
		// flag = 2, melee attack

		switch (dir) {
		case N:
			if (flag == 0) {
			} else if (flag == 1) {
				return GameResources.enemyNMoveAnimation;
			} else if (flag == 2) {
				return GameResources.enemyNAttackAnimation;
			}
		case NE:
			if (flag == 0) {
			} else if (flag == 1) {
				return GameResources.enemyNEMoveAnimation;
			} else if (flag == 2) {
				return GameResources.enemyNEAttackAnimation;
			}
		case NW:
			if (flag == 0) {
			} else if (flag == 1) {
				return GameResources.enemyNWMoveAnimation;
			} else if (flag == 2) {
				return GameResources.enemyNWAttackAnimation;
			}
		case S:
			if (flag == 0) {
			} else if (flag == 1) {
				return GameResources.enemySMoveAnimation;
			} else if (flag == 2) {
				return GameResources.enemySAttackAnimation;
			}
		case SE:
			if (flag == 0) {
			} else if (flag == 1) {
				return GameResources.enemySEMoveAnimation;
			} else if (flag == 2) {
				return GameResources.enemySEAttackAnimation;
			}
		case SW:
			if (flag == 0) {
			} else if (flag == 1) {
				return GameResources.enemySWMoveAnimation;
			} else if (flag == 2) {
				return GameResources.enemySWAttackAnimation;
			}
		case W:
			if (flag == 0) {
			} else if (flag == 1) {
				return GameResources.enemyWMoveAnimation;
			} else if (flag == 2) {
				return GameResources.enemyWAttackAnimation;
			}
		case E:
			if (flag == 0) {
			} else if (flag == 1) {
				return GameResources.enemyEMoveAnimation;
			} else if (flag == 2) {
				return GameResources.enemyEAttackAnimation;
			}
		default:
			return GameResources.enemyNMoveAnimation;
		}
	}

	public static Animation queryPlayerSprite(CardinalDirections dir,
						  int flag)
	{
		// flag = 0, gun idle position,
		// flag = 1, gun move direction
		// flag = 2, melee attack idle
		// flag = 3, melee attack move
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
			}
		default:
			return GameResources.playerNGunIdleAnimation;
		}
	}
}
