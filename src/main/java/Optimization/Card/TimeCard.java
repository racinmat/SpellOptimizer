package Optimization.Card;

public class TimeCard extends Card {

    public TimeCard(Level level) {
        super(level);
    }

    @Override
    public int getMana() {
        switch (level) {
            case LEVEL_1: return 10;
            case LEVEL_2: return 15;
            case LEVEL_3: return 22;
            case LEVEL_4: return 33;
        }
        return 0;
    }

    @Override
    public int getCastChanceBonus() {
        switch (level) {
            case LEVEL_1: return -15;
            case LEVEL_2: return -22;
            case LEVEL_3: return -33;
            case LEVEL_4: return -49;
        }
        return 0;
    }

    @Override
    public int getCastTimeReduction() {
        switch (level) {
            case LEVEL_1: return 1;
            case LEVEL_2: return 2;
            case LEVEL_3: return 3;
            case LEVEL_4: return 4;
        }
        return 0;
    }

    @Override
    public Card clone() throws CloneNotSupportedException {
        return new TimeCard(level);
    }

}
