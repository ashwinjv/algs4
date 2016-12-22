/******************************************************************************
 *  Compilation:  javac Percolation.java
 *  Execution:    java Percolation
 *
 *  Implements Percolation data type
 *
 *  Author: Ashwin Venkatesan
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private WeightedQuickUnionUF wquf;
    private int gridSize, cellsCount;
    private int[] grid;
    private int virtualTop, virtualBot;
    
    /**
     * Percolation data type constructor. Creates a n-by-n grid,
     * with all sites blocked. 
     *
     * @param  n  size of the grid
     */
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        gridSize = n;
        cellsCount = n*n;
        virtualTop = n*n;
        virtualBot = virtualTop + 1;
        grid = new int[cellsCount];
        wquf = new WeightedQuickUnionUF(cellsCount+2);
        for (int i = 0; i < gridSize; i++) {
            this.wquf.union(i, this.virtualTop);
            this.wquf.union(this.cellsCount-(i+1), this.virtualBot);
        }
    }
    
    /**
     * Calculate the equivalent 1D index from the 2D row, col indices.
     * Note: since we are using a 0 based indices for the actual wquf,
     * decrementing both row and column before getting the 1D index.
     *
     * @param  row  row (1-n)
     * @param  col  col (1-n)
     * @return      1D index
     */
    private int calculateGridLoc(int row, int col) {
        row--;
        col--;
        return (row*this.gridSize) + col;
    }
    
    /**
     * open site (row, col) if it is not open already
     *
     * @param  row  row [1, n]
     * @param  col  col [1, n]
     */
    public void open(int row, int col) {
        if (isOpen(row, col)) {
            return;
        }
        int currentLoc = calculateGridLoc(row, col);
        this.unionHelper(row-1, col, currentLoc);
        this.unionHelper(row+1, col, currentLoc);
        this.unionHelper(row, col-1, currentLoc);
        this.unionHelper(row, col+1, currentLoc);
        this.grid[currentLoc] = 1;
    }


    /**
     * Check for out of bounds condition. The indices for row and col
     * are out of bounds if they are outside the set [1, n]
     *
     * @param  row  row [1, n]
     * @param  col  col [1, n]
     * @return      true if out of bounds else false
     */
    private boolean isOutOfBounds(int row, int col) {
        return (row <= 0 || row > this.gridSize
                || col <= 0 || col > this.gridSize);
    }
    

    /**
     * Helper function to create a union between the cell defined by
     * the (row, col) and the currentLoc. First we check that the cell
     * is in our boundary and then if it is open, create a union.
     *
     * @param  row  row [1, n]
     * @param  col  col [1, n]
     * @param  currentLoc  location of second cell in the grid
     */
    private void unionHelper(int row, int col, int currentLoc) {
        if (this.isOutOfBounds(row,  col)) {
            return;
        }
        else {
            if (this.isOpen(row, col)) {
                int cellLoc;
                cellLoc = calculateGridLoc(row, col);
                this.wquf.union(currentLoc, cellLoc);
            }
        }
    }
    
    /**
     * Check if site (row, col) is open
     *
     * @param  row  row [1, n]
     * @param  col  col [1, n]
     * @return      true if open else false
     * @throws IndexOutOfBoundsException if row and col not in bounds
     */
    public boolean isOpen(int row, int col) {
        if (this.isOutOfBounds(row,  col)) {
            throw new IndexOutOfBoundsException();
        }
        return this.grid[calculateGridLoc(row, col)] == 1;
    }

    /**
     * Check if site (row, col) is full
     *
     * @param  row  row [1, n]
     * @param  col  col [1, n]
     * @return      true if full else false
     * @throws IndexOutOfBoundsException if row and col not in bounds
     */
    public boolean isFull(int row, int col) {
        if (this.isOutOfBounds(row,  col)) {
            throw new IndexOutOfBoundsException();
        }
        
        if (!this.isOpen(row, col)) {
            return false;
        }
        int currentLoc = calculateGridLoc(row, col);
        boolean connectedToTop = this.wquf.connected(this.virtualTop, currentLoc);
        if (!this.percolates()) {
            return connectedToTop;
        }
        else {
            return connectedToTop && this.wquf.find(currentLoc) == this.wquf.find(this.virtualBot);
        }
        
    }

    /**
     * Check if system percolates
     *
     * @return      true if percolates else false
     */
    public boolean percolates() {
        if (this.gridSize == 1) {
            return this.isOpen(1, 1);
        }
        return this.wquf.connected(this.virtualTop, this.virtualBot);
    }

    /**
     * Test main function
     *
     * @param args list of string arguments
     */
    public static void main(String[] args) {
        Percolation p = new Percolation(10);
        p.open( 10 ,  2);
        p.open(   2,  10);
        p.open(   6,   8);
        p.open(   2,   6);
        p.open(   1,   4);
        p.open(   8,   4);
        p.open(  10,   1);
        p.open(   4,   2);
        p.open(   4,   8);
        p.open(   9,   3);
        p.open(   2,   2);
        p.open(   9,   1);
        p.open(   4,   3);
        p.open(   5,   5);
        p.open(   5,   7);
        p.open(   2,   8);
        p.open(   6,   4);
        p.open(   7,   5);
        p.open(   9,   6);
        p.open(   3,   7);
        p.open(   4,   7);
        p.open(   7,   1);
        p.open(   9,   4);
        p.open(   3,  10);
        p.open(   1,  10);
        p.open(  10,  10);
        p.open(   9,   7);
        p.open(   1,   5);
        p.open(   9,   8);
        p.open(   6,   1);
        p.open(   2,   5);
        p.open(   3,   4);
        p.open(   6,   9);
        p.open(   5,   8);
        p.open(   3,   2);
        p.open(   4,   6);
        p.open(   1,   7);
        p.open(   7,   9);
        p.open(   3,   9);
        p.open(   4,   4);
        p.open(   4,  10);
        p.open(   3,   5);
        p.open(   3,   8);
        p.open(   1,   8);
        p.open(   3,   1);
        p.open(   6,   7);
        p.open(   2,   3);
        p.open(   7,   4);
        p.open(   9,  10);
        p.open(   7,   6);
        p.open(   5,   2);
        p.open(   8,   3);
        p.open(  10,   8);
        p.open(   7,  10);
        p.open(   4,   5);
        p.open(   8,  10);
        StdOut.println(p.isFull(9, 1));
    }
}