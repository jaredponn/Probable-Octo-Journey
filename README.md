# Probably Octo Journey: CPSC 233 Project


# Running the demo
Execute the following commands in the command line

```bash
git clone https://github.com/jaredponn/probable-octo-journey
cd probable-octo-journey
./run.sh 
```

Note: this script only works with bash.

# Version number:
- 2019 February 24, Version 1.0 Pre-Alpha unstable release

- 2019 March 11, Version 1.1 Pre-Alpha half-stable release. Debug renderer is enabled and the red dots indicate collision box points.


# Architecture / UML diagrams
The architecture uses an entity component system that favors composition.

There are 2 UML diagrams provided, which can be found in the following locations:
```bash
./simplifiedumldiagram.png # simplified UML diagram without all the classes
./demo2umldiagram.png      # actual UML diagram with all the classes in it
```

# Announcements -- for Contributors:

## TODO List
HIGH PRIORITY (Needed for 03/25/19):
- [x] Render the map layer properly! in render() in PlayGame
- [ ] Make the hitboxes for the tilemap - WIP (Ramiro)
- [ ] Aligning hit boxes with the map - WIP (Ramiro)
- [x] Melee attack - DONE (jared 03/24/19)
- [ ] Game over screen (restart / play again, go back to menu) - half done
- [x] Update text based version - DONE (alex 03/24/19)
- [x] Powerups (more damage) - DONE (alex 03/24/19)
- [x] Collectibles (HP refill, ammo refill, money) - DONE (alex 03/24/19)
- [x] Finite ammo - DONE (alex 03/20/19)
- [x] Buying ammo - DONE (alex 03/22/19)/(haiyang 03/23/19)
- [x] Polishing zombie spawn points - DONE (alex 03/24/19)
- [ ] Automated tests
- [x] Some sort of save game or high score feature

LOW PRIORITY (Not needed for 03/25/19):
- [ ] Make path find work around fences and buildings??
- [ ] Put back the poles beside the buildings and make the buildings one image
- [ ] No attack animation when there is no more ammo - seems someone broke this
- [x] Aligning the aggro hit box properly - DONE (alex 03/20/19) 
- [x] Actually using the aggro hit box - DONE (alex 03/22/19)
- [ ] Make mobs slowly move towards player when in aggro range 
- [x] Shrinking the PPhysicsHitBox of zombies and players - DONE (alex 03/22/19)
- [ ] Wave style of zombie spawning (e.g. a wave comes every 10 seconds and more zombies spawn as the game goes on)
- [ ] Boss zombies (may need more graphics from Ramiro for this)
- [ ] Audio - partially done
- [ ] Turret sprites - just need to be implemented
- [ ] Bullet sprite
- [ ] Melee sprites with a more visible sword
- [ ] Slower zombie attacks
- [ ] refine melee attacks/use animation

## Directory Hierarchy
The directory hierarchy is as follows:
```bash
src/    # source files
src/poj/ # game engine
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



Majority of the collision algorithms were from or built upon *Real-Time Collision Detection* by Christer Ericson, published by Morgan Kaufmann Publishers, Copyright 2005 Elsevier Inc.


The game engine design came from various posts and contributions from: https://jaredponn.github.io/ and from the project https://github.com/jaredponn/improved-octo-waffle


# Useful readings
Path-finding: http://www.cs.colorado.edu/~ralex/papers/PDF/OOPSLA06antiobjects.pdf


Collision detection and resolution: http://www.cs.colorado.edu/~ralex/papers/PDF/OOPSLA06antiobjects.pdf



