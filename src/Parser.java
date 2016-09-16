import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by camillom on 16/09/16.
 */
public abstract class Parser {

    final int  LEFT_ARC=0, RIGHT_ARC=1, SWAP=2, SHIFT=3;
    DependencyTree gold = null, builded = null;

    abstract int predictAction(ArrayList<Integer> stack);

}
