package Optimization.Optimizer.BranchAndBound;

import org.apache.commons.math3.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Node {

    private Constraint constraint;

    private Node leftChild;

    private Node rightChild;

    private Node parent;

    private int variable;

    private boolean noSolution;

    public Node(Constraint constraint, Node parent, int variable) {
        this.constraint = constraint;
        this.parent = parent;
        this.variable = variable;
        this.noSolution = false;
    }

    public void expand(double value, int variable) {
        List<Pair<Constraint, Integer>> constraints = getConstraints().stream().filter(pair -> pair.getSecond() == variable).collect(Collectors.toList());
        if (constraints.isEmpty()) {
            //expanding root, or first expandion of this variable
            if (value < 1) {
                leftChild = new Node(new Constraint(Comparison.EQUAL, Math.floor(value)), this, variable);
            } else {
                leftChild = new Node(new Constraint(Comparison.LESSER_OR_EQUAL, Math.floor(value)), this, variable);
            }
            rightChild = new Node(new Constraint(Comparison.BIGGER_OR_EQUAL, Math.ceil(value)), this, variable);
        }
        double lowerBound = 0;
        double higherBound = Double.POSITIVE_INFINITY;
        for (Pair<Constraint, Integer> pair : constraints) {
            Constraint constraint = pair.getFirst();
            if (constraint.getComparison().isLesser() && ! constraint.complies(higherBound)) {
                higherBound = constraint.getLimit();
            }
            if (constraint.getComparison().isBigger() && ! constraint.complies(lowerBound)) {
                lowerBound = constraint.getLimit();
            }
        }
        if (lowerBound == Math.floor(value)) {
            leftChild = new Node(new Constraint(Comparison.EQUAL, Math.floor(value)), this, variable);
        } else {
            leftChild = new Node(new Constraint(Comparison.LESSER_OR_EQUAL, Math.floor(value)), this, variable);
        }
        if (higherBound == Math.ceil(value)) {
            rightChild = new Node(new Constraint(Comparison.EQUAL, Math.ceil(value)), this, variable);
        } else {
            rightChild = new Node(new Constraint(Comparison.BIGGER_OR_EQUAL, Math.ceil(value)), this, variable);
        }
    }

    public boolean isRoot() {
        return parent == null;
    }

    public List<Pair<Constraint, Integer>> getConstraints() {
        List<Pair<Constraint, Integer>> constraints = new ArrayList<>();
        Node iter = this;
        while ( ! iter.isRoot()) {  //root does not have any constraint
            constraints.add(new Pair<>(iter.constraint, iter.variable));
            iter = iter.parent;
        }
        return constraints;
    }
}
