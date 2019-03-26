#!/bin/bash

javac -cp .:junit-4.12.jar:hamcrest-core-1.3.jar src/poj/test*.java && java -cp .:junit-4.12.jar:hamcrest-core-1.3.jar org.junit.runner.JUnitCore src/poj/test/GJKTests src/poj/test/MatrixEquality src/poj/test/MatrixTest src/poj/test/PackedVectorTest src/poj/test/PolyonTest src/poj/test/ShapeTests src/poj/test/Vector2MatrixTransformTest src/poj/test/Vector2fTest
