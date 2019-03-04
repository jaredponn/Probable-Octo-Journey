public class Player extends ActiveEntity {
    
    public Player( int defIndex ) {
        super( defIndex );
        setHealth( GameConfig.PLAYER_HEALTH );
        setDisplayChar("P ");
    }
    
    public Player( int defHealth , int defX , int defY , int defIndex ) {
        super( defHealth , defX , defY , defIndex );
        setDisplayChar("P ");
    }
}