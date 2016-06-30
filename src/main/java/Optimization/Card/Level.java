package Optimization.Card;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum Level {

    LEVEL_1, LEVEL_2, LEVEL_3, LEVEL_4;

    private static final List<Level> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    public static Level random() {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }
}
