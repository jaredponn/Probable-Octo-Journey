/**
 * A sub-class of entity that will give the player health when
 * the player moves into the same position.
 * @author Alex
 * @version 2.0
 */
public abstract class PickUp extends Entity {
    
    private int pickupAmount;
    
    /**
     * Basic contructor
     * @param amount: amount of health this pick-up will provide
     * @param defIndex: the index of this entity in the worlds entities ArrayList
     */
    public PickUp( int amount , int defIndex ) {
        super( defIndex );
        this.pickupAmount = amount;
        setDisplayChar("X ");
    }
    
    /**
     * Constructor with location
     * @param amount: amount of health this pick-up will provide
     * @param defX: X-location to spawn entity at
     * @param defY: Y-location to spawn entity at
     * @param defIndex: the index of this entity in the worlds entities ArrayList
     */
    public PickUp( int amount , int defX , int defY , int defIndex ) {
        this(amount , new Position(defX,defY) , defIndex );
    }
    
    public PickUp( int amount , Position pos , int defIndex ) {
        super( pos , defIndex );
        this.pickupAmount = amount;
        setDisplayChar("X ");
    }
    
    public abstract void collect(Player player);
    
    ///// Getters /////
    public int getAmount() {
        return this.pickupAmount;
    }
    
    ///// Setters /////
    public void setAmount( int newAmount ) {
        this.pickupAmount = newAmount;
    }
}