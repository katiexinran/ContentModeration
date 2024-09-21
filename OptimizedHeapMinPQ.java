// javac ModeratorMultiTest.java && java ModeratorMultiTest
import java.util.*;

public class OptimizedHeapMinPQ<T> implements ExtrinsicMinPQ<T> {
    private List<PriorityNode<T>> items;
    private Map<T, Integer> itemToIndex;
    private int size;

    public OptimizedHeapMinPQ() {
        items = new ArrayList<>();
        itemToIndex = new HashMap<>();
        size = 0;
        //items.add(null);
    }

    public void add(T item, double priority) {
        // Diana Debugging tips: I used this exception to find the problem
        // It actually throws an exception because it detects a duplicate in PQ, which should not happen
        /*
            if (contains(item)) {
            throw new IllegalArgumentException("Already contains " + item);
        }
        */
        size++;
        PriorityNode<T> pn = new PriorityNode(item, priority);
        items.add(pn); //add to back of list (bottom of tree)
        // If you do the swim before put, notice how you put the item back to the last index
        // So the swim operation basically has no effect
        itemToIndex.put(item, size - 1); //size - 1 = index of last item in list
        swim(size - 1); //swim until in correct position (invariants maintained)
    }

    public boolean contains(T item) {
        return itemToIndex.containsKey(item);
    }

    public int size() {
        return size;
    }

    public T peekMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("PQ is empty");
        }
        PriorityNode<T> min = items.get(0);    
        return min.item();
    }

    // Removes and returns the item with the lowest priority value.
    public T removeMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("PQ is empty");
        }
        // Swap the root with the last leaf
        exch(0, size - 1);

        // Remove the last leaf
        PriorityNode<T> minNode = items.get(size - 1);
        T minItem = minNode.item();

        // items is a list of PriorityNodes
        items.remove(minNode);
        itemToIndex.remove(minItem);
        size--;

        // Sink the new root to its proper place, promoting the lesser child
        sink(0);
        return minItem;
    }

    //changes the priority associated with the given item to the given priority
    //restores heap invariants using calls to either sink or swim
    // Updates the priority of the given item.
    public void changePriority(T item, double priority) {
        if(!itemToIndex.containsKey(item)){
            throw new NoSuchElementException("PQ does not contain " + item);
        }
        int index = itemToIndex.get(item); // index of the item in the map
        double currPriority = items.get(index).priority();
        items.get(index).setPriority(priority);
        if (priority > currPriority) { //restore heap invariants
            sink(index);
        } else if(priority < currPriority) {
            swim(index);
        }
    }

    //sink the parent node that is too big down until it isn't too big anymore
    private void sink(int bigParent) {
        while (2 * bigParent + 1 < size) {
            int child = 2 * bigParent + 1;
            if (child < size - 1 && greater(child, child + 1)) {
                child++;
            }
            if (!greater(bigParent, child)) {
                break;
            }
            exch(bigParent, child); //exchange the nodes
            bigParent = child;
        }
    }

    private void swim(int lastElement) {
        while ((lastElement - 1)/2 >= 0 && size > 1 && greater((lastElement - 1) / 2, lastElement)) { //greater -> is parent greater than child?
            exch(lastElement, (lastElement - 1) / 2); //exch parent and child
            lastElement = (lastElement - 1) / 2; //change the parent to the child
        }
    }

    
    //i & j are indexes
    private void exch(int i, int j) {
        PriorityNode<T> startsAtI = items.get(i); 
        PriorityNode<T> startsAtJ = items.get(j);
        items.set(i, startsAtJ);
        items.set(j, startsAtI);
        itemToIndex.put(startsAtJ.item(), i);
        itemToIndex.put(startsAtI.item(), j);
    }
    
    //returns true if PriorityNode at index i (parent) > PriorityNode at index j (child)
    private boolean greater(int i, int j) {
        PriorityNode<T> pnAtI = items.get(i);
        PriorityNode<T> pnAtJ = items.get(j);
        return pnAtI.priority() > pnAtJ.priority();
    }

    public double getPriority(T item) {
        int index = itemToIndex.get(item); //just getting info
        PriorityNode<T> pn = items.get(index);
        return pn.priority();
    }
}
