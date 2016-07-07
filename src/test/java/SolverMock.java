import Optimization.Optimizer.BranchAndBound.Constraint;
import Optimization.Optimizer.BranchAndBound.NoSolutionFoundException;
import Optimization.Optimizer.BranchAndBound.Solver;
import org.apache.commons.math3.util.Pair;

import java.util.List;

public class SolverMock implements Solver {

    private int counter;

    private double[][] data = {
            {1.25, 1.5, 1.75},
            {1, 1.2, 1.8},
            {0.8, 1, 1.8},
            {0, 0, 2},
    };

    public SolverMock() {
        counter = 0;
    }

    @Override
    public double[] solve(List<Pair<Constraint, Integer>> constraints) throws NoSolutionFoundException {
        return data[counter++];
    }

}
