import static org.junit.Assert.assertEquals;

import Optimization.Optimizer.BranchAndBound.IntegerBranchAndBound;
import org.junit.Test;

public class BranchAndBoundTest {

    @Test
    public void testBoundAndBranch() {
        IntegerBranchAndBound branchAndBound = new IntegerBranchAndBound(new SolverMock());

        branchAndBound.solve();

    }
}