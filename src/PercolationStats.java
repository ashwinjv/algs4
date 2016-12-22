/******************************************************************************
 *  Compilation:  javac PercolationStats.java
 *  Execution:    java PercolationStats n trials
 *
 *  Implements PercolationStats data type. It runs the percolation test for a
 *  n-by-n grid as many times as mentioned in the trials. It then collects the
 *  stats for the experiment and prints it to StdOut
 *
 *  % java PercolationStats 200 100
 *  mean                    = 0.5928212999999983
 * stddev                  = 0.009630073306636308
 * 95% confidence interval = 0.5926325505631883, 0.5930100494368084
 * 
 *  Author: Ashwin Venkatesan
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats {

    private double[] thresholds;
    private int gridSize;
    private double sqrtOfT;
    private double meanVal;
    private double stddevVal;
    private double confidenceLoVal;
    private double confidenceHiVal;

    /**
     *  PercolationStats data type constructor. It runs the percolation test for a
     *  n-by-n grid as many times as mentioned in the trials. It then collects the
     *  stats for the experimentCreates a n-by-n grid, with all sites blocked. 
     *
     * @param  n  size of the grid
     * @param  trials number of times to run the experiment
     */
    public PercolationStats(int n, int trials) {
        thresholds = new double[trials];
        gridSize = n;
        sqrtOfT = Math.sqrt(trials);
        start(trials);
        calculateStats();
    }
    

    /**
     * start the expermient
     *
     * @param  trials  number of times to run the experiment
     */
    private void start(int trials) {
        for (int currentTrial = 0; currentTrial < trials; currentTrial++) {
            this.run(currentTrial);
        }
    }
    

    /**
     * run one iteration of the experiment
     *
     * @param  trialNum  current iteraction number
     */
    private void run(int trialNum) {
        Percolation percolationObj = new Percolation(this.gridSize);
        double i = 0;
        int row, col;
        while (!percolationObj.percolates()) {
            row = StdRandom.uniform(1, this.gridSize+1);
            col = StdRandom.uniform(1, this.gridSize+1);
            while (percolationObj.isOpen(row, col)) {
                row = StdRandom.uniform(1, this.gridSize+1);
                col = StdRandom.uniform(1, this.gridSize+1);
            }
            percolationObj.open(row, col);
            i++;
        }
        this.thresholds[trialNum] = i/(this.gridSize*this.gridSize); 
    }


    /**
     * Calculate all the statistice from the experiment
     * 
     * sample mean and standard deviation is calculated by using the functions
     * provided in the library. for the 95% confidence low and high value, the
     * formula provided in the question is used.
     *
     */
    private void calculateStats() {
        this.meanVal = StdStats.mean(this.thresholds);
        this.stddevVal = StdStats.stddev(this.thresholds);
        this.confidenceLoVal = meanVal - 
                            ((1.96*this.stddevVal)/this.sqrtOfT);
        this.confidenceHiVal = this.meanVal + 
                            ((1.96*this.stddevVal)/this.sqrtOfT);
    }
    
    /**
     * get the sample mean of the percolation threshold
     *
     * @return   mean value
     */
    public double mean() {
        return this.meanVal;
    }

    /**
     * get the sample standard deviation of the percolation threshold
     *
     * @return  standard deviation value
     */
    public double stddev() {
        return this.stddevVal;
    }

    /**
     * get the low  endpoint of 95% confidence interval
     *
     * @return  low  endpoint of 95% confidence interval
     */
    public double confidenceLo() {
        return this.confidenceLoVal;
    }

    /**
     * get the high  endpoint of 95% confidence interval
     *
     * @return  high  endpoint of 95% confidence interval
     */
    public double confidenceHi() {
        return this.confidenceHiVal;
    }
    

    /**
     * main client. 2 arguments (n, trials) must be provided to run the client.
     * It runs the experiment and prints the result to the StdOut.
     */
    public static void main(String[] args) {
        if (args.length != 2) {
            StdOut.println("Must provide two args n, trials");
            return;
        }
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        PercolationStats stats = new PercolationStats(n, trials);
        StdOut.println("mean                    = " + stats.mean());
        StdOut.println("stddev                  = " + stats.stddev());
        StdOut.println("95% confidence interval = " 
                + stats.confidenceLo() + ", " + stats.confidenceHi());
    }
}