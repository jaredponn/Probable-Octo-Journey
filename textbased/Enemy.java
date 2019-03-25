/**
 * Sub-class of ActiveEntity that has basic pathfinding
 * @author Alex
 * @version 2.0
 */
public class Enemy extends ActiveEntity {
    
    public Enemy( int defIndex ) {
        this( 1 , 1 , defIndex );
    }
    
    public Enemy( int defX , int defY , int defIndex ) {
        super( GameConfig.ENEMY_HEALTH , defX , defY , defIndex );
        setDisplayChar("E ");
    }
    
    public Enemy( Position pos , int defIndex ) {
        this( pos.getXPos() , pos.getYPos() , defIndex );
    }
    
    /**
    * returns a semi random direction for an enemy to move towards the player
    * @param playerPosition: the current world position of the player
    * @return the direction this enemy needs to move to reach the player
    */
    public String moveToPlayer( Position playerPosition ) {
        int thisXPos = this.getXPosition();
        int thisYPos = this.getYPosition();
        int playerXPos = playerPosition.getXPos();
        int playerYPos = playerPosition.getYPos();
        double roll = Math.random();
        
        if (thisXPos != playerXPos && roll >= 0.5 ) {
            int xDifference = playerXPos - thisXPos; 
            if (xDifference > 0)
                return GameConfig.EAST_KEY;
            else
                return GameConfig.WEST_KEY;
        }
        
        else if (thisYPos != playerYPos) {
            int yDifference = playerYPos - thisYPos;
            if (yDifference > 0)
                return GameConfig.SOUTH_KEY;
            else
                return GameConfig.NORTH_KEY;
        }
        
        // in case it needs to move east/west but roll < 0.5 and did not move north/south
        else if (thisXPos != playerXPos) {
            int xDifference = playerXPos - thisXPos; 
            if (xDifference > 0)
                return GameConfig.EAST_KEY;
            else
                return GameConfig.WEST_KEY;
        }
        
        else
            return "";
    }
}