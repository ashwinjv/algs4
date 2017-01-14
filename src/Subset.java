/******************************************************************************
 *  Compilation:  javac Subset.java
 *  Execution:    java Subset
 *  
 *  Usage:
 *  % echo A B C D E F G H I | java Subset 3
 *  C
 *  G
 *  A
 *
 *  Implements Subset data type
 *
 *  Author: Ashwin Venkatesan
 *
 ******************************************************************************/

import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Subset {
    
    /**
     * Subset client code. It takes a command line argument k and
     * reads from StdIn N strings. It creates a subset of size k from
     * the strings
     *
     * @param  args  Command line argument k
     */
    public static void main(String[] args) {

        int k = Integer.parseInt(args[0]);
        int output = 0;
        RandomizedQueue<String> rq = new RandomizedQueue<String>();
        try {
            while (true) {
                rq.enqueue(StdIn.readString());
            }
        }
        catch (NoSuchElementException e) {
            while (output < k) {
                StdOut.println(rq.dequeue());
                output++;
            }
        }
    }
   
}