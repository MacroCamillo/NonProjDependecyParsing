import java.util.ArrayList;

/**
 * Created by camillom on 16/09/16.
 */
@SuppressWarnings("DefaultFileTemplate")
public class NivreProjectiveParser extends ProjectiveParser {

    public NivreProjectiveParser(DependencyTree sent) {
        super(sent);
    }

    /**
     * Returns the Arc reference from gold Tree corresponding to given head and tail, if existing
     * @param head_id Id of the head of the wanted arc
     * @param tail_id Id of the tail of the wanted arc
     * @return A reference to the wanted Arc, o null if this doesn't exist
     */
    private boolean findArc(int head_id, int tail_id) {
        //System.out.println("Searching arc " + head_id + "->" + tail_id);
        Arc possible_arc = null;
        if (head_id > tail_id) {
            for (Arc arc : gold.getNodes().get(head_id).getLeft_children())
                if (arc.getTail().getId() == tail_id)
                    possible_arc = arc;
        }
        else
            for (Arc arc : gold.getNodes().get(head_id).getRight_children()) {
                if (arc.getTail().getId() == tail_id)
                    possible_arc = arc;
        }
        //Check if the found arc has a complete tail (we have already found all his tail's children)
        boolean completo = true;
        if (possible_arc != null) {
            Node right_tail = possible_arc.getTail(); //coda del possibile arco
            for (Arc child : right_tail.getLeft_children())
                if (!child.isAdded()) {
                    completo = false;
                    //System.out.print("Manca arco " + right_tail.getId() + "->" + child.getTail().getId() + " => ");
                    break;
                }
            for (Arc child : right_tail.getRight_children())
                if (!child.isAdded()) {
                    //System.out.print("Manca arco " + right_tail.getId() + "->" + child.getTail().getId() + " => ");
                    completo = false;
                    break;
                }
            if (completo) {
                possible_arc.setAdded(true);
                return true;
            }
        }

        return false;
    }

    /**
     * Oracle function of the parser, tells which action should be enterprised given the stack situation
     * @param sentence The stack and buffer content
     * @param top Index to the top element of the stack
     * @return Constant coding the action
     */
    public int predictAction(ArrayList<Integer> sentence, int top) {

        if (top == 0)
            return SHIFT;
        //Ipotizzo LEFT ARC
        boolean found = findArc(sentence.get(top), sentence.get(top - 1));
        if (found)
            return LEFT_ARC;

        //Ipotizzo RIGHT ARC
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
     * Executes the parsing process
     * @return The DependencyTree generated from parsing process
     */
    public DependencyTree execute() {

        sent_length = gold.getNodes().entrySet().size(); //COMPRESO ROOT

        ArrayList<Integer> sentence = new ArrayList<>();
        for (int i = 0; i < sent_length; i++)
                sentence.add(i);

        int top_index = 1;
        n_shift = 2;            //Lo stack è inizializzato con due elementi
        n_swap = 0;
        n_op = 2;

        DependencyTree builded = new DependencyTree(gold.getSent_number());

        Node head_node, tail_node;
        while(sentence.size() > 1) {
            switch (predictAction(sentence, top_index)) {
                case LEFT_ARC:
                    //System.out.println("LEFT_ARC " + tail_id + " <- " + head_id);

                    head_node = builded.addNode(sentence.get(top_index));
                    tail_node = builded.addNode(sentence.get(top_index - 1));

                    head_node.addLeftSon(tail_node);
                    sentence.remove(top_index - 1);
                    top_index--;
                    break;

                case RIGHT_ARC:
                    //System.out.println("RIGHT_ARC " + head_id + " -> " + tail_id);

                    head_node = builded.addNode(sentence.get(top_index - 1));
                    tail_node = builded.addNode(sentence.get(top_index));

                    head_node.addRightSon(tail_node);
                    sentence.remove(top_index);
                    top_index--;
                    break;

                case SWAP:
                    //System.out.println("SWAP " + swap_id);

                    int swap_id = sentence.get(top_index - 1);
                    sentence.set(top_index - 1, sentence.get(top_index));
                    sentence.set(top_index, swap_id);
                    top_index--;
                    n_swap++;
                    break;

                case SHIFT:
                    //System.out.println("SHIFT");

                    top_index++;
                    n_shift++;
                    break;
            }
            n_op++;
        }
        printExecutionStats();

        return builded;
    }

}
