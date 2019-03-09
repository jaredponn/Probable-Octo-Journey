/**
 * Sub-class of Entity that can 'contain' other entities
 * @author Alex
 * @version 1.0
 */
public class Floor extends Entity {
    
    private Entity contents;
    private boolean hasContents = false;
    
    public Floor( int defIndex ) {
        super( defIndex );
        setDisplayChar(". ");
    }
    
    public Floor( int defX , int defY , int defIndex ) {
        super( defX , defY , defIndex );
        setDisplayChar(". ");
    }
    
    ///// Getters /////
    public Entity getContents() {
        return contents;
    }
    
    public boolean hasContents() {
        return this.hasContents;
    }
    
    ///// Setters /////
    /**
     * Set this entity to 'contain' another entity and
     * sets the hasContents flag to true
     * @param newContent: the entity for this entity to 'contain'
     */
    public void setContents( Entity newContent ) {
        this.contents = newContent;
        this.hasContents = true;
    }
    
    /**
     * Removes the contents of this entity and
     * sets the hasContents flag to false
     */
    public void clearContents() {
        this.contents = null;
        this.hasContents = false;
    }
    
    ///// Print /////
    /**
     * Overrides this entity's DisplayChar with that of its contents
     */
    public void print() {
        if ( this.contents != null )
            System.out.print( this.contents.getDisplayChar() );
        else
            System.out.print( this.getDisplayChar() );
    }
    
}