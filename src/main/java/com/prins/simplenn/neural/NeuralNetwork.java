package com.prins.simplenn.neural;

import java.util.List;
import java.util.Map;

/**
 * Neural Network interface.
 * @author prinswu
 * @version v1.0
 * @since v1.0 2018/2/9
 */
public interface NeuralNetwork {
    /**
     * Start to training this neural network.
     * @param trains training data array
     * @param corrects correct data array
     * @param epos loop times of training data
     */
    void train(double[][] trains, double[][] corrects, int epos);

    /**
     * To test and get result by current neural network model.
     * @param test test data array
     * @return result array
     */
    double[] test(double[] test);

    /**
     * Layer map[layer number, List[Neural]]
     * @return layer map
     */
    Map<Integer, List<Neural>> getLayerMap();

    /**
     * Layer activation function map[layer number, ActivationFunction]
     * @return layer activation function map
     */
    Map<Integer, ActivationFunction> getLayerActivationFunctionMap();

    /**
     * Display neural network weights.
     */
    void displayNN();

    void setCostActivationFunction(ActivationFunction costActivationFunction);
    void setFormatPattern(String formatPattern);
    void setLearningRate(double learningRate);
    void setBiasStrategy(BiasStrategy biasStrategy);
    void setDisplayInputLog(boolean displayInputLog);
    void setDisplayProcessLog(boolean displayProcessLog);
    void setDisplayResultLog(boolean displayResultLog);
}
