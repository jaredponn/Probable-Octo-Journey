import java.util.ArrayList;

/**
 * Contains and controls info about the current state of the game
 * @author Alex
 * @version 2.0
 */
public class World {
    
    private ArrayList<Entity> entities = new ArrayList<Entity>();
    private ArrayList<ArrayList<Entity>> map = new ArrayList<ArrayList<Entity>>();
    private int playerIndex;
    private int lastIndex = 0;
    
    /**
     * Initializes the game world
     */
    public World() {
        createMap();
        spawnWalls();
        spawnFloors();
        spawnPlayer( GameConfig.PLAYER_SPAWN_X , GameConfig.PLAYER_SPAWN_Y );
        spawnEnemies();
        spawnPickup( 10 , 10 );
    }
    
    /**
     * Fills a 2D ArrayList to the size defined in GameConfig
     */
    private void createMap() {
        for ( int x = 0 ; x < GameConfig.MAP_WIDTH ; x++ ) {
            map.add(new ArrayList<Entity>(  ));
            for ( int y = 0 ; y < GameConfig.MAP_HEIGHT ; y++)
                map.get(x).add(null);
        }
    }
    
    /**
     * Creates Walls to go around the game world
     */
    private void spawnWalls() {
        // North Wall
        for ( int i = 0 ; i < GameConfig.MAP_WIDTH ; i++ ) {
            entities.add( new Wall( 0 , i , this.lastIndex ) );
            this.lastIndex += 1;
        }
        // West Wall
        for ( int i = 1 ; i < GameConfig.MAP_HEIGHT ; i++ ) {
            entities.add( new Wall( i , 0 , this.lastIndex ) );
            this.lastIndex += 1;
        }
        // South Wall
        for ( int i = 1 ; i < GameConfig.MAP_WIDTH ; i++ ) {
            entities.add( new Wall( GameConfig.MAP_WIDTH - 1 , i , this.lastIndex ) );
            this.lastIndex += 1;
        }
        // East Wall
        for ( int i = 1 ; i < GameConfig.MAP_HEIGHT - 1 ; i++ ) {
            entities.add( new Wall( i , GameConfig.MAP_HEIGHT - 1 , this.lastIndex ) );
            this.lastIndex += 1;
        }
        
        putWallsOnMap();
    }
    
    /**
     * Creates Floors to fill the game world
     */
    private void spawnFloors() {
        for ( int x = 1 ; x < GameConfig.MAP_WIDTH - 1 ; x++ ) {
            for ( int y = 1 ; y < GameConfig.MAP_HEIGHT - 1 ; y++ ) {
                entities.add( new Floor( x , y , this.lastIndex ) );
                this.lastIndex += 1;
            }
        }
        putFloorsOnMap();
    }
    
    /**
     * Adds the Walls to the game map
     */
    private void putWallsOnMap() {
        for (Entity thisEntity : entities) {
            if (thisEntity instanceof Wall) {
                int thisWallColumn = thisEntity.getXPosition();
                map.get(thisWallColumn).set( thisEntity.getYPosition() , thisEntity );
            }
        }
    }
    
    /**
     * Adds the Floors to the game map
     */
    private void putFloorsOnMap() {
        for (Entity thisEntity : entities) {
            if (thisEntity instanceof Floor) {
                int thisFloorColumn = thisEntity.getXPosition();
                map.get(thisFloorColumn).set( thisEntity.getYPosition() , thisEntity );
            }
        }
    }
    
    /**
     * Spawns the player character at the specified coordinates
     * @param x-coordinate of the player spawn point
     * @param y-coordinate of the player spawn point
     */
    public void spawnPlayer( int x , int y) {
        Player player = new Player( GameConfig.PLAYER_HEALTH , x , y , this.lastIndex );
        entities.add( player );
        this.lastIndex += 1;
        this.playerIndex = this.lastIndex - 1;
        Floor playerSpawnPoint = (Floor) map.get(x).get(y);
        playerSpawnPoint.setContents(player);
    }
    
