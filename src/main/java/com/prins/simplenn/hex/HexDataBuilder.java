package com.prins.simplenn.hex;

import java.util.Random;

/**
 * Hex data builder.<p></p>
 * To build multiple hex digits for training data and correct data.
 *
 * @author prinswu
 * @version v1.0
 * @since v1.0 2018/1/29
 */
public class HexDataBuilder {
    public static final Random r = new Random();
    public static final int hex = 16;

    /**
     * To build hex training data.
     * @param trainSize how many training loop
     * @param inputSize hom many input size, means hom many hex digits
     * @return double[trainSize][inputSize] each element is 0~15 int number
     */
    public static final double[][] buildTrainingData(int trainSize, int inputSize) {
        double[][] trains = new double[trainSize][inputSize];
        //training data init with random
        for (int t = 0; t < trainSize; t++) {
            for (int i = 0; i < inputSize; i++) {
                trains[t][i] = transTo16(r.nextDouble());//hex number
            }
        }
        return trains;
    }

    /**
     * To calculate correct data with the trains[][].
     *
     * @param trainSize how many training loop
     * @param trains training data
     * @param outputSize no use, always 1
     * @return double[trainSize][1]
     */
    public static final double[][] buildCorrectResultData(int trainSize, double[][] trains, int outputSize) {
        //correct data
        double[][] corrects = new double[trainSize][outputSize];
        int inputSize = trains[0].length;
        for (int t = 0; t < trainSize; t++) {
            int c = 0;
            for (int i = 0; i < inputSize; i++) {
                c += trains[t][i] * (i == 0 ? 1 : Math.pow(hex, i));
            }
            corrects[t][0] = c;
        }
        return corrects;
    }

    public static final double transTo16(double x) {
        return (int) (x * hex);
    }
}
