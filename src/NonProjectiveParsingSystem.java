import java.util.ArrayList;

/**
 * Created by camillom on 16/09/16.
 */

public abstract class NonProjectiveParsingSystem {

    final int  LEFT_ARC=0, RIGHT_ARC=1, SWAP=2, SHIFT=3;
    DependencyTree gold = null;
    protected int n_shift, n_op, n_swap, sent_length;

    protected final ArrayList<Integer> projective_order = new ArrayList<>();
    abstract int predictAction(ArrayList<Integer> sentence, int top);
    abstract DependencyTree execute();

    public NonProjectiveParsingSystem(DependencyTree target) {
        setTargetTree(target);
    }

    /**
     * Sets the Dependency Tree the oracle function must follow
     * @param target the target Dependency tree
     */

    public void setTargetTree(DependencyTree target) {
        this.gold = target;
        Node root = gold.getNodes().get(0);
        if(root == null)
            return;
        createInorderTraversal(root);
    }

    /**
     * Initialize an ArrayList with the sequence generated from the inorder traversal
     * @param n the starting Node
     */
    private void createInorderTraversal(Node n) {
        if(n == null)
            return;

        for(Arc left : n.getLeft_children())
            createInorderTraversal(left.getTail());

        projective_order.add(n.getId());

        for(Arc right : n.getRight_children())
            createInorderTraversal(right.getTail());

    }

    /**
     * Checks if an arc between any two nodes exists
     * @param head_id Id of the head of the wanted arc
     * @param tail_id Id of the tail of the wanted arc
     * @return whether an Arc has been found or not
     */
    protected boolean findArc(int head_id, int tail_id) {
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
        boolean complete = true;
        if (possible_arc != null) {
            Node possible_tail = possible_arc.getTail();
            for (Arc child : possible_tail.getLeft_children())
                if (!child.isAdded()) {
                    complete = false;
                    break;
                }
            for (Arc child : possible_tail.getRight_children())
                if (!child.isAdded()) {
                    complete = false;
                    break;
                }
            if (complete) {
                possible_arc.setAdded(true);
                return true;
            }
        }
        return false;
    }

    protected void printExecutionStats() {
        System.out.println("Sentence length : " + (sent_length - 1));
        System.out.println("# SHIFT: " + n_shift);
        System.out.println("# SWAP: " + n_swap);
        System.out.println("# total operations: " + n_op);

    }

    public int getN_op() {
        return n_op;
    }
}
