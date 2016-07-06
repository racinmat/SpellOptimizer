package Optimization.Optimizer.BranchAndBound;

import org.apache.commons.math3.util.Pair;

import java.util.function.DoubleBinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;

public enum Comparison {

    BIGGER (pair -> pair.getFirst() > pair.getSecond(), false, true),
    LESSER (pair -> pair.getFirst() < pair.getSecond(), true, false),
    BIGGER_OR_EQUAL (pair -> pair.getFirst() >= pair.getSecond(), false, true),
    LESSER_OR_EQUAL (pair -> pair.getFirst() <= pair.getSecond(), true, false),
    EQUAL (pair -> pair.getFirst().equals(pair.getSecond()), false, false);

    private Predicate<Pair<Double, Double>> comparison;
    private boolean isLesser;
    private boolean isBigger;

    private Comparison(Predicate<Pair<Double, Double>> comparison, boolean isLesser, boolean isBigger) {
        this.comparison = comparison;
        this.isLesser = isLesser;
        this.isBigger = isBigger;
    }

    public boolean isLesser() {
        return isLesser;
    }

    public boolean isBigger() {
        return isBigger;
    }

    public boolean compare(double first, double second) {
        return comparison.test(new Pair<>(first, second));
    }


}
