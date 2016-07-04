package Optimization.Card;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum Level {

    LEVEL_1(3, 3, 2, 2, 2, -2, 3, 10,    -10,  -5, 10,  -5,  -5,  -5,  -5, -15),
    LEVEL_2(4, 4, 3, 3, 3, -4, 4, 15,    -15,  -7, 20,  -7,  -7,  -7,  -7, -22),
    LEVEL_3(6, 6, 4, 4, 4, -6, 6, 22,    -30, -10, 30, -10, -10, -10, -10, -33),
    LEVEL_4(9, 9, 6, 6, 6, -8, 9, 33,    -45, -15, 40, -15, -15, -15, -15, -49);

    private int areaCardMana;
    private int basicCardMana;
    private int castingCardMana;
    private int damageCardMana;
    private int durationCardMana;
    private int manaCardMana;
    private int rangeCardMana;
    private int timeCardMana;

    private int areaCastChance;
    private int basicCastChance;
    private int castingCastChance;
    private int damageCastChance;
    private int durationCastChance;
    private int manaCastChance;
    private int rangeCastChance;
    private int timeCastChance;

    private static final List<Level> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    Level(int areaCardMana, int basicCardMana, int castingCardMana, int damageCardMana, int durationCardMana, int manaCardMana, int rangeCardMana, int timeCardMana, int areaCastChance, int basicCastChance, int castingCastChance, int damageCastChance, int durationCastChance, int manaCastChance, int rangeCastChance, int timeCastChance) {
        this.areaCardMana = areaCardMana;
        this.basicCardMana = basicCardMana;
        this.castingCardMana = castingCardMana;
        this.damageCardMana = damageCardMana;
        this.durationCardMana = durationCardMana;
        this.manaCardMana = manaCardMana;
        this.rangeCardMana = rangeCardMana;
        this.timeCardMana = timeCardMana;
        this.areaCastChance = areaCastChance;
        this.basicCastChance = basicCastChance;
        this.castingCastChance = castingCastChance;
        this.damageCastChance = damageCastChance;
        this.durationCastChance = durationCastChance;
        this.manaCastChance = manaCastChance;
        this.rangeCastChance = rangeCastChance;
        this.timeCastChance = timeCastChance;
    }

    public static Level random() {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }

    public static Level fromInteger(int number) {
        return VALUES.get(number);
    }

    public int getAreaCardMana() {
        return areaCardMana;
    }

    public int getAreaCastChance() {
        return areaCastChance;
    }

    public int getBasicCardMana() {
        return basicCardMana;
    }

    public int getBasicCastChance() {
        return basicCastChance;
    }

    public int getCastingCardMana() {
        return castingCardMana;
    }

    public int getCastingCastChance() {
        return castingCastChance;
    }

    public int getDamageCardMana() {
        return damageCardMana;
    }

    public int getDamageCastChance() {
        return damageCastChance;
    }

    public int getDurationCardMana() {
        return durationCardMana;
    }

    public int getDurationCastChance() {
        return durationCastChance;
    }

    public int getManaCardMana() {
        return manaCardMana;
    }

    public int getManaCastChance() {
        return manaCastChance;
    }

    public int getRangeCardMana() {
        return rangeCardMana;
    }

    public int getRangeCastChance() {
        return rangeCastChance;
    }

    public int getTimeCardMana() {
        return timeCardMana;
    }

    public int getTimeCastChance() {
        return timeCastChance;
    }
}
