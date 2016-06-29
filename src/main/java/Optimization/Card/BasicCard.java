package Optimization.Card;

public class BasicCard extends Card {

    public BasicCard(Level level) {
        super(level);
    }

    public double getDamage() {
        int maxNumber = 0;//returns average of dice roll
        switch (level) {
            case LEVEL_1: maxNumber = 6; break;
            case LEVEL_2: maxNumber = 8; break;
            case LEVEL_3: maxNumber = 10; break;
            case LEVEL_4: maxNumber = 20; break;
        }
        return ( 1 + maxNumber ) / 2;
    }

    @Override
    public int getMana() {
        switch (level) {
            case LEVEL_1: return 3;
            case LEVEL_2: return 4;
            case LEVEL_3: return 6;
            case LEVEL_4: return 9;
        }
        return 0;
    }

    @Override
    public int getCastChanceBonus() {
        switch (level) {
            case LEVEL_1: return -5;
            case LEVEL_2: return -7;
            case LEVEL_3: return -10;
            case LEVEL_4: return -15;
        }
        return 0;
    }

}
