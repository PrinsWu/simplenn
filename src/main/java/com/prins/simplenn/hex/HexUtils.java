package com.prins.simplenn.hex;

/**
 * @author prinswu
 * @version v1.0
 * @since v1.0 2018/2/2
 */
public class HexUtils {
    public static final String toHexString(double value) {
        return String.format("%02x", (int) value);
    }

    public static final String toHexString(int value) {
        return String.format("%02x", value);
    }

    public static void main(String[] args) {
        System.out.println(HexUtils.toHexString(255));
        System.out.println(HexUtils.toHexString(1));
        double[][] trains = new double[1][2];
        trains[0][0] = 9;
        trains[0][1] = 2;
        double[][] corrects = HexDataBuilder.buildCorrectResultData(1, trains, 1);
        System.out.println(corrects[0][0]);
    }
}
