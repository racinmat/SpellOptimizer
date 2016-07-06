package Optimization.Optimizer.BranchAndBound;

import java.util.List;

/**
 * Created by Azathoth on 6. 7. 2016.
 */
public interface Solver {

    public double[] solve(List<Constraint> constraints);

    public boolean isFeasible(double[] solution);
}
