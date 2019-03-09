/**
 * Collidable sub-class of Entity
 * @author Alex
 * @version 1.0
 */
public class Wall extends Entity {
    
    public Wall( int defIndex ) {
        super( defIndex );
        setCollidable(true);
        setDisplayChar("X ");
    }
    
    public Wall( int defX , int defY , int defIndex ) {
        super( defX , defY , defIndex );
        setCollidable(true);
        setDisplayChar("X ");
    }
    
}