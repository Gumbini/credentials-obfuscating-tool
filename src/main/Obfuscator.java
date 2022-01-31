package main;

import util.CircularShifts;
import util.SimplifiedSecureRandom;

import java.util.*;

public final class Obfuscator {
    public static final String LN = System.lineSeparator();

    /**
     * Dummy constructor: This is a utility class which should not be instantiated.
     */
    private Obfuscator() {
    }

    /**
     * Builds a {@code constexpr uint8_t data[]} array containing all {@code byte}s from the given List in that order.
     *
     * @param byteList a {@link List} which contains {@link Byte}s
     * @return a {@link String} representing the given {@code byte}s in a C++ suitable {@code constexpr}-form
     */
    public static String buildHeaderCpp(List<Byte> byteList) {
        StringBuilder data = new StringBuilder();

        data.append("#pragma once");
        data.append(LN);
        data.append("#ifndef OBFUSCATED_DATA_HPP");
        data.append(LN);
        data.append("#define OBFUSCATED_DATA_HPP");
        data.append(LN);
        data.append(LN);
        data.append("#include <climits> // for CHAR_BIT");
        data.append(LN);
        data.append("#include <cstdint> // for uint8_t");
        data.append(LN);
        data.append(LN);
        data.append("static_assert(CHAR_BIT == 8, \"Proper deobfuscation can only be guaranteed with 8-bit characters!\");");
        data.append(LN);
        data.append(LN);
        data.append("namespace ObfuscatedCredentials {");
        data.append(LN);
        data.append("\tconstexpr uint8_t data[] = {");
        data.append(LN);
        data.append("\t\t");

        int i = 0;
        while (byteList.size() > 0) {
            byte b = CircularShifts.srlC(byteList.remove(0), 3);
            data.append(String.format("0x%02X", b));
            data.append(",");
            if (++i == 8) {
                data.append(LN);
                data.append("\t\t");
                i = 0;
                continue;
            }
            data.append(' ');
        }
        data.reverse().delete(0, 2).reverse();
        data.append(LN);
        data.append("\t};");
        data.append(LN);
        data.append("};");
        data.append(LN);
        data.append(LN);
        data.append("#endif // OBFUSCATED_DATA_HPP");
        data.append(LN);

        return data.toString();
    }

    /**
     * Construct a {@code List} of {@code byte}s which contains metadata about character position and the characters themselves.
     * <br> {@code Structure of the return List: [chars offset, string0 offset, ..., stringN offset, string0 length, string0
     * idx0,
     * ..., string0 idxN, ..., stringN length, stringN idx0, ..., stringN idxN, char0, ..., charN]}
     *
     * @param shuffledChars a <b>duplicate-free</b> {@link List} containing all {@link Character}s <b>preferably unsorted!</b>
     * @param strings       the {@link String}s which should be encrypted
     * @return a {@link List} containing metadata about character position and the characters themselves
     */
    public static List<Byte> constructByteList(List<Byte> shuffledChars, String... strings) {
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
        byteList.addAll(shuffledChars);

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
}
