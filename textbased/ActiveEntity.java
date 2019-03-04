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
    public void moveNorth( int amount ) {
        setYPosition( getYPosition() - amount );
    }
    
    public void moveSouth( int amount ) {
        setYPosition( getYPosition() + amount );
    }
    
    public void moveWest( int amount ) {
        setXPosition( getXPosition() - amount );
    }
    
    public void moveEast( int amount ) {
        setXPosition( getXPosition() + amount );
    }
}