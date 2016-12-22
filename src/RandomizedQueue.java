/******************************************************************************
 *  Compilation:  javac RandomizedQueue.java
 *  Execution:    java RandomizedQueue
 *
 *  Implements RandomizedQueue data type
 *
 *  Author: Ashwin Venkatesan
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;
import java.util.NoSuchElementException;


public class RandomizedQueue<Item> implements Iterable<Item> {
    
    private Item[] items;
    private int last = 0;
    private int removed = 0;
    

    /**
     * RandomizedQueue constructor.
     */
    public RandomizedQueue() {
        items = (Item[]) new Object[1];
    }

    /**
     * Check if queue is empty
     * 
     * @return      true if empty else false
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Return the number of items on the queue
     *
     * @return  size of the queue
     */
    public int size() {
        return last - removed;
    }

    /**
     * Add item to the queue. If the items array is full, resize the array
     * to twice its current size.
     *
     * @param  Item  item to add
     */
    public void enqueue(Item item) {
        if (item == null) throw new NullPointerException();
        if (last == items.length) resize(2*items.length);
        items[last++] = item;
    }

    /**
     * Remove and return a random item from the queue. If the size of
     * the queue after removing the item is less the 1/4 the lenght of
     * the items array, resizing the array. 
     *
     * @return  Item  removed item
     * @throws NoSuchElementException if queue is Empty
     */
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException();
        int randomIndex = getRandomIndex();
        Item item = items[randomIndex];
        items[randomIndex] = null;
        removed++;
        int currentSize = size();
        if (currentSize > 0 && currentSize == (1*items.length)/4) {
            resize(items.length/2);
        }
        return item;
    }
    
    /**
     * Get a random index. the returned index always contains a non null
     * element in the items array.
     *
     * @return  randomIndex
     */
    private int getRandomIndex() {
        int randomIndex = StdRandom.uniform(last);
        while (items[randomIndex] == null) {
            randomIndex = StdRandom.uniform(last);
        }
        return randomIndex;
    }
    
    /**
     * Resize the items array to the capacity provided. 
     *
     * @param  capacity  final size of the items array required
     */
    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        int newIndex = 0;
        for (int i = 0; i < last; i++) {
            if (items[i] != null) copy[newIndex++] = items[i];
        }
        items = copy;
        last = size();
        removed = 0;
    }

    /**
     * Return (but do not remove) a random item
     *
     * @return  Item  sampled item
     */
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException();
        return items[getRandomIndex()];
    }

    /**
     * Return an independent iterator over items in random order
     *
     * @return  Iterator to iterate over the items
     */
    public Iterator<Item> iterator() {
        return new RandomQueueIterator();
    }
    
    /**
     * private class RandomQueueIterator that implements Iterator logic
     */
    private class RandomQueueIterator implements Iterator<Item> {
        
        private int index = 0;
        private int count = 0;
        private int[] indexArray;
        
        /**
         * Constructor. Create an array of all non null indexes in the items
         * array. Then shuffle that array to get a random array of indexes that
         * is then used iterate.
         *
         */
        public RandomQueueIterator() {
            indexArray = new int[size()];
            for (int i = 0; i < items.length; i++) {
                if (items[i] != null) indexArray[count++] = i;
            }
            StdRandom.shuffle(indexArray);
        }
        
        /**
         * Get an item from the items array specified by the next index value 
         * in the indexArray.
         * 
         * @return  Item  next item
         */
        private Item getNextItem() {
            return items[indexArray[index++]];
        }
        
        /**
         * Check for next item
         * @return      true if next item exists else false
         */
        public boolean hasNext() { 
            return index < count; 
        }
        
        /**
         * remove - unsupported
         */
        public void remove() { throw new UnsupportedOperationException(); }
        
        /**
         * Get next item
         * 
         * @return  Item  next item
         * @throws NoSuchElementException if next item does not exist
         */
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return getNextItem();
        }
    }
    

    /**
     * Client testing code
     */
    public static void main(String[] args) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
        rq.enqueue(1);
        rq.enqueue(2);
        rq.enqueue(3);
        rq.enqueue(4);
        rq.enqueue(5);
        rq.enqueue(1);
        rq.enqueue(2);
        rq.enqueue(3);
        rq.enqueue(4);
        rq.enqueue(5);
        StdOut.println("Dequeue: " + rq.dequeue());
        StdOut.println("Dequeue: " + rq.dequeue());
        StdOut.println("Enqueued all ints: " + rq.size());
        for (int r : rq) {
            StdOut.println(r);
        }
        StdOut.println("Size: " + rq.size());
        StdOut.println("Dequeue: " + rq.dequeue());
        StdOut.println("Size: " + rq.size());
        StdOut.println("Dequeue: " + rq.dequeue());
        StdOut.println("Size: " + rq.size());
        StdOut.println("Dequeue: " + rq.dequeue());
        StdOut.println("Size: " + rq.size());
        StdOut.println("Dequeue: " + rq.dequeue());
        StdOut.println("Size: " + rq.size());
        StdOut.println("Dequeue: " + rq.dequeue());
        StdOut.println("Size: " + rq.size());
        StdOut.println("Dequeue: " + rq.dequeue());
        StdOut.println("Size: " + rq.size());
        StdOut.println("Dequeue: " + rq.dequeue());
        StdOut.println("Size: " + rq.size());
        StdOut.println("Dequeue: " + rq.dequeue());
        StdOut.println("Size: " + rq.size());
        StdOut.println("isEmpty: " + rq.isEmpty());
        rq.enqueue(5);
        StdOut.println("Size: " + rq.size());
        StdOut.println("isEmpty: " + rq.isEmpty());
        
    }
}