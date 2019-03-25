/**
 * A sub-class of ActiveEntity for the player character
 * @author Alex
 * @version 2.0
 */
public class Player extends ActiveEntity {
    
    private int ammo;
    
    public Player( int defIndex ) {
        this( GameConfig.PLAYER_HEALTH , GameConfig.PLAYER_SPAWN_X , GameConfig.PLAYER_SPAWN_Y , defIndex );
    }
    
    public Player( int defHealth , int defX , int defY , int defIndex ) {
        super( defHealth , defX , defY , defIndex );
        setDisplayChar("P ");
        this.ammo = GameConfig.PLAYER_STARTING_AMMO;
    }
    
    ///// Getters /////
    /**
    * how much ammo does the player have
    * @return the amount of ammo the player has
    */
    public int getAmmo() {
        return this.ammo;
    }
    
    /**
    * check if the player has at least a certain amount of ammo
    * @param amount: how much ammo the player needs to have
    * @return true if the player has at least <amount> ammo
    */
    public boolean hasAmmo( int amount ) {
        if (this.ammo >= amount)
            return true;
        else
            return false;
    }
    
    ///// Setters /////
    /**
    * set the ammount of ammo the player has
    * @param amount: the total amount of ammo the player now has
    */
    public void setAmmo( int amount ) {
        this.ammo = amount;
    }
    
    /**
    * increase the amount of ammo the player has (negative numbers subtract)
    * @param the amount to add to the players ammo
    */
    public void addAmmo( int amount ) {
        this.ammo += amount;
    }
}