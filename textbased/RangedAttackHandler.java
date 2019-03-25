/**
* A set of methods for handling ranged attacks
* @author Alex 
* @version 2.0
*/

public class RangedAttackHandler {
    
    public static void findBulletHits(World world , String direction) {
        Player player = world.getPlayer();
        Position pos = player.getPosition();
        boolean bulletHit = false;
        
        while (bulletHit == false) {
            Entity neigbor = world.getNeighborInDirection(pos , direction);
            if (neigbor instanceof Floor ) {
                Floor thisFloor = (Floor) neigbor;
                if (thisFloor.hasContents()) {
                    Entity contents = thisFloor.getContents();
                    if (contents instanceof Enemy) {
                        Enemy enemy = (Enemy) contents;
                        enemy.hurt(GameConfig.BULLET_DAMAGE);
                        System.out.println("You shot an enemy for "+GameConfig.BULLET_DAMAGE+" damage.");
                        bulletHit = true;
                    }
                    else{
                        pos = neigbor.getPosition();
                    }
                }
                else{
                    pos = neigbor.getPosition();
                }
            }
            else {
                System.out.println("You missed your shot");
                bulletHit = true;
            }
        }
    }
    
    public static void attack(World world , String direction) {
        if ( world.getPlayer().hasAmmo(1) )
        {
            world.getPlayer().addAmmo(-1);
            if (direction.equals("8"))
                findBulletHits(world , "N");
            else if (direction.equals("2"))
                findBulletHits(world , "S");
            else if (direction.equals("6"))
                findBulletHits(world , "E");
            else if (direction.equals("4"))
                findBulletHits(world , "W");
        }
        else
            System.out.println("Not enough ammo");
    }
}