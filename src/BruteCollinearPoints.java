import java.util.Arrays;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints 
{
    private Point[] newPoints;
    private LineSegment[] lines = new LineSegment[1];
    private int lineCount = 0;
//    finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        checkArgument(points);
        runAlgorithm(points);
    }
    
    private void checkArgument(Point[] points) {
        if (points == null) throw new java.lang.IllegalArgumentException();
        newPoints = new Point[points.length];
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) throw new java.lang.NullPointerException();
            if (checkDuplicate(points[i])) throw new java.lang.IllegalArgumentException();
        }
    }
    

    private boolean checkDuplicate(Point a) {
        int i;
        for (i = 0; i < newPoints.length; i++) {
            if (newPoints[i] == null) break;
            if (newPoints[i].slopeTo(a) == Double.NEGATIVE_INFINITY) {
                return true;
            }
        }
        newPoints[i] = a;
        return false;
    }
    
    private void runAlgorithm(Point[] points) {
        for (int i = 0; i < points.length; i++) {
            for (int j = 0; j < points.length; j++) {
                for (int k = 0; k < points.length; k++) {
                    for (int l = 0; l < points.length; l++) {
                        Point[] segment = {points[i], points[j], points[k], points[l]};
                        Arrays.sort(segment);
                        if (checkCollinear(segment)) {
                            LineSegment line = new LineSegment(segment[0], segment[segment.length-1]);
                            addLine(line);
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
    
    private void addLine(LineSegment line) {
        if (checkDuplicateLines(line)) return;
        if (lineCount == lines.length) resizeLines(lines.length*2);
        lines[lineCount++] = line;
    }
    
    private boolean checkDuplicateLines(LineSegment line) {
//        
        for (int i = 0; i < lineCount; i++) {
            if (lines[i].toString().equals(line.toString())) {
                return true;
            }
        }
//        printLines();
        return false;
    }
    
    private void printLines() {
        for (int i = 0; i < lineCount; i++) StdOut.print(lines[i].toString() + ", ");
        StdOut.println();
    }
    
    private void resizeLines(int capacity) {
        LineSegment[] newLines = new LineSegment[capacity];
        for (int i = 0; i < lines.length; i++) newLines[i] = lines[i];
        lines = newLines;
    }
    
//    the number of line segments
    public int numberOfSegments() {
        return lineCount;
    }
    
//    the line segments
    public LineSegment[] segments() {
        LineSegment[] newLines = new LineSegment[lineCount];
        for (int i = 0; i < lineCount; i++) newLines[i] = lines[i];
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
 