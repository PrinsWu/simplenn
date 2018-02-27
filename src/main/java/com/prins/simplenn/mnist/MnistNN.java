package com.prins.simplenn.mnist;

import com.prins.simplenn.neural.*;

/**
 * To learn the dot matrix displat.<p></p>
 * Classification NN.<br>
 * 64(8 * 8) inputs and 10 outputs<br>
 *
 *
 * @author prinswu
 * @version v1.0
 * @since v1.0 2018/2/2
 */
public class MnistNN {
    private static double learningRate = 0.1;
    private static double randomWeight = 1.0;
    private static final int INPUTSIZE = 28 * 28;
    public static final int LAYER_ONE_SIZE = 100;
    public static final int LAYER_TWO_SIZE = 10;
    private static final int OUTPUTSIZE = 1;

    public MnistNN() {
        boolean isBinary = true;
        //training data
        int trainSize = 1;
        int epos = 1000;
        double[][] trains = MnistDataBuilder.buildTrainingData(MnistDataBuilder.TRAIN_IMAGE_NAME, isBinary, 10000000);
        //correct data
        double[][] corrects = MnistDataBuilder.buildCorrectResultData(MnistDataBuilder.TRAIN_LABEL_NAME, 10000000);

        try {
            NNBuilder builder = new SimpleNNBuilder(BackpropagationNeuralNetwork.class);
            builder.formatPattern("0.0000").learningRate(learningRate).randomWeight(randomWeight)
                    .costActivationFunction(ActivationFunction.SQUARE_ERROR)
//                    .biasStrategy(BiasStrategy.STATIC)
                    .displayInputLog(false).displayProcessLog(false).displayResultLog(false)
                    .layer(1, LAYER_ONE_SIZE, INPUTSIZE, null, null, ActivationFunction.SIGMOID)
                    .layer(2, LAYER_TWO_SIZE, LAYER_ONE_SIZE, null, null, ActivationFunction.SIGMOID);

            NeuralNetwork nn = builder.neuralNetwork();
            nn.train(trains, corrects, epos);

            double[][] tests = MnistDataBuilder.buildTrainingData(MnistDataBuilder.TEST_IMAGE_NAME, isBinary, 1);
            int[] testCorrects = MnistDataBuilder.getCorrectResultData(MnistDataBuilder.TEST_LABEL_NAME);
            for (int i = 0; i < 1; i++) {
                double max = 0;
                double max2 = 0;
                int maxIndex = 0;
                int maxIndex2 = 0;
                double[] result = nn.test(tests[i]);
//                for (int j = 0; j < result.length; j++) {
//                    System.out.print(result[j] + " ");
//                }
                System.out.println("--------------------->" + testCorrects[i]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        MnistNN n = new MnistNN();
    }
}
