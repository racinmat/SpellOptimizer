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
import com.sun.corba.se.impl.javax.rmi.CORBA.Util;
import org.apache.log4j.BasicConfigurator;
import org.ejml.simple.SimpleMatrix;

public class LinearProgrammingOptimizer extends AbstractLinearProgrammingOptimizer {

    public Spell optimize(int maximalCastTime, int maxMana, int maxCardCount, int minimalCastChance) {

        //G matrix is used for constraints
        double[][] G = createConstraintsMatrix();

        SimpleMatrix onesMatrix = SimpleMatrix.identity(32);
        SimpleMatrix minusOnesMatrix = onesMatrix.scale(-1);

        double[][] extendedG = Utils.mergeMatricesVertically(G, Utils.matrixTo2Darray(minusOnesMatrix));

        // Objective function (plane) = cost function to be minimized, in form f(x) = q.x + r
        //cost function is damage dealt
        double r = 0;
        double[] q = createCostFunctionArray();
        LinearMultivariateRealFunction objectiveFunction = new LinearMultivariateRealFunction(q, r);

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

        double[] zeroVector = new double[q.length];
        for (int i = 0; i < zeroVector.length; i++) {
            zeroVector[i] = 0;
        }

        double[] smallValuesVector = new double[zeroVector.length];
        for (int i = 0; i < zeroVector.length; i++) {
            smallValuesVector[i] = 0.01;
        }

        double[] constraints = new double[h.length + zeroVector.length];
        System.arraycopy(h, 0, constraints, 0, h.length);
        System.arraycopy(smallValuesVector, 0, constraints, h.length, smallValuesVector.length);

        //added constraints for 0 cards or more
        //inequalities (polyhedral feasible set G.X<H )
        ConvexMultivariateRealFunction[] inequalities = new ConvexMultivariateRealFunction[constraints.length];
        for (int i = 0; i < extendedG.length; i++) {
            inequalities[i] = new LinearMultivariateRealFunction(extendedG[i], -constraints[i]);
        }

        //optimization problem
        OptimizationRequest or = new OptimizationRequest();
        or.setF0(objectiveFunction);
        or.setFi(inequalities);
        or.setInitialPoint(zeroVector);
        or.setToleranceFeas(1.E-2);
        or.setTolerance(1.E-2);

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

        //todo: add rounding, probably later

        return new Spell();
    }

}
