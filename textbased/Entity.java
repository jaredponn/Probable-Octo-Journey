/**
 * Default entity for textbased game
 * @author Alex
 * @version 1.0
 */
public class Entity {
    
    private boolean isCollidable = false;
    private Position position;
    private String displayChar = "! ";
    private int index;
    
    /**
     * Creates new entity at default world location (0,0)
     * @param defIndex: the index of this entity in the worlds entities ArrayList
     */
    public Entity( int defIndex ) {
        this.position = new Position( 0 , 0 );
        this.index = defIndex;
    }
    
    /**
     * Creates new entity at world location (defX,defY)
     * @param defX: X-location to spawn entity at
     * @param defY: Y-location to spawn entity at
     * @param defIndex: the index of this entity in the worlds entities ArrayList
     */
    public Entity( int defX , int defY , int defIndex ) {
        this.position = new Position( defX , defY );
        this.index = defIndex;
    }
    
    ///// Getters /////
    /**
     * Get this entities world position
     * @return Position object of this entity
     */
    public Position getPosition() {
        return this.position;
    }
    
    public int getXPosition() {
        return this.position.getXPos();
    }
    
    public int getYPosition() {
        return this.position.getYPos();
    }
    
    /**
     * Finds out if this entity is flaged as collidable
     * @return true if this entity is flaged as collidable
     */
    public boolean isCollidable() {
        return this.isCollidable;
    }
    
    public String getDisplayChar() {
        return this.displayChar;
    }
    
    public int getIndex() {
        return this.index;
    }
    
    ///// Setters /////
    public void setCollidable( boolean value ) {
        this.isCollidable = value;
    }
    
    public void setXPosition( int x ) {
        this.position.setXPos( x );
    }
    
    public void setYPosition( int y ) {
        this.position.setYPos( y );
    }
    
    /**
     * Define how this entity is displayed when the current game world is printed
     * @param newDisplay: should be a single character followed by a space to display properly
     */
    public void setDisplayChar( String newDisplay ) {
        this.displayChar = newDisplay;
    }
    
    ///// Print /////
    /**
     * Used when printing the current game world to show the player what entity is where
     */
    public void print() {
        System.out.print(this.displayChar);
    }
    
    public String toString() {
        String thisString = "X= "+this.getXPosition()+" | Y= "+this.getYPosition();
        return thisString;
    }
}