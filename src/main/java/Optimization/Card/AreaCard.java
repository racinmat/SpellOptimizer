package Optimization.Card;

public class AreaCard extends Card {

    public AreaCard(Level level) {
        super(level);
    }

    @Override
    public int getAreaRadiusIncrease() {
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
        return new AreaCard(level);
    }

    @Override
    public int getMana() {
        return level.getAreaCardMana();
    }

    @Override
    public int getCastChanceBonus() {
        return level.getAreaCastChance();
    }

}
