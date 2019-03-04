public class Position {
    
    private int xPos;
    private int yPos;
    
    public Position( int x , int y ) {
        this.xPos = x;
        this.yPos = y;
    }
    
    public Position( Position positionToCopy ) {
        this.xPos = positionToCopy.getXPos();
        this.yPos = positionToCopy.getYPos();
    }
    
    public void setXPos( int x ) {
        this.xPos = x;
    }
    
    public void setYPos(int y ) {
        this.yPos = y;
    }
    
    public int getXPos() {
        return this.xPos;
    }
    
    public int getYPos() {
        return this.yPos;
    }
}