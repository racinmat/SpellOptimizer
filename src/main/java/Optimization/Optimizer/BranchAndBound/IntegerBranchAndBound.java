package Optimization.Optimizer.BranchAndBound;

import org.apache.commons.math3.geometry.euclidean.oned.Interval;
import org.apache.commons.math3.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class IntegerBranchAndBound {

    private Solver solver;

    private Tree tree;

    public IntegerBranchAndBound(Solver solver) {
        this.tree = new Tree();
        this.solver = solver;
    }

    public double[] solve() {
        double[] solution;
        try {
            solution = solver.solve(new ArrayList<>());
        } catch (NoSolutionFoundException e) {
            e.printStackTrace();
            return new double[0];
        }
        Node currentNode = tree.getRoot();
        currentNode.explore();
        while ( !isFeasible(solution, currentNode.getConstraints())) {
            //todo: dodělat výběr proměnné
            int variable = 0;
            double value = solution[variable];
            currentNode = getNextNotExploredNode(currentNode);
            if (currentNode.canBeExpanded()) {
                currentNode.expand(value, variable);
            } else {
                throw new RuntimeException("shit happens");
            }
            currentNode.explore();
            try {
                solution = solver.solve(currentNode.getConstraints());
            } catch (NoSolutionFoundException e) {
                currentNode.setNoSolution();
                currentNode = currentNode.getParent();
            }

        }
        return solution;
    }

    private Node getNextNotExploredNode(Node node) {
        Node iter = node;
        boolean found = false;
        while ( ! found) {
            try {
                iter = iter.getNotExploredChild();
                found = true;
            } catch (EverythingExploredException e) {
                iter = iter.getParent();
            }
        }
        return iter;
    }

    private boolean isFeasible(double[] solution, List<Pair<Constraint, Integer>> constraints) {
        for (double value : solution) {
            if (value != Math.floor(value)) {   //double integer check
                return false;
            }
        }

        for (Pair<Constraint, Integer> constraint : constraints) {
            int variable = constraint.getSecond();
            if ( ! constraint.getFirst().complies(solution[variable])) {
                return false;
            }
        }

        return true;

    }
}
