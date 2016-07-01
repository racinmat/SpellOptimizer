package Optimization.Card;

public class ManaCard extends Card {

    public ManaCard(Level level) {
        super(level);
    }

    @Override
    public int getMana() {
        return level.getManaCardMana();
    }

    @Override
    public int getCastChanceBonus() {
        return level.getManaCastChance();
    }

    @Override
    public Card clone() throws CloneNotSupportedException {
        return new ManaCard(level);
    }

}
