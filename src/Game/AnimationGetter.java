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

		// flag = 0, idle position,
		// flag = 1, move direction
		// flag = 2, melee attack
		switch (dir) {
		case N:
			if (flag == 0) {
				return GameResources.playerNIdleAnimation;
			} else if (flag == 1) {
				return GameResources.playerNMoveAnimation;
			}
		case NE:
			if (flag == 0) {
				return GameResources.playerNEIdleAnimation;
			} else if (flag == 1) {
				return GameResources.playerNEMoveAnimation;
			}
		case NW:
			if (flag == 0) {
				return GameResources.playerNWIdleAnimation;
			} else if (flag == 1) {
				return GameResources.playerNWMoveAnimation;
			}
		case S:
			if (flag == 0) {
				return GameResources.playerSIdleAnimation;
			} else if (flag == 1) {
				return GameResources.playerSMoveAnimation;
			}
		case SE:
			if (flag == 0) {
				return GameResources.playerSEIdleAnimation;
			} else if (flag == 1) {
				return GameResources.playerSEMoveAnimation;
			}
		case SW:
			if (flag == 0) {
				return GameResources.playerSWIdleAnimation;
			} else if (flag == 1) {
				return GameResources.playerSWMoveAnimation;
			}
		case W:
			if (flag == 0) {
				return GameResources.playerWIdleAnimation;
			} else if (flag == 1) {
				return GameResources.playerWMoveAnimation;
			}
		case E:
			if (flag == 0) {
				return GameResources.playerEIdleAnimation;
			} else if (flag == 1) {
				return GameResources.playerEMoveAnimation;
			}
		default:
			return GameResources.playerNIdleAnimation;
		}
	}
}
