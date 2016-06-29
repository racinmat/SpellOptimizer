package Optimization.Card;

public class CastingCard extends Card {

    public CastingCard(Level level) {
        super(level);
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
            case LEVEL_1: return 10;
            case LEVEL_2: return 20;
            case LEVEL_3: return 30;
            case LEVEL_4: return 40;
        }
        return 0;
    }

}
