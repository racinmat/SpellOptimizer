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
    private boolean explored;
    private boolean solution;

    public Node(Constraint constraint, Node parent, int variable) {
        this.constraint = constraint;
        this.parent = parent;
        this.variable = variable;
        this.solution = true;
        this.explored = false;
        System.out.println("Creating node: " + toString());
    }

    public void expand(double value, int variable) {
        if (! canBeExpanded()) {
            throw new RuntimeException("Can not be expanded.");
        }
        if (isExpanded()) {
            throw new RuntimeException("Already is expanded.");
        }

        List<Pair<Constraint, Integer>> constraints = getConstraints().stream().filter(pair -> pair.getSecond() == variable).collect(Collectors.toList());
        if (constraints.isEmpty()) {
            //expanding root, or first expandion of this variable
            if (value < 1) {
                leftChild = new Node(new Constraint(Comparison.EQUAL, Math.floor(value)), this, variable);
            } else {
                leftChild = new Node(new Constraint(Comparison.LESSER_OR_EQUAL, Math.floor(value)), this, variable);
            }
            rightChild = new Node(new Constraint(Comparison.BIGGER_OR_EQUAL, Math.ceil(value)), this, variable);
            return;
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

    public Node getLeftChild() {
        return leftChild;
    }

    public Node getRightChild() {
        return rightChild;
    }

    public boolean isExplored() {
        return explored;
    }

    public void explore() {
        this.explored = true;
    }

    public boolean hasSolution() {
        return solution;
    }

    public void setNoSolution() {
        this.solution = false;
    }

    public Node getParent() {
        return parent;
    }

    public Node getNotExploredChild() throws EverythingExploredException {
        if ( ! isExplored()) {
            return this;
        }
        if (! isExpanded()) {
            throw new EverythingExploredException();
        }
        try {
            assert leftChild != null;
            return leftChild.getNotExploredChild();
        } catch (EverythingExploredException e) {
            assert rightChild != null;
            return rightChild.getNotExploredChild();       //not try-catch in right child. If neither left or right child have not explored child, exception should be thrown.
        }
    }

    public boolean isExpanded() {
        return leftChild != null && rightChild != null;
    }

    public boolean canBeExpanded() {
        return isRoot() || constraint.getComparison() != Comparison.EQUAL;
    }

    @Override
    public String toString() {
        return "Node{" +
                "constraint=" + constraint +
//                ", leftChild=" + leftChild +
//                ", rightChild=" + rightChild +
                ", parent=" + parent +
                ", variable=" + variable +
                ", explored=" + explored +
                ", solution=" + solution +
                '}';
    }

    public void subTreeToString() {
        int level = 0;
        Node iter = this;
        while ( ! iter.isRoot()) {
            level++;
            iter = iter.getParent();
        }
        for (int i = 0; i < level; i++) {
            System.out.print("\t");
        }
        if (isRoot()) {
            System.out.println("root");
        } else {
            System.out.println("variable: " + variable + ", " + constraint.toString());
        }
        if (isExpanded()) {
            leftChild.subTreeToString();
            rightChild.subTreeToString();
        }
    }

}
