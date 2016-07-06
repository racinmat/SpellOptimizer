package Optimization.Optimizer;

import Optimization.Card.*;
import Optimization.Spell;
import Optimization.Utils;

import java.lang.reflect.InvocationTargetException;

public abstract class AbstractLinearProgrammingOptimizer extends Optimizer {

    protected double[][] createConstraintsMatrix() {

        //cast chance is inverted because the constraint is  - castChance < someValue
        double[][] areaCard = new double[][] {
                {Level.LEVEL_1.getAreaCardMana(), Level.LEVEL_2.getAreaCardMana(), Level.LEVEL_3.getAreaCardMana(), Level.LEVEL_4.getAreaCardMana()},
                {- Level.LEVEL_1.getAreaCastChance(), - Level.LEVEL_2.getAreaCastChance(), - Level.LEVEL_3.getAreaCastChance(), - Level.LEVEL_4.getAreaCastChance()},
                {1, 1, 1, 1},
                {1, 1, 1, 1},
        };
        double[][] basicCard = new double[][] {
                {Level.LEVEL_1.getBasicCardMana(), Level.LEVEL_2.getBasicCardMana(), Level.LEVEL_3.getBasicCardMana(), Level.LEVEL_4.getBasicCardMana()},
                {- Level.LEVEL_1.getBasicCastChance(), - Level.LEVEL_2.getBasicCastChance(), - Level.LEVEL_3.getBasicCastChance(), - Level.LEVEL_4.getBasicCastChance()},
                {1, 1, 1, 1},
                {1, 1, 1, 1},
        };
        double[][] castingCard = new double[][] {
                {Level.LEVEL_1.getCastingCardMana(), Level.LEVEL_2.getCastingCardMana(), Level.LEVEL_3.getCastingCardMana(), Level.LEVEL_4.getCastingCardMana()},
                {- Level.LEVEL_1.getCastingCastChance(), - Level.LEVEL_2.getCastingCastChance(), - Level.LEVEL_3.getCastingCastChance(), - Level.LEVEL_4.getCastingCastChance()},
                {1, 1, 1, 1},
                {1, 1, 1, 1},
        };
        double[][] damageCard = new double[][] {
                {Level.LEVEL_1.getDamageCardMana(), Level.LEVEL_2.getDamageCardMana(), Level.LEVEL_3.getDamageCardMana(), Level.LEVEL_4.getDamageCardMana()},
                {- Level.LEVEL_1.getDamageCastChance(), - Level.LEVEL_2.getDamageCastChance(), - Level.LEVEL_3.getDamageCastChance(), - Level.LEVEL_4.getDamageCastChance()},
                {1, 1, 1, 1},
                {1, 1, 1, 1},
        };
        double[][] durationCard = new double[][] {
                {Level.LEVEL_1.getDurationCardMana(), Level.LEVEL_2.getDurationCardMana(), Level.LEVEL_3.getDurationCardMana(), Level.LEVEL_4.getDurationCardMana()},
                {- Level.LEVEL_1.getDurationCastChance(), - Level.LEVEL_2.getDurationCastChance(), - Level.LEVEL_3.getDurationCastChance(), - Level.LEVEL_4.getDurationCastChance()},
                {1, 1, 1, 1},
                {1, 1, 1, 1},
        };
        double[][] manaCard = new double[][] {
                {Level.LEVEL_1.getManaCardMana(), Level.LEVEL_2.getManaCardMana(), Level.LEVEL_3.getManaCardMana(), Level.LEVEL_4.getManaCardMana()},
                {- Level.LEVEL_1.getManaCastChance(), - Level.LEVEL_2.getManaCastChance(), - Level.LEVEL_3.getManaCastChance(), - Level.LEVEL_4.getManaCastChance()},
                {1, 1, 1, 1},
                {1, 1, 1, 1},
        };
        double[][] rangeCard = new double[][] {
                {Level.LEVEL_1.getRangeCardMana(), Level.LEVEL_2.getRangeCardMana(), Level.LEVEL_3.getRangeCardMana(), Level.LEVEL_4.getRangeCardMana()},
                {- Level.LEVEL_1.getRangeCastChance(), - Level.LEVEL_2.getRangeCastChance(), - Level.LEVEL_3.getRangeCastChance(), - Level.LEVEL_4.getRangeCastChance()},
                {1, 1, 1, 1},
                {1, 1, 1, 1},
        };
        double[][] timeCard = new double[][] {
                {Level.LEVEL_1.getTimeCardMana(), Level.LEVEL_2.getTimeCardMana(), Level.LEVEL_3.getTimeCardMana(), Level.LEVEL_4.getTimeCardMana()},
                {- Level.LEVEL_1.getTimeCastChance(), - Level.LEVEL_2.getTimeCastChance(), - Level.LEVEL_3.getTimeCastChance(), - Level.LEVEL_4.getTimeCastChance()},
                {1, 1, 1, 1},
                {1 - 3 * new TimeCard(Level.LEVEL_1).getCastTimeReduction(), 1 - 3 * new TimeCard(Level.LEVEL_2).getCastTimeReduction(), 1 - 3 * new TimeCard(Level.LEVEL_3).getCastTimeReduction(), 1 - 3 * new TimeCard(Level.LEVEL_4).getCastTimeReduction()},
                //shorter cast time is converted to card amount equivalent
        };

        return Utils.mergeMatricesHorizontally(areaCard, basicCard, castingCard, damageCard, durationCard, manaCard, rangeCard, timeCard);
    }

