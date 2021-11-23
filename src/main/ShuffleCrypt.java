package main;

import util.SimplifiedSecureRandom;

import java.util.*;

public final class ShuffleCrypt {
    public static final String LN = System.lineSeparator();

    /**
     * Dummy constructor: This is a utility class which should not be instantiated.
     */
    private ShuffleCrypt() {
    }

    /**
     * Construct a {@code List} of {@code byte}s which contains metadata about character position and the characters themselves.
     * {@code List Structure: [chars offset, string0 offset, ..., stringN offset, string0 length, string0 idx0, ..., string0 idxN,
     * ..., stringN length, stringN idx0, ..., stringN idxN, char0, ..., charN]}
     *
     * @param shuffledChars a <b>duplicate-free</b> {@link List} containing all {@link Character}s <b>preferably unsorted!</b>
     * @param strings       the {@link String}s which should be encrypted
     * @return a {@link List} containing metadata about character position and the characters themselves
     */
    public static List<Byte> constructByteList(List<Character> shuffledChars, String... strings) {
        List<Byte> byteList = new ArrayList<>();
        Stack<Integer> offsets = new Stack<>();

        for (String s : strings) {
            offsets.push(byteList.size());
            byteList.add((byte) s.length());
            char[] chars = s.toCharArray();
            for (char c : chars) {
                int idx = shuffledChars.indexOf(c);
                byteList.add((byte) idx);
            }
        }

        int charsOffset = byteList.size();
        for (char c : shuffledChars) {
            byteList.add((byte) c);
        }

        int elementsBeforeIdx = strings.length + 1;

        for (int i = 0; i < strings.length; i++) {
            byteList.add(0, (byte) (offsets.pop() + elementsBeforeIdx));
        }
        byteList.add(0, (byte) (charsOffset + elementsBeforeIdx));

        return byteList;
    }

    /**
     * Collect a non-sorted duplicate-free list of all characters used
     *
     * @param strings (multiple) {@link String}s
     * @return a {@link List} containing alle characters in the given Strings
     */
    public static List<Character> shuffleChars(String... strings) {
        List<Character> characters = new ArrayList<>();

        for (String s : strings) {
            char[] chars = s.toCharArray();
            for (char c : chars) {
                characters.add(c);
            }
        }

        List<Character> shuffled = new ArrayList<>();

        while (characters.size() > 0) {
            int idx = SimplifiedSecureRandom.randomInt(0, characters.size());
            char c = characters.remove(idx);

            if (!shuffled.contains(c))
                shuffled.add(c);
        }
        return shuffled;
    }

    /**
     * Builds a {@code static constexpr uint8_t data[]} containing all {@code byte}s from the given List in that order.
     *
     * @param byteList a {@link List} which contains {@link Byte}s
     * @return a {@link String} representing the given {@code byte}s in a C++ suitable {@code constexpr}-form
     */
    public static String buildHeaderCpp(List<Byte> byteList) {
        StringBuilder data = new StringBuilder();

        data.append("#pragma once");
        data.append(LN);
        data.append("#ifndef DATA_HPP");
        data.append(LN);
        data.append("#define DATA_HPP");
        data.append(LN);
        data.append(LN);
        data.append("#include <stdint.h>");
        data.append(LN);
        data.append(LN);
        data.append("static constexpr uint8_t data[] = {");
        data.append(LN);
        data.append('\t');

        int i = 0;
        while (byteList.size() > 0) {
            byte b = byteList.remove(0);
            data.append(String.format("0x%02X", b));
            //data.append((char) b);
            data.append(",");
            if (++i == 10) {
                data.append(LN);
                data.append('\t');
                i = 0;
                continue;
            }
            data.append(' ');
        }
        data.reverse().delete(0, 2).reverse();
        data.append(LN);
        data.append("};");
        data.append(LN);
        data.append(LN);
        data.append("#endif // DATA_HPP");

        return data.toString();
    }


}
