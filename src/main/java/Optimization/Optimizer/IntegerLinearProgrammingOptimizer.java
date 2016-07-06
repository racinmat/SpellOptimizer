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

        String[] names = getVariableNames();

        //G matrix is used for constraints
        double[][] constraints = createConstraintsMatrix();

        // - damage dealt is minimized
        double[] damage = createCostFunctionArray();

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
        problem.setObjective(linear, OptType.MIN);

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

        int[] cardAmounts = new int[damage.length];
        for (int i = 0; i < damage.length; i++) {
            if (i % 4 == 0) {
                System.out.println("");
            }
            System.out.print(names[i] + ": ");
            cardAmounts[i] = (int) result.getPrimalValue(Integer.toString(i));
            System.out.print(cardAmounts[i] + ",");
        }

//        System.out.println("");
        return createSpellFromCardAmounts(cardAmounts);
    }

}
