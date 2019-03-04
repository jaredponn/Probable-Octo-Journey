import java.util.ArrayList;

public class World {
    
    private ArrayList<Entity> entities = new ArrayList<Entity>();
    private ArrayList<ArrayList<Entity>> map = new ArrayList<ArrayList<Entity>>();
    private int playerIndex;
    private int lastIndex = 0;
    
    public World() {
        createMap();
        spawnWalls();
        putWallsOnMap();
        spawnFloors();
        putFloorsOnMap();
        spawnPlayer( GameConfig.PLAYER_SPAWN_X , GameConfig.PLAYER_SPAWN_Y );
        spawnEnemies();
        spawnPickup();
    }
    
    private void createMap() {
        for ( int x = 0 ; x < GameConfig.MAP_WIDTH ; x++ ) {
            map.add(new ArrayList<Entity>(  ));
            for ( int y = 0 ; y < GameConfig.MAP_HEIGHT ; y++)
                map.get(x).add(null);
        }
    }
    
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
    }
    
    private void spawnFloors() {
        for ( int x = 1 ; x < GameConfig.MAP_WIDTH - 1 ; x++ ) {
            for ( int y = 1 ; y < GameConfig.MAP_HEIGHT - 1 ; y++ ) {
                entities.add( new Floor( x , y , this.lastIndex ) );
                this.lastIndex += 1;
            }
        }
    }
    
    private void putWallsOnMap() {
        for (Entity thisEntity : entities) {
            if (thisEntity instanceof Wall) {
                int thisWallColumn = thisEntity.getXPosition();
                map.get(thisWallColumn).set( thisEntity.getYPosition() , thisEntity );
            }
        }
    }
    
    private void putFloorsOnMap() {
        for (Entity thisEntity : entities) {
            if (thisEntity instanceof Floor) {
                int thisFloorColumn = thisEntity.getXPosition();
                map.get(thisFloorColumn).set( thisEntity.getYPosition() , thisEntity );
            }
        }
    }
    
    public void spawnPlayer( int x , int y) {
        Player player = new Player( GameConfig.PLAYER_HEALTH , x , y , this.lastIndex );
        entities.add( player );
        this.lastIndex += 1;
        this.playerIndex = this.lastIndex - 1;
        Floor playerSpawnPoint = (Floor) map.get(x).get(y);
        playerSpawnPoint.setContents(player);
    }
    
    public void spawnPickup() {
        PickUp pickup = new PickUp( GameConfig.HEALTH_PICKUP_AMOUNT , 10 , 10 , this.lastIndex );
        entities.add( pickup );
        this.lastIndex += 1;
        
        Floor spawnPoint = (Floor) this.getEntityAt( pickup.getPosition() );
        spawnPoint.setContents( pickup );
        
    }
    
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
    
    public int countEnemies() {
        int count = 0;
        for (Entity thisEntity : entities) {
            if (thisEntity instanceof Enemy)
                count++;
        }
        return count;
    }
    
    public void removeEntity( int index ) {
        entities.set( index , new Entity( -1 ) );
        System.out.println("Entity Removed!");
        if (index == this.playerIndex) {
            System.out.println("The Player Has Been Defeated!");
            System.exit(0);
        }
    }
    
    private boolean doMoveHelper(Entity entity , Floor startPosition) {
        Entity endPosition = this.getEntityAt( entity.getPosition() );
        if (endPosition instanceof Floor) {
            Floor endFloor = (Floor) endPosition;
            if (endFloor.hasContents()){
                if (entity instanceof Player && endFloor.getContents() instanceof Enemy) {
                    Enemy thisEnemy = (Enemy) endFloor.getContents();
                    thisEnemy.hurt( 33 );
                    System.out.println("Enemy health now at " + thisEnemy.getHealth() );
                    if (thisEnemy.getHealth() <= 0) {
                        removeEntity( thisEnemy.getIndex() );
                        endFloor.clearContents();
                    }
                    return false;
                }
                else if (entity instanceof Enemy && endFloor.getContents() instanceof Player) {
                    Player player = (Player) endFloor.getContents();
                    player.hurt( 33 );
                    return false;
                }
                else if (entity instanceof Player && endFloor.getContents() instanceof PickUp) {
                    Player player = (Player) entity;
                    PickUp pickup = (PickUp) endFloor.getContents();
                    player.heal( pickup.getAmount() );
                    removeEntity( pickup.getIndex() );
                    endFloor.clearContents();
                }
                else
                    return false;
            }
            endFloor.setContents(entity);
            startPosition.clearContents();
            return true;
        }
        else
            return false;
    }
    
    public void doMove( ActiveEntity entity , String direction , int amount ) {
        Floor startPosition = (Floor) this.getEntityAt(entity.getPosition());
        
        if ( direction.equals( GameConfig.NORTH_KEY ) ) {
            entity.moveNorth(amount);
            boolean canMove = doMoveHelper( entity  , startPosition );
            if (canMove == false) {
                entity.moveSouth(amount);
                System.out.println("Cannot move that direction");
            }
        }
        else if ( direction.equals( GameConfig.SOUTH_KEY ) ) {
            entity.moveSouth(amount);
            boolean canMove = doMoveHelper( entity , startPosition);
            if (canMove == false) {
                entity.moveNorth(amount);
                System.out.println("Cannot move that direction");
            }
        }
        else if ( direction.equals( GameConfig.EAST_KEY ) ) {
            entity.moveEast(amount);
            boolean canMove = doMoveHelper( entity , startPosition);
            if (canMove == false) {
                entity.moveWest(amount);
                System.out.println("Cannot move that direction");
            }
        }
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