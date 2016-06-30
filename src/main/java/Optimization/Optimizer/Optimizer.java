package Optimization.Optimizer;

import Optimization.Spell;

/**
 * Created by Azathoth on 29. 6. 2016.
 */
abstract public class Optimizer {

    abstract public Spell optimize(int maximalCastTime, int maxMana, int maxCardCount);

}
