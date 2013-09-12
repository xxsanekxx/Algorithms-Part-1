public class PercolationStats
{
    private double[] sumMOpened;
    private int T;
    // perform T independent computational experiments on an N-by-N grid
    public PercolationStats(int N, int T)
    {
        if (N <= 0 || T <= 0)
            throw new java.lang.IllegalArgumentException("Illegal parameter values");
        this.T = T;
        sumMOpened = new double[T];
        
        for (int i = 0; i < T; i++) {
            Percolation p = new Percolation(N);
            while (!p.percolates()) {
                int row = StdRandom.uniform(1, N+1);
                int col = StdRandom.uniform(1, N+1);
                
                if (!p.isOpen(row, col))
                {
                    p.open(row, col);
                    sumMOpened[i]++;
                }
                
             }
            sumMOpened[i] /= (N*N);
            
         }
        
    }

    // sample mean of percolation threshold
    public double mean()
    {
        return StdStats.mean(sumMOpened);
    }

    // sample standard deviation of percolation threshold
    public double stddev()
    {
        return StdStats.stddev(sumMOpened);
    }

    // returns lower bound of the 95% confidence interval
    public double confidenceLo()
    {
        return mean() - ((1.96 * stddev()) / Math.sqrt(T));
    }

    // returns upper bound of the 95% confidence interval
    public double confidenceHi()
    {
        return mean() + ((1.96 * stddev()) / Math.sqrt(T));
    }
    public static void main(String[] args)
    {   
        int N = StdIn.readInt();
        int T = StdIn.readInt();
        Stopwatch stop = new Stopwatch();
        PercolationStats ps = new PercolationStats(N, T);
        double hi = ps.confidenceHi();
        double lo = ps.confidenceLo();
        StdOut.println("mean                    = " + ps.mean());
        StdOut.println("stddev                  = " + ps.stddev());
        StdOut.println("95% confidence interval = " + hi+ ", " + lo);
        StdOut.println("Total elapsed Time\t= "+stop.elapsedTime()+"s");
    }
}
