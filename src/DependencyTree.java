import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by camillom on 06/09/16.
 */
@SuppressWarnings("DefaultFileTemplate")
public class DependencyTree {

    private HashMap<Integer,Node> nodes = new HashMap<>();
    private ArrayList<Arc> arcs = new ArrayList<>();
    private int sent_number;

    public DependencyTree(int num_sentence) {
        sent_number = num_sentence;
        Node root = new Node(0, num_sentence);
        nodes.put(0, root);
    }

    public int getSent_number() {
        return sent_number;
    }

    public HashMap<Integer,Node> getNodes() {
        return nodes;
    }

    /**
     * Adds a Node to the tree only if it didn't exist before
     * @param id key of Node to add
     * @return Node added
     */
    public Node addNode(int id) {
        if (nodes.containsKey(id))
            return nodes.get(id);
        else {
            Node new_node = new Node(id, sent_number);
            nodes.put(id, new_node);
            return new_node;
        }
    }

    public void printTree() {
        System.out.println("Tree representation of sentence n." + this.sent_number);

        for (Map.Entry<Integer, Node> head : this.nodes.entrySet()) {
            System.out.print(head.getKey() + ": ");
            ArrayList<Arc> children = head.getValue().getLeft_children();
            for (Arc son : children) {
                int son_id = son.getTail().getId();
                System.out.print(son_id + " ");
            }
            System.out.print(", ");
            children = head.getValue().getRight_children();
            for (Arc son : children) {
                int son_id = son.getTail().getId();
                System.out.print(son_id + " ");
            }
            System.out.print("\n");
        }
        System.out.print("\n");
    }
}