    protected double[] createCostFunctionArray() {

        double[] areaCardDamage = new double[] {
                0, 0, 0, 0
        };

        double[] basicCardDamage = new double[] {
                - new BasicCard(Level.LEVEL_1).getDamage(), - new BasicCard(Level.LEVEL_2).getDamage(), - new BasicCard(Level.LEVEL_3).getDamage(), - new BasicCard(Level.LEVEL_4).getDamage()
        };
        double[] castingCardDamage = new double[] {
                0, 0, 0, 0
        };
        double[] damageCardDamage = new double[] {
                - new DamageCard(Level.LEVEL_1).getDamage(), - new DamageCard(Level.LEVEL_2).getDamage(), - new DamageCard(Level.LEVEL_3).getDamage(), - new DamageCard(Level.LEVEL_4).getDamage()
        };
        double[] durationCardDamage = new double[] {
                0, 0, 0, 0
        };
        double[] manaCardDamage = new double[] {
                0, 0, 0, 0
        };
        double[] rangeCardDamage = new double[] {
                0, 0, 0, 0
        };
        double[] timeCardDamage = new double[] {
                0, 0, 0, 0
        };

        return Utils.mergeArrays(areaCardDamage, basicCardDamage, castingCardDamage, damageCardDamage, durationCardDamage, manaCardDamage, rangeCardDamage, timeCardDamage);
    }

    protected Class<? extends Card>[] getCardVariableClasses() {
        Class<? extends Card>[] classes = new Class[] {
                AreaCard.class,
                AreaCard.class,
                AreaCard.class,
                AreaCard.class,

                BasicCard.class,
                BasicCard.class,
                BasicCard.class,
                BasicCard.class,

                CastingCard.class,
                CastingCard.class,
                CastingCard.class,
                CastingCard.class,

                DamageCard.class,
                DamageCard.class,
                DamageCard.class,
                DamageCard.class,

                DurationCard.class,
                DurationCard.class,
                DurationCard.class,
                DurationCard.class,

                ManaCard.class,
                ManaCard.class,
                ManaCard.class,
                ManaCard.class,

                RangeCard.class,
                RangeCard.class,
                RangeCard.class,
                RangeCard.class,

                TimeCard.class,
                TimeCard.class,
                TimeCard.class,
                TimeCard.class,
        };
        return classes;
    }

    public String[] getVariableNames() {
        String[] names = new String[] {
                "Area 1",
                "Area 2",
                "Area 3",
                "Area 4",

                "Basic 1",
                "Basic 2",
                "Basic 3",
                "Basic 4",

                "Casting 1",
                "Casting 2",
                "Casting 3",
                "Casting 4",

                "Damage 1",
                "Damage 2",
                "Damage 3",
                "Damage 4",

                "Duration 1",
                "Duration 2",
                "Duration 3",
                "Duration 4",

                "Mana 1",
                "Mana 2",
                "Mana 3",
                "Mana 4",

                "Range 1",
                "Range 2",
                "Range 3",
                "Range 4",

                "Time 1",
                "Time 2",
                "Time 3",
                "Time 4",
        };
        return names;
    }

    protected Spell createSpellFromCardAmounts(double[] cardAmounts) {
        int[] integerCardAmounts = new int[cardAmounts.length];
        for (int i = 0; i < cardAmounts.length; i++) {
            integerCardAmounts[i] = (int) Math.floor(cardAmounts[i]);
        }
        return createSpellFromCardAmounts(integerCardAmounts);
    }

    protected Spell createSpellFromCardAmounts(int[] cardAmounts) {
        Spell spell = new Spell();
        for (int i = 0; i < cardAmounts.length; i++) {
            int cardAmount = cardAmounts[i];
            try {
                int levelNumber = i % 4;
                for (int j = 0; j < cardAmount; j++) {
                    spell.cards.add(getCardVariableClasses()[i].getConstructor(Level.class).newInstance(Level.fromInteger(levelNumber)));
                }
            } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        return spell;
    }
}
