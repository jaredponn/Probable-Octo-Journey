#!/bin/bash

# compiling

javac -d rbin/ -cp .:junit-4.13.jar:hamcrest-core-1.3.jar src/*.java src/Components/*.java src/EntitySets/*.java src/Game/*.java src/Game/GameEvents*.java src/poj/*.java src/App/*.java src/poj/Collisions/*.java src/poj/Component/*.java src/poj/EntitySet/*.java src/poj/GameWindow/*.java src/poj/HList/*.java src/poj/linear/*.java src/poj/Logger/*.java src/poj/Render/*.java src/poj/Time/*.java src/Resources/*.java src/TileMap/*.java src/PathFinding/*.java src/poj/test/*.java

JUNITVAR=.:junit-4.13.jar:hamcrest-core-1.3.jar:rbin/

java -cp $JUNITVAR org.junit.runner.JUnitCore poj.test.GJKTests
java -cp $JUNITVAR org.junit.runner.JUnitCore poj.test.MatrixEquality
java -cp $JUNITVAR org.junit.runner.JUnitCore poj.test.PackedVectorTest
java -cp $JUNITVAR org.junit.runner.JUnitCore poj.test.PolyonTest
java -cp $JUNITVAR org.junit.runner.JUnitCore poj.test.ShapeTests
java -cp $JUNITVAR org.junit.runner.JUnitCore poj.test.Vector2MatrixTransformTest
java -cp $JUNITVAR org.junit.runner.JUnitCore poj.test.Vector2fTest


