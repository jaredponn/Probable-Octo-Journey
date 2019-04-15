# Probable Octo Journey: CPSC 233 Project
A top down isometric zombie shooter with a custom game engine (entity component system), custom path-finding engine, and custom collision engine with 3D spritesheets made with blender.


# Gameplay
![Gameplay](./gameplay.gif)

# Running the demo
Execute the following commands in the command line

```bash
# clone the repo
git clone https://github.com/jaredponn/probable-octo-journey

# change directory 
cd probable-octo-journey

# compile and run the project
./run.sh 
```

Note: this script only works with bash.

# Playing the game
Current version controls:
- WASD to move
- SPACE or Left Mouse Click to attack (shoot or attack with a bat)
- F to swap weapons
- Q to place a turret (if you have enough money)
- E to place a trap (if you have enough money)
- R to buy more ammo (if you have enough money)
- P to pause the game (only in the main game state)
- ECS (escape key) to quit the game (only in the main game state, and it will bring you to the end game screen)

Aim with the mouse. Go kill some zombies! See if you can get on the score board.

Rumour has it that there's an epic boss zombie fight if you can make it that far. Killing him might make you win the game and you can hear some nice victory vibes.

------------------------------
The final submission for this project used these controls, but they are not used anymore because demo players found them too confusing.
- WASD to move
- SPACE or Left mouse click to attack (shoot or attack with a bat)
- X to swap weapons
- Q to place a turret (if you have enough money)
- E to place a trap (if you have enough money)
- B to buy more ammo (if you have enough money)
- P to pause the game (only in the main game state)
- ECS (escape key) to quit the game (only in the main game state, and it will bring you to the end game screen)

# Running the unit tests
Execute the following commands in the command line

```bash
# clone the repo
git clone https://github.com/jaredponn/probable-octo-journey

# change directory
cd probable-octo-journey

# copy the "hamcrest-core-1.3.jar" and "junit-4.13.jar" files 
# into the folder probable-octo-journey/
cp <hamcrest-core-1.3.jar> .
cp <junit-4.13.jar> .

# running the unit tests
./unittests.sh

# individual unit tests can be found at src/poj/test/*
```

Note: this script only works with bash.

# Version number:
- 2019 February 24, Version 1.0 Pre-Alpha unstable release

- 2019 March 11, Version 1.1 Pre-Alpha half-stable release. Debug renderer is enabled and the red dots indicate collision box points.

- 2019 March 25, Version 1.1 Pre-Alpha half-unstable release. 

- 2019 April 13, Version 1.2 Final version. 

# Understanding the Code Base 
The book *Design Patterns: Elements of Reusable Object-Oriented Software* famously wrote that we should write code to "Favor 'object composition' over 'class inheritance'." (Gang of Four 1995:20). This code base does exactly that -- it utilizes an entity component system that favors object composition of different Component classes in EntitySets instead of inheritance. This method  results in highly generalized reusable code when used properly. 


See `src/Components/ExampleComponent.java` and `src/EntitySets/ExampleEntitySet.java` for examples on how to create a Component and how to create an EntitySet.


The entity component system (the game engine) can be found in the directory `src/poj`. It includes various generalized code for rendering, animations, and entity creation and deletion.


The game engine design came from various posts and contributions from: https://jaredponn.github.io/ and from the project https://github.com/jaredponn/improved-octo-waffle


The UML diagram can be found at:
```bash
./finaluml.png      # actual UML diagram
./simplifieduml.png # simplifed UML diagram
```

## Technical accomplishments
- The game engine features O(1) creation and deletion of new game entities.
- The collision system queries collisions in O(nlog(n)) time with an AABB binary tree to query likely collisions
- The collision system works with arbitrary convex polygons
- The path finding system does path finding in O(n) time, where n is the number of agents.
- This game supports 2 resolutions -- if the player's computer resolution is greater or equal to 1920x1080, then the game will be displayed at resolution of 1920x1080. Otherwise, it will be displayed at resolution of 1366x768. 


# Textbased Version:
See `textbased/README.md` for more information about the text based version.

# Announcements -- for Contributors:

## TODO List
- [x] Boss fight -- boss exists, but doesnt' have an exciting victory screen -- imo it should drop something you pick up, then you win. If you wanna modify this chekcout out Game/GameEvents/DefeatedBossEvent.java
- [x] Make a simplified uml diagram - for some reason our UML diagram is too complicated??
- [x] Make the player stronger as time/wave goes on, maybe done by powerups.. 
- [x] Automated tests UNIT TESTING
- [x] Wave style of zombie spawning (e.g. Max number of zombies on map is 80, will spawn wave in N seconds, have a timer for the next wave incoming)
- [x] Comments / Javadoc
- [x] Line up the hitboxes of the Collectibles(powerups, money, hp) with it's hit box
- [x] Refactor to reduce code duplication -- kinda got worse when we added the boss tho
- [x] Make the hitboxes for the tilemap - Buildings are done 03/29/19 haiyang
- [x] Haiyang - Replace buildings with solid images ... for the tilemap so buildings fit the tiles perfectly (either delete or add new blocks)
- [x] Haiyang - Game over screen (make it pretty)
- [x] Optimization
- [x] Make the zombies stronger as time/wave goes on, and give them random speeds (some fast some slow)


