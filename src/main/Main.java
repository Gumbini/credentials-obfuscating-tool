package main;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<Character> charsShuffled = ShuffleCrypt.shuffleChars(args);
        List<Byte> bytes = ShuffleCrypt.constructByteList(charsShuffled, args);

        System.out.println(ShuffleCrypt.buildHeaderCpp(bytes));
    }
}
