package Optimization;

import Optimization.Optimizer.*;

public class Main {

    public static void main(String[] args) {
        Optimizer optimizer;
        optimizer = new LinearProgrammingOptimizer();
        int minCastChance = -5;
        int maxActions = 2;
        int maxMana = 30;
        int maxCards = 10;
        Spell spell = optimizer.optimize(maxActions, maxMana, maxCards, minCastChance);
        System.out.println(spell.toString());
    }

}
