package Optimization.Optimizer;

import Optimization.Card.BasicCard;
import Optimization.Card.CardType;
import Optimization.Card.DamageCard;
import Optimization.Card.Level;
import Optimization.Spell;
import Optimization.Utils;
import com.joptimizer.functions.ConvexMultivariateRealFunction;
import com.joptimizer.functions.LinearMultivariateRealFunction;
import com.joptimizer.optimizers.JOptimizer;
import com.joptimizer.optimizers.OptimizationRequest;
import org.apache.log4j.BasicConfigurator;

public class LinearProgrammingOptimizer extends AbstractLinearProgrammingOptimizer {

    public Spell optimize(int maximalCastTime, int maxMana, int maxCardCount, int minimalCastChance) {

        //G matrix is used for constraints
        double[][] G = createConstraintsMatrix();

        // Objective function (plane) = cost function to be minimized, in form f(x) = q.x + r
        //cost function is damage dealt
        double r = 0;
        double[] q = createCostFunctionArray();
        LinearMultivariateRealFunction objectiveFunction = new LinearMultivariateRealFunction(q, r);

        //inequalities (polyhedral feasible set G.X<H )
        ConvexMultivariateRealFunction[] inequalities = new ConvexMultivariateRealFunction[4];

        double[] h = new double[] {
                maxMana + 1,
                - minimalCastChance + 1,
                maxCardCount + 1,
                (maximalCastTime * 3) + 1,      //cast time will be counted as card count with modifications
        };

        /* actions to cast -> maximal card count
            1 action -> 3 cards
            2 actions -> 6 cards
            3 actions -> 9 cards
            ...
        */

        for (int i = 0; i < G.length; i++) {
            inequalities[i] = new LinearMultivariateRealFunction(G[i], -h[i]);
        }

        //optimization problem
        OptimizationRequest or = new OptimizationRequest();
        or.setF0(objectiveFunction);
        or.setFi(inequalities);
        //or.setInitialPoint(new double[] {0.0, 0.0});//initial feasible point, not mandatory
        or.setToleranceFeas(1.E-1);
        or.setTolerance(1.E-1);

        BasicConfigurator.configure();

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

        //todo: add rounding, propably later

        return new Spell();
    }

}
