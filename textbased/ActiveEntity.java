/**
 * Sub-class of Entity that gives movement and health
 * @author Alex
 * @version 1.0
 */
public class ActiveEntity extends Entity {
    
    private int health;
    
    public ActiveEntity( int defIndex ) {
        super( defIndex );
        setCollidable(true);
    }
    
    public ActiveEntity( int defHealth , int defX , int defY , int defIndex ) {
        super( defX , defY , defIndex );
        this.health = defHealth;
        setCollidable(true);
    }
    
    ///// Getters /////
    public int getHealth() {
        return this.health;
    }
    
    ///// Setters /////
    public void setHealth( int amount ) {
        this.health = amount;
    }
    
    public void heal( int amount ) {
        this.health += amount;
    }
    
    public void hurt( int amount ) {
        this.health -= amount;
    }
    
    ///// Movement /////
    /**
     * Moves entity north by input amount.
     * WARNING: movement only looks at start and end positions so
     * it is possible to move through obstacles and off the world
     * if amount > 1
     * @param amount: the number of spaces to move
     */
    public void moveNorth( int amount ) {
        setYPosition( getYPosition() - amount );
    }
    
    /**
     * Moves entity south by input amount.
     * WARNING: movement only looks at start and end positions so
     * it is possible to move through obstacles and off the world
     * if amount > 1
     * @param amount: the number of spaces to move
     */
    public void moveSouth( int amount ) {
        setYPosition( getYPosition() + amount );
    }
    
    /**
     * Moves entity west by input amount.
     * WARNING: movement only looks at start and end positions so
     * it is possible to move through obstacles and off the world
     * if amount > 1
     * @param amount: the number of spaces to move
     */
    public void moveWest( int amount ) {
        setXPosition( getXPosition() - amount );
    }
    
    /**
     * Moves entity east by input amount.
     * WARNING: movement only looks at start and end positions so
     * it is possible to move through obstacles and off the world
     * if amount > 1
     * @param amount: the number of spaces to move
     */
    public void moveEast( int amount ) {
        setXPosition( getXPosition() + amount );
    }
}