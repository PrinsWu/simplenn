package com.prins.simplenn.neural;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * It is a simple full connections neural network support backpropagation.<br>
 * To use {@link ActivationFunction} to setup activation function for each layer.
 *
 * @author prinswu
 * @version v1.0
 * @since v1.0 2018/2/12
 */
public class SimpleNeuralNetwork implements NeuralNetwork {

    double learningRate;
    Map<Integer, List<Neural>> layerMap = new HashMap<>();
    Map<Integer, ActivationFunction> layerActivationFunctionMap = new HashMap<>();
    ActivationFunction costActivationFunction = ActivationFunction.NULL;
    BiasStrategy biasStrategy = BiasStrategy.NORMAL;

    DecimalFormat FMT = null;
    //display input data
    boolean displayInputLog = false;
    //display process ex:update wight
    boolean displayProcessLog = false;
    //display result log
    boolean displayResultLog = true;

    double[][] trains;
    double[][] corrects;
    double totalLoss;
    double[][] losses;
    double totalCost;
    double[][] costs;

    @Override
    public void train(double[][] trains, double[][] corrects, int epos) {
        this.trains = trains;
        this.corrects = corrects;
        this.losses = new double[corrects.length][corrects[0].length];
        this.costs = new double[corrects.length][corrects[0].length];

        if (displayInputLog) {
            for (int i = 0; i < this.trains.length; i++) {
                System.out.print("I[" + i + "]");
                Arrays.stream(this.trains[i]).forEach(x -> System.out.printf("%s ", FMT.format(x)));
                System.out.println("");
                System.out.print("C[" + i + "]");
                Arrays.stream(this.corrects[i]).forEach(x -> System.out.printf("%s ", FMT.format(x)));
                System.out.println("");
            }
        }

        SimpleDateFormat sf = new SimpleDateFormat("HH:mm:ss");
        for (int e = 0; e < epos; e++) {
            long time = System.currentTimeMillis();
//            if (displayResultLog) {
                System.out.println("=======================[" + e + "] start");
//            }
            int layer = 1;
            for (int loop = 0; loop < trains.length; loop++) {
                if (displayProcessLog) {
                    System.out.println("=======================[" + loop + "] start");
                }
                train(layer, trains[loop]);
                loss(loop);
                updateWeight(loop);
                if (displayProcessLog) {
                    System.out.println("=======================[" + loop + "] end");
                }
            }

            if (displayResultLog) {
                for (int i = 0; i < this.trains.length; i++) {
                    System.out.print("L[" + i + "]");
                    for (int nn = 0; nn < this.losses[0].length; nn++) {
                        System.out.printf("L:%s C:%s ", FMT.format(this.losses[i][nn]), FMT.format(this.costs[i][nn]));
                    }
//            Arrays.stream(this.losses[i]).forEach(x -> System.out.printf("%s ", FMT.format(x)));
                    System.out.println("");
                }

                System.out.println("======================= final weight");
                displayWeight(1);
            }
            time = System.currentTimeMillis() - time - TimeZone.getDefault().getRawOffset();;
//            if (displayResultLog) {
                System.out.println("=======================[" + e + "] end " + sf.format(time));
//            }
        }
    }

    /**
     * To transfer neurals of the layer with the input data.
     * @param layer layer number
     * @param inputs the input data of this layer
     */
    protected void train(int layer, double[] inputs) {
        if (!layerMap.containsKey(layer)) {//no the layer
            return;
        }
        List<Neural> layerNN = layerMap.get(layer);
        //activation function of this layer
        ActivationFunction activationFunction = layerActivationFunctionMap.get(layer);

        int layerNeuralSize = layerNN.size();
        for (int nn = 0; nn < layerNeuralSize; nn++) {
            Neural n = layerNN.get(nn);
            //set input data
            for (int i = 0; i < inputs.length; i++) {
                n.getInputs()[i] = inputs[i];
            }
        }

        double[] nextLayerInputs = new double[layerNeuralSize];
        for (int nn = 0; nn < layerNeuralSize; nn++) {
            Neural n = layerNN.get(nn);
            n.output();
            n.setActivate(ActivationFunctionCalculate.activationFunction(n.getOutput(), activationFunction));
            nextLayerInputs[nn] = n.getActivate();
            if (displayProcessLog) {
                System.out.println("[" + layer + ":" + (nn + 1) + "]" + neuralInputs(n));
                System.out.println("[" + layer + ":" + (nn + 1) + "]" + neuralWeight(n));
                System.out.println("[" + layer + ":" + (nn + 1) + "]" + neuralOutput(n));
            }
        }

        train(layer + 1, nextLayerInputs);
    }

