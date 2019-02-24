# Probably Octo Journey: CPSC 233 Project
This is super strange

# Announcements:
- The coordinate system is a little strange. The following diagram will illustrate:
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

- Using Optional in Java (should be used for all values that may or may not exist):
```Java
Optional<Double> a = Optional.of(3d); // initilizes with value of 3
                // = Optional.empty(); // initlizes with no value
if (a.isEmpty())
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

# Credits
Tileset: https://opengameart.org/content/cave-tileset
Turrent: https://opengameart.org/content/orange-defense-gun-isometric


Majority of the collision algorithms were from or built upon /*Real-Time Collision Detection*/ by Christer Ericson, published by Morgan Kaufmann Publishers, Copyright 2005 Elsevier Inc.


# Useful readings
Path-finding: http://www.cs.colorado.edu/~ralex/papers/PDF/OOPSLA06antiobjects.pdf

Collision detection and resolution: http://www.cs.colorado.edu/~ralex/papers/PDF/OOPSLA06antiobjects.pdf



