/**
 * A sub-class of ActiveEntity for the player character
 * @author Alex
 * @version 1.0
 */
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