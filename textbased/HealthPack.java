/**
* A type of pick-up that gives the player more health
* @author Alex
* @version 2.0
*/
public class HealthPack extends PickUp{
    
    public HealthPack(int defIndex) {
        this(GameConfig.HEALTH_PICKUP_AMOUNT , GameConfig.HEALTH_PICKUP_SPAWNER , defIndex);
    }
    
    public HealthPack(int defAmount , int defX , int defY , int defIndex) {
        this(defAmount , new Position(defX,defY) , defIndex);
    }
    
    public HealthPack(int defAmount , Position pos , int defIndex ) {
        super(defAmount , pos , defIndex);
        setDisplayChar("+ ");
    }
    
    public void collect(Player player) {
        player.heal(getAmount());
    }
}