    /**
     * loss calculate on last layer.
     * @param loop training loop
     */
    protected void loss(int loop) {
        int layer = layerMap.size();//last layer
        List<Neural> layerNN = layerMap.get(layer);

        totalLoss = 0;
        totalCost = 0;
        int layerNeuralSize = layerNN.size();
        for (int nn = 0; nn < layerNeuralSize; nn++) {
            Neural n = layerNN.get(nn);
            double loss = corrects[loop][nn] - n.getActivate();
            n.setLoss(loss);
            n.setCost(ActivationFunctionCalculate.activationFunction(loss, costActivationFunction));
            if (displayProcessLog) {
                System.out.println(
                        "[" + layer + ":" + (nn + 1) + "]c(" + FMT.format(corrects[loop][nn]) +
                                ") a(" + FMT.format(n.getActivate()) + ") loss(" + FMT.format(n.getLoss()) + ") cost(" +
                                FMT.format(n.getCost()) + ")");
            }
            totalLoss += n.getLoss();
            totalCost += n.getCost();
            losses[loop][nn] = n.getLoss();
            costs[loop][nn] = n.getCost();
        }
        //squareError(loss) = sum(1/2 (c - a)^2)
        if (displayProcessLog) {
            System.out.println("=======================total loss(" + FMT.format(totalLoss) + ")");
        }
    }

