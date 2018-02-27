package com.prins.simplenn.neural;

/**
 * @author prinswu
 * @version v1.0
 * @since v1.0 2018/2/1
 */
public class SimpleNeural implements Neural {
    double inputs[];
    double b;
    double output;
    double weight[];
    double newWeight[];
    double loss;
    double activate;
    double cost;
    double theta;

    public double output() {
        output = 0;
        for (int i = 0; i < inputs.length; i++) {
            output += inputs[i] * weight[i];
        }
        output += b;
        return output;
    }

    public double[] getInputs() {
        return inputs;
    }

    public void setInputs(double[] inputs) {
        this.inputs = inputs;
    }

    public double getB() {
        return b;
    }

    public void setB(double b) {
        this.b = b;
    }

    public double getOutput() {
        return output;
    }

    public void setOutput(double output) {
        this.output = output;
    }

    public double[] getWeight() {
        return weight;
    }

    public double[] getNewWeight() {
        return newWeight;
    }

    public void setNewWeight(double[] newWeight) {
        this.newWeight = newWeight;
    }

    public void setWeight(double[] weight) {
        this.weight = weight;
    }

    public double getLoss() {
        return loss;
    }

    public void setLoss(double loss) {
        this.loss = loss;
    }

    public double getActivate() {
        return activate;
    }

    public void setActivate(double activate) {
        this.activate = activate;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getTheta() {
        return theta;
    }

    public void setTheta(double theta) {
        this.theta = theta;
    }

    public void updateWeight() {
        for (int i = 0; i < weight.length; i++) {
            weight[i] = newWeight[i];
        }
    }

    public void copyToNewWeight() {
        for (int i = 0; i < weight.length; i++) {
            newWeight[i] = weight[i];
        }
    }
}
