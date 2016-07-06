package Optimization.Optimizer.BranchAndBound;

import org.apache.commons.math3.geometry.euclidean.oned.Interval;

public class Constraint {

    private Comparison comparison;

    private double limit;

    public Constraint(Comparison comparison, double limit) {
        this.comparison = comparison;
        this.limit = limit;
    }

    public Comparison getComparison() {
        return comparison;
    }

    public double getLimit() {
        return limit;
    }

    public boolean complies(double value) {
        return comparison.compare(value, limit);
    }
}
