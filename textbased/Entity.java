public class Entity {
    
    private boolean isCollidable = false;
    private Position position;
    private String displayChar = "! ";
    private int index;
    
    public Entity( int defIndex ) {
        this.position = new Position( 0 , 0 );
        this.index = defIndex;
    }
    
    public Entity( int defX , int defY , int defIndex ) {
        this.position = new Position( defX , defY );
        this.index = defIndex;
    }
    
    ///// Getters /////
    public Position getPosition() {
        return this.position;
    }
    
    public int getXPosition() {
        return this.position.getXPos();
    }
    
    public int getYPosition() {
        return this.position.getYPos();
    }
    
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
    
    public void setDisplayChar( String newDisplay ) {
        this.displayChar = newDisplay;
    }
    
    ///// Movement ///// (now in ActiveEntity)
    // public void moveNorth( int amount ) {
        // setYPosition( getYPosition() - amount );
    // }
    
    // public void moveSouth( int amount ) {
        // setYPosition( getYPosition() + amount );
    // }
    
    // public void moveWest( int amount ) {
        // setXPosition( getXPosition() - amount );
    // }
    
    // public void moveEast( int amount ) {
        // setXPosition( getXPosition() + amount );
    // }
    
    ///// Print /////
    public void print() {
        System.out.print(this.displayChar);
    }
    
    public String toString() {
        String thisString = "X= "+this.getXPosition()+" | Y= "+this.getYPosition();
        return thisString;
    }
}