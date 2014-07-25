package com.ladro.topcoder.asteroid;

public interface IAsteroidTracker {

    int initialize(double[] antennaPositions, double peakGain, double[] minDistanceGain, double[] maxDistanceGain);

    int asteroidAppearance(int asteroidIndex, double scienceScoreMultiplier, double reflectivityMultiplier, double initialImageInformation,
            double initialTrajectoryInformation, double[] trajectory);

    String nextCommand(double currentTime);

}
