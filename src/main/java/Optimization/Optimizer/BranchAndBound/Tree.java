package Optimization.Optimizer.BranchAndBound;

public class Tree {

    private Node root;

    public Tree() {
        this.root = new Node(null, null, -1);
    }

    public Node getRoot() {
        return root;
    }
}