    /**
     * Spawns a pick-up at the specified coordinates
     * @param x-coordinate of the pick-up spawn point
     * @param y-coordinate of the pick-up spawn point
     */
    public void spawnPickup( int x , int y ) {
        HealthPack pickup = new HealthPack( GameConfig.HEALTH_PICKUP_AMOUNT , x , y , this.lastIndex );
        entities.add( pickup );
        this.lastIndex += 1;
        
        Floor spawnPoint = (Floor) this.getEntityAt( pickup.getPosition() );
        spawnPoint.setContents( pickup );
        
    }
    
    /**
     * Spawns five enemies around the map
     */
    public void spawnEnemies() {
        entities.add( new Enemy( 1 , 3 , this.lastIndex ) );
        this.lastIndex += 1;
        entities.add( new Enemy( 3 , 1 , this.lastIndex ) );
        this.lastIndex += 1;
        entities.add( new Enemy( 1 , 8 , this.lastIndex ) );
        this.lastIndex += 1;
        entities.add( new Enemy( 8 , 1 , this.lastIndex ) );
        this.lastIndex += 1;
        entities.add( new Enemy( 5 , 5 , this.lastIndex ) );
        this.lastIndex += 1;
        
        for (Entity thisEntity : entities) {
            if (thisEntity instanceof Enemy) {
                Floor spawnPoint = (Floor) this.getEntityAt(thisEntity.getPosition());
                spawnPoint.setContents(thisEntity);
            }
        }
        
    }
    
    /**
     * Displays the current game world in the console
     */
    public void print() {
        for ( int x = 0 ; x < GameConfig.MAP_WIDTH ; x++ ) {
            for (int y = 0 ; y < GameConfig.MAP_HEIGHT ; y++ ) {
                Entity thisEntity = map.get(y).get(x);
                if (thisEntity != null)
                    thisEntity.print();
                else
                    System.out.print("N ");
            }
            System.out.print("\n");
        }
    }
    
    ///// Getters /////
    public Player getPlayer() {
        Player player = (Player) entities.get(this.playerIndex);
        return player;
    }
    
    public ArrayList<ArrayList<Entity>> getMap() {
        return this.map;
    }
    
    public ArrayList<Entity> getEntities() {
        return this.entities;
    }
    
    public Entity getEntityAt( Position position ) {
        return map.get( position.getXPos() ).get( position.getYPos() );
    }
    
    public Entity getEntityAtIndex(int index) {
        return entities.get( index );
    }
    
    public int getIndex() {
        return this.lastIndex;
    }
    
    public int countEnemies() {
        int count = 0;
        for (Entity thisEntity : entities) {
            if (thisEntity instanceof Enemy)
                count++;
        }
        return count;
    }
    
    /**
    * Finds the neighboring entity in a direction
    * @param pos: the world position of the entity whos neighbor you are searching for
    * @param direction: the direction in which to look for a neighbor
    */
    public Entity getNeighborInDirection( Position pos , String direction ) {
        Position neighborPosition;
        
        if (direction.equals("N")){
            neighborPosition = new Position( pos.getXPos() , pos.getYPos()-1 );
            return getEntityAt(neighborPosition);
        }
        else if (direction.equals("S")){
            neighborPosition = new Position( pos.getXPos() , pos.getYPos()+1 );
            return getEntityAt(neighborPosition);
        }
        else if (direction.equals("E")){
            neighborPosition = new Position( pos.getXPos()+1 , pos.getYPos() );
            return getEntityAt(neighborPosition);
        }
        else if (direction.equals("W")){
            neighborPosition = new Position( pos.getXPos()-1 , pos.getYPos() );
            return getEntityAt(neighborPosition);
        }
        else {
            System.out.println("failed to find neighbor");
            return GameConfig.NULL_ENTITY;
        }
    }
    
    ///// Setters /////
    /**
     * Removes an entity from the list of entities and
     * ends the game if it was the player that was removed
     * @param index of the entity to be removed
     */
    public void removeEntity( int index ) {
        entities.set( index , GameConfig.NULL_ENTITY );
        if (index == this.playerIndex) {
            System.out.println("The Player Has Been Defeated!");
            System.exit(0);
        }
    }
    
