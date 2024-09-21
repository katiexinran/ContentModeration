import java.util.*;

public class UnsortedArrayMinPQ<T> implements ExtrinsicMinPQ<T> {
    private List<PriorityNode<T>> items;

    public UnsortedArrayMinPQ() {
        items = new ArrayList<>();
    }

    public void add(T item, double priority) {
        if (contains(item)) {
            throw new IllegalArgumentException("Already contains " + item);
        }
        PriorityNode<T> priorityNode = new PriorityNode(item, priority);
        items.add(priorityNode);
    }

    public boolean contains(T item) {
        for (PriorityNode<T> i : items) {
            if (i.item() == item) {
                return true;
            }
        }
        return false;
    }

    public T peekMin() {
        if (!items.isEmpty()) {
            double lowPriority = Double.MAX_VALUE;
            T lowestItem = null;
            for (PriorityNode<T> i : items) {
                if (i.priority() <= lowPriority) {
                    lowPriority = i.priority();
                    lowestItem = i.item();
                }
            }
            return lowestItem;
        }
        return null;
    }

    public T removeMin() {
        if (!items.isEmpty()) {
            double lowPriority = Double.MAX_VALUE;
            T lowestItem = null;
            PriorityNode<T> lowestNode = null;
            for (PriorityNode<T> i : items) {
                if (i.priority() <= lowPriority) {
                    lowPriority = i.priority();
                    lowestItem = i.item();
                    lowestNode = i;
                }
            }
            items.remove(lowestNode);
            return lowestItem;
        }
        return null;
    }

    public void changePriority(T item, double priority) {
        for (int i = 0; i < items.size(); i++) {
            if (item.equals(items.get(i).item())){
                items.get(i).setPriority(priority);
            }
        }
    }

    public int size() {
        return items.size();
    }
}
