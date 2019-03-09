import java.util.Scanner;

/**
 * User Interface that handles game logic based on user input
 * @author Alex
 * @version 1.0
 */
public class UserInterface {
    
    private World gameWorld;
    private int numEnemies;
    private Scanner scanner = new Scanner(System.in);
    
    public UserInterface() {
        gameWorld = new World();
        boolean enemyTurn = false;
        numEnemies = gameWorld.countEnemies();
        gameWorld.print();
        
        // Game will end when no enemies remain
        while (numEnemies > 0) {
            System.out.print("\n---------------------\n");
            System.out.println("Your Health: " + gameWorld.getPlayer().getHealth() );
            System.out.print("Please choose a direction to move:");// prompt user for input
            String input = this.getInput().toUpperCase();
            gameWorld.doMove( gameWorld.getPlayer() , input , 1);// moves player based on input
            
            // the enemies will move every other turn
            if (enemyTurn) {
                for (Entity thisEntity : gameWorld.getEntities() ) {
                    if (thisEntity instanceof Enemy) {
                        Enemy thisEnemy = (Enemy) thisEntity;
                        gameWorld.doMove(thisEnemy , thisEnemy.moveToPlayer( gameWorld.getPlayer().getPosition() ) , 1 );
                    }
                }
                enemyTurn = false;
            }
            else
                enemyTurn = true;
            
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
            gameWorld.print();
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