/******************************************************************************
 *  Compilation:  javac Deque.java
 *  Execution:    java Deque
 *
 *  Implements Deque data type
 *
 *  Author: Ashwin Venkatesan
 *
 ******************************************************************************/

import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item> {
    
    private class Node {
        private Node next, previous;
        private Item item;
    }
    private Node first, last;
    // construct an empty deque
    public Deque() {
    }
    // is the deque empty?
    public boolean isEmpty() {
        return first == null;
    }
    // return the number of items on the deque
    public int size() {
        int count = 0;
        Node node = first;
        while (node != null) {
            count++;
            node = node.next;
        }
        return count;
    }
    // add the item to the front
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
    // add the item to the end
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
    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item item = first.item;
        first = first.next;
        if (first != null) first.previous = null;
        if (this.size() == 0) last = null;
        return item;
    }
    // remove and return the item from the end
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
    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }
    
    private class DequeIterator implements Iterator<Item> {
        private Node current = first;
        public boolean hasNext() { return current != null; }
        public void remove() { throw new UnsupportedOperationException(); }
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = current.item;
            current = current.next;
            return item;
        }
    }
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
    // unit testing
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
