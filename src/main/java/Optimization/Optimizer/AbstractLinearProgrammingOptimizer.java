package Optimization.Optimizer;

import Optimization.Card.Level;
import Optimization.Card.TimeCard;
import Optimization.Utils;

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

}
