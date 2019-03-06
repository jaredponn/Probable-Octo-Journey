public class PickUp extends Entity {
    
    private int pickupAmount;
    
    public PickUp( int amount , int defIndex ) {
        super( defIndex );
        this.pickupAmount = amount;
        setDisplayChar("$ ");
    }
    
    public PickUp( int amount , int defX , int defY , int defIndex ) {
        super( defX , defY , defIndex );
        this.pickupAmount = amount;
        setDisplayChar("$ ");
    }
    
    public int getAmount() {
        return this.pickupAmount;
    }
    
    public void setAmount( int newAmount ) {
        this.pickupAmount = newAmount;
    }
}