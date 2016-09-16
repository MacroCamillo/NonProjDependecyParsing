import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by camillom on 16/09/16.
 */
public class NivreParser extends Parser {

    public NivreParser(DependencyTree target_tree) {
        gold = target_tree;
    }

    public int predictAction(ArrayList<Integer> stack) {

        int action=SHIFT;


        return action;
    }

    public void execute() {

        int sent_length = gold.getNodes().entrySet().size();

        ArrayList<Integer> stack = new ArrayList<>();
        stack.add(0);
        stack.add(1);

        int[] buffer = new int[sent_length];
        for(int i = 2; i < sent_length; i++)
            buffer[i-2] = i;

        int head_id, tail_id;
        Node head_node, tail_node;
        switch (predictAction(stack)) {

            case LEFT_ARC :
                head_id = stack.get(stack.size()-1);
                tail_id = stack.get(stack.size()-2);

                head_node = builded.addNode(head_id);
                tail_node = builded.addNode(tail_id);

                builded.getArcs().add(new Arc(head_node,tail_node));
                stack.remove(stack.size()-2);
                break;

            case RIGHT_ARC :
                head_id = stack.get(stack.size()-2);
                tail_id = stack.get(stack.size()-1);

                head_node = builded.addNode(head_id);
                tail_node = builded.addNode(tail_id);

                builded.getArcs().add(new Arc(head_node,tail_node));
                stack.remove(stack.size()-1);
                break;

            case SWAP :
                int swap_id = stack.get(stack.size()-2);
                stack.remove(stack.size()-2);

        }
    }

}
