import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private static final int[][] DIRS = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    private boolean[][] site = null;
    private int nOpenSites = 0;
    private final int size;
    private final WeightedQuickUnionUF uf;
    private final WeightedQuickUnionUF uf2;
//    private int percolated = -1;

    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        this.size = n;
        this.site = new boolean[n][n];
        this.uf = new WeightedQuickUnionUF(n * n + 1);
        this.uf2 = new WeightedQuickUnionUF(n * n + 2);
        for (int i = 0; i < n; i++) {
            this.uf.union(i, n * n);
            this.uf2.union(i, n * n);
            this.uf2.union(n * (n - 1) + i, n * n + 1);
        }
    }

    private void validateIndex(int row, int col) {
        if (row < 1 || row > size || col < 1 || col > size) {
            throw new IllegalArgumentException();
        }
    }

    public void open(int row, int col) {
        validateIndex(row, col);
        row--;
        col--;
        if (this.site[row][col]) {
            return;
        }
        this.site[row][col] = true;
//            if (row == size - 1) {
//                this.uf.union(row * size + col, size * size + 1);
//            }
        unionNeighbours(row, col);
        this.nOpenSites += 1;
    }

    private void unionNeighbours(int row, int col) {
        for (int[] d : DIRS) {
            int row0 = row + d[0];
            int col0 = col + d[1];
//                StdOut.printf("%d,%d,%d,%d\n", row, col, row0, col0);
            try {
                if (isOpen(row0 + 1, col0 + 1)) {
                    this.uf.union(row0 * size + col0, row * size + col);
                    this.uf2.union(row0 * size + col0, row * size + col);
                }
            } catch (IllegalArgumentException ignored) {

            }
        }
    }

    public boolean isOpen(int row, int col) {
        validateIndex(row, col);
        return this.site[row - 1][col - 1];
    }


    public boolean isFull(int row, int col) {
        validateIndex(row, col);
        if (isOpen(row, col)) {
            return this.uf.connected((row - 1) * size + col - 1, size * size);
        }
        return false;
    }

    public int numberOfOpenSites() {
        return this.nOpenSites;
    }

    public boolean percolates() {
        return uf2.connected(size * size, size * size + 1);
    }


    public static void main(String[] args) {

//        In in = new In(args[0]);
        In in = new In("greeting57.txt");
        int n = in.readInt();
        Percolation perc = new Percolation(n);
        while (!in.isEmpty()) {
            int i = in.readInt();
            int j = in.readInt();
            perc.open(i, j);

        }
        StdOut.printf("%b", perc.percolates());


    }
}
