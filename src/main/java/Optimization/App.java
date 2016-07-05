package Optimization;

import Optimization.Optimizer.IntegerLinearProgrammingOptimizer;
import Optimization.Optimizer.LinearProgrammingOptimizer;
import Optimization.Optimizer.Optimizer;
import com.joptimizer.functions.ConvexMultivariateRealFunction;
import com.joptimizer.functions.LinearMultivariateRealFunction;
import com.joptimizer.functions.LogTransformedPosynomial;
import com.joptimizer.optimizers.JOptimizer;
import com.joptimizer.optimizers.OptimizationRequest;

public class App {

    public static void main(String[] args) {
//        // Objective function (plane)
//        LinearMultivariateRealFunction objectiveFunction = new LinearMultivariateRealFunction(new double[] { -1., -1. }, 4);
//
//        //inequalities (polyhedral feasible set G.X<H )
//        ConvexMultivariateRealFunction[] inequalities = new ConvexMultivariateRealFunction[4];
//        double[][] G = new double[][] {{4./3., -1}, {-1./2., 1.}, {-2., -1.}, {1./3., 1.}};
//        double[] h = new double[] {2., 1./2., 2., 1./2.};
//        inequalities[0] = new LinearMultivariateRealFunction(G[0], -h[0]);
//        inequalities[1] = new LinearMultivariateRealFunction(G[1], -h[1]);
//        inequalities[2] = new LinearMultivariateRealFunction(G[2], -h[2]);
//        inequalities[3] = new LinearMultivariateRealFunction(G[3], -h[3]);
//
//        //optimization problem
//        OptimizationRequest or = new OptimizationRequest();
//        or.setF0(objectiveFunction);
//        or.setFi(inequalities);
//        //or.setInitialPoint(new double[] {0.0, 0.0});//initial feasible point, not mandatory
//        or.setToleranceFeas(1.E-2);
//        or.setTolerance(1.E-2);
//
//        //optimization
//        JOptimizer opt = new JOptimizer();
//        opt.setOptimizationRequest(or);
//        try {
//            int returnCode = opt.optimize();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        double[] sol = opt.getOptimizationResponse().getSolution();
//        System.out.println("result:");
//        for (double v : sol) {
//            System.out.println(v);
//        }


//        // Objective function (variables (x,y), dim = 2)
//        double[] a01 = new double[]{2,1};
//        double b01 = 0;
//        double[] a02 = new double[]{3,1};
//        double b02 = 0;
//        ConvexMultivariateRealFunction objectiveFunction = new LogTransformedPosynomial(new double[][]{a01, a02}, new double[]{b01, b02});
//
//        //constraints
//        double[] a11 = new double[]{1,0};
//        double b11 = Math.log(1);
//        double[] a21 = new double[]{0,1};
//        double b21 = Math.log(1);
//        double[] a31 = new double[]{-1,-1.};
//        double b31 = Math.log(0.7);
//        ConvexMultivariateRealFunction[] inequalities = new ConvexMultivariateRealFunction[3];
//        inequalities[0] = new LogTransformedPosynomial(new double[][]{a11}, new double[]{b11});
//        inequalities[1] = new LogTransformedPosynomial(new double[][]{a21}, new double[]{b21});
//        inequalities[2] = new LogTransformedPosynomial(new double[][]{a31}, new double[]{b31});
//
//        //optimization problem
//        OptimizationRequest or = new OptimizationRequest();
//        or.setF0(objectiveFunction);
//        or.setFi(inequalities);
//        or.setInitialPoint(new double[]{Math.log(0.9), Math.log(0.9)});
//        //or.setInteriorPointMethod(JOptimizer.BARRIER_METHOD);//if you prefer the barrier-method
//
//        //optimization
//        JOptimizer opt = new JOptimizer();
//        opt.setOptimizationRequest(or);
//        try {
//            int returnCode = opt.optimize();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        double[] sol = opt.getOptimizationResponse().getSolution();
//        System.out.println("result:");
//        for (double v : sol) {
//            System.out.println(v);
//        }






        int minCastChance = -5;
        int maxActions = 2;
        int maxMana = 15;
        int maxCards = 10;

        Optimizer optimizer;
        optimizer = new LinearProgrammingOptimizer();
        Spell spell = optimizer.optimize(maxActions, maxMana, maxCards, minCastChance);
        System.out.println(spell.toString());
    }

}