Maybe list (if we have time)
- [x] hp bars
- [x] Prettier HUD
- [x] move pathfinding to its component and iterate through that to decide which entities to path find. Makes it easier for the attack cycler as well
- [x] Damage numbers ? at least tell the user how much damage does the weapons do
- [x] Aligning collision boxes for the players&zombie 
- [x] Audio
- [x] Turret sprites - just need to be implemented (04/06/19 jared)
- [x] Melee sprites with a more visible sword (04/06/19 jared)
- [x] flat damage bonus instead of percent modifier
- [x] Slower zombie attacks
- [x] Put back the poles beside the buildings 
- [x] Make path find work around fences and buildings
- [x] Render the map layer properly! in render() in PlayGame
- [x] Melee attack - DONE (jared 03/24/19)
- [x] Update text based version - DONE (alex 03/24/19)
- [x] Powerups (more damage) - DONE (alex 03/24/19)
- [x] Collectibles (HP refill, ammo refill, money) - DONE (alex 03/24/19)
- [x] Finite ammo - DONE (alex 03/20/19)
- [x] Buying ammo - DONE (alex 03/22/19)/(haiyang 03/23/19)
- [x] Polishing zombie spawn points - DONE (alex 03/24/19)
- [x] Some sort of save game or high score feature
- [x] Aligning the aggro hit box properly - DONE (alex 03/20/19) 
- [x] Actually using the aggro hit box - DONE (alex 03/22/19)
- [x] Shrinking the PPhysicsHitBox of zombies and players - DONE (alex 03/22/19)
- [x] Change to the better buttons
       ``` 
- WASD to move
- SPACE to attack (shoot or attack with a bat)
- F to swap weapons
- Q to place a turret (if you have enough money)
- E to place a trap (if you have enough money)
- R to buy more ammo (if you have enough money)
- P to pause the game (only in the main game state)
       ```

## Coordinate System
The coordinate system is a little strange. The following diagram will illustrate:
```
     (-y / S)      (-x / W)
             \      /
              '    /
               \  /
                '/ (0,0)
                /\
               /  '
              /    \
             /      ' (+y / mapHeight / N )
  (+x / mapWidth / E)

- 1 mapWidth is one length of the x direction
- 1 mapHeight is one length of the y direction


```

## Using Optional in Java
Using Optional in Java (should be used for all values that may or may not exist):
```Java
Optional<Double> a = Optional.of(3d); // initilizes with value of 3
                // = Optional.empty(); // initlizes with no value
if (!a.isPresent()) // is not present
{
	// handle error
}
else
{
	// do whatever
	a.get() // gets the value
}

// 2 ways to test if the value exists or not respectively
// .isPresent() / isEmpty()
```

# Audio tracks
Gun sound: https://www.youtube.com/watch?v=GAT-AwwMP_I

Empty clip sound: https://www.youtube.com/watch?v=cqYlilUqGfs

Zombie death sounds: https://opengameart.org/content/zombies-sound-pack

Zombie spawn sounds: https://opengameart.org/content/zombie-moans

Menu sound: https://opengameart.org/content/rpg-title-screen-music-pack, used Steven Soucy - RPG Title Screen Music Pack - 03 Contingency.wav 

Menu selection sound: https://opengameart.org/content/menu-selection-click

Game background sound: https://opengameart.org/content/rpg-title-screen-music-pack, used Salvatore Maccarrone - RPG Title Screen Music Pack - 15 Overworld.wav 

Health pickup sound: https://opengameart.org/content/life-pickup-yo-frankie

Alert sound: https://opengameart.org/content/tx-alert

Player drop HP sound: https://opengameart.org/content/15-vocal-male-strainhurtpainjump-sounds

Player death sound: https://opengameart.org/content/grunts-male-death-and-pain

Player lost music: https://opengameart.org/content/skin-cells-touch

Player win music: https://opengameart.org/content/death-is-just-another-path

Boss fight music: https://opengameart.org/content/heroism
# Credits
Creeper font credit: https://www.1001fonts.com/creepster-font.html

# Useful readings
Path-finding: http://www.cs.colorado.edu/~ralex/papers/PDF/OOPSLA06antiobjects.pdf
