import java.util.Scanner;

/**
 * User Interface that handles game logic based on user input
 * @author Alex
 * @version 2.0
 */
public class UserInterface {
    
    private World gameWorld;
    private int numEnemies;
    public int turnNumber = 0;
    private Scanner scanner = new Scanner(System.in);
    
    public UserInterface() {
        gameWorld = new World();
        numEnemies = gameWorld.countEnemies();
        gameWorld.print();
        
        // Game will end when no enemies remain
        while (numEnemies > 0) {
            System.out.print("\n---------------------\n");
            System.out.println("Your Health: " + gameWorld.getPlayer().getHealth() );
            System.out.println("Your Ammo: " + gameWorld.getPlayer().getAmmo() );
            System.out.print("Please choose a direction to move:");// prompt user for input
            String input = this.getInput().toUpperCase();
            if (input.equals("W") || input.equals("A") || input.equals("S") || input.equals("D") ) {
                gameWorld.doMove( gameWorld.getPlayer() , input , 1);// moves player based on input
                turnNumber++;
            }
            else if (input.equals("8") || input.equals("2") || input.equals("6") || input.equals("4")) {
                RangedAttackHandler.attack(gameWorld , input);
                turnNumber++;
            }
            else
                System.out.println("That was not a valid command");
            
            // the enemies will move every other turn
            if ( (turnNumber % 2) == 0 ) {
                for (Entity thisEntity : gameWorld.getEntities() ) {
                    if (thisEntity instanceof Enemy) {
                        Enemy thisEnemy = (Enemy) thisEntity;
                        gameWorld.doMove(thisEnemy , thisEnemy.moveToPlayer( gameWorld.getPlayer().getPosition() ) , 1 );
                    }
                }
            }
            
            // make sure that there is no ActiveEntity with <= 0 health
            for (Entity thisEntity : gameWorld.getEntities() ) {
                if (thisEntity instanceof ActiveEntity) {
                    ActiveEntity thisActiveEntity = (ActiveEntity) thisEntity;
                    if (thisActiveEntity.getHealth() <= 0 ) {
                        Entity thisPosition = gameWorld.getEntityAt( thisActiveEntity.getPosition() );
                        Floor thisFloor = (Floor) thisPosition;
                        thisFloor.clearContents();
                        gameWorld.removeEntity( thisActiveEntity.getIndex() );
                    }
                }
            }
            
            numEnemies = gameWorld.countEnemies();
            Spawners.enemySpawner(gameWorld , turnNumber);
            Spawners.healthSpawner(gameWorld , turnNumber);
            Spawners.ammoSpawner(gameWorld , turnNumber);
            gameWorld.print();
            numEnemies = gameWorld.countEnemies();
            System.out.println("Enemies remaining: "+numEnemies);
        }
        System.out.println("**** THE GAME HAS ENDED! ****");
    }
    
    /**
     * Get user input
     * @return a string of the user input
     */
    public String getInput() {
        String input = scanner.nextLine();
        return input;
    }
    
}