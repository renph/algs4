import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;


public class PercolationStats {
    private final int gridSize;
    private final int trials;
    private final double mean;
    private final double stddev;

    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n < 1 || trials < 1) {
            throw new IllegalArgumentException();
        }
        this.gridSize = n;
        this.trials = trials;
        double[] results = new double[trials];
        for (int i = 0; i < trials; i++) {
            results[i] = trail();
        }

        mean = StdStats.mean(results);
        stddev = StdStats.stddev(results);
    }

    private double trail() {
        Percolation perc = new Percolation(gridSize);
        int randRow, randCol;
        while (!perc.percolates()) {
            randRow = StdRandom.uniform(gridSize) + 1;
            randCol = StdRandom.uniform(gridSize) + 1;
            perc.open(randRow, randCol);
        }
        return ((double) perc.numberOfOpenSites()) / (gridSize * gridSize);
    }

    // sample mean of percolation threshold
    public double mean() {
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return stddev;
    }

    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean - 1.96 * stddev / Math.sqrt((double) trials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean + 1.96 * stddev / Math.sqrt((double) trials);
    }

    public static void main(String[] args) {

        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats percStats = new PercolationStats(n, trials);
//        PercolationStats percStats = new PercolationStats(2 , 10000);
        System.out.println(percStats.mean());
        System.out.println(percStats.stddev());
        System.out.println(percStats.confidenceLo());
        System.out.println(percStats.confidenceHi());
    }
}