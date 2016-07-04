package Optimization;

import Optimization.Card.Card;

import java.util.ArrayList;
import java.util.Collection;

public class Spell implements Cloneable {

    public Collection<Card> cards;

    public Spell() {
        this.cards = new ArrayList<>();
    }

    public int getMana() {
        int totalCost = 0;
        for (Card card : cards) {
            totalCost += card.getMana();
        }
        return totalCost;
    }

    public int getCastChanceBonus() {
        int totalBonus = 0;
        for (Card card : cards) {
            totalBonus += card.getCastChanceBonus();
        }
        return totalBonus;
    }

    public int getCastTime() {
        int totalReduction = 0;
        for (Card card : cards) {
            totalReduction += card.getCastTimeReduction();
        }
        return (int) Math.ceil(cards.size()) - totalReduction;
    }

    public double getTotalDamage() {
        int totalDamage = 0;
        for (Card card : cards) {
            totalDamage += card.getDamage();
        }
        return totalDamage * getDuration();
    }

    public int getAffectedArea() {
        int radius = 0;
        for (Card card : cards) {
            radius += card.getAreaRadiusIncrease();
        }
        return HexMath.getArea(radius);
    }

    public int getDuration() {
        int totalDuration = 1;
        for (Card card : cards) {
            totalDuration += card.getDurationIncrease();
        }
        return totalDuration;
    }

    public double getCostFunction() {
        return - getTotalDamage();
    }

    public int getRange() {
        int totalRange = 0;
        for (Card card : cards) {
            totalRange += card.getRangeIncrease();
        }
        return totalRange;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Spell spell = (Spell) o;

        return cards.equals(spell.cards);

    }

    @Override
    public int hashCode() {
        return cards.hashCode();
    }

    @Override
    public String toString() {
        return "Spell{" +
                "\ncast chance=" + getCastChanceBonus() +
                ", \ncast time=" + getCastTime() +
                ", \nmana=" + getMana() +
                ", \ncards=\n" + cards.size() +
//                ", \ncards=\n" + cards.toString() +
                "\n}"
        ;
    }

    @Override
    public Spell clone() throws CloneNotSupportedException {
        Spell spell = new Spell();
        for (Card card : cards) {
            spell.cards.add(card.clone());
        }
        return spell;
    }

}
