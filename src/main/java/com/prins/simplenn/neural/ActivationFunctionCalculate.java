package com.prins.simplenn.neural;

/**
 * @author prinswu
 * @version v1.0
 * @since v1.0 2018/2/1
 */
public class ActivationFunctionCalculate {

    public static double step(double x) {
        return (x > 0 ? 1 : 0);
    }

    public static double relu(double x) {
        return (x > 0 ? x : 0);
    }

    public static double relu_pd(double x) {
        return step(x);
    }

    public static double nzrelu(double x) {
        return (x > 0 ? x : 0.01);
    }

    public static double nzrelu_pd(double x) {
        return step(x);
    }

    public static double tanh(double x) {
        //(ex - e-x)/(ex + e-x)
        return Math.tanh(x);
    }

    public static double tanh_pd(double x) {
        //(1 - tanh^2)
        return (1 - (x * x));
    }

    public static double sigmoid(double x) {
        //1 / (1 + e^-x)
        return 1 / (1 + Math.exp(-x));
    }

    public static double sigmoid_pd(double x) {
        //sigmoid(x) (1 - sigmoid(x))
        return x * (1 - x);
    }

    public static double squareError(double x) {
//        System.out.println("x(" + x + ") x^2(" + (x*x) + ") ===>" + (0.5 * (x * x)));
        return 0.5 * (x * x);
    }

    public static double squareError_pd(double x) {
//        System.out.println("x(" + x + ") x^2(" + (x*x) + ") ===>" + (0.5 * (x * x)));
        return -1 * x;
    }

    public static double same(double x) {
        return x;
    }

    public static double same_pd(double x) {
        return x;
    }

    public static double positive_equals(double x) {
        return x;
    }

    public static double positive_equals_pd(double x) {
        return 1;
    }

    public static double negative_equals(double x) {
        return x;
    }

    public static double negative_equals_pd(double x) {
        return -1;
    }

    public static double activationFunction(double x, ActivationFunction activationFunction) {
        double activate = 0;
        switch (activationFunction) {
            case STEP:
                activate = ActivationFunctionCalculate.step(x);
                break;
            case RELU:
                activate = ActivationFunctionCalculate.relu(x);
                break;
            case TANH:
                activate = ActivationFunctionCalculate.tanh(x);
                break;
            case SIGMOID:
                activate = ActivationFunctionCalculate.sigmoid(x);
                break;
            case SQUARE_ERROR:
                activate = ActivationFunctionCalculate.squareError(x);
                break;
            case POSITIVE_EQUALS:
                activate = ActivationFunctionCalculate.positive_equals(x);
                break;
            case NEGATIVE_EQUALS:
                activate = ActivationFunctionCalculate.negative_equals(x);
                break;
            case SAME:
                activate = ActivationFunctionCalculate.same(x);
            default:
                activate = x;
        }
        return activate;
    }

    public static double activationFunctionPD(double x, ActivationFunction activationFunction) {
        double activate = 0;
        switch (activationFunction) {
            case STEP:
                activate = ActivationFunctionCalculate.step(x);
                break;
            case RELU:
                activate = ActivationFunctionCalculate.relu_pd(x);
                break;
            case TANH:
                activate = ActivationFunctionCalculate.tanh_pd(x);
                break;
            case SIGMOID:
                activate = ActivationFunctionCalculate.sigmoid_pd(x);
                break;
            case SQUARE_ERROR:
                activate = ActivationFunctionCalculate.squareError_pd(x);
                break;
            case POSITIVE_EQUALS:
                activate = ActivationFunctionCalculate.positive_equals_pd(x);
                break;
            case NEGATIVE_EQUALS:
                activate = ActivationFunctionCalculate.negative_equals_pd(x);
                break;
            case SAME:
                activate = ActivationFunctionCalculate.same_pd(x);
            default:
                activate = x;
        }
        return activate;
    }

    public static void main(String[] args) {
        System.out.println(ActivationFunctionCalculate.tanh(35.01));
        System.out.println(ActivationFunctionCalculate.tanh_pd(35.01));
    }
}
