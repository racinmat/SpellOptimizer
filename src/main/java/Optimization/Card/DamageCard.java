package Optimization.Card;

public class DamageCard extends Card {


    public DamageCard(Level level) {
        super(level);
    }

    public double getDamage() {
        switch (level) {
            case LEVEL_1: return 1;
            case LEVEL_2: return 2;
            case LEVEL_3: return 3;
            case LEVEL_4: return 4;
        }
        return 0;
    }

    @Override
    public int getMana() {
        switch (level) {
            case LEVEL_1: return 2;
            case LEVEL_2: return 3;
            case LEVEL_3: return 4;
            case LEVEL_4: return 6;
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

    @Override
    public Card clone() throws CloneNotSupportedException {
        return new DamageCard(level);
    }

}
