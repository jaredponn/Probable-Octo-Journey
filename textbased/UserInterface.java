import java.util.Scanner;

public class UserInterface {
    
    private World gameWorld;
    private int numEnemies;
    
    public UserInterface() {
        gameWorld = new World();
        boolean enemyTurn = false;
        numEnemies = gameWorld.countEnemies();
        gameWorld.print();
        
        while (numEnemies > 0) {
            System.out.print("\n---------------------\n");
            System.out.println("Your Health: " + gameWorld.getPlayer().getHealth() );
            System.out.print("Please choose a direction to move:");
            String input = this.getInput().toUpperCase();
            gameWorld.doMove( gameWorld.getPlayer() , input , 1);
            
            // the enemies will move every other turn
            if (enemyTurn) {
                for (Entity thisEntity : gameWorld.getEntities() ) {
                    if (thisEntity instanceof Enemy) {
                        Enemy thisEnemy = (Enemy) thisEntity;
                        gameWorld.doMove(thisEnemy , thisEnemy.moveToPlayer( gameWorld.getPlayer().getPosition() ) , 1 );
                    }
                }
                // System.out.println("The enemies should move now");
                enemyTurn = false;
            }
            else
                enemyTurn = true;
            
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
    
    private Scanner scanner = new Scanner(System.in);
    
    public String getInput() {
        String input = scanner.nextLine();
        return input;
    }
    
}