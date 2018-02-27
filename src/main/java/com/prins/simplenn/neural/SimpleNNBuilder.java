package com.prins.simplenn.neural;

import java.util.*;

/**
 * The builder a NeuralNetwork instance.<br>
 *
 * @author prinswu
 * @version v1.0
 * @since v1.0 2018/2/1
 * @see SimpleNeuralNetwork
 */
public class SimpleNNBuilder implements NNBuilder {

    NeuralNetwork nn;

    double randomWeight = 1.0;
    boolean includeNegative = false;

    public SimpleNNBuilder(Class clazz) throws Exception {
        nn = (NeuralNetwork) clazz.newInstance();
    }

    @Override
    public NeuralNetwork neuralNetwork() {
        return nn;
    }

    @Override
    public NNBuilder layer(int layer, int layerNeuralSize, int inputSize, Double defaultW, Double defaultB,
            ActivationFunction activationFunction) {
        Random r = null;
        if (null == defaultW) {
            r = new Random(System.currentTimeMillis());
        }
        List<Neural> layerNN = new ArrayList<Neural>();
        nn.getLayerMap().put(layer, layerNN);
        nn.getLayerActivationFunctionMap().put(layer, activationFunction);
        for (int nn = 0; nn < layerNeuralSize; nn++) {
            SimpleNeural n = new SimpleNeural();
            layerNN.add(n);
            n.inputs = new double[inputSize];
            n.weight = new double[inputSize];
            n.newWeight = new double[inputSize];

            for (int i = 0; i < inputSize; i++) {
                if (null == defaultW) {
                    n.weight[i] = randomDouble(r);
                } else {
                    n.weight[i] = defaultW;
                }
            }
            if (null == defaultB) {
                n.b = randomDouble(r);
            } else {
                n.b = defaultB;
            }
        }
        return this;
    }

    protected double randomDouble(Random r) {
        return r.nextDouble() * randomWeight * (includeNegative ? (r.nextBoolean() ? 1 : -1) : 1);
    }

    public NNBuilder learningRate(double learningRate) {
        this.nn.setLearningRate(learningRate);
        return this;
    }

    public NNBuilder formatPattern(String formatPattern) {
        this.nn.setFormatPattern(formatPattern);
        return this;
    }

    public NNBuilder costActivationFunction(ActivationFunction activationFunction) {
        this.nn.setCostActivationFunction(activationFunction);
        return this;
    }

    public NNBuilder randomWeight(double randomWeight) {
        this.randomWeight = randomWeight;
        return this;
    }

    public NNBuilder includeNegative(boolean includeNegative) {
        this.includeNegative = includeNegative;
        return this;
    }

    public NNBuilder biasStrategy(BiasStrategy biasStrategy) {
        this.nn.setBiasStrategy(biasStrategy);
        return this;
    }

    public NNBuilder displayInputLog(boolean display) {
        this.nn.setDisplayInputLog(display);
        return this;
    }

    public NNBuilder displayProcessLog(boolean display) {
        this.nn.setDisplayProcessLog(display);
        return this;
    }

    public NNBuilder displayResultLog(boolean display) {
        this.nn.setDisplayResultLog(display);
        return this;
    }
}
