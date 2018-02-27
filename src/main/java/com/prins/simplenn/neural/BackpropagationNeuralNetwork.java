package com.prins.simplenn.neural;

import java.util.Arrays;
import java.util.List;

/**
 * @author prinswu
 * @version v1.0
 * @since v1.0 2018/2/21
 */
public class BackpropagationNeuralNetwork extends SimpleNeuralNetwork {

    @Override
    protected void updateWeight(int loop) {
        int layer = layerMap.size();//last layer
        List<Neural> layerNN = layerMap.get(layer);
        ActivationFunction activationFunction = layerActivationFunctionMap.get(layer);
        int layerNeuralSize = layerNN.size();
        for (int nn = 0; nn < layerNeuralSize; nn++) {
            Neural n = layerNN.get(nn);
            n.copyToNewWeight();
            //-(c - a3) * (a3 (1 - a3)) = -(c - a3) * (activate_function_pd(x))
//            double lossValueForNeural = -1 * (n.getLoss()) * (n.getActivate() * (1 - n.getActivate()));
//            double loss = corrects[loop][nn] - n.getActivate();
            double loss = n.getLoss();
            double activatePd = ActivationFunctionCalculate.activationFunctionPD(n.getActivate(), activationFunction);
            double lossValueForNeural = n.getCost() * activatePd;
            n.setTheta(lossValueForNeural);
            if (displayProcessLog) {
                System.out.println(
                        "[" + layer + ":" + (nn + 1) + "] cpd:" + FMT.format(n.getCost()) + " apd:" + FMT.format(activatePd) +
                                " lv:" + FMT.format(lossValueForNeural));
            }
        }

        calculateThetaAndWeight(layer);
        updateLayerWeight(layer);
    }

    protected void updateLayerWeight(int layer) {
        if (!layerMap.containsKey(layer)) {
            return;
        }
        List<Neural> layerNN = layerMap.get(layer);
        int layerNeuralSize = layerNN.size();
        for (int nn = 0; nn < layerNeuralSize; nn++) {
            Neural n = layerNN.get(nn);
            int wsize = n.getWeight().length;
            for (int w = 0; w < wsize; w++) {
                n.getWeight()[w] = n.getWeight()[w] - ( n.getTheta() * n.getInputs()[w] * learningRate );
            }
            if (displayProcessLog) {
                System.out.print("[" + layer + ":" + (nn + 1) + "]");
                Arrays.stream(n.getWeight()).forEach(x -> System.out.printf("%s ", FMT.format(x)));
            }
            if (biasStrategy == null || biasStrategy != BiasStrategy.STATIC) {
                //update bias
                if (displayProcessLog) {
                    System.out.print(" oldB:" + FMT.format(n.getB()));
                }
                //new = old - (dloss/dw * learning rate)
                n.setB(n.getB() - (n.getTheta() * learningRate));
                if (displayProcessLog) {
                    System.out.println(" newB:" + FMT.format(n.getB()));
                }
            }
        }

        layer--;
        updateLayerWeight(layer);
    }

    protected void calculateThetaAndWeight(int layer) {
        int prelayer = layer - 1;
        if (!layerMap.containsKey(prelayer)) {
            return;
        }
        List<Neural> layerNN = layerMap.get(layer);
        int layerNeuralSize = layerNN.size();
        //pre layer neural size
        int prelayerNeuralSize = layerMap.get(prelayer).size();
        //thetasAndWeights is backpropagation pd(loss) * pd(activation) * w
        double[] thetasAndWeights = new double[prelayerNeuralSize];
        for (int nn = 0; nn < layerNeuralSize; nn++) {
            Neural n = layerNN.get(nn);
            int wsize = n.getWeight().length;
            for (int w = 0; w < wsize; w++) {
                //summary (theta * w)
                thetasAndWeights[w] += n.getTheta() * n.getWeight()[w];
            }
        }
        calculateTheta(prelayer, thetasAndWeights);
        calculateThetaAndWeight(prelayer);
    }

    protected void calculateTheta(int layer, double[] thetasAndWeights) {
        List<Neural> layerNN = layerMap.get(layer);
        ActivationFunction activationFunction = layerActivationFunctionMap.get(layer);
        int layerNeuralSize = layerNN.size();
        for (int nn = 0; nn < layerNeuralSize; nn++) {
            Neural n = layerNN.get(nn);
            n.setLoss(thetasAndWeights[nn]);
            double activatePd = ActivationFunctionCalculate.activationFunctionPD(n.getActivate(), activationFunction);
            double lossValueForNeural = thetasAndWeights[nn] * activatePd;
            n.setTheta(lossValueForNeural);
        }
    }
}
