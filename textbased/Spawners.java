import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
* A set of methods for spawning entities
* @author Alex
* @version 2.0
*/
public class Spawners {
    
    /**
    * spawns enemies every over time
    * @param world: the main game world
    * @param turn: the current turn
    */
    public static void enemySpawner( World world , int turn ) {
        ArrayList<Entity> entities = world.getEntities();
        ArrayList<Position> spawners = GameConfig.ENEMY_SPAWNERS;
        int spawnTimer = GameConfig.ENEMY_SPAWN_TIMER;
        
        if (turn % spawnTimer == 0) {
            // the spawner chosen to spawn this enemy
            int random = ThreadLocalRandom.current().nextInt(0,10);
            Position thisSpawner = spawners.get(random);
            Floor spawnPoint = (Floor) world.getEntityAt(thisSpawner);
        
            // check that the spawn point is not already occupied
            if (spawnPoint.hasContents() == false) {
                // create new enemy
                Enemy newEnemy = new Enemy( thisSpawner , world.getIndex() );
                entities.add(newEnemy);
                world.incIndex();
                System.out.println("A new enemy has arrived");
        
                // add enemy to floor
                spawnPoint.setContents(newEnemy);
            }
        }
    }
    
    /**
    * spawns new health packs over time
    * @param world: the main game world
    * @param turn: the current turn
    */
    public static void healthSpawner( World world , int turn ) {
        ArrayList<Entity> entities = world.getEntities();
        Position thisSpawner = GameConfig.HEALTH_PICKUP_SPAWNER;
        int spawnTimer = GameConfig.HEALTH_PICKUP_SPAWN_TIMER;
        Floor spawnPoint = (Floor) world.getEntityAt(thisSpawner);
        
        if (turn % spawnTimer == 0 && spawnPoint.hasContents() == false) {
            // create new heath pack
            HealthPack newPickUp = new HealthPack(GameConfig.HEALTH_PICKUP_AMOUNT , thisSpawner , world.getIndex() );
            entities.add(newPickUp);
            System.out.println("A new health-pack has spawned");
            
            // add pick-up to floor
            spawnPoint.setContents(newPickUp);
        }
    }
    
    /**
    * spawns new ammo packs over time
    * @param world: the main game world
    * @param turn: the current turn
    */
    public static void ammoSpawner( World world , int turn ) {
        ArrayList<Entity> entities = world.getEntities();
        Position thisSpawner = GameConfig.AMMO_PICKUP_SPAWNER;
        int spawnTimer = GameConfig.AMMO_PICKUP_SPAWN_TIMER;
        Floor spawnPoint = (Floor) world.getEntityAt(thisSpawner);
        
        if (turn % spawnTimer == 0 && spawnPoint.hasContents() == false) {
            // create new heath pack
            AmmoPack newPickUp = new AmmoPack(GameConfig.AMMO_PICKUP_AMOUNT , thisSpawner , world.getIndex() );
            entities.add(newPickUp);
            System.out.println("A new ammo-pack has spawned");
            
            // add pick-up to floor
            spawnPoint.setContents(newPickUp);
        }
    }
}