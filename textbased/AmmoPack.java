/**
* A type of pick-up that gives the player more ammo
* @author Alex
* @version 2.0
*/
public class AmmoPack extends PickUp{
    
    public AmmoPack(int defIndex) {
        this(GameConfig.AMMO_PICKUP_AMOUNT , GameConfig.AMMO_PICKUP_SPAWNER , defIndex);
    }
    
    public AmmoPack(int defAmount , Position pos , int defIndex ) {
        super(defAmount , pos , defIndex);
        setDisplayChar("A ");
    }
    
    public void collect(Player player) {
        player.addAmmo(getAmount());
    }
}