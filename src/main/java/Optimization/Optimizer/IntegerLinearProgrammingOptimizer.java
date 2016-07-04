package Optimization.Optimizer;

import Optimization.Card.*;
import Optimization.Spell;
import Optimization.Utils;
import com.joptimizer.functions.ConvexMultivariateRealFunction;
import com.joptimizer.functions.LinearMultivariateRealFunction;
import com.joptimizer.optimizers.JOptimizer;
import com.joptimizer.optimizers.OptimizationRequest;
import net.sf.javailp.*;

import java.lang.reflect.InvocationTargetException;

public class IntegerLinearProgrammingOptimizer extends AbstractLinearProgrammingOptimizer {

    public Spell optimize(int maximalCastTime, int maxMana, int maxCardCount, int minimalCastChance) {

        String[] names = new String[] {
                AreaCard.class.toString() + " Level 1",
                AreaCard.class.toString() + " Level 2",
                AreaCard.class.toString() + " Level 3",
                AreaCard.class.toString() + " Level 4",

                BasicCard.class.toString() + " Level 1",
                BasicCard.class.toString() + " Level 2",
                BasicCard.class.toString() + " Level 3",
                BasicCard.class.toString() + " Level 4",

                CastingCard.class.toString() + " Level 1",
                CastingCard.class.toString() + " Level 2",
                CastingCard.class.toString() + " Level 3",
                CastingCard.class.toString() + " Level 4",

                DamageCard.class.toString() + " Level 1",
                DamageCard.class.toString() + " Level 2",
                DamageCard.class.toString() + " Level 3",
                DamageCard.class.toString() + " Level 4",

                DurationCard.class.toString() + " Level 1",
                DurationCard.class.toString() + " Level 2",
                DurationCard.class.toString() + " Level 3",
                DurationCard.class.toString() + " Level 4",

                ManaCard.class.toString() + " Level 1",
                ManaCard.class.toString() + " Level 2",
                ManaCard.class.toString() + " Level 3",
                ManaCard.class.toString() + " Level 4",

                RangeCard.class.toString() + " Level 1",
                RangeCard.class.toString() + " Level 2",
                RangeCard.class.toString() + " Level 3",
                RangeCard.class.toString() + " Level 4",

                TimeCard.class.toString() + " Level 1",
                TimeCard.class.toString() + " Level 2",
                TimeCard.class.toString() + " Level 3",
                TimeCard.class.toString() + " Level 4",
        };

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

        double[] areaCardDamage = new double[] {
                0, 0, 0, 0
        };

        double[] basicCardDamage = new double[] {
                new BasicCard(Level.LEVEL_1).getDamage(), new BasicCard(Level.LEVEL_2).getDamage(), new BasicCard(Level.LEVEL_3).getDamage(), new BasicCard(Level.LEVEL_4).getDamage()
        };
        double[] castingCardDamage = new double[] {
                0, 0, 0, 0
        };
        double[] damageCardDamage = new double[] {
                new DamageCard(Level.LEVEL_1).getDamage(), new DamageCard(Level.LEVEL_2).getDamage(), new DamageCard(Level.LEVEL_3).getDamage(), new DamageCard(Level.LEVEL_4).getDamage()
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

        //G matrix is used for constraints
        double[][] constraints = createConstraintsMatrix();

        //damage dealt is maximized
        double[] damage = Utils.mergeArrays(areaCardDamage, basicCardDamage, castingCardDamage, damageCardDamage, durationCardDamage, manaCardDamage, rangeCardDamage, timeCardDamage);

        double[] maxValues = new double[] {
                maxMana,
                - minimalCastChance,
                maxCardCount,
                (maximalCastTime * 3),      //cast time will be counted as card count with modifications
        };

        SolverFactory factory = new SolverFactoryLpSolve(); // use lp_solve
        factory.setParameter(Solver.VERBOSE, 0);
        factory.setParameter(Solver.TIMEOUT, 100); // set timeout to 100 seconds

        Problem problem = new Problem();

        Linear linear = new Linear();
        for (int i = 0; i < damage.length; i++) {
            linear.add(damage[i], Integer.toString(i));
        }
        problem.setObjective(linear, OptType.MAX);

        for (int i = 0; i < constraints.length; i++) {
            linear = new Linear();
            for (int j = 0; j < constraints[i].length; j++) {
                linear.add(constraints[i][j], Integer.toString(j));
            }
            problem.add(linear, "<=", maxValues[i]);
        }

        for (int i = 0; i < damage.length; i++) {
            problem.setVarType(Integer.toString(i), Integer.class);
        }

        Solver solver = factory.get(); // you should use this solver only once for one problem
        Result result = solver.solve(problem);

//        System.out.println(result);

        Spell spell = new Spell();
        for (int i = 0; i < damage.length; i++) {
//            System.out.print(names[i] + ": ");
            int cardAmount = (int) result.getPrimalValue(Integer.toString(i));
//            System.out.println(cardAmount);


            try {
                int levelNumber = i % 4;
                for (int j = 0; j < cardAmount; j++) {
                    spell.cards.add(classes[i].getConstructor(Level.class).newInstance(Level.fromInteger(levelNumber)));
                }
            } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        return spell;
    }

}
