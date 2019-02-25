#!/bin/bash
mkdir -p rbin

javac -d rbin/ src/*.java src/Components/*.java src/EntitySets/*.java src/Game/*.java src/poj/*.java src/poj/Collisions/*.java src/poj/Component/*.java src/poj/EntitySet/*.java src/poj/GameWindow/*.java src/poj/HList/*.java src/poj/linear/*.java src/poj/Logger/*.java src/poj/Render/*.java src/poj/Time/*.java src/Resources/*.java src/TileMap/*.java && java -cp rbin/ Main
