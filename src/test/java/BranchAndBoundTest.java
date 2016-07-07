import static org.junit.Assert.assertEquals;

import Optimization.Optimizer.BranchAndBound.IntegerBranchAndBound;
import org.junit.Test;

public class BranchAndBoundTest {

    @Test
    public void testBoundAndBranch() {
        IntegerBranchAndBound branchAndBound = new IntegerBranchAndBound(new SolverMock());
        //todo: opravit 2x za sebou lesser or equal 1
        branchAndBound.solve();

    }
}