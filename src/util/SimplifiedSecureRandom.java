package util;

import java.security.SecureRandom;

public final class SimplifiedSecureRandom {
    private static final SecureRandom secureRandom = new SecureRandom();

    static {
        secureRandom.setSeed(System.currentTimeMillis() * 187L);
    }

    /**
     * Dummy constructor: This is a utility class which should not be instantiated.
     */
    private SimplifiedSecureRandom() {
    }

    /**
     * @return a cryptographically strong generated random integer in [{@code origin}, {@code bound})
     */
    public static int randomInt(int origin, int bound) {
        return (origin == bound) ? origin : secureRandom.ints(1, origin, bound).sum();
    }
}
