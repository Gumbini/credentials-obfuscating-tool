package util;

import java.util.ArrayList;
import java.util.List;

public final class ListCheck {

    /**
     * Dummy constructor: This is a utility class which should not be instantiated.
     */
    private ListCheck() {
    }

    /**
     * Checks if all characters in the {@code characterList} are inside the 8-bit ASCII range normally used in C/C++ programs and
     * returns a converted List.
     *
     * @param characterList a {@link List} containing Java (Unicode) characters
     * @return a List of {@code byte}s if the conversion succeeds
     * @throws RuntimeException if a char is outside the convertible 8-bit ASCII range
     */
    public static List<Byte> checkRangeAndConvert(List<Character> characterList) {
        List<Byte> byteList = new ArrayList<>();

        for (char c : characterList) {
            if ((int) c > 0xFF) {
                throw new RuntimeException("char '" + c + "' is outside the 8-bit ASCII range!");
            }
            byteList.add((byte) c);
        }

        return byteList;
    }
}
