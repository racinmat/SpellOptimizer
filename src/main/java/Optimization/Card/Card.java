package Optimization.Card;

abstract public class Card implements Cloneable {

    public Level level;

    public Card(Level level) {
        this.level = level;
    }

    abstract public int getMana();

    abstract public int getCastChanceBonus();

    public int getCastTimeReduction() {
        return 0;
    }

    public double getDamage() {
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Card)) return false;

        Card card = (Card) o;

        return level == card.level && getClass().equals(card.getClass());

    }

    @Override
    public int hashCode() {
        return level.hashCode() + 31 * getClass().hashCode();
    }

    public int getRangeIncrease() {
        return 0;
    }

    public int getDurationIncrease() {
        return 0;
    }

    public int getAreaRadiusIncrease() {
        return 0;
    }

    @Override
    public String toString() {
        return "\nCard{" +
                "level=" + level +
                ", " + getClass().toString() +
                "}";
    }

    @Override
    abstract public Card clone() throws CloneNotSupportedException;
}
