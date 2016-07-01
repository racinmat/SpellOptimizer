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
        return level.getBasicCardMana();
    }

    @Override
    public int getCastChanceBonus() {
        return level.getBasicCastChance();
    }

    @Override
    public Card clone() throws CloneNotSupportedException {
        return new BasicCard(level);
    }

}