    /**
     * update weight with backpropagation(if multiple layer).
     * @param loop training loop
     */
    protected void updateWeight(int loop) {
        //back propagation for sigmoid only
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
            double costPd = ActivationFunctionCalculate.activationFunctionPD(loss, costActivationFunction);
            double activatePd = ActivationFunctionCalculate.activationFunctionPD(n.getActivate(), activationFunction);
            double lossValueForNeural = costPd * activatePd;
            if (displayProcessLog) {
                System.out.println(
                        "[" + layer + ":" + (nn + 1) + "] cpd:" + FMT.format(costPd) + " apd:" + FMT.format(activatePd) +
                                " lv:" + FMT.format(lossValueForNeural));
            }
            for (int i = 0; i < n.getInputs().length; i++) {
                //-(c - a3) * (a3 (1 - a3)) * a1(input)
                double newWeight = lossValueForNeural * n.getInputs()[i];
                if (displayProcessLog) {
                    System.out.print("[" + layer + ":" + (nn + 1) + "]->[" + (i + 1) + "]" +
                            " i:" + FMT.format(n.getInputs()[i]) +
                            " nw*r:" + FMT.format(newWeight * learningRate) +
                            " oldW:" + FMT.format(n.getNewWeight()[i]));
                }
                //new = old - (dloss/dw * learning rate)
                n.getNewWeight()[i] = n.getNewWeight()[i] - (newWeight * learningRate);
                if (displayProcessLog) {
                    System.out.println(" newW:" + FMT.format(n.getNewWeight()[i]));
                }
            }
            if (biasStrategy == null || biasStrategy != BiasStrategy.STATIC) {
                //update bias
                if (displayProcessLog) {
                    System.out.print("[" + layer + ":" + (nn + 1) + "]->[b] oldB:" + FMT.format(n.getB()));
                }
                //new = old - (dloss/dw * learning rate)
                n.setB(n.getB() - (lossValueForNeural * learningRate));
                if (displayProcessLog) {
                    System.out.println(" newB:" + FMT.format(n.getB()));
                }
            }

            int preLayer = layer - 1;
            List<Neural> prelayerNN = layerMap.get(preLayer);
            if (null == prelayerNN) {
                n.updateWeight();
                continue;
            }
            ActivationFunction preactivationFunction = layerActivationFunctionMap.get(preLayer);
            int prelayerNeuralSize = prelayerNN.size();
            for (int pn = 0; pn < prelayerNeuralSize; pn++) {
                Neural p = prelayerNN.get(pn);
                p.copyToNewWeight();
                //-(c - a3) * (a3 (1 - a3)) * w5 * (a1 (1 - a1)
                double preactivatePd = ActivationFunctionCalculate.activationFunctionPD(p.getActivate(), preactivationFunction);
                double preWeight = n.getWeight()[pn];
                double prelossValueForNeural = lossValueForNeural * preWeight * preactivatePd;
                if (displayProcessLog) {
                    System.out.println(
                            "[" + layer + ":" + (nn + 1) + "]pn[" + (pn + 1) + "] pno:" + FMT.format(p.getOutput()) +
                                    " pna:" + FMT.format(p.getActivate()) +
                                    " apd:" + FMT.format(preactivatePd) +
                                    " pw:" + FMT.format(preWeight) + " plv:" + FMT.format(prelossValueForNeural));
                }
                for (int i = 0; i < p.getInputs().length; i++) {
                    //-(c - a3) * (a3 (1 - a3)) * w5 * (a1 (1 - a1) * x1(input)
                    double newWeight = prelossValueForNeural * p.getInputs()[i];
                    if (displayProcessLog) {
                        System.out.print("[" + layer + ":" + (nn + 1) + "]pn[" + (pn + 1) + "]->[" + (i + 1) + "]" +
                                " i:" + FMT.format(p.getInputs()[i]) +
                                " nw*r:" + FMT.format(newWeight * learningRate) +
                                " oldW:" + FMT.format(p.getNewWeight()[i]));
                    }
                    //new = old - (dloss/dw * learning rate)
                    p.getNewWeight()[i] = p.getNewWeight()[i] - (newWeight * learningRate);
                    if (displayProcessLog) {
                        System.out.println(" newW:" + FMT.format(p.getNewWeight()[i]));
                    }
                }
                if (biasStrategy == null || biasStrategy != BiasStrategy.STATIC) {
                    //update bias
                    if (displayProcessLog) {
                        System.out.print(
                                "[" + layer + ":" + (nn + 1) + "]pn[" + (pn + 1) + "]->[b] oldB:" + FMT.format(
                                        p.getB()));
                    }
                    //new = old - (dloss/dw * learning rate)
                    p.setB(p.getB() - (lossValueForNeural * learningRate));
                    if (displayProcessLog) {
                        System.out.println(" newB:" + FMT.format(p.getB()));
                    }
                }

                p.updateWeight();
            }

            n.updateWeight();
        }
    }

    protected void displayWeight(int layer) {
        System.out.println("======================= layer[" + layer + "]");
        List<Neural> layerNN = layerMap.get(layer);
        if (layer > layerMap.size()) {
            return;
        }
//        ActivationFunction activationFunction = layerActivationFunctionMap.get(layer);

        int layerNeuralSize = layerNN.size();
        for (int nn = 0; nn < layerNeuralSize; nn++) {
            Neural n = layerNN.get(nn);
            System.out.print("[" + layer + "][" + (nn+1) + "]");
            Arrays.stream(n.getWeight()).forEach(x -> System.out.printf("%s ", FMT.format(x)));
            System.out.println("");
        }
    }

    @Override
    public double[] test(double[] test) {
        train(1, test);
        List<Neural> layerNN = layerMap.get(layerMap.size());
        double[] out = new double[layerNN.size()];
        int i = 0;
        for (Neural n : layerNN) {
            out[i] = n.getActivate();
            i++;
        }
        System.out.println("================ test input ================");
        Arrays.stream(test).forEach(x -> System.out.printf("%s ", FMT.format(x)));
        System.out.println("");
        System.out.println("================ test output ================");
        Arrays.stream(out).forEach(x -> System.out.printf("%s ", FMT.format(x)));
        System.out.println("");
        return out;
    }

    @Override
    public Map<Integer, List<Neural>> getLayerMap() {
        return layerMap;
    }

    @Override
    public Map<Integer, ActivationFunction> getLayerActivationFunctionMap() {
        return layerActivationFunctionMap;
    }

    @Override
    public void setCostActivationFunction(ActivationFunction costActivationFunction) {
        this.costActivationFunction = costActivationFunction;
    }

    @Override
    public void setFormatPattern(String formatPattern) {
        if (null != formatPattern) {
            FMT = new DecimalFormat(formatPattern);
        } else {
            FMT = new DecimalFormat("00.00000");
        }
    }

    @Override
    public void setLearningRate(double learningRate) {
        this.learningRate = learningRate;
    }

    @Override
    public void setBiasStrategy(BiasStrategy biasStrategy) {
        this.biasStrategy = biasStrategy;
    }

    @Override
    public void setDisplayInputLog(boolean displayInputLog) {
        this.displayInputLog = displayInputLog;
    }

    @Override
    public void setDisplayProcessLog(boolean displayProcessLog) {
        this.displayProcessLog = displayProcessLog;
    }

    @Override
    public void setDisplayResultLog(boolean displayResultLog) {
        this.displayResultLog = displayResultLog;
    }

    @Override
    public void displayNN() {
        for (int layer = 1; layer <= layerMap.size(); layer++) {
            List<Neural> layerNN = layerMap.get(layer);
            int layerNeuralSize = layerNN.size();
            for (int nn = 0; nn < layerNeuralSize; nn++) {
                Neural n = layerNN.get(nn);
                System.out.println("[" + layer + ":" + (nn + 1) + "] " + neuralWeight(n));
            }
        }
    }

    protected String neuralInputs(Neural n) {
        String str = "i(";
        for (double value : n.getInputs()) {
            str += FMT.format(value) + " ";
        }
        str += ")";
        return str;
    }

    protected String neuralWeight(Neural n) {
        String str = "w(";
        for (double value : n.getWeight()) {
            str += FMT.format(value) + " ";
        }
        str += ") b(" + FMT.format(n.getB()) + ")";
        return str;
    }

    protected String neuralOutput(Neural n) {
        return "o(" + FMT.format(n.getOutput()) + ") a(" + FMT.format(n.getActivate()) + ")";
    }
}
