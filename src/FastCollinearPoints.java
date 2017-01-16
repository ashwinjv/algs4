/******************************************************************************
 *  Compilation:  javac FastCollinearPoints.java
 *  Execution:    java FastCollinearPoints
 *
 *  Implements FastCollinearPoints data type
 *
 *  Author: Ashwin Venkatesan
 *
 ******************************************************************************/

import java.util.ArrayList;
import java.util.Arrays;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {
    
    private ArrayList<LineSegment> lines = new ArrayList<LineSegment>();
    
//  finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        Point[] sortedPoints = points.clone();
        checkArgument(points);
        if (points.length < 4) return;
        for (int a = 0; a < points.length; a++) {
            Point origin = points[a];
            Arrays.sort(sortedPoints, origin.slopeOrder());
            int count = 1;
            int start = 0;
            double slope = origin.slopeTo(sortedPoints[1]);
            for (int i = 1; i < sortedPoints.length; i++) {
                double newSlope = origin.slopeTo(sortedPoints[i]);
                if (newSlope != slope) {
                    if (count >= 3) {
                        Point[] n = new Point[count+1];
                        
                        for (int j = 0; j < count; j++) {
                            n[j] = sortedPoints[start + j];
                        }
                        n[count] = origin;
                        Arrays.sort(n);
                        if (n[0].equals(origin)) {
                            LineSegment line = new LineSegment(n[0], n[count]);
                            lines.add(line);
                        }
                    }
                    count = 1;
                    start = i;
                    slope = newSlope;
                }
                else {
                    count++;
                }
            }
            if (count >= 3) {
                Point[] n = new Point[count+1];
                
                for (int j = 0; j < count; j++) {
                    n[j] = sortedPoints[start + j];
                }
                n[count] = origin;
                Arrays.sort(n);
                if (n[0].equals(origin)) {
                    LineSegment line = new LineSegment(n[0], n[count]);
                    lines.add(line);
                }
            }
        }
    }
    
    private void checkArgument(Point[] points) {
        if (points == null) throw new java.lang.IllegalArgumentException();
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) throw new java.lang.NullPointerException();
            for (int j = 0; j < points.length; j++) {
                if (i != j && points[i].compareTo(points[j]) == 0) throw new java.lang.IllegalArgumentException();
            }
        }
        
    }
    
//  the number of line segments
    public int numberOfSegments() {
        return lines.size();
    }
  
//  the line segments
    public LineSegment[] segments() {
        LineSegment[] newLines = new LineSegment[lines.size()];
        for (int i = 0; i < lines.size(); i++) newLines[i] = lines.get(i);
        return newLines;
    }

    public static void main(String[] args) {
        /* YOUR CODE HERE */

//      Point f = new Point(8,9);
//      Point g = new Point(4,10);
//        Point a = new Point(1,1);
//        Point b = new Point(2,2);
//        Point c = new Point(3,3);
//        Point d = new Point(4,4);
//        Point e = new Point(5,5);
//        Point[] xpoints = new Point[7]; 
//        xpoints[0] = f;
//        xpoints[1] = g;
//        xpoints[2] = a;
//        xpoints[3] = b;
//        xpoints[4] = c;
//        xpoints[5] = d;
//        xpoints[6] = e;
//        FastCollinearPoints bcp = new FastCollinearPoints(xpoints);
        
        
//        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
