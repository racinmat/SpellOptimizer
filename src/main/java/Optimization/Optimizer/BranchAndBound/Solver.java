package Optimization.Optimizer.BranchAndBound;

import org.apache.commons.math3.util.Pair;
import java.util.List;

/**
 * Created by Azathoth on 6. 7. 2016.
 */
public interface Solver {

    public double[] solve(List<Pair<Constraint, Integer>> constraints) throws NoSolutionFoundException;

}
