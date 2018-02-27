package com.prins.simplenn.digits;

/**
 * @author prinswu
 * @version v1.0
 * @since v1.0 2018/2/21
 */
public class LogicgateDataBuilder {
    static double[][] logicgateData = new double[][] {
            {0, 0},
            {0, 1},
            {1, 0},
            {1, 1}
    };
    static double[][] orcorrect = new double[][] {
            {0},
            {1},
            {1},
            {1}
    };
    static double[][] andcorrect = new double[][] {
            {0},
            {0},
            {0},
            {1}
    };
    static double[][] xorcorrect = new double[][] {
            {0},
            {1},
            {1},
            {0}
    };

    public static final double[][] buildTrainingData() {
        return logicgateData;
    }

    public static final double[][] buildCorrectResultDataOr() {
        return xorcorrect;
    }

    public static final double[][] buildCorrectResultDataAnd() {
        return xorcorrect;
    }

    public static final double[][] buildCorrectResultDataXor() {
        return xorcorrect;
    }
}
