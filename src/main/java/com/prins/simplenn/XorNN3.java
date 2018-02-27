package com.prins.simplenn;

import java.text.DecimalFormat;
import java.util.Random;

/**
 * @author prinswu
 * @version v1.0
 * @since v1.0 2018/1/19
 */
public class XorNN3 {

    private XorNN3.Neural[] ns = null;
    private static DecimalFormat fmt = new DecimalFormat("0.0000");
    private static double defaultW = 0.5;
    private static double learningRate = 1.0;
    private static Random r = new Random(System.currentTimeMillis());

    public XorNN3() {

        //training data
        int trainSize = 4 * 7;
        int[][] trains = new int[trainSize][2];
        //training data init with random
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

        int  hiddenSize = 2;
        ns = new Neural[hiddenSize];
        for (int i = 0; i < hiddenSize; i++) {
            ns[i] = new XorNN3.Neural();
            if (i == 0) {
                ns[i].inputSize = 2;
            } else {
                ns[i].inputSize = 3;
            }
            ns[i].inputs = new double[ns[i].inputSize];
            ns[i].weight = new double[ns[i].inputSize];
            ns[i].initW();
            System.out.println("[" + i + "] " + ns[i].toWstr());
        }

        for (int t = 0; t < trainSize; t++) {
            System.out.println("[" + t + "]" + "\ti:" + trains[t][0] + " " + trains[t][1] + "->" + corrects[t]);

            Neural n1 = ns[0];
            Neural n2 = ns[1];

            n1.inputs[0] = trains[t][0];
            n1.inputs[1] = trains[t][1];
            n1.output();
            n1.activate = tanh(n1.output);
            System.out.println("n1 o:" + fmt.format(n1.output) + "\ta:" + fmt.format(n1.activate) + "\t" + n1.toWstr());

            n2.inputs[0] = trains[t][0];
            n2.inputs[1] = trains[t][1];
            n2.inputs[2] = n1.activate;
            n2.output();
            n2.activate = step(n2.output);
            System.out.println("n2 o:" + fmt.format(n2.output) + "\ta:" + fmt.format(n2.activate) + "\t" + n2.toWstr());
            n2.loss = corrects[t] - n2.activate;
            System.out.println("=====\tl:" + fmt.format(n2.loss));

            if (n2.loss != 0) {
                //偏微分 = -loss * 該input
                n2.updateW();
                n1.loss = n2.weight[2];
                n1.updateW();
            }
        }
    }

//    public double test(int x, int y) {
//        n.inputs[0] = x;
//        n.inputs[1] = y;
//        n.output = (n.inputs[0] * n.weight[0]) + (n.inputs[1] * n.weight[1]) + n.b;
//        n.activate = step(n.output);
//        return n.activate;
//    }

    private int correct(int x, int y) {
//        return x | y; //OR
//        return x & y; //AND
        return x ^ y; //XOR
    }

    private static double step(double x) {
        return (x > 0 ? 1 : 0);
    }

    private static double tanh(double x) {
        //(ex - e-x)/(ex + e-x)
        return Math.tanh(x);
    }

    public static void main(String[] args) {
        XorNN3 nn = new XorNN3();
//        System.out.println("test[1,1]=" + nn.test(1, 1));
    }

    /**
     * Neural class, one input, one bias, one output, one weight, one loss.
     */
    public class Neural {
        double inputs[];
        double b;
        double output;
        double weight[];
        double loss;
        double activate;
        int inputSize;

        double output() {
            output = 0;
            for (int i = 0; i < inputSize; i++) {
                output += inputs[i] * weight[i];
            }
            output += b;
            return output;
        }

        void initW() {
            for (int i = 0; i < inputSize; i++) {
                weight[i] = defaultW;
//                weight[i] = r.nextDouble();
            }
            b = defaultW;
//            b = r.nextDouble();
        }

        void updateW() {
            for (int i = 0; i < inputSize; i++) {
                System.out.print(i + "w=" + fmt.format(weight[i]) + "+" + fmt.format((loss * inputs[i] * learningRate)));
                weight[i] = weight[i] + (loss * inputs[i] * learningRate);
                System.out.println("=" + fmt.format(weight[i]));
            }
//            System.out.print("b=" + FMT.format(b) + "+" + FMT.format((loss * learningRate)));
//            b = b + (loss * learningRate);
//            System.out.println("=" + FMT.format(b));
        }

        String toWstr() {
            String wstr = "w(";
            for (double w : weight) {
                wstr += fmt.format(w) + " ";
            }
            wstr += " " + b + ")";
            return wstr;
        }
    }
}
