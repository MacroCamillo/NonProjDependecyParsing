import java.util.ArrayList;

/**
 * Created by camillom on 06/09/16.
 */
public class Node {

    private int id, sent_id;
    private ArrayList<Arc> left_children = new ArrayList<>();
    private ArrayList<Arc> right_children = new ArrayList<>();

    private ArrayList<Node> children = new ArrayList<>();

    public Node(int num, int sentence) {
        id = num;
        sent_id = sentence;
    }

    public int getId() {
        return id;
    }

    public int getSent_id() {
        return sent_id;
    }

    public ArrayList<Arc> getLeft_children() {
        return left_children;
    }

    public ArrayList<Arc> getRight_children() {
        return right_children;
    }

    public void addLeftSon(Node left_son) {
        left_children.add(new Arc(this, left_son));
        children.add(left_son);
    }

    public void addRightSon(Node right_son) {
        right_children.add(new Arc(this, right_son));
        children.add(right_son);
    }

   /* public boolean hasChild(int child_id) {
        return children.contains();
    }*/
}
