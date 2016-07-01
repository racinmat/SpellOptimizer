package Optimization.Card;

public class CastingCard extends Card {

    public CastingCard(Level level) {
        super(level);
    }

    @Override
    public int getMana() {
        return level.getCastingCardMana();
    }

    @Override
    public int getCastChanceBonus() {
        return level.getCastingCastChance();
    }

    @Override
    public Card clone() throws CloneNotSupportedException {
        return new CastingCard(level);
    }

}
