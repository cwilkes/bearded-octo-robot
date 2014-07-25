#!/bin/bash

java -jar AsteroidTrackerVisualizer.jar -exec "java -cp target/classes com.ladro.topcoder.asteroid.CLI" $@
