package com.prins.simplenn.neural;

/**
 * @author prinswu
 * @version v1.0
 * @since v1.0 2018/2/1
 */
public interface Neural {

    double output();

    double[] getInputs();

    void setInputs(double[] inputs);

    double getB();

    void setB(double b);

    double getOutput();

    void setOutput(double output);

    double[] getWeight();

    void setWeight(double[] weight);

    double[] getNewWeight();

    void setNewWeight(double[] newWeight);

    double getLoss();

    void setLoss(double loss);

    double getActivate();

    void setActivate(double activate);

    double getCost();

    void setCost(double cost);

    double getTheta();

    void setTheta(double theta);

    void updateWeight();

    void copyToNewWeight();
}
