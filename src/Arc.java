import java.util.ArrayList;

/**
 * Created by camillom on 06/09/16.
 */
public class Arc {

    private Node head = null;
    private Node tail = null;

    public boolean added = false;

    public Arc(Node head, Node tail) {
        this.head = head;
        this.tail = tail;
    }
    public Node getHead() {
        return head;
    }

    public Node getTail() {
        return tail;
    }

    public void setHead(Node head) {
        this.head = head;
    }

    public void setTail(Node tail) {
        this.tail = tail;
    }
}
