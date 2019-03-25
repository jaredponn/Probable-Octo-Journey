/**
 * Sub-class of Entity that can 'contain' other entities
 * @author Alex
 * @version 2.0
 */
public class Floor extends Entity {
    
    private Entity contents = GameConfig.NULL_ENTITY;
    private Entity suppressedContents = GameConfig.NULL_ENTITY;
    private boolean hasContents = false;
    private boolean hasSupressedContents = false;
    
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
        this.contents = GameConfig.NULL_ENTITY;
        this.hasContents = false;
    }
    
    /**
    * Suppresses contents to allow enemies to move onto a floor with a pick-up
    */
    public void suppressContents() {
        if (this.hasContents() ) {
            this.suppressedContents = this.contents;
            this.hasSupressedContents = true;
            this.clearContents();
            
            if (this.suppressedContents instanceof ActiveEntity) {
                System.out.println("Just suppressed an ActiveEntity! This should not be happening!");
            }
        }
    }
    
    /**
    * Unsuppresses contents when enemy moves off floor
    */
    public void unsuppressContents() {
        if (this.hasSupressedContents) {
            setContents(this.suppressedContents);
            this.suppressedContents = GameConfig.NULL_ENTITY;
            this.hasSupressedContents = false;
        }
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