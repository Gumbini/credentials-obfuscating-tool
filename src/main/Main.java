package main;

import util.ListCheck;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<Character> charsShuffledUnicode = Obfuscator.shuffleChars(args);
        List<Byte> charsShuffledASCII = ListCheck.checkRangeAndConvert(charsShuffledUnicode);
        List<Byte> bytes = Obfuscator.constructByteList(charsShuffledASCII, args);

        System.out.println(Obfuscator.buildHeaderCpp(bytes));
    }
}
