package Optimization.Optimizer;

import Optimization.Card.*;
import Optimization.Spell;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Random;

public class GradientDescent extends Optimizer {

    public Spell optimize(int maximalCastTime, int maxMana, int maxCardCount) {
        Spell spell = getInitialSpell(maxCardCount);
        Spell best = new Spell();

        while ( ! spell.equals(best)) {
            Collection<Spell> neighbours = getNeighbourSpells(spell);
        }
        return best;
    }

    private Spell getInitialSpell(int maxCardCount) {
        Spell spell = new Spell();
        Random r = new Random();
        for (int i = 0; i < r.nextInt(maxCardCount + 1 - spell.cards.size()); i++) {
            spell.cards.add(new BasicCard(Level.random()));
        }
        for (int i = 0; i < r.nextInt(maxCardCount + 1 - spell.cards.size()); i++) {
            spell.cards.add(new CastingCard(Level.random()));
        }
        for (int i = 0; i < r.nextInt(maxCardCount + 1 - spell.cards.size()); i++) {
            spell.cards.add(new DamageCard(Level.random()));
        }
        for (int i = 0; i < r.nextInt(maxCardCount + 1 - spell.cards.size()); i++) {
            spell.cards.add(new DurationCard(Level.random()));
        }
        for (int i = 0; i < r.nextInt(maxCardCount + 1 - spell.cards.size()); i++) {
            spell.cards.add(new ManaCard(Level.random()));
        }
        for (int i = 0; i < r.nextInt(maxCardCount + 1 - spell.cards.size()); i++) {
            spell.cards.add(new RangeCard(Level.random()));
        }
        for (int i = 0; i < r.nextInt(maxCardCount + 1 - spell.cards.size()); i++) {
            spell.cards.add(new TimeCard(Level.random()));
        }
        return spell;
    }

    /**
     *
     * @return returns spells next to provided spell in state space
     */
    private Collection<Spell> getNeighbourSpells(Spell spell) {
        Collection<Spell> neighbours = new HashSet<>();
        for (CardType cardType : CardType.values()) {
            for (Level level : Level.values()) {
                try {
                    Spell neighbour = spell.clone();
                    neighbour.cards.add(cardType.getCardClass().getConstructor(Level.class).newInstance(level));
                    neighbours.add(neighbour);
                } catch (CloneNotSupportedException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
                    e.printStackTrace();
                }
                try {
                    Spell neighbour = spell.clone();
//                    if (neighbour.cards.stream().fi)
                    //TODO: implement card removing to get to neighbouring state
                    neighbours.add(neighbour);
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
            }
        }
        return  neighbours;
    }

}
