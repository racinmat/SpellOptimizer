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
        return level.getDamageCardMana();
    }

    @Override
    public int getCastChanceBonus() {
        return level.getDamageCastChance();
    }

    @Override
    public Card clone() throws CloneNotSupportedException {
        return new DamageCard(level);
    }

}
