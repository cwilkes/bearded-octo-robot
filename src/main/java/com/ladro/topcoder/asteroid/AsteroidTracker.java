package com.ladro.topcoder.asteroid;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class AsteroidTracker implements IAsteroidTracker {

    private double[] m_antennaPositions;
    private int m_numberAntennas;
    private Random m_rand = new Random(8675309);
    private double m_peakGain;
    private TimerAndLogger m_timer;

    private static final int MAX_TRANSMITTING_POWER = 40000;

    private final Set<Integer> uniqueAsteroidIndex = new HashSet<Integer>();
    private final List<Integer> asteroids = new ArrayList<Integer>();

    @Override
    public int initialize(double[] antennaPositions, double peakGain, double[] minDistanceGain, double[] maxDistanceGain) {
        m_timer = new TimerAndLogger();
        m_numberAntennas = antennaPositions.length / 2;
        m_antennaPositions = antennaPositions;
        m_peakGain = peakGain;
        m_timer.log("Number antennas: %d", m_numberAntennas);
        m_timer = null;
        return -1;
    }

    @Override
    public int asteroidAppearance(int asteroidIndex, double scienceScoreMultiplier, double reflectivityMultiplier, double initialImageInformation,
            double initialTrajectoryInformation, double[] trajectory) {
        if (uniqueAsteroidIndex.add(asteroidIndex)) {
            asteroids.add(asteroidIndex);
        }
        if (m_timer == null) {
            m_timer = new TimerAndLogger();
        }
        return 0;
    }

    private static class TimerAndLogger {

        private void log(String fmt, Object... args) {
            System.err.println(String.format(fmt, args));
        }

    }

    private int apPos = 0;

    @Override
    public String nextCommand(double currentTime) {
        Command cmd = new Command();
        cmd.time = currentTime + 1;
        cmd.antenna = apPos;
        if (asteroids.isEmpty() || m_rand.nextBoolean()) {
            cmd.doPower(m_rand.nextDouble() * MAX_TRANSMITTING_POWER);
        } else {
            int astPos = asteroids.get(m_rand.nextInt(asteroids.size()));
            while (astPos == 37 || astPos == 35 || astPos == 26) {
                astPos = asteroids.get(m_rand.nextInt(asteroids.size()));
            }
            cmd.doRelocate(astPos);
        }
        apPos = (apPos + 1) % m_numberAntennas;
        return cmd.toString();
    }

    private static class Command {
        private int antenna;
        private double time;
        private double m_power = -1;
        private int m_asteroidIndex = -1;

        private void doPower(double power) {
            m_power = power;
        }

        private void doRelocate(int asteroidIndex) {
            m_asteroidIndex = asteroidIndex;
        }

        @Override
        public String toString() {
            String ret = String.format("%s %d", time, antenna);
            if (m_power == -1) {
                return ret + String.format(" %s %d", "R", m_asteroidIndex);
            } else {
                return ret + String.format(" %s %s", "P", m_power);
            }
        }
    }
}
