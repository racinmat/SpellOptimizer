package Optimization.Optimizer;

import Optimization.Card.*;
import Optimization.Spell;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Stream;

public class GradientDescent extends Optimizer {

    private int retries;

    public GradientDescent(int retries) {
        this.retries = retries;
    }

    public Spell optimize(int maximalCastTime, int maxMana, int maxCardCount, int minimalCastChance) {
        List<Spell> localMinima = new ArrayList<>();
        for (int i = 0; i < retries; i++) {
            localMinima.add(findLocalMinimum(maximalCastTime, maxMana, maxCardCount, minimalCastChance));
        }
        Spell best = localMinima.get(0);
        for (Spell localMinimum : localMinima) {
            if (localMinimum.getCostFunction() < best.getCostFunction()) {
                best = localMinimum;
            }
        }
        return best;
    }

    private Spell findLocalMinimum(int maximalCastTime, int maxMana, int maxCardCount, int minimalCastChance) {
        Spell best;
        do {
            best = getInitialSpell(maxCardCount);
        } while (best.getMana() > maxMana || best.cards.size() > maxCardCount || best.getCastTime() > maximalCastTime || best.getCastChanceBonus() < minimalCastChance);
        boolean extremeDetected;
        do {
            extremeDetected = true;
            Collection<Spell> neighbours = getNeighbourSpells(best);
            for (Spell neighbour : neighbours) {
                int manaCost = neighbour.getMana();
                int castTime = neighbour.getCastTime();
                int castChance = neighbour.getCastChanceBonus();
                int cardCount = neighbour.cards.size();
                if (manaCost <= maxMana && cardCount <= maxCardCount && castTime <= maximalCastTime && castChance <= minimalCastChance) {
                    if (neighbour.getCostFunction() < best.getCostFunction()) {
                        best = neighbour;
                        extremeDetected = false;
                        System.out.println("Found best, cost function is " + best.getCostFunction());
                    }
                }
            }
        } while ( ! extremeDetected);
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
                    Stream<Card> sameCards = neighbour.cards.stream().filter((Card card) -> card.getClass().equals(cardType.getCardClass()) && card.level == level);
                    Optional<Card> sameCard = sameCards.findFirst();
                    if (sameCard.isPresent()) {
                        neighbour.cards.remove(sameCard.get());
                        neighbours.add(neighbour);
                    }
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
            }
        }
        return  neighbours;
    }

}
