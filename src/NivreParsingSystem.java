import java.util.ArrayList;

/**
 * Created by camillom on 16/09/16.
 */
@SuppressWarnings("DefaultFileTemplate")
public class NivreParsingSystem extends NonProjectiveParsingSystem {

    public NivreParsingSystem(DependencyTree sent) {
        super(sent);
    }

    /**
     * Oracle function of the parser, tells which transition should be chosen given the stack situation
     * @param sentence The stack and buffer content
     * @param top Index to the top element of the stack
     * @return Constant coding the action
     */
    public int predictAction(ArrayList<Integer> sentence, int top) {

        if (top == 0)
            return SHIFT;
        //Case LEFT ARC
        boolean found = findArc(sentence.get(top), sentence.get(top - 1));
        if (found)
            return LEFT_ARC;

        //Case RIGHT ARC
        found = findArc(sentence.get(top - 1), sentence.get(top));
        if (found) {
            return RIGHT_ARC;
        }

        //Case SWAP
        if(projective_order.indexOf(sentence.get(top)) < projective_order.indexOf(sentence.get(top-1)))
            return SWAP;

        return SHIFT;
    }

    /**
     * Executes the parsing algorithm
     * @return The DependencyTree generated from parsing process
     */
    public DependencyTree execute() {

        sent_length = gold.getNodes().entrySet().size();

        ArrayList<Integer> sentence = new ArrayList<>();
        for (int i = 0; i < sent_length; i++)
                sentence.add(i);

        int top_index = 1;
        n_shift = 1; n_op = 1;           //We initialize the stack to two elements, as the first choice is always SHIFT
        n_swap = 0;

        DependencyTree builded = new DependencyTree(gold.getSent_number());

        Node head_node, tail_node;
        while(sentence.size() > 1) {
            switch (predictAction(sentence, top_index)) {
                case LEFT_ARC:

                    head_node = builded.addNode(sentence.get(top_index));
                    tail_node = builded.addNode(sentence.get(top_index - 1));

                    head_node.addLeftSon(tail_node);
                    sentence.remove(top_index - 1);
                    top_index--;
                    break;

                case RIGHT_ARC:

                    head_node = builded.addNode(sentence.get(top_index - 1));
                    tail_node = builded.addNode(sentence.get(top_index));

                    head_node.addRightSon(tail_node);
                    sentence.remove(top_index);
                    top_index--;
                    break;

                case SWAP:

                    int swap_id = sentence.get(top_index - 1);
                    sentence.set(top_index - 1, sentence.get(top_index));
                    sentence.set(top_index, swap_id);
                    top_index--;
                    n_swap++;
                    break;

                case SHIFT:

                    top_index++;
                    n_shift++;
                    break;
            }
            n_op++;
        }
        if (n_swap > 0)
            printExecutionStats();

        return builded;
    }

}
