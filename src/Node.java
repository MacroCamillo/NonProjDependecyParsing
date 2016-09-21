import java.util.ArrayList;

/**
 * Created by camillom on 06/09/16.
 */
@SuppressWarnings("DefaultFileTemplate")
public class Node {

    private int id, sent_id;
    private ArrayList<Arc> left_children = new ArrayList<>();
    private ArrayList<Arc> right_children = new ArrayList<>();

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
        int i = 0;
        for (Arc a : left_children) {
            if (a.getTail().getId() > left_son.getId())
                break;
            i++;
        }
        left_children.add(i,new Arc(this, left_son));
    }

    public void addRightSon(Node right_son) {
        int i = 0;
        for (Arc a : right_children) {
            if (a.getTail().getId() > right_son.getId())
                break;
            i++;
        }
        right_children.add(i,new Arc(this, right_son));

    }

   /* public boolean hasChild(int child_id) {
        return children.contains();
    }*/
}
