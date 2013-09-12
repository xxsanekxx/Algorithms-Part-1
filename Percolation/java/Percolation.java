public class Percolation
{

    private boolean[] grid;
    private int gridN, ufN;
    private WeightedQuickUnionUF uf;
    private WeightedQuickUnionUF full;
    // create N-by-N grid, with all sites blocked
    public Percolation(int N)
    {
        gridN = N;
        int size = N*N;
        uf = new WeightedQuickUnionUF(size+2);
        full = new WeightedQuickUnionUF(size+1);
        ufN = uf.count() - 1;
        grid = new boolean[size]; 
        for (int i = 0; i < size; i++)
            grid[i] = false;
    }

    // open site (row i, column j) if it is not already
    public void open(int i, int j)
    {
        if (i < 1 || i > gridN)
            throw new IndexOutOfBoundsException("row index i out of bounds");
        if (j < 1 || j > gridN)
            throw new IndexOutOfBoundsException("col index j out of range");
        
        int index = xyTo1D(i, j);
        grid[index] = true;
        if (i == 1) {
            uf.union(ufN-1, index);
            full.union(ufN-1, index);
        }
        if (i == gridN) {
            uf.union(ufN, index);
        }
            
        if (j > 1 && grid[index-1]) {
            uf.union(index, index-1);
            full.union(index, index-1);
        }
        if (i > 1 && grid[index-gridN]) {
            uf.union(index, index-gridN);
            full.union(index, index-gridN);
        }
        if (j < gridN && grid[index+1]) {
            uf.union(index, index+1);
            full.union(index, index+1);
        }
        if (i < gridN && grid[index+gridN]) {
            uf.union(index, index+gridN);
            full.union(index, index+gridN);
        }
    }

    // is site (row i, column j) open?
    public boolean isOpen(int i, int j)
    {
        if (i < 1 || i > gridN)
            throw new IndexOutOfBoundsException("row index i out of bounds");
        if (j < 1 || j > gridN)
            throw new IndexOutOfBoundsException("col index j out of range");
        return grid[xyTo1D(i, j)];
    }

    // is site (row i, column j) full?
    public boolean isFull(int i, int j)
    {
        if (i < 1 || i > gridN)
            throw new IndexOutOfBoundsException("row index i out of bounds");
        if (j < 1 || j > gridN)
            throw new IndexOutOfBoundsException("col index j out of range");
        return full.connected(xyTo1D(i, j), ufN-1);
    }

    // does the system percolate?
    public boolean percolates()
    {
        return uf.connected(ufN-1, ufN);
    }
    //convert 2d (i(row),j(column)) to 1d array, return index
    private int xyTo1D(int i, int j)
    {
        if (i < 1 || i > gridN)
            throw new IndexOutOfBoundsException("row index i out of bounds");
        if (j < 1 || j > gridN)
            throw new IndexOutOfBoundsException("col index j out of range");
        return gridN * (i-1) + (j-1);
    }
//    //test coonect index of grid
//    public boolean connected (int i, int j)
//    {
//      if (i < 0 || i > ufN)
//          throw new IndexOutOfBoundsException("i must between 0 and"+ ufN);
//      if (j < 0 || j > ufN)
//          throw new IndexOutOfBoundsException("j must between 0 and"+ ufN);
//      return uf.connected(i, j);
//    }
    public static void main(String[] args)
    {
//        int N = StdIn.readInt();
//        Percolation p = new Percolation(N);
//        //test simple
//        //p.open(1, 1);
//        //p.open(1, 2);
//        p.isFull(1, 1);
//        p.percolates();
//        StdOut.println("test connect is "+ p.connected(0, 1));
    }
}