class Solution {

    static final int INF = 1_000_000;

    int[] c2 = new int[10];
    int[] c3 = new int[10];
    int[] c5 = new int[10];
    int[] c7 = new int[10];

    int need2, need3, need5, need7;

    int[][] dp;

    public String smallestNumber(String num, long t) {

        // Factorize t.
        while (t % 2 == 0) {
            need2++;
            t /= 2;
        }

        while (t % 3 == 0) {
            need3++;
            t /= 3;
        }

        while (t % 5 == 0) {
            need5++;
            t /= 5;
        }

        while (t % 7 == 0) {
            need7++;
            t /= 7;
        }

        // Digits 1..9 cannot produce any other prime factor.
        if (t != 1) {
            return "-1";
        }

        buildFactors();
        buildDP();

        int n = num.length();

        // If num itself works, return it.
        if (isValid(num)) {
            return num;
        }

        /*
         * Try to construct an answer with the same length.
         *
         * We change one digit and make everything after it
         * as small as possible.
         */
        int[] p2 = new int[n + 1];
        int[] p3 = new int[n + 1];
        int[] p5 = new int[n + 1];
        int[] p7 = new int[n + 1];

        boolean[] zeroPrefix = new boolean[n + 1];
        zeroPrefix[0] = true;

        for (int i = 0; i < n; i++) {
            int d = num.charAt(i) - '0';

            p2[i + 1] = p2[i] + c2[d];
            p3[i + 1] = p3[i] + c3[d];
            p5[i + 1] = p5[i] + c5[d];
            p7[i + 1] = p7[i] + c7[d];

            zeroPrefix[i + 1] =
                zeroPrefix[i] && d != 0;
        }

        /*
         * Try positions from right to left.
         *
         * The prefix before pos must contain no zero.
         * This is essential for cases like 109.
         */
        for (int pos = n - 1; pos >= 0; pos--) {

            if (!zeroPrefix[pos]) {
                continue;
            }

            int oldDigit = num.charAt(pos) - '0';

            for (int digit = oldDigit + 1; digit <= 9; digit++) {

                int r2 = Math.max(
                    0,
                    need2 - p2[pos] - c2[digit]
                );

                int r3 = Math.max(
                    0,
                    need3 - p3[pos] - c3[digit]
                );

                int r5 = Math.max(
                    0,
                    need5 - p5[pos] - c5[digit]
                );

                int r7 = Math.max(
                    0,
                    need7 - p7[pos] - c7[digit]
                );

                int remaining = n - pos - 1;

                if (canMake(r2, r3, r5, r7, remaining)) {

                    StringBuilder ans = new StringBuilder();

                    // Original prefix.
                    ans.append(num, 0, pos);

                    // Increased digit.
                    ans.append(digit);

                    // Smallest possible suffix.
                    buildSmallest(
                        ans,
                        remaining,
                        r2,
                        r3,
                        r5,
                        r7
                    );

                    return ans.toString();
                }
            }
        }

        /*
         * No number of the same length works.
         *
         * Therefore construct the smallest possible number
         * having a larger length.
         */
        int minLength = minimumDigits(
            need2,
            need3,
            need5,
            need7
        );

        int length = Math.max(n + 1, minLength);

        StringBuilder ans = new StringBuilder();

        buildSmallest(
            ans,
            length,
            need2,
            need3,
            need5,
            need7
        );

        return ans.toString();
    }

    private void buildFactors() {

        for (int d = 1; d <= 9; d++) {

            int x = d;

            while (x % 2 == 0) {
                c2[d]++;
                x /= 2;
            }

            while (x % 3 == 0) {
                c3[d]++;
                x /= 3;
            }

            while (x % 5 == 0) {
                c5[d]++;
                x /= 5;
            }

            while (x % 7 == 0) {
                c7[d]++;
                x /= 7;
            }
        }
    }

    /*
     * Only factors 2 and 3 need DP.
     *
     * 2 -> (1,0)
     * 3 -> (0,1)
     * 4 -> (2,0)
     * 6 -> (1,1)
     * 8 -> (3,0)
     * 9 -> (0,2)
     */
    private void buildDP() {

        dp = new int[need2 + 1][need3 + 1];

        for (int i = 0; i <= need2; i++) {
            java.util.Arrays.fill(dp[i], INF);
        }

        dp[0][0] = 0;

        int[][] factors = {
            {1, 0},
            {0, 1},
            {2, 0},
            {1, 1},
            {3, 0},
            {0, 2}
        };

        for (int a = 0; a <= need2; a++) {
            for (int b = 0; b <= need3; b++) {

                if (dp[a][b] == INF) {
                    continue;
                }

                for (int[] f : factors) {

                    int na = Math.min(
                        need2,
                        a + f[0]
                    );

                    int nb = Math.min(
                        need3,
                        b + f[1]
                    );

                    dp[na][nb] = Math.min(
                        dp[na][nb],
                        dp[a][b] + 1
                    );
                }
            }
        }
    }

    private int minimumDigits(
        int a,
        int b,
        int c,
        int d
    ) {
        return dp[a][b] + c + d;
    }

    private boolean canMake(
        int a,
        int b,
        int c,
        int d,
        int positions
    ) {
        return minimumDigits(a, b, c, d) <= positions;
    }

    /*
     * Construct the lexicographically smallest suffix.
     */
    private void buildSmallest(
        StringBuilder ans,
        int length,
        int a,
        int b,
        int c,
        int d
    ) {

        for (int pos = 0; pos < length; pos++) {

            int remaining = length - pos - 1;

            for (int digit = 1; digit <= 9; digit++) {

                int na = Math.max(
                    0,
                    a - c2[digit]
                );

                int nb = Math.max(
                    0,
                    b - c3[digit]
                );

                int nc = Math.max(
                    0,
                    c - c5[digit]
                );

                int nd = Math.max(
                    0,
                    d - c7[digit]
                );

                if (canMake(
                    na,
                    nb,
                    nc,
                    nd,
                    remaining
                )) {

                    ans.append(digit);

                    a = na;
                    b = nb;
                    c = nc;
                    d = nd;

                    break;
                }
            }
        }
    }

    private boolean isValid(String s) {

        int a = 0;
        int b = 0;
        int c = 0;
        int d = 0;

        for (char ch : s.toCharArray()) {

            int x = ch - '0';

            if (x == 0) {
                return false;
            }

            a += c2[x];
            b += c3[x];
            c += c5[x];
            d += c7[x];
        }

        return a >= need2 &&
               b >= need3 &&
               c >= need5 &&
               d >= need7;
    }
}