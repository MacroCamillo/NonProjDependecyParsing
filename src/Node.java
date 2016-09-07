import java.util.ArrayList;

/**
 * Created by camillom on 06/09/16.
 */
public class Node {

    int id, sent_id;
    ArrayList<Arc> left_children, right_children;

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

    public ArrayList<Arc> getLeft_son() {
        return left_children;
    }

    public ArrayList<Arc> getRight_son() {
        return right_children;
    }

    public void addLeftSon(Node left_son) {
        left_children.add(new Arc(this, left_son));
        //TODO sort
    }

    public void addRightSon(Node right_son) {
        right_children.add(new Arc(this, right_son));
        //TODO sort
    }
}
