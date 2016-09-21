/**
 * Created by camillom on 06/09/16.
 */
@SuppressWarnings("DefaultFileTemplate")
public class Arc {

    private Node head = null;
    private Node tail = null;
    private int head_id;
    private int tail_id;

    private boolean added = false;

    public Arc(Node head, Node tail) {
        this.head = head;
        this.head_id = head.getId();
        this.tail = tail;
        this.tail_id = tail.getId();
    }

    public boolean equals(Arc obj) {
        return (this.head_id == obj.head.getId() && this.tail_id == obj.tail.getId());
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

    public void setAdded(boolean value) {
        this.added = value;
    }

    public boolean isAdded() {
        return added;
    }
}
