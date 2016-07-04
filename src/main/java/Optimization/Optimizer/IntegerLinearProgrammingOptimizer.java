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
//            if (i % 4 == 0) {
//                System.out.println("");
//            }
//            System.out.print(names[i] + ": ");
            int cardAmount = (int) result.getPrimalValue(Integer.toString(i));
//            System.out.print(cardAmount + ",");


            try {
                int levelNumber = i % 4;
                for (int j = 0; j < cardAmount; j++) {
                    spell.cards.add(classes[i].getConstructor(Level.class).newInstance(Level.fromInteger(levelNumber)));
                }
            } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }

//        System.out.println("");
        return spell;
    }

}
