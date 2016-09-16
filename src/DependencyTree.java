import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by camillom on 06/09/16.
 */
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

    public ArrayList<Arc> getArcs() {
        return arcs;
    }
}
