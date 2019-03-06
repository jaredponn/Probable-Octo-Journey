public class Floor extends Entity {
    
    private Entity contents;
    private boolean hasContents = false;
    
    public Floor( int defIndex ) {
        super( defIndex );
        setDisplayChar("O ");
    }
    
    public Floor( int defX , int defY , int defIndex ) {
        super( defX , defY , defIndex );
        setDisplayChar("O ");
    }
    
    ///// Getters /////
    public Entity getContents() {
        return contents;
    }
    
    public boolean hasContents() {
        return this.hasContents;
    }
    
    ///// Setters /////
    public void setContents( Entity newContent ) {
        this.contents = newContent;
        this.hasContents = true;
    }
    
    public void clearContents() {
        this.contents = null;
        this.hasContents = false;
    }
    
    ///// Print /////
    public void print() {
        if ( this.contents != null )
            System.out.print( this.contents.getDisplayChar() );
        else
            System.out.print( this.getDisplayChar() );
    }
    
}