# Probable Octo Journey: CPSC 233 Project


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
The book *Design Patterns: Elements of Reusable Object-Oriented Software* famously wrote that we should write code to "Favor 'object composition' over 'class inheritance'." (Gang of Four 1995:20). This code base does exactly that -- it utilizes an entity component system that favors object composition of different Component classes in EntitySets instead of inheritance which results in highly generalized reusable code when used properly. See `src/Components/ExampleComponent.java` and `src/EntitySets/ExampleEntitySet.java` for examples.


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
- [ ] Initials for the GAME OVER SCREEN (Ramiro)
- [ ] Make the hitboxes for the tilemap - WIP (Ramiro)
- [ ] Aligning hit boxes with the map - WIP (Ramiro)
- [ ] Aligning collision boxes for the players&zombie 
- [ ] Game over screen (restart / play again, go back to menu) - half done
- [ ] Automated tests
- [ ] No attack animation when there is no more ammo - seems someone broke this
- [ ] Make mobs slowly move towards player when in aggro range 
- [ ] Wave style of zombie spawning (e.g. a wave comes every 10 seconds and more zombies spawn as the game goes on)
- [ ] Boss zombies (may need more graphics from Ramiro for this)
- [ ] Audio - partially done
- [ ] Turret sprites - just need to be implemented
- [ ] Bullet sprite
- [ ] Melee sprites with a more visible sword
- [ ] Slower zombie attacks
- [ ] refine melee attacks/use animation
- [ ] make trying to close game window generate a confirmation prompt 
- [ ] ability to enter name/initials on high score screen
- [ ] pause game (maybe)
- [ ] move pathfinding to its component and iterate through that to decide which entities to path find. Makes it easier for the attack cycler as well


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

Zombie sounds: https://opengameart.org/content/zombies-sound-pack

Menu sound: https://opengameart.org/content/rpg-title-screen-music-pack, used Steven Soucy - RPG Title Screen Music Pack - 03 Contingency.wav 

Menu selection sound: https://opengameart.org/content/menu-selection-click

Game background sound: https://opengameart.org/content/rpg-title-screen-music-pack, used Salvatore Maccarrone - RPG Title Screen Music Pack - 15 Overworld.wav 

Health pickup sound: https://opengameart.org/content/life-pickup-yo-frankie

Alert sound: https://opengameart.org/content/tx-alert

Player drop HP sound: https://opengameart.org/content/15-vocal-male-strainhurtpainjump-sounds
# Credits
Tileset: https://opengameart.org/content/cave-tileset


Turret: https://opengameart.org/content/orange-defense-gun-isometric


Coin: https://opengameart.org/content/spinning-pixel-coin-0


# Useful readings
Path-finding: http://www.cs.colorado.edu/~ralex/papers/PDF/OOPSLA06antiobjects.pdf


Collision detection and resolution: http://www.cs.colorado.edu/~ralex/papers/PDF/OOPSLA06antiobjects.pdf