    /**
    * increase the last index used
    */
    public void incIndex() {
        this.lastIndex++;
    }
    
    /**
     * Checks if a move is valid and moves the entity back if
     * it was not a valid move. Also applies damage if applicable
     * @param entity: What entity is moving
     * @param startPosition: Where the entity was when it started moving
     * @return false if the move was invalid
     */
    private boolean doMoveHelper(Entity entity , Floor startPosition) {
        Entity endPosition = this.getEntityAt( entity.getPosition() );
        if (endPosition instanceof Floor) {
            Floor endFloor = (Floor) endPosition;
            if (endFloor.hasContents()){
            	// player moving into an enemy
                if (entity instanceof Player && endFloor.getContents() instanceof Enemy) {
                    Enemy thisEnemy = (Enemy) endFloor.getContents();
                    thisEnemy.hurt( GameConfig.MELEE_DAMAGE );
                    System.out.println("Enemy health now at " + thisEnemy.getHealth() );
                    if (thisEnemy.getHealth() <= 0) {
                        removeEntity( thisEnemy.getIndex() );
                        endFloor.clearContents();
                    }
                    return false;
                }
                // enemy moving into the player
                else if (entity instanceof Enemy && endFloor.getContents() instanceof Player) {
                    Player player = (Player) endFloor.getContents();
                    player.hurt( GameConfig.MELEE_DAMAGE );
                    return false;
                }
                // enemy moving into an enemy
                else if (entity instanceof Enemy && endFloor.getContents() instanceof Enemy) {
                    return false;
                }
                // player moving into a pick-up
                else if (entity instanceof Player && endFloor.getContents() instanceof PickUp) {
                    Player player = (Player) entity;
                    PickUp pickup = (PickUp) endFloor.getContents();
                    pickup.collect(player);
                    removeEntity( pickup.getIndex() );
                    endFloor.clearContents();
                }
                // enemy moving into a pick-up
                else if (entity instanceof Enemy && endFloor.getContents() instanceof PickUp) {
                    endFloor.suppressContents();
                }
                // should never get here
                else {
                	System.out.println("Something tried to move into a spot occupied by something strange");
                    return false;
                }
            }
            // move entity into the end position
            endFloor.setContents(entity);
            startPosition.clearContents();
            startPosition.unsuppressContents();
            return true;
        }
        else
            return false;
    }
    
    /**
     * Moves an entity to a new location
     * @param entity to move
     * @param direction in which to move
     * @param amount of spaces to move
     */
    public void doMove( ActiveEntity entity , String direction , int amount ) {
        Floor startPosition = (Floor) this.getEntityAt(entity.getPosition());
        
        // Move North
        if ( direction.equals( GameConfig.NORTH_KEY ) ) {
            entity.moveNorth(amount);
            boolean canMove = doMoveHelper( entity  , startPosition );
            if (canMove == false) {
                entity.moveSouth(amount);
                System.out.println("Cannot move that direction");
            }
        }
        // Move South
        else if ( direction.equals( GameConfig.SOUTH_KEY ) ) {
            entity.moveSouth(amount);
            boolean canMove = doMoveHelper( entity , startPosition);
            if (canMove == false) {
                entity.moveNorth(amount);
                System.out.println("Cannot move that direction");
            }
        }
        // Move East
        else if ( direction.equals( GameConfig.EAST_KEY ) ) {
            entity.moveEast(amount);
            boolean canMove = doMoveHelper( entity , startPosition);
            if (canMove == false) {
                entity.moveWest(amount);
                System.out.println("Cannot move that direction");
            }
        }
        // Move West
        else if ( direction.equals( GameConfig.WEST_KEY ) ) {
            entity.moveWest(amount);
            boolean canMove = doMoveHelper( entity , startPosition);
            if (canMove == false) {
                entity.moveEast(amount);
                System.out.println("Cannot move that direction");
            }
        }
    }
    
}