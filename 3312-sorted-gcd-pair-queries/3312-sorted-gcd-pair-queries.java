class Solution {
    public int[] gcdValues(int[] nums, long[] queries) {
        int max = 0;
        for (int x : nums) max = Math.max(max, x);

        int[] freq = new int[max + 1];
        for (int x : nums) freq[x]++;

        long[] exact = new long[max + 1];
        int[] cnt = new int[max + 1];

        // cnt[g] = numbers divisible by g
        for (int g = 1; g <= max; g++) {
            for (int m = g; m <= max; m += g) {
                cnt[g] += freq[m];
            }
            long c = cnt[g];
            exact[g] = c * (c - 1) / 2;
        }

        // Inclusion-Exclusion
        for (int g = max; g >= 1; g--) {
            for (int m = g + g; m <= max; m += g) {
                exact[g] -= exact[m];
            }
        }

        // Prefix counts
        long[] pref = new long[max + 1];
        for (int i = 1; i <= max; i++) {
            pref[i] = pref[i - 1] + exact[i];
        }

        int[] ans = new int[queries.length];

        for (int i = 0; i < queries.length; i++) {
            long k = queries[i];

            int lo = 1, hi = max;
            while (lo < hi) {
                int mid = (lo + hi) >>> 1;
                if (pref[mid] > k)
                    hi = mid;
                else
                    lo = mid + 1;
            }

            ans[i] = lo;
        }

        return ans;
    }
}