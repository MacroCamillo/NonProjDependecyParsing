import java.util.ArrayList;

/**
 * Created by camillom on 16/09/16.
 */
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
    private Arc findArc(int head_id, int tail_id) {
        //System.out.println("Searching arc " + head_id + "->" + tail_id);

        if (head_id > tail_id) {
            for (Arc possible_arc : gold.getNodes().get(head_id).getLeft_children())
                if (possible_arc.getTail().getId() == tail_id)
                    return possible_arc;
        }
        else
            for (Arc possible_arc : gold.getNodes().get(head_id).getRight_children()) {
                //System.out.println("Possible tail "+ possible_arc.getTail().getId());
                if (possible_arc.getTail().getId() == tail_id)
                    return possible_arc;
        }
        return null;
    }

    public int predictAction(ArrayList<Integer> sentence, int top) {

        //Ipotizzo LEFT ARC
        Arc searched_arc = findArc(sentence.get(top), sentence.get(top - 1));
        if (searched_arc != null) {
            searched_arc.setAdded(true);
            return LEFT_ARC;
        }

        //Ipotizzo RIGHT ARC
        searched_arc = findArc(sentence.get(top - 1), sentence.get(top));
        boolean completo = true;
        if (searched_arc !=  null) {
            Node right_tail = searched_arc.getTail();
            for (Arc child : right_tail.getLeft_children())
                if (!child.isAdded()) {
                    completo = false;
                    System.out.print("Manca arco " + right_tail.getId() + "->" + child.getTail().getId() + " => ");
                    break;
                }
            for (Arc child : right_tail.getRight_children())
                if (!child.isAdded()) {
                    System.out.print("Manca arco " + right_tail.getId() + "->" + child.getTail().getId() + " => ");
                    completo = false;
                    break;
                }
            if(completo) {
                searched_arc.setAdded(true);
                return RIGHT_ARC;
            }
            else
                System.out.println(right_tail.getId() + " non ha ancora tutti i figli");
        }

        //Case SWAP
        if(inorder.indexOf(top) < inorder.indexOf(top-1))
            return SWAP;

        return SHIFT;

    }

    public DependencyTree execute() {

        int sent_length = gold.getNodes().entrySet().size();

        ArrayList<Integer> sentence = new ArrayList<>();
        for (int i =0; i < sent_length; i++)
                sentence.add(i);

        int top_index = 1;
        builded = new DependencyTree(gold.getSent_number());//TODO number

        int head_id, tail_id;
        Node head_node, tail_node;
        while(sentence.size() > 1) {
            switch (predictAction(sentence, top_index)) {

                case LEFT_ARC:
                    head_id = sentence.get(top_index);
                    tail_id = sentence.get(top_index - 1);

                    System.out.println("LEFT_ARC " + tail_id + " <- " + head_id);

                    head_node = builded.addNode(head_id);
                    tail_node = builded.addNode(tail_id);

                    head_node.addLeftSon(tail_node);
                    sentence.remove(top_index - 1);
                    top_index--;
                    break;

                case RIGHT_ARC:
                    head_id = sentence.get(top_index - 1);
                    tail_id = sentence.get(top_index);

                    System.out.println("RIGHT_ARC " + head_id + " -> " + tail_id);

                    head_node = builded.addNode(head_id);
                    tail_node = builded.addNode(tail_id);

                    head_node.addRightSon(tail_node);
                    sentence.remove(top_index);
                    top_index--;
                    break;

                case SWAP:
                    int swap_id = sentence.get(top_index - 1);

                    System.out.println("SWAP " + swap_id);

                    sentence.add(top_index - 1, sentence.get(top_index));
                    sentence.add(top_index, swap_id);
                    top_index--;
                    break;

                case SHIFT:
                    System.out.println("SHIFT");
                    top_index++;
                    break;
            }
        }
        return builded;
    }

}
