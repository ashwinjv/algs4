import java.util.Arrays;
import java.util.ArrayList;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints 
{
//    private Point[] myPoints;
    private ArrayList<LineSegment> lines = new ArrayList<LineSegment>();
    private int lineCount = 0;
//    finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        checkArgument(points);
        runAlgorithm(points);
    }
    
    private void checkArgument(Point[] points) {
        if (points == null) throw new java.lang.IllegalArgumentException();
        Arrays.sort(points);
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) throw new java.lang.NullPointerException();
            if (i > 0 && points[i].compareTo(points[i-1]) == 0) throw new java.lang.IllegalArgumentException();
        }
    }
    

    private void runAlgorithm(Point[] points) {
        for (int i = 0; i < points.length-3; i++) {
            for (int j = i+1; j < points.length-2; j++) {
                for (int k = j+1; k < points.length-1; k++) {
                    for (int l = k+1; l < points.length; l++) {
                        Point[] segment = {points[i], points[j], points[k], points[l]};
                        Arrays.sort(segment);
                        if (checkCollinear(segment)) {
                            Point x = segment[0];
                            Point y = segment[segment.length-1];
                            if (x.compareTo(points[i]) == 0) {
                                LineSegment line = new LineSegment(x, y);
                                lines.add(line);
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    private boolean checkCollinear(Point[] points) {
        double slope = points[0].slopeTo(points[1]);
        for (int i = 1; i < points.length; i++) {
            if (points[i-1].slopeTo(points[i]) != slope) return false;
        }
        if (slope == Double.NEGATIVE_INFINITY) return false;
        return true;
    }

//    the number of line segments
    public int numberOfSegments() {
        return lines.size();
    }
    
//    the line segments
    public LineSegment[] segments() {
        LineSegment[] newLines = new LineSegment[lines.size()];
        for (int i = 0; i < lines.size(); i++) newLines[i] = lines.get(i);
        return newLines;
    }
    
    public static void main(String[] args) {
        /* YOUR CODE HERE */
//        Point a = new Point(1,1);
//        Point b = new Point(2,2);
//        Point c = new Point(3,3);
//        Point d = new Point(4,4);
//        Point[] points = new Point[4]; 
//        points[0] = a;
//        points[1] = b;
//        points[2] = c;
//        points[3] = d;
//        BruteCollinearPoints bcp = new BruteCollinearPoints(points);
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
 