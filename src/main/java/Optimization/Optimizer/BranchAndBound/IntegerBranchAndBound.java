package Optimization.Optimizer.BranchAndBound;

import org.apache.commons.math3.geometry.euclidean.oned.Interval;

import java.util.ArrayList;

public class IntegerBranchAndBound {

    private Solver solver;

    private Tree tree;

    public IntegerBranchAndBound(Solver solver) {
        this.tree = new Tree();
        this.solver = solver;
    }

    public double[] solve() {
        double[] solution = solver.solve(new ArrayList<>());
        Node currentNode = tree.getRoot();
        while ( ! solver.isFeasible(solution)) {
            int variable = 0;
            double value = solution[variable];
            currentNode.expand(value, variable);
        }
        return solution;


    }

}
