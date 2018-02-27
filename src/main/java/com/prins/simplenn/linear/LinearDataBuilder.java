package com.prins.simplenn.linear;

import java.util.Random;

/**
 * @author prinswu
 * @version v1.0
 * @since v1.0 2018/1/29
 */
public class LinearDataBuilder {
    public static final Random r = new Random(1);

    public static final double[][] buildTrainingData(int trainSize, int intputSize) {
        double[][] trains = new double[trainSize][intputSize];
        //training data init with random
        for (int t = 0; t < trainSize; t++) {
            trains[t][0] = r.nextDouble();
        }
        return trains;
    }

    public static final double[][] buildCorrectResultData(int trainSize, double[][] trains, int outputSize) {
        //correct data
        double[][] corrects = new double[trainSize][outputSize];
        for (int t = 0; t < trainSize; t++) {
            corrects[t][0] = 0.5 * trains[t][0];
        }
        return corrects;
    }
}
