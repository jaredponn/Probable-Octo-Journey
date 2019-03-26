#!/bin/bash

# creating needed files and directories...
mkdir -p rbin
touch scores.txt 

# compiling
javac -d rbin/ src/*.java src/Components/*.java src/EntitySets/*.java src/Game/*.java src/poj/*.java src/App/*.java src/poj/Collisions/*.java src/poj/Component/*.java src/poj/EntitySet/*.java src/poj/GameWindow/*.java src/poj/HList/*.java src/poj/linear/*.java src/poj/Logger/*.java src/poj/Render/*.java src/poj/Time/*.java src/Resources/*.java src/TileMap/*.java src/PathFinding/*.java  

if [ $? -eq 0 ]; then
        java -cp rbin/ Main # run Main class if compiled
else
        echo "error compiling"
fi
