package com.prins.simplenn;

import java.text.DecimalFormat;
import java.util.Random;

/**
 * 2 inputs, 1 output and B is 0. use step function be activation function. w use loss * input to learn the loss.<br>
 * OR, AND are work but XOR not.
 * @author prinswu
 * @version v1.0
 * @since v1.0 2018/1/19
 */
public class XorNN2 {

    private XorNN2.Neural n = null;

    public XorNN2() {
        DecimalFormat fmt = new DecimalFormat("0.0000");
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
        n = new XorNN2.Neural();
        n.b = r.nextDouble();
        n.inputs = new int[2];
        n.weight = new double[2];
        n.weight[0] = r.nextDouble();
        n.weight[1] = r.nextDouble();
        System.out.println("w:" + n.weight[0] + " " + n.weight[1] + " b:" + n.b);

        for (int t = 0; t < trainSize; t++) {
            n.inputs[0] = trains[t][0];
            n.inputs[1] = trains[t][1];
            n.output = (n.inputs[0] * n.weight[0]) + (n.inputs[1] * n.weight[1]) + n.b;
            n.activate = step(n.output);
            n.loss = corrects[t] - n.activate;//loss function
            //learning function
            n.weight[0] = n.weight[0] + (n.loss * n.inputs[0]);
            n.weight[1] = n.weight[1] + (n.loss * n.inputs[1]);
            n.b = n.b + (n.loss * 1);//bias always input 1
            System.out.println("[" + t + "]" + "\ti:" + n.inputs[0] + " " + n.inputs[1] +
                    "->" + corrects[t] + "\to:" + fmt.format(n.output) + "\ta:" + fmt.format(n.activate) + "\tl:" + fmt.format(n.loss) +
                    "\tw:" + fmt.format(n.weight[0]) + " " + fmt.format(n.weight[1]) + "\tb:" + fmt.format(n.b));
        }
    }

    public double test(int x, int y) {
        n.inputs[0] = x;
        n.inputs[1] = y;
        n.output = (n.inputs[0] * n.weight[0]) + (n.inputs[1] * n.weight[1]) + n.b;
        n.activate = step(n.output);
        return n.activate;
    }

    private int correct(int x, int y) {
//        return x | y; //OR
        return x & y; //AND
//        return x ^ y; //XOR
    }

    private static double step(double x) {
        return (x > 0 ? 1 : 0);
    }

    public static void main(String[] args) {
        XorNN2 nn = new XorNN2();
        System.out.println("test[1,1]=" + nn.test(1, 1));
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
        double activate;

        public Neural() {
        }
    }
}
