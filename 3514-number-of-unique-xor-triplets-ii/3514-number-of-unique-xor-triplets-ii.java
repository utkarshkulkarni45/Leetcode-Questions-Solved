class Solution {
    public int uniqueXorTriplets(int[] nums) {
        int MAX = 2048; // XOR values: 0..2047

        boolean[][] dp = new boolean[4][MAX];
        dp[0][0] = true;

        for (int val : nums) {
            boolean[][] next = new boolean[4][MAX];

            // Option: don't use this index
            for (int c = 0; c <= 3; c++) {
                for (int x = 0; x < MAX; x++) {
                    if (dp[c][x]) next[c][x] = true;
                }
            }

            // Use this index 1, 2, or 3 times
            for (int c = 0; c <= 3; c++) {
                for (int x = 0; x < MAX; x++) {
                    if (!dp[c][x]) continue;

                    // Take once
                    if (c + 1 <= 3)
                        next[c + 1][x ^ val] = true;

                    // Take twice
                    if (c + 2 <= 3)
                        next[c + 2][x] = true;

                    // Take three times
                    if (c + 3 <= 3)
                        next[c + 3][x ^ val] = true;
                }
            }

            dp = next;
        }

        int ans = 0;
        for (int x = 0; x < MAX; x++) {
            if (dp[3][x]) ans++;
        }
        return ans;
    }
}