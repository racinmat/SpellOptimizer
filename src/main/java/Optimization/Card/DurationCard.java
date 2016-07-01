package Optimization.Card;

public class DurationCard extends Card {

    public DurationCard(Level level) {
        super(level);
    }

    @Override
    public int getMana() {
        return level.getDurationCardMana();
    }

    @Override
    public int getCastChanceBonus() {
        return level.getDurationCastChance();
    }

    @Override
    public int getDurationIncrease() {
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
        return new DurationCard(level);
    }

}
