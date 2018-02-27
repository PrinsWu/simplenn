package com.prins.simplenn.digits;

/**
 * Seven-segment display data builder.<p></p>
 * The data sequence as bellow and 1 means bright, o means dark.
 *
 *     0
 *   _____
 *  |     | 1
 * 5|     |
 *   __6__
 *  |     | 2
 * 4|     |
 *   -----
 *     3
 *
 * @author prinswu
 * @version v1.0
 * @since v1.0 2018/2/9
 */
public class SevenSegmentDisplayDataBuilder {
    static double[][] sevenSegmentData = new double[][] {
            {1, 1, 1, 1, 1, 1, 0},//0
            {0, 1, 1, 0, 0, 0, 0},//1
            {0, 1, 0, 1, 1, 0, 1},//2
            {1, 1, 1, 1, 0, 0, 1},//3
            {0, 1, 1, 0, 0, 1, 1},//4
            {1, 1, 0, 1, 1, 1, 0},//5
            {1, 0, 1, 1, 1, 1, 1},//6
            {1, 1, 1, 0, 0, 0, 0},//7
            {1, 1, 1, 1, 1, 1, 1},//8
            {1, 1, 1, 0, 0, 1, 1} //9
    };
    static double[][] correct = new double[][] {
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0},//0
            {0, 1, 0, 0, 0, 0, 0, 0, 0, 0},//1
            {0, 0, 1, 0, 0, 0, 0, 0, 0, 0},//2
            {0, 0, 0, 1, 0, 0, 0, 0, 0, 0},//3
            {0, 0, 0, 0, 1, 0, 0, 0, 0, 0},//4
            {0, 0, 0, 0, 0, 1, 0, 0, 0, 0},//5
            {0, 0, 0, 0, 0, 0, 1, 0, 0, 0},//6
            {0, 0, 0, 0, 0, 0, 0, 1, 0, 0},//7
            {0, 0, 0, 0, 0, 0, 0, 0, 1, 0},//8
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 1} //9
    };

    public static final double[][] buildTrainingData(int trainSize) {
        int size = 10;
        int seven = 7;
        double[][] data = new double[trainSize * size][seven];
        for (int t = 0; t < trainSize; t++) {
            System.arraycopy(sevenSegmentData, 0, data, t * size, size);
        }
        return data;
    }

    public static final double[][] buildCorrectResultData(int trainSize) {
        int size = 10;
        //correct data
        double[][] corrects = new double[trainSize * size][size];
        for (int t = 0; t < trainSize; t++) {
            System.arraycopy(correct, 0, corrects, t * size, size);
        }
        return corrects;
    }
}
