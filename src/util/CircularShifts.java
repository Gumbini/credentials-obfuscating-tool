package util;

public final class CircularShifts {
    /**
     * Shift right logical circular. <br> {@code srlC((byte) 0b1010_1100, 2) == 0b0010_1011}
     *
     * @param value a {@code byte} value
     * @param count amount of bits to be shifted right
     * @return the circular shifted {@code byte}
     */
    public static byte srlC(byte value, int count) {
        return (byte) srlC(value & 0xFF, count, Byte.SIZE);
    }

    // Shift Right Logical Circular
    private static long srlC(long value, long count, int bits) {
        final long mask = bits - 1L;
        count &= mask;
        return ((value >> count) | (value << (-count & mask)));
    }

    /**
     * Shift left logical circular. <br> {@code sllC((byte) 0b1010_1100, 2) == 0b1011_0010}
     *
     * @param value a {@code byte} value
     * @param count amount of bits to be shifted left
     * @return the circular shifted {@code byte}
     */
    public static byte sllC(byte value, int count) {
        return (byte) sllC(value & 0xFF, count, Byte.SIZE);
    }

    // Shift Left Logical Circular
    private static long sllC(long value, long count, int bits) {
        final long mask = bits - 1L;
        count &= mask;
        return ((value << count) | (value >> (-count & mask)));
    }
}
