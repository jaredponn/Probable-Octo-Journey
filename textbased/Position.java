/**
 * A basic vector for storing x and y coordinates
 * @author Alex
 * @version 2.0
 */
public class Position {
    
    private int xPos;
    private int yPos;
    
    /**
     * Basic constructor
     * @param x: starting x-coordinate
     * @param y: starting y-coordinate
     */
    public Position( int x , int y ) {
        this.xPos = x;
        this.yPos = y;
    }
    
    /**
     * Copy constructor
     * @param positionToCopy: Position to copy to new position
     */
    public Position( Position positionToCopy ) {
        this.xPos = positionToCopy.getXPos();
        this.yPos = positionToCopy.getYPos();
    }
    
    ///// Getters /////
    public int getXPos() {
        return this.xPos;
    }
    
    public int getYPos() {
        return this.yPos;
    }
    
    public String toString() {
        return "X: " + this.xPos + " | Y: " + this.yPos;
    }
    
    public void print() {
        System.out.println(this.toString());
    }
    
    ///// Setters /////
    public void setXPos( int x ) {
        this.xPos = x;
    }
    
    public void setYPos(int y ) {
        this.yPos = y;
    }
}