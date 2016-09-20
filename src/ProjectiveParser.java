import java.util.ArrayList;
import java.util.Stack;


/**
 * Created by camillom on 16/09/16.
 */
public abstract class ProjectiveParser {

    final int  LEFT_ARC=0, RIGHT_ARC=1, SWAP=2, SHIFT=3;
    DependencyTree gold = null, builded = null;

    protected final ArrayList<Integer> inorder = new ArrayList<>();
    abstract int predictAction(ArrayList<Integer> sentence, int top);
    abstract DependencyTree execute();

    private void createInorderTraversal(Node n) {
        if(n == null)
            return;

        for(Arc left : n.getLeft_children())
            createInorderTraversal(left.getTail());

        inorder.add(n.getId());

        for(Arc right : n.getRight_children())
            createInorderTraversal(right.getTail());

    }

    public ProjectiveParser(DependencyTree target) {
        this.gold = target;
        Node root = gold.getNodes().get(0);
        if(root == null)
            return;
        createInorderTraversal(root);
/*
        //Create INORDER traversal ---- VERSIONE ITERATIVA CON ERRORE


        Stack<Node> s = new Stack<>();
        Node currentNode = root;

        while(!s.empty() || currentNode!=null) {

            if(currentNode!=null) {
                s.push(currentNode);
                if (currentNode.getLeft_children().size() == 0)
                    currentNode = null;
                else {
                    for (int i = currentNode.getLeft_children().size() - 1; i >= 1; --i) {
                        Arc left_child = currentNode.getLeft_children().get(i);
                        s.push(left_child.getTail());
                    }
                    currentNode = currentNode.getLeft_children().get(0).getTail();
                }
            }
            else {
                Node n = s.pop();
                //MANCA EVENTUALI LEFT CHILDREN DI POPPATI SE LI PUSHO COME RIGHT CHILDREN DOPO IL PRIMO
                //visit Node
                inorder.add(n.getId());
                if (n.getRight_children().size() == 0)
                    currentNode = null;
                else {
                    for(int i = n.getRight_children().size() - 1; i >= 1; --i) {
                        Arc right_child = n.getRight_children().get(i);
                        s.push(right_child.getTail());
                    }
                    currentNode = n.getRight_children().get(0).getTail();
                }
            }
        }*/


    }

}
