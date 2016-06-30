package Optimization.Optimizer;

import Optimization.Card.*;
import Optimization.Spell;

public class NaiveOptimizer extends Optimizer {

    public Spell optimize(int maximalCastTime, int maxMana, int maxCardCount) {
        Spell best = new Spell();
        for (int basic = 0; basic < maxCardCount; basic++) {
            for (Level level : Level.values()) {
                for (int casting = 0; casting < maxCardCount - basic; casting++) {
                    for (Level level2 : Level.values()) {
                        for (int damage = 0; damage < maxCardCount - basic - casting; damage++) {
                            for (Level level3 : Level.values()) {
                                for (int duration = 0; duration < maxCardCount - basic - casting - damage; duration++) {
                                    for (Level level4 : Level.values()) {
                                        for (int mana = 0; mana < maxCardCount - basic - casting - damage - duration; mana++) {
                                            for (Level level5 : Level.values()) {
                                                for (int range = 0; range < maxCardCount - basic - casting - damage - duration - mana; range++) {
                                                    for (Level level6 : Level.values()) {
                                                        for (int time = 0; time < maxCardCount - basic - casting - damage - duration - mana - range; time++) {
                                                            for (Level level7 : Level.values()) {
                                                                Spell spell = new Spell();
                                                                for (int i = 0; i < basic; i++) {
                                                                    spell.cards.add(new BasicCard(level));
                                                                }
                                                                for (int i = 0; i < casting; i++) {
                                                                    spell.cards.add(new CastingCard(level2));
                                                                }
                                                                for (int i = 0; i < damage; i++) {
                                                                    spell.cards.add(new DamageCard(level3));
                                                                }
                                                                for (int i = 0; i < duration; i++) {
                                                                    spell.cards.add(new DurationCard(level4));
                                                                }
                                                                for (int i = 0; i < mana; i++) {
                                                                    spell.cards.add(new ManaCard(level5));
                                                                }
                                                                for (int i = 0; i < range; i++) {
                                                                    spell.cards.add(new RangeCard(level6));
                                                                }
                                                                for (int i = 0; i < time; i++) {
                                                                    spell.cards.add(new TimeCard(level7));
                                                                }

                                                                int manaCost = spell.getMana();
                                                                int castTime = spell.getCastTime();
                                                                int cardCount = spell.cards.size();
                                                                if (manaCost <= maxMana && cardCount <= maxCardCount && castTime <= maximalCastTime) {
                                                                    if (spell.getCostFunction() < best.getCostFunction()) {
                                                                        best = spell;
                                                                        System.out.println("Found best, cost function is " + best.getCostFunction());
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return best;
    }

}
