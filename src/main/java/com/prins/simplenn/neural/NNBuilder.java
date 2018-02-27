package com.prins.simplenn.neural;

/**
 * @author prinswu
 * @version v1.0
 * @since v1.0 2018/2/1
 */
public interface NNBuilder {

    NeuralNetwork neuralNetwork();

    NNBuilder layer(int layer, int layerNeuralSize, int inputSize, Double defaultW, Double defaultB,
            ActivationFunction activationFunction);

    NNBuilder learningRate(double learningRate);

    NNBuilder formatPattern(String formatPattern);

    NNBuilder costActivationFunction(ActivationFunction activationFunction);

    NNBuilder randomWeight(double randomWeight);

    NNBuilder includeNegative(boolean includeNegative);

    NNBuilder biasStrategy(BiasStrategy biasStrategy);

    NNBuilder displayInputLog(boolean display);

    NNBuilder displayProcessLog(boolean display);

    NNBuilder displayResultLog(boolean display);
}
