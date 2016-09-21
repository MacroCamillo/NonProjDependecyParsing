import java.util.ArrayList;


/**
 * Created by camillom on 16/09/16.
 */
@SuppressWarnings("DefaultFileTemplate")
public abstract class ProjectiveParser {

    final int  LEFT_ARC=0, RIGHT_ARC=1, SWAP=2, SHIFT=3;
    DependencyTree gold = null;
    int n_shift, n_op, n_swap, sent_length;

    protected final ArrayList<Integer> projective_order = new ArrayList<>();
    abstract int predictAction(ArrayList<Integer> sentence, int top);
    abstract DependencyTree execute();

    public ProjectiveParser(DependencyTree target) {
        setTargetTree(target);
    }

    public void setTargetTree(DependencyTree target) {
        this.gold = target;
        Node root = gold.getNodes().get(0);
        if(root == null)
            return;
        createInorderTraversal(root);
    }

    private void createInorderTraversal(Node n) {
        if(n == null)
            return;

        for(Arc left : n.getLeft_children())
            createInorderTraversal(left.getTail());

        projective_order.add(n.getId());

        for(Arc right : n.getRight_children())
            createInorderTraversal(right.getTail());

    }

    protected void printExecutionStats() {
        if (n_swap > 0) {
            System.out.println("Sentence length : " + (sent_length - 1));
            System.out.println("# SHIFT: " + n_shift);
            System.out.println("# SWAP: " + n_swap);
            System.out.println("# total operations: " + n_op);
        }
    }
}
