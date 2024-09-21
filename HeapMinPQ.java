import java.util.*;

public class HeapMinPQ<T> implements ExtrinsicMinPQ<T> {
    private final PriorityQueue<PriorityNode<T>> pq;

    public HeapMinPQ() {
        pq = new PriorityQueue<>(Comparator.comparingDouble(PriorityNode::priority));
    }

    public void add(T item, double priority) {
        PriorityNode<T> pn = new PriorityNode(item, priority);
        pq.add(pn);
    }

    public boolean contains(T item) {
        for (PriorityNode pn : pq) {
            if (pn.item().equals(item)) {
                return true;
            }
        }
        return false;
    }

    public T peekMin() {
        PriorityNode<T> pn = pq.peek();
        return pn.item();
    }

    public T removeMin() {
        PriorityNode<T> pn = pq.peek();
        pq.remove(pn);
        return pn.item();
    }

    public void changePriority(T item, double priority) {
        PriorityNode <T> pn = new PriorityNode(item, priority);
        if(!pq.contains(pn)) {
            throw new NoSuchElementException("PQ does not contain " + item);
        } else {
            pq.remove(pn);
            pq.add(pn); 
        }
    }

    public int size() {
        return pq.size();
    }
}
