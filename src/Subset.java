/******************************************************************************
 *  Compilation:  javac Subset.java
 *  Execution:    java Subset
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
    
    public static void main(String[] args) {

        int k = Integer.parseInt(args[0]);
        int output = 0;
        String s;
        boolean shouldDequeue = false;
        RandomizedQueue<String> rq = new RandomizedQueue<String>();
        try {
            while (true) {
                s = StdIn.readString();
                if (rq.size() == k) {
                    shouldDequeue = true;
                    output++;
                }
                if (shouldDequeue) StdOut.println(rq.dequeue());
                if (output == k)break;
                rq.enqueue(s);
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