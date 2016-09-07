import java.util.ArrayList;

/**
 * Created by camillom on 06/09/16.
 */
public class DependencyTree {

    private ArrayList<Node> nodes = new ArrayList<>();
    private ArrayList<Arc> arcs = new ArrayList<>();

    public DependencyTree(int num_sentence) {
        Node root = new Node(0, num_sentence);
        nodes.add(root);
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }

    public ArrayList<Arc> getArcs() {
        return arcs;
    }
}
