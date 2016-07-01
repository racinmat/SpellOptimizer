package Optimization;

import Optimization.Optimizer.GradientDescent;
import Optimization.Optimizer.NaiveOptimizer;
import Optimization.Optimizer.Optimizer;

public class Main {

    public static void main(String[] args) {
        Optimizer optimizer = new GradientDescent(1000);
        Spell spell = optimizer.optimize(2, 30, 10, -30);
        System.out.println(spell.toString());
    }

}
