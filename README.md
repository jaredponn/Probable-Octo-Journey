# Probable Octo Journey: CPSC 233 Project
A top down isometric zombie shooter with a custom game engine (entity component system), path-finding engine, and collision engine.


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
- WASD to move
- SPACE to attack (shoot or attack with a bat)
- X to swap weapons
- Q to place a turret (if you have enough money)
- B to buy more ammo (if you have enough money)

Aim with the mouse.Go kill some zombies! See if you can get on the score board.


NOTES: unstable release still. Many collision boxes have not been implemented yet.


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


# Understanding the Code Base 
The book *Design Patterns: Elements of Reusable Object-Oriented Software* famously wrote that we should write code to "Favor 'object composition' over 'class inheritance'." (Gang of Four 1995:20). This code base does exactly that -- it utilizes an entity component system that favors object composition of different Component classes in EntitySets instead of inheritance. This method  results in highly generalized reusable code when used properly. 


See `src/Components/ExampleComponent.java` and `src/EntitySets/ExampleEntitySet.java` for examples on how to create a Component and how to create an EntitySet.


The entity component system (the game engine) can be found in the directory `src/poj`. It includes various generalized code for rendering, animations, and entity creation and deletion.


The game engine design came from various posts and contributions from: https://jaredponn.github.io/ and from the project https://github.com/jaredponn/improved-octo-waffle


The UML diagram can be found at:
```bash
./demo3umldiagram.png   
```
# Textbased Version:
See `textbased/README.md` for more information about the text based version.

# Announcements -- for Contributors:

## TODO List
- [ ] Boss fight
- [ ] Make the zombies stronger as time/wave goes on, and give them random speeds (some fast some slow)
- [ ] Make the player stronger as time/wave goes on, maybe done by powerups..
- [ ] Haiyang - Replace buildings with solid images ... for the tilemap so buildings fit the tiles perfectly (either delete or add new blocks)
- [ ] Make the hitboxes for the tilemap - Buildings are done 03/29/19 haiyang
- [ ] Haiyang - Game over screen (make it pretty)
- [ ] Automated tests
- [ ] Wave style of zombie spawning (e.g. Max number of zombies on map is 80, will spawn wave in N seconds, have a timer for the next wave incoming)
- [ ] Refactor to reduce code duplication
- [ ] Comments / Javadoc
- [ ] Optimization
- [ ] Line up the hitboxes of the Collectibles(powerups, money, hp) with it's hit box


Maybe list (if we have time)
- [x] hp bars
- [ ] Boss zombies (may need more graphics from Ramiro for this)
- [ ] Prettier HUD
- [ ] move pathfinding to its component and iterate through that to decide which entities to path find. Makes it easier for the attack cycler as well



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
# Credits
Creeper font credit: https://www.1001fonts.com/creepster-font.html

# Useful readings
Path-finding: http://www.cs.colorado.edu/~ralex/papers/PDF/OOPSLA06antiobjects.pdf


Collision detection and resolution: http://www.cs.colorado.edu/~ralex/papers/PDF/OOPSLA06antiobjects.pdf



