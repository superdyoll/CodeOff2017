package analysis;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Lloyd on 04/03/2017.
 */
public interface Analysis {
    void performAnalysisOnNextRow() throws Exception;

    HashMap<Integer, List<Integer>> getResults();

    void close() throws Exception;
}
