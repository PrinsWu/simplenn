package com.prins.simplenn.digits;

/**
 * @aut"or prinswu
 * @version v1.0
 * @since v1.0 2018/2/12
 */
public class DotMatrixDataBuilder {

    static int eight = 8;
    static String[][] dotMatrixData = new String[][] {
            {"00", "00", "3E", "41", "41", "41", "3E", "00"},//0
            {"00", "00", "00", "00", "21", "7F", "01", "00"},//1
            {"00", "00", "27", "45", "45", "45", "39", "00"},//2
            {"00", "00", "22", "49", "49", "49", "36", "00"},//3
            {"00", "00", "0C", "14", "24", "7F", "04", "00"},//4
            {"00", "00", "72", "51", "51", "51", "4E", "00"},//5
            {"00", "00", "3E", "49", "49", "49", "26", "00"},//6
            {"00", "00", "40", "40", "40", "4F", "70", "00"},//7
            {"00", "00", "36", "49", "49", "49", "36", "00"},//8
            {"00", "00", "32", "49", "49", "49", "3E", "00"}//9
//            {"00", "1C", "22", "22", "22", "3E", "22", "22"}//A
    };
    static double[][] correct = new double[][] {
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0},//0
            {0, 1, 0, 0, 0, 0, 0, 0, 0, 0},//1
            {0, 0, 1, 0, 0, 0, 0, 0, 0, 0},//2
            {0, 0, 0, 1, 0, 0, 0, 0, 0, 0},//3
            {0, 0, 0, 0, 1, 0, 0, 0, 0, 0},//4
            {0, 0, 0, 0, 0, 1, 0, 0, 0, 0},//5
            {0, 0, 0, 0, 0, 0, 1, 0, 0, 0},//6
            {0, 0, 0, 0, 0, 0, 0, 1, 0, 0},//7
            {0, 0, 0, 0, 0, 0, 0, 0, 1, 0},//8
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 1} //9
    };

    public static double[][] transfer() {
        double[][] data = new double[dotMatrixData.length][eight * eight];
        for (int i = 0; i < dotMatrixData.length; i++) {
            int index = 0;
            for (int j = 0; j < dotMatrixData[i].length; j++) {
                int d = Integer.parseInt(dotMatrixData[i][j], 16);
                String s = String.format("%08d", Integer.valueOf(Integer.toBinaryString(d)));
                System.out.printf("%s\n", s);

                for(int c = 0; c < s.length(); c++) {
                    data[i][c + (j * eight)] = (s.charAt(c) == 48 ? 0.0 : 1.0);
                    index += eight;
                }

            }
            System.out.println();
        }
        return data;
    }

    public static final double[][] buildTrainingData(int trainSize) {
        int size = 10;
        double[][] digits = transfer();
        double[][] data = new double[trainSize * size][eight * eight];
        for (int t = 0; t < trainSize; t++) {
            System.arraycopy(digits, 0, data, t * size, size);
        }
        return data;
    }

    public static final double[][] buildCorrectResultData(int trainSize) {
        int size = 10;
        //correct data
        double[][] corrects = new double[trainSize * size][size];
        for (int t = 0; t < trainSize; t++) {
            System.arraycopy(correct, 0, corrects, t * size, size);
        }
        return corrects;
    }

    public static void main(String[] args) {
        DotMatrixDataBuilder.transfer();
    }
}
