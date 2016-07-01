package Optimization.Optimizer;

import Optimization.Card.CardType;
import Optimization.Card.Level;
import Optimization.Spell;
import Optimization.Utils;
import com.joptimizer.functions.ConvexMultivariateRealFunction;
import com.joptimizer.functions.LinearMultivariateRealFunction;
import com.joptimizer.optimizers.JOptimizer;
import com.joptimizer.optimizers.OptimizationRequest;
import org.apache.commons.lang3.ArrayUtils;

public class LinearProgrammingOptimizer extends Optimizer {

    public Spell optimize(int maximalCastTime, int maxMana, int maxCardCount, int minimalCastChance) {

        // Objective function (plane)
        LinearMultivariateRealFunction objectiveFunction = new LinearMultivariateRealFunction(new double[] { -1., -1. }, 4);

        //inequalities (polyhedral feasible set G.X<H )
        ConvexMultivariateRealFunction[] inequalities = new ConvexMultivariateRealFunction[4];
        double[][] areaCard = new double[][] {
                {Level.LEVEL_1.getAreaCardMana(), Level.LEVEL_2.getAreaCardMana(), Level.LEVEL_3.getAreaCardMana(), Level.LEVEL_4.getAreaCardMana()},
                {Level.LEVEL_1.getAreaCastChance(), Level.LEVEL_2.getAreaCastChance(), Level.LEVEL_3.getAreaCastChance(), Level.LEVEL_4.getAreaCastChance()},
                {1, 1, 1, 1},
                {1, 1, 1, 1},
        };
        double[][] basicCard = new double[][] {
                {Level.LEVEL_1.getBasicCardMana(), Level.LEVEL_2.getBasicCardMana(), Level.LEVEL_3.getBasicCardMana(), Level.LEVEL_4.getBasicCardMana()},
                {Level.LEVEL_1.getBasicCastChance(), Level.LEVEL_2.getBasicCastChance(), Level.LEVEL_3.getBasicCastChance(), Level.LEVEL_4.getBasicCastChance()},
                {1, 1, 1, 1},
                {1, 1, 1, 1},
        };
        double[][] castingCard = new double[][] {
                {Level.LEVEL_1.getCastingCardMana(), Level.LEVEL_2.getCastingCardMana(), Level.LEVEL_3.getCastingCardMana(), Level.LEVEL_4.getCastingCardMana()},
                {Level.LEVEL_1.getCastingCastChance(), Level.LEVEL_2.getCastingCastChance(), Level.LEVEL_3.getCastingCastChance(), Level.LEVEL_4.getCastingCastChance()},
                {1, 1, 1, 1},
                {1, 1, 1, 1},
        };
        double[][] damageCard = new double[][] {
                {Level.LEVEL_1.getDamageCardMana(), Level.LEVEL_2.getDamageCardMana(), Level.LEVEL_3.getDamageCardMana(), Level.LEVEL_4.getDamageCardMana()},
                {Level.LEVEL_1.getDamageCastChance(), Level.LEVEL_2.getDamageCastChance(), Level.LEVEL_3.getDamageCastChance(), Level.LEVEL_4.getDamageCastChance()},
                {1, 1, 1, 1},
                {1, 1, 1, 1},
        };
        double[][] durationCard = new double[][] {
                {Level.LEVEL_1.getDurationCardMana(), Level.LEVEL_2.getDurationCardMana(), Level.LEVEL_3.getDurationCardMana(), Level.LEVEL_4.getDurationCardMana()},
                {Level.LEVEL_1.getDurationCastChance(), Level.LEVEL_2.getDurationCastChance(), Level.LEVEL_3.getDurationCastChance(), Level.LEVEL_4.getDurationCastChance()},
                {1, 1, 1, 1},
                {1, 1, 1, 1},
        };
        double[][] manaCard = new double[][] {
                {Level.LEVEL_1.getManaCardMana(), Level.LEVEL_2.getManaCardMana(), Level.LEVEL_3.getManaCardMana(), Level.LEVEL_4.getManaCardMana()},
                {Level.LEVEL_1.getManaCastChance(), Level.LEVEL_2.getManaCastChance(), Level.LEVEL_3.getManaCastChance(), Level.LEVEL_4.getManaCastChance()},
                {1, 1, 1, 1},
                {1, 1, 1, 1},
        };
        double[][] rangeCard = new double[][] {
                {Level.LEVEL_1.getRangeCardMana(), Level.LEVEL_2.getRangeCardMana(), Level.LEVEL_3.getRangeCardMana(), Level.LEVEL_4.getRangeCardMana()},
                {Level.LEVEL_1.getRangeCastChance(), Level.LEVEL_2.getRangeCastChance(), Level.LEVEL_3.getRangeCastChance(), Level.LEVEL_4.getRangeCastChance()},
                {1, 1, 1, 1},
                {1, 1, 1, 1},
        };
        double[][] timeCard = new double[][] {
                {Level.LEVEL_1.getTimeCardMana(), Level.LEVEL_2.getTimeCardMana(), Level.LEVEL_3.getTimeCardMana(), Level.LEVEL_4.getTimeCardMana()},
                {Level.LEVEL_1.getTimeCastChance(), Level.LEVEL_2.getTimeCastChance(), Level.LEVEL_3.getTimeCastChance(), Level.LEVEL_4.getTimeCastChance()},
                {1, 1, 1, 1},
                {1, 1, 1, 1},
        };


        double[][] G = Utils.mergeArrays(areaCard, basicCard, castingCard, damageCard, durationCard, manaCard, rangeCard, timeCard);


        double[] h = new double[] {
                maxMana + 1,
                minimalCastChance - 1,
                maxCardCount + 1,
                (maximalCastTime * 3) + 1,      //cast time will be counted as card count with modifications

        };
        /* actions -> maximal card count
            1 action -> 3 cards
            2 actions -> 6 cards
            3 actions -> 9  cards
        */


        inequalities[0] = new LinearMultivariateRealFunction(G[0], -h[0]);
        inequalities[1] = new LinearMultivariateRealFunction(G[1], -h[1]);
        inequalities[2] = new LinearMultivariateRealFunction(G[2], -h[2]);
        inequalities[3] = new LinearMultivariateRealFunction(G[3], -h[3]);

        //optimization problem
        OptimizationRequest or = new OptimizationRequest();
        or.setF0(objectiveFunction);
        or.setFi(inequalities);
        //or.setInitialPoint(new double[] {0.0, 0.0});//initial feasible point, not mandatory
        or.setToleranceFeas(1.E-1);
        or.setTolerance(1.E-1);

        //optimization
        JOptimizer opt = new JOptimizer();
        opt.setOptimizationRequest(or);
        try {
            int returnCode = opt.optimize();
            double[] sol = opt.getOptimizationResponse().getSolution();
            System.out.println("Solution found:");
            for (double v : sol) {
                System.out.println(v + ", ");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return new Spell();
    }

}
