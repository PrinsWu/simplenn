package com.prins.simplenn;

/**
 * @author prinswu
 * @version v1.0
 * @since v1.0 2018/1/29
 */
public class BinaryDataBuilder {

    public static final String OR = "OR";
    public static final String AND = "AND";
    public static final String XOR = "XOR";

    public static final int[][] buildTrainingData(int trainSize) {
        int[][] trains = new int[trainSize][2];
        //training data init with random
        for (int t = 0; t < trainSize; t=t+4) {
            trains[t+0][0] = 0;
            trains[t+0][1] = 0;
            trains[t+1][0] = 0;
            trains[t+1][1] = 1;
            trains[t+2][0] = 1;
            trains[t+2][1] = 0;
            trains[t+3][0] = 1;
            trains[t+3][1] = 1;
        }
        return trains;
    }

    public static final int[] buildCorrectResultData(int trainSize, int[][] trains, String method) {
        //correct data
        int[] corrects = new int[trainSize];
        for (int t = 0; t < trainSize; t++) {
            corrects[t] = correct(trains[t][0], trains[t][1], method);
        }
        return corrects;
    }

    public static final int[][] buildTwoCorrectResultData(int trainSize, int[][] trains, String method) {
        //correct data
        int[][] corrects = new int[trainSize][2];
        for (int t = 0; t < trainSize; t++) {
            int c = correct(trains[t][0], trains[t][1], method);
            corrects[t][0] = (c == 0 ? 1 : 0);
            corrects[t][1] = (corrects[t][0] == 0 ? 1 : 0);
        }
        return corrects;
    }

    private static int correct(int x, int y, String method) {
        switch (method) {
            case OR:
                return x | y; //OR
            case AND:
                return x & y; //AND
            case XOR:
                return x ^ y; //XOR
        }
        throw new RuntimeException("No such correct method:" + method);
    }
}
