package com.prins.simplenn.linear;

import com.prins.simplenn.neural.*;

/**
 * @author prinswu
 * @version v1.0
 * @since v1.0 2018/2/2
 */
public class LinearNN1 {
    private static double learningRate = 0.5;
    private static double randomWeight = 1.0;
    private static final int INPUTSIZE = 1;
    public static final int LAYER_ONE_SIZE = 1;
    public static final int LAYER_TWO_SIZE = 1;
    private static final int OUTPUTSIZE = 1;

    public LinearNN1() {
        //training data
        int trainSize = 20;
        double[][] trains = LinearDataBuilder.buildTrainingData(trainSize, INPUTSIZE);
        //correct data
        double[][] corrects = LinearDataBuilder.buildCorrectResultData(trainSize, trains, OUTPUTSIZE);

        try {
            NNBuilder builder = new SimpleNNBuilder(SimpleNeuralNetwork.class);
            builder.formatPattern("00.000").learningRate(learningRate).randomWeight(randomWeight)
    //                .costActivationFunction(ActivationFunction.EQUALS)
                    .biasStrategy(BiasStrategy.STATIC)
                    .displayProcessLog(true).displayResultLog(true)
                    .layer(1, LAYER_ONE_SIZE, INPUTSIZE, null, 0.0, ActivationFunction.NEGATIVE_EQUALS);

            NeuralNetwork nn = builder.neuralNetwork();
            nn.train(trains, corrects, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        LinearNN1 n = new LinearNN1();
    }
}
