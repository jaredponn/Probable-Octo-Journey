public class Enemy extends ActiveEntity {
    
    public Enemy( int defIndex ) {
        super( defIndex );
        setHealth( GameConfig.ENEMY_HEALTH );
        setDisplayChar("E ");
    }
    
    public Enemy( int defX , int defY , int defIndex ) {
        super( GameConfig.ENEMY_HEALTH , defX , defY , defIndex );
        setDisplayChar("E ");
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
            // System.out.println("This enemy need to move east by " + xDifference + " spaces to reach the player");
            if (xDifference > 0)
                return GameConfig.EAST_KEY;
            else
                return GameConfig.WEST_KEY;
        }
        
        else if (thisYPos != playerYPos) {
            int yDifference = playerYPos - thisYPos;
            // System.out.println("This enemy need to move south by " + yDifference + " spaces to reach the player");
            if (yDifference > 0)
                return GameConfig.SOUTH_KEY;
            else
                return GameConfig.NORTH_KEY;
        }
        
        // in case it needs to move east/west but roll < 0.5 and did not move north/south
        else if (thisXPos != playerXPos) {
            int xDifference = playerXPos - thisXPos; 
            // System.out.println("This enemy need to move east by " + xDifference + " spaces to reach the player");
            if (xDifference > 0)
                return GameConfig.EAST_KEY;
            else
                return GameConfig.WEST_KEY;
        }
        
        else
            return "";
    }
}