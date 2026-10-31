class Solution {
    public int uniquePaths(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int MOD = 1_000_000_007;

        long[][] dp = new long[m][n];
        dp[0][0] = 1;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                if (grid[i][j] == 1) {
                    continue;
                }

                long ways = dp[i][j];

                if (ways == 0) continue;

                // Move right
                if (j + 1 < n) {
                    int x = i;
                    int y = j + 1;

                    while (x < m && y < n && grid[x][y] == 1) {
                        x++;
                        if (x < m && y < n && grid[x][y] == 1) {
                            y++;
                        }
                    }

                    if (x < m && y < n) {
                        dp[x][y] = (dp[x][y] + ways) % MOD;
                    }
                }

                // Move down
                if (i + 1 < m) {
                    int x = i + 1;
                    int y = j;

                    while (x < m && y < n && grid[x][y] == 1) {
                        y++;
                        if (x < m && y < n && grid[x][y] == 1) {
                            x++;
                        }
                    }

                    if (x < m && y < n) {
                        dp[x][y] = (dp[x][y] + ways) % MOD;
                    }
                }
            }
        }

        return (int) dp[m - 1][n - 1];
    }
}