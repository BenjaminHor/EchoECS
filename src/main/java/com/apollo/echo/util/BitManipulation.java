package com.echo.util;

public class BitManipulation {

    private BitManipulation() {
    }

    public static int setBit(int value, int bitPosition) {
        return value |= (1 << bitPosition);
    }

    public static int clearBit(int value, int bitPosition) {
        return value &= ~(1 << bitPosition);
    }

    public static boolean checkBit(int value, int bitPosition) {
        int check = value & (1 << bitPosition);
        return (check == 1);
    }
}
