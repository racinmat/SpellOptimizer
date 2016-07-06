package Optimization.Optimizer;

import Optimization.Card.*;
import Optimization.Spell;
import Optimization.Utils;
import com.joptimizer.functions.ConvexMultivariateRealFunction;
import com.joptimizer.functions.LinearMultivariateRealFunction;
import com.joptimizer.optimizers.JOptimizer;
import com.joptimizer.optimizers.OptimizationRequest;
import com.sun.corba.se.impl.javax.rmi.CORBA.Util;
import com.sun.javafx.collections.MappingChange;
import org.apache.commons.math3.util.Pair;
import org.apache.log4j.BasicConfigurator;
import org.ejml.simple.SimpleMatrix;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LinearProgrammingOptimizer extends AbstractLinearProgrammingOptimizer {

    public Spell optimize(int maximalCastTime, int maxMana, int maxCardCount, int minimalCastChance) {
        double[] cardAmounts = optimizeContinuous(maximalCastTime, maxMana, maxCardCount, minimalCastChance);
        boolean areAmountsInteger = true;
        for (double cardAmount : cardAmounts) {
            if (cardAmount != Math.floor(cardAmount)) {
                areAmountsInteger = false;
                break;
            }
        }

        if (areAmountsInteger) {
            return createSpellFromCardAmounts(cardAmounts);
        }

        //values are not integer, add equality constraints on Damage and Basic cards, where I know exactly their amount
        Map<Pair<Class<? extends Card>, Level>, Integer> constraints = new HashMap<>();
        for (int i = 0; i < cardAmounts.length; i++) {
            Class<? extends Card> cardClass = getCardVariableClasses()[i];
            if (cardClass == BasicCard.class || getCardVariableClasses()[i] == DamageCard.class ) {
                constraints.put(Pair.create(cardClass, Level.fromInteger(i % 4)), (int) Math.floor(cardAmounts[i]));
            }
        }

        optimizeContinuous(maximalCastTime, maxMana, maxCardCount, minimalCastChance, constraints);
        return new Spell();
    }

    private double[] optimizeContinuous(int maximalCastTime, int maxMana, int maxCardCount, int minimalCastChance) {
        OptimizationRequest or = buildOptimizationRequest(maximalCastTime, maxMana, maxCardCount, minimalCastChance);
        return processOptimization(or);
    }

    private double[] optimizeContinuous(int maximalCastTime, int maxMana, int maxCardCount, int minimalCastChance, Map<Pair<Class<? extends Card>, Level>, Integer> equalityConstraints) {
        int numVariables = 32;
        OptimizationRequest or = buildOptimizationRequest(maximalCastTime, maxMana, maxCardCount, minimalCastChance);

        //add equality constraint Ax=b, only non zero constraints are in matrix A
        int constraintsCount = equalityConstraints.size();
        SimpleMatrix matrixA = new SimpleMatrix(constraintsCount, numVariables);
        int row = 0;
        List<Pair<? extends Card,Level>> rows = new ArrayList<>();
        double[] b = new double[equalityConstraints.size()];
        for (Map.Entry<Pair<Class<? extends Card>,Level>,Integer> cardConstraint : equalityConstraints.entrySet()) {
            for (int i = 0; i < numVariables; i++) {
                Class<? extends Card> cardClass = cardConstraint.getKey().getFirst();
                Level level = cardConstraint.getKey().getSecond();
                if (getCardVariableClasses()[i] == cardClass && level == Level.fromInteger(i % 4)) {
                    matrixA.set(row, i, 1);
                } else {
                    matrixA.set(row, i, 0);
                }
            }
            b[row] = cardConstraint.getValue();
            row++;
        }

        double[][] AasArray = Utils.matrixTo2Darray(matrixA);
        or.setA(AasArray);
        or.setB(b);

        return processOptimization(or);
    }

    private OptimizationRequest buildOptimizationRequest(int maximalCastTime, int maxMana, int maxCardCount, int minimalCastChance) {
        double epsilon = 1.E-2;


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
                maxMana + epsilon,
                - minimalCastChance + epsilon,
                maxCardCount + epsilon,
                (maximalCastTime * 3) + epsilon,      //cast time will be counted as card count with modifications
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
            smallValuesVector[i] = epsilon;
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
//        or.setInitialPoint(zeroVector);
        or.setToleranceFeas(epsilon / 10);
        or.setTolerance(epsilon / 10);

        return or;
    }

    private double[] processOptimization(OptimizationRequest or) {

        //optimization
        JOptimizer opt = new JOptimizer();
        opt.setOptimizationRequest(or);
        try {
            int returnCode = opt.optimize();
        } catch (Exception e) {
            e.printStackTrace();
        }
        double[] sol = opt.getOptimizationResponse().getSolution();
        System.out.println("Solution found:");
        for (double v : sol) {
            System.out.println(v + ", ");
        }

        //todo: add rounding, probably later
        String[] names = getVariableNames();
        double[] cardAmounts = new double[sol.length];
        for (int i = 0; i < sol.length; i++) {
            if (i % 4 == 0) {
                System.out.println("");
            }
            System.out.print(names[i] + ": ");

            double cardAmount = sol[i];
//            System.out.print("before round: " + cardAmount + ", ");
            System.out.print(cardAmount + ", ");
            if (Math.abs(cardAmount) < 0.01) {  //zero value
                cardAmount = 0;
            }
            //if basic card or damage card is not whole value, round it down, because round up would cause lesser value than global minimum
//            if (getCardVariableClasses()[i] == BasicCard.class || getCardVariableClasses()[i] == DamageCard.class) {
                cardAmount = Math.floor(cardAmount);
//            }
            cardAmounts[i] = cardAmount;
//            System.out.print("after round" + cardAmount + ", ");
//            System.out.print(cardAmount + ", ");
        }
        return cardAmounts;
    }

}

//        Area 1: 0,Area 2: 0,Area 3: 0,Area 4: 0,
//        Basic 1: 0,Basic 2: 0,Basic 3: 0,Basic 4: 2,
//        Casting 1: 0,Casting 2: 0,Casting 3: 2,Casting 4: 0,
//        Damage 1: 0,Damage 2: 0,Damage 3: 0,Damage 4: 0,
//        Duration 1: 0,Duration 2: 0,Duration 3: 0,Duration 4: 0,
//        Mana 1: 0,Mana 2: 0,Mana 3: 2,Mana 4: 0,
//        Range 1: 0,Range 2: 0,Range 3: 0,Range 4: 0,
//        Time 1: 0,Time 2: 0,Time 3: 0,Time 4: 0,

//        Area 1: 0.0,Area 2: 0.0,Area 3: 0.0,Area 4: 0.0,
//        Basic 1: 0.0,Basic 2: 0.0,Basic 3: 0.0,Basic 4: 2.0,
//        Casting 1: 0.0,Casting 2: 0.0,Casting 3: 0.0,Casting 4: 2.0,
//        Damage 1: 0.0,Damage 2: 0.0,Damage 3: 0.0,Damage 4: 0.0,
//        Duration 1: 0.0,Duration 2: 0.0,Duration 3: 0.0,Duration 4: 0.0,
//        Mana 1: 0.0,Mana 2: 0.0,Mana 3: 0.0,Mana 4: 4.0,
//        Range 1: 0.0,Range 2: 0.0,Range 3: 0.0,Range 4: 0.0,
//        Time 1: 0.0,Time 2: 0.0,Time 3: 0.0,Time 4: 0.0,Spell{