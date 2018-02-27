package com.prins.simplenn.hex;

import com.prins.simplenn.neural.*;

/**
 * To learn the rate of hex to decimal.<p></p>
 * This NN structure can learn the rate of hex and decimal.<br>
 * You can change INPUTSIZE and trainSize to monitor the result, the final weight should be 1 16 256 ...<br>
 * If INPUTSIZE increase then the trainSize have to increase and the learninfRate have to decrease.
 *
 * @author prinswu
 * @version v1.0
 * @since v1.0 2018/2/2
 */
public class HexNN1 {
    private static double learningRate = 0.01;
    private static double randomWeight = 1.0;
    private static final int INPUTSIZE = 2;
    public static final int LAYER_ONE_SIZE = 1;
//    public static final int LAYER_TWO_SIZE = 2;
    private static final int OUTPUTSIZE = 1;

    public HexNN1() {
        //training data
        int trainSize = 50;
        double[][] trains = HexDataBuilder.buildTrainingData(trainSize, INPUTSIZE);
        //correct data
        double[][] corrects = HexDataBuilder.buildCorrectResultData(trainSize, trains, OUTPUTSIZE);

        try {
            NNBuilder builder = new SimpleNNBuilder(SimpleNeuralNetwork.class);
            builder.formatPattern("00.00").learningRate(learningRate).randomWeight(randomWeight)
                    .costActivationFunction(ActivationFunction.SQUARE_ERROR)
                    .biasStrategy(BiasStrategy.STATIC)
                    .displayProcessLog(true).displayResultLog(true)
                    .layer(1, LAYER_ONE_SIZE, INPUTSIZE, null, 0.0, ActivationFunction.POSITIVE_EQUALS);

            NeuralNetwork nn = builder.neuralNetwork();
            nn.train(trains, corrects, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        HexNN1 n = new HexNN1();
    }
}
