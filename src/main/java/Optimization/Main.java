package Optimization;

import Optimization.Optimizer.*;

public class Main {

    public static void main(String[] args) {
        Optimizer optimizer;
        optimizer = new IntegerLinearProgrammingOptimizer();
        int minCastChance = -70;
        int maxActions = 2;
        int maxMana = 30;
        int maxCards = 30;
        Spell spell = optimizer.optimize(maxActions, maxMana, maxCards, minCastChance);
        System.out.println(spell.toString());
    }

}
