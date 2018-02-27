package com.prins.simplenn.digits;

import com.prins.simplenn.neural.*;

/**
 * To learn the Seven Segment Display.<p></p>
 * Classification NN.<br>
 * 7 inputs and 10 outputs<br>
 *
 *
 * @author prinswu
 * @version v1.0
 * @since v1.0 2018/2/2
 */
public class SevenSegmentDisplayNN {
    private static double learningRate = 0.1;
    private static double randomWeight = 1.0;
    private static final int INPUTSIZE = 7;
    public static final int LAYER_ONE_SIZE = 10;
//    public static final int LAYER_TWO_SIZE = 2;
    private static final int OUTPUTSIZE = 1;

    public SevenSegmentDisplayNN() {
        //training data
        int trainSize = 20000;
        double[][] trains = SevenSegmentDisplayDataBuilder.buildTrainingData(trainSize);
        //correct data
        double[][] corrects = SevenSegmentDisplayDataBuilder.buildCorrectResultData(trainSize);

        try {
            NNBuilder builder = new SimpleNNBuilder(SimpleNeuralNetwork.class);
            builder.formatPattern("0.0000").learningRate(learningRate).randomWeight(randomWeight)
                    .costActivationFunction(ActivationFunction.SQUARE_ERROR)
                    .biasStrategy(BiasStrategy.STATIC)
                    .displayInputLog(false).displayProcessLog(false).displayResultLog(false)
                    .layer(1, LAYER_ONE_SIZE, INPUTSIZE, null, 0.0, ActivationFunction.SIGMOID);

            NeuralNetwork nn = builder.neuralNetwork();
            nn.train(trains, corrects, 1);

            for (int i = 0; i < 10; i++) {
                double max = 0;
                double max2 = 0;
                int maxIndex = 0;
                int maxIndex2 = 0;
                double[] result = nn.test(trains[i]);
                for (int j = 0; j < result.length; j++) {
                    if (result[j] > max) {
                        max = result[j];
                        maxIndex = j;
                    } else if (result[j] > max2) {
                        max2 = result[j];
                        maxIndex2 = j;
                    }
                }
                System.out.println("[" + maxIndex + "]" + max + " [" + maxIndex2 + "]" + max2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SevenSegmentDisplayNN n = new SevenSegmentDisplayNN();
    }
}
