import java.util.ArrayList;

/**
 * A collection of constants
 * @author Alex
 * @version 2.0
 */
public class GameConfig {
    
    // Map Config
    public static final int MAP_HEIGHT = 20;
    public static final int MAP_WIDTH =  MAP_HEIGHT; // ** MAP MUST BE SQUARE ** //
    
    // Player config
    public static final int PLAYER_SPAWN_X = 1;
    public static final int PLAYER_SPAWN_Y = 1;
    public static final int PLAYER_HEALTH =  100;
    public static final int PLAYER_STARTING_AMMO = 7;
    
    // Enemy config
    public static final int ENEMY_HEALTH  =  100;
    public static final int ENEMY_SPAWN_TIMER = 10;
    public static final ArrayList<Position> ENEMY_SPAWNERS = new ArrayList<Position>() {
        {
            add( new Position( 1  , 3  ) );
            add( new Position( 3  , 1  ) );
            add( new Position( 1  , 8  ) );
            add( new Position( 8  , 1  ) );
            add( new Position( 5  , 5  ) );
            add( new Position( 10 , 3  ) );
            add( new Position( 6  , 14 ) );
            add( new Position( 17 , 1  ) );
            add( new Position( 3  , 8  ) );
            add( new Position( 11 , 16 ) );
        }
    };
    
    // Pick-Up config
    public static final int HEALTH_PICKUP_AMOUNT = 100;
    public static final int HEALTH_PICKUP_SPAWN_TIMER = 15;
    public static final Position HEALTH_PICKUP_SPAWNER = new Position( 10 , 10 );
    
    public static final int AMMO_PICKUP_AMOUNT = 5;
    public static final int AMMO_PICKUP_SPAWN_TIMER = 20;
    public static final Position AMMO_PICKUP_SPAWNER = new Position( 18 , 18 );
    
    // Movement config
    public static final String NORTH_KEY = "W";
    public static final String SOUTH_KEY = "S";
    public static final String EAST_KEY  = "D";
    public static final String WEST_KEY  = "A";
    
    // Combat config
    public static final int MELEE_DAMAGE = 33;
    public static final int BULLET_DAMAGE = 50;
    
    public static final Entity NULL_ENTITY = new Entity(-1);
}