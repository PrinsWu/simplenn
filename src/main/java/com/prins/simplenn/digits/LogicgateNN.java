package com.prins.simplenn.digits;

import com.prins.simplenn.neural.*;

import java.text.DecimalFormat;

/**
 * @author prinswu
 * @version v1.0
 * @since v1.0 2018/2/21
 */
public class LogicgateNN {
    private static double learningRate = 0.5;
    private static double randomWeight = 1.0;
    private static final int INPUTSIZE = 2;
    public static final int LAYER_ONE_SIZE = 2;
    public static final int LAYER_TWO_SIZE = 1;
    private static final int OUTPUTSIZE = 1;

    public LogicgateNN() {
        //training data
        int epos = 10000;
        double[][] trains = LogicgateDataBuilder.buildTrainingData();
        //correct data
        double[][] corrects = LogicgateDataBuilder.buildCorrectResultDataXor();

        try {
            NNBuilder builder = new SimpleNNBuilder(BackpropagationNeuralNetwork.class);
            builder.formatPattern("0.0000").learningRate(learningRate).randomWeight(randomWeight)
                    .costActivationFunction(ActivationFunction.SAME)
                    .biasStrategy(BiasStrategy.STATIC)
                    .displayInputLog(false).displayProcessLog(false).displayResultLog(false)
                    .layer(1, LAYER_ONE_SIZE, INPUTSIZE, null, 0.0, ActivationFunction.TANH)
                    .layer(2, LAYER_TWO_SIZE, INPUTSIZE, null, 0.0, ActivationFunction.STEP);

            NeuralNetwork nn = builder.neuralNetwork();
            nn.train(trains, corrects, epos);
            nn.displayNN();

            DecimalFormat FMT = new DecimalFormat("0.0000");
            for (int i = 0; i < 4; i++) {
                double[] result = nn.test(trains[i]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        LogicgateNN n = new LogicgateNN();
    }

}
