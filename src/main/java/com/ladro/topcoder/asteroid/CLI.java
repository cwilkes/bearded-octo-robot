package com.ladro.topcoder.asteroid;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class CLI {

    private static int readNextInt(BufferedReader br) throws NumberFormatException, IOException {
        return Integer.parseInt(br.readLine());
    }

    private static double readNextDouble(BufferedReader br) throws NumberFormatException, IOException {
        return Double.parseDouble(br.readLine());
    }

    private static double[] readNextDoubleLines(BufferedReader br, int lineCount) throws NumberFormatException, IOException {
        double[] ret = new double[lineCount];
        for (int i = 0; i < lineCount; i++) {
            ret[i] = readNextDouble(br);
        }
        return ret;
    }

    private static double[] readNextDoubleMulti(BufferedReader br) throws IOException {
        String[] parts = br.readLine().split("\\s+");
        double[] ret = new double[parts.length];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = Double.parseDouble(parts[i]);
        }
        return ret;
    }

    private static class InitializationArgs {
        private final double[] antennaPositions;
        private final double peakGain;
        private final double[] minDistanceGain, maxDistanceGain;

        private InitializationArgs(BufferedReader br) throws NumberFormatException, IOException {
            int numberOfAntennas = readNextInt(br);
            antennaPositions = new double[numberOfAntennas * 2];
            int i = 0;
            while (i < antennaPositions.length) {
                double[] XandY = readNextDoubleMulti(br);
                antennaPositions[i++] = XandY[0];
                antennaPositions[i++] = XandY[1];
            }
            peakGain = readNextDouble(br);
            int gainValuesCount = readNextInt(br);
            minDistanceGain = readNextDoubleLines(br, gainValuesCount);
            maxDistanceGain = readNextDoubleLines(br, gainValuesCount);
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        InitializationArgs initArgs = new InitializationArgs(br);
        AsteroidTracker at = new AsteroidTracker();
        at.initialize(initArgs.antennaPositions, initArgs.peakGain, initArgs.minDistanceGain, initArgs.maxDistanceGain);
        String s;
        while ((s = br.readLine()) != null) {
            System.err.println("Line: " + s);
            String[] parts = s.split("\\s+");
            if (parts.length == 2) {
                String command = at.nextCommand(Double.parseDouble(parts[1]));
                System.out.println(command);
                System.out.flush();
            } else {
                System.err.println("Args: " + Arrays.toString(parts));
                int i = 1;
                at.asteroidAppearance(Integer.parseInt(parts[i++]), Double.parseDouble(parts[i++]), Double.parseDouble(parts[i++]),
                        Double.parseDouble(parts[i++]), Double.parseDouble(parts[i++]), getTrajectory(Integer.parseInt(parts[i++]), br));
            }
        }
        br.close();
    }

    private static double[] getTrajectory(int size, BufferedReader br) throws IOException {
        double[] ret = new double[size * 4];
        int i = 0;
        while (i < ret.length) {
            for (double d : readNextDoubleMulti(br)) {
                ret[i++] = d;
            }
        }
        return ret;
    }
}
