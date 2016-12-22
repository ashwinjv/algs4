/******************************************************************************
 *  Compilation:  javac Deque.java
 *  Execution:    java Deque
 *
 *  Implements Deque data type
 *
 *  Author: Ashwin Venkatesan
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.StdOut;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    
    /**
     * private class that defines a single node. It maintains a reference to
     * both the next item and the previous item.
     */
    private class Node {
        private Node next, previous;
        private Item item;
    }
    private Node first, last;
    
    /**
     * Deque constructor
     */
    public Deque() {
    }
    
    /**
     * Check if deque is empty
     *
     * @return      true if empty else false
     */
    public boolean isEmpty() {
        return first == null;
    }
    
    /**
     * Return the number of items on the deque
     *
     * @return      size of deque
     */
    public int size() {
        int count = 0;
        Node node = first;
        while (node != null) {
            count++;
            node = node.next;
        }
        return count;
    }
    
    /**
     * add item to the front of the deque
     *
     * @param  Item  item to add
     */
    public void addFirst(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.next = oldfirst;
        if (oldfirst != null) oldfirst.previous = first;
        if (last == null) last = first;
    }

    /**
     * add item to the end of the deque
     *
     * @param  Item  item to add
     */
    public void addLast(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }
        Node oldlast = last;
        last = new Node();
        last.item = item;
        last.previous = oldlast;
        if (oldlast != null) oldlast.next = last;
        if (first == null) first = last;
    }

    /**
     * Remove and return the item from the front
     *
     * @return  Item  item removed
     * @throws NoSuchElementException if deque is empty
     */
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item item = first.item;
        first = first.next;
        if (first != null) {
            first.previous = null;
        }
        else {
            last = null;
        }
        return item;
    }

    /**
     * Remove and return the item from the end
     *
     * @return  Item  item removed
     * @throws NoSuchElementException if deque is empty
     */
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item item = last.item;
        last = last.previous;
        if (last != null) {
            last.next = null;
        }
        else {
            first = null;
        } 
        return item;
    }

    /**
     * Return an iterator over items in order from front to end
     *
     * @return  Iterator<Item>  iterator
     */
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }
    
    /**
     * DequeIterator private class that implements the iterator logic
     */
    private class DequeIterator implements Iterator<Item> {
        
        private Node current = first;
        
        /**
         * check for next item
         *
         * @return      true if next item exists else false
         */
        public boolean hasNext() {
            return current != null;
        }
        
        /**
         * remove - Unsupported
         */
        public void remove() {
            throw new UnsupportedOperationException();
        }
        
        /**
         * get next item
         *
         * @return  Item  next item in the deque
         * @throws NoSuchElementException if no next item
         */
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = current.item;
            current = current.next;
            return item;
        }
    }
    
    /**
     * A test helper. It prints isEmpty(), size() and the contents of the
     * deque
     */
    private void testPrinter() {
        StdOut.println("=====================================================");
        StdOut.println("Empty: " + this.isEmpty());
        StdOut.println("Size: " + this.size());
        StdOut.println("Contents:");
        for (Item s : this) {
            StdOut.print(s + " ");
        }
        StdOut.println();
        StdOut.println();
    }

    /**
     * Client code to test the deque class
     */
    public static void main(String[] args) {
        Deque<String> d = new Deque<String>();
        d.testPrinter();
        StdOut.println("Adding Item1");
        d.addFirst("Item1");
        d.testPrinter();
        StdOut.println("Adding Item2");
        d.addFirst("Item2");
        d.testPrinter();
//        StdOut.println("Adding Item3 last");
//        d.addLast("Item3");
//        d.testPrinter();
//        d.addLast("Item4");
//        d.testPrinter();
//        String f = d.removeFirst();
//        StdOut.println("removeFirst: " + f);
//        d.testPrinter();
        String l = d.removeLast();
        StdOut.println("removeLast: " + l);
        d.testPrinter();
        l = d.removeLast();
        StdOut.println("removeLast: " + l);
        d.testPrinter();
//        l = d.removeLast();
//        StdOut.println("removeLast: " + l);
//        d.testPrinter();
        
    }
}
