/******************************************************************************
 *  Compilation:  javac RandomizedQueue.java
 *  Execution:    java RandomizedQueue
 *
 *  Implements RandomizedQueue data type
 *
 *  Author: Ashwin Venkatesan
 *
 ******************************************************************************/

import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] items;
    private int last = 0;
    private int removed = 0;
    
    // construct an empty randomized queue
    public RandomizedQueue() {
        items = (Item[]) new Object[1];
    }
    // is the queue empty?
    public boolean isEmpty() {
        return size() == 0;
    }
    // return the number of items on the queue
    public int size() {
        return last - removed;
    }
    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new NullPointerException();
        if (last == items.length) resize(2*items.length);
        items[last++] = item;
    }
    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException();
        int randomIndex = getRandomIndex();
        Item item = items[randomIndex];
        items[randomIndex] = null;
        removed++;
        if (removed == (3*items.length)/4) resize(items.length/2);
        return item;
    }
    
    private int getRandomIndex() {
        int randomIndex = StdRandom.uniform(last);
        while (items[randomIndex] == null) {
            randomIndex = StdRandom.uniform(last);
        }
        return randomIndex;
    }
    
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
    // return (but do not remove) a random item
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException();
        return items[getRandomIndex()];
    }
    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomQueueIterator(items.length);
    }
    private class RandomQueueIterator implements Iterator<Item> {
        
        private int index = 0;
        private int[] indexArray;
        private Item current;
        
        public RandomQueueIterator(int length) {
            indexArray = new int[length];
            for (int i = 0; i < length; i++) {
                indexArray[i] = i;
            }
            StdRandom.shuffle(indexArray);
            current = getNextItem();
        }
        
        private Item getNextItem() {
            int randomIndex = indexArray[index++];
            Item item = items[randomIndex];
            if (item == null && index < indexArray.length) {
                randomIndex = indexArray[index++];
                item = items[randomIndex];
            }
            return item;
        }
        
        public boolean hasNext() { return current != null; }
        
        public void remove() { throw new UnsupportedOperationException(); }
        
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = current;
            current = getNextItem();
            return item;
        }
    }
    
    // unit testing
    public static void main(String[] args) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
        
    }
}