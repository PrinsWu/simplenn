package com.prins.simplenn.mnist;

import java.util.List;

/**
 * @author prinswu
 * @version v1.0
 * @since v1.0 2018/2/12
 */
public class MnistDataBuilder {

    public static final String ROOT = "/Users/prinswu/MNIST/";
    public static final String TRAIN_IMAGE_NAME = "train-images-idx3-ubyte";
    public static final String TRAIN_LABEL_NAME = "train-labels-idx1-ubyte";
    public static final String TEST_IMAGE_NAME = "t10k-images-idx3-ubyte";
    public static final String TEST_LABEL_NAME = "t10k-labels-idx1-ubyte";


    public static final double[][] buildTrainingData(String filename, boolean isBinary, int max) {
        List<int[][]> images = MnistReader.getImages(ROOT + filename);
        int size = Math.min(images.size(), max);
        int inputSize = images.get(0).length * images.get(0)[0].length;//28*28=748

        double[][] trains = new double[size][inputSize];
        //training data init with random
        for (int t = 0; t < size; t++) {
            int[][] data = images.get(t);
            int[] temp = new int[inputSize];
            int index = 0;
            for (int row = 0; row < data.length; row++) {
                for (int col = 0; col < data[row].length; col++) {
                    temp[index] = data[row][col];
//                    System.out.println(data[row][col]);
                    index++;
                }
//                temp = ArrayUtils.addAll(temp, data[row]);
            }

//            System.out.println("++++++++++++++++++++++++++++");
            for (int i = 0; i < inputSize; i++) {
//                System.out.println(temp[i]);
                if (isBinary) {
                    if (temp[i] > 30) {
                        trains[t][i] = 1.0;
//                        System.out.println("[" + i + "]" + trains[t][i]);
                    } else {
                        trains[t][i] = 0.0;
                    }
                } else {
                    trains[t][i] = (double) temp[i] / 255.0;
                }
            }
//            System.arraycopy(temp, 0, trains[t], 0, inputSize);
        }
//        System.out.println(trains);
        return trains;
    }

    public static final double[][] buildCorrectResultData(String filename, int max) {
        int[] labels = MnistReader.getLabels(ROOT + filename);
        int size = Math.min(labels.length, max);
        int outputSize = 10;
        //correct data
        double[][] corrects = new double[size][outputSize];
        for (int t = 0; t < size; t++) {
            int c = labels[t];
            corrects[t][c] = 1.0;
        }
        return corrects;
    }

    public static final int[] getCorrectResultData(String filename) {
        return MnistReader.getLabels(ROOT + filename);
    }

    public static void main(String[] args) {
        try {
            String LABEL_FILE = "/Users/prinswu/MNIST/train-labels-idx1-ubyte";
            String IMAGE_FILE = "/Users/prinswu/MNIST/train-images-idx3-ubyte";

//            int[] labels = MnistReader.getLabels(LABEL_FILE);
//            List<int[][]> images = MnistReader.getImages(IMAGE_FILE);
//
////            assert(labels.length, images.size());
////            assert(28, images.get(0).length);
////            assert(28, images.get(0)[0].length);
//
//            for (int i = 0; i < Math.min(1, labels.length); i++) {
//                System.out.printf("================= LABEL %d\n", labels[i]);
//                System.out.printf("%s", MnistReader.renderImage(images.get(i)));
//            }

            System.out.println("=================================================================");
            double[][] trains = MnistDataBuilder.buildTrainingData(MnistDataBuilder.TRAIN_IMAGE_NAME, true, 1);
            System.out.println(trains.length + ":" + trains[0].length);
            for (int i = 0; i < 28; i++) {
                for (int j = 0; j < 28; j++) {
                    int index = (i * 28) + j;
//                    if (trains[0][i+j] > 0) {
//                        System.out.println("[" + i + ":" + j + "]");
//                    }
                    System.out.print(trains[0][index] + " ");
                }
                System.out.println("");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
