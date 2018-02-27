package com.prins.simplenn;

import java.text.DecimalFormat;
import java.util.Random;

/**
 * 2 inputs, 1 output and B is 0. no activation funciton. w use avg to learn the loss.<br>
 * OR, AND both not work.
 * @author prinswu
 * @version v1.0
 * @since v1.0 2018/1/19
 */
public class XorNN {

    public XorNN() {
        DecimalFormat fmt = new DecimalFormat("0.00000");
        //training data
        int trainSize = 4 * 7;
        int[][] trains = new int[trainSize][2];
        //training data init with random
        Random r = new Random(System.currentTimeMillis());
        for (int t = 0; t < trainSize; t=t+4) {
            trains[t+0][0] = 0;
            trains[t+0][1] = 0;
            trains[t+1][0] = 0;
            trains[t+1][1] = 1;
            trains[t+2][0] = 1;
            trains[t+2][1] = 0;
            trains[t+3][0] = 1;
            trains[t+3][1] = 1;
        }

        //correct data
        int[] corrects = new int[trainSize];
        for (int t = 0; t < trainSize; t++) {
            corrects[t] = correct(trains[t][0], trains[t][1]);
        }

        double trainingRate = 0.5;
        XorNN.Neural n = new XorNN.Neural();
        n.b = 0;
        n.inputs = new int[2];
        n.weight = new double[2];
        n.weight[0] = r.nextDouble();
        n.weight[1] = r.nextDouble();
        System.out.println("w:" + fmt.format(n.weight[0]) + " " + fmt.format(n.weight[1]) + "\tb:" + n.b);

        for (int t = 0; t < trainSize; t++) {
            n.inputs[0] = trains[t][0];
            n.inputs[1] = trains[t][1];
            n.output = (n.inputs[0] * n.weight[0]) + (n.inputs[1] * n.weight[1]) + n.b;
            n.loss = corrects[t] - n.output;
            n.weight[0] = n.weight[0] + (n.loss / 2 * trainingRate);
            n.weight[1] = n.weight[1] + (n.loss / 2 * trainingRate);
            System.out.println("[" + t + "]" + "\ti:" + n.inputs[0] + " " + n.inputs[1] +
                    "\tc:" + corrects[t] + "\to:" + fmt.format(n.output) + "\tl:" + fmt.format(n.loss) +
                    "\tw:" + fmt.format(n.weight[0]) + " " + fmt.format(n.weight[1]));
        }
    }

    private int correct(int x, int y) {
//        return x | y;
        return x & y;
//        return x ^ y;
    }

    public static void main(String[] args) {
        new XorNN();
    }

    /**
     * Neural class, one input, one bias, one output, one weight, one loss.
     */
    public class Neural {
        int inputs[];
        double b;
        double output;
        double weight[];
        double loss;

        public Neural() {
        }
    }
}
