
class Solution {

    int m, n;
    int[][] directions = {
        {0, 1},
        {0, -1},
        {1, 0},
        {-1, 0}
    };

    long dfs(int[][] grid, int r, int c) {

        if (r < 0 || c < 0 || r >= m || c >= n ||
            grid[r][c] == 0) {
            return 0;
        }

        // Mark visited
        int value = grid[r][c];
        grid[r][c] = 0;

        long sum = value;

        for (int[] dir : directions) {
            int nr = r + dir[0];
            int nc = c + dir[1];

            sum += dfs(grid, nr, nc);
        }

        return sum;
    }

    public int countIslands(int[][] grid, int k) {

        m = grid.length;
        n = grid[0].length;

        int count = 0;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                if (grid[i][j] > 0) {

                    long sum = dfs(grid, i, j);

                    if (sum % k == 0) {
                        count++;
                    }
                }
            }
        }

        return count;
    }
}
