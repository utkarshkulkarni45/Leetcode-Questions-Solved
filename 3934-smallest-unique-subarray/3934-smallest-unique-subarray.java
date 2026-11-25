
import java.util.*;

class Solution {
    public int smallestUniqueSubarray(int[] nums) {
        int n = nums.length;

        // Step 1: Build suffix array
        Integer[] sa = new Integer[n];
        int[] rank = new int[n];

        for (int i = 0; i < n; i++) {
            sa[i] = i;
            rank[i] = nums[i];
        }

        int[] newRank = new int[n];

        for (int k = 1; k < n; k *= 2) {

            final int len = k;
            final int[] r = rank;

            Arrays.sort(sa, (a, b) -> {
                if (r[a] != r[b]) {
                    return Integer.compare(r[a], r[b]);
                }

                int ra = (a + len < n) ? r[a + len] : -1;
                int rb = (b + len < n) ? r[b + len] : -1;

                return Integer.compare(ra, rb);
            });

            newRank[sa[0]] = 0;

            for (int i = 1; i < n; i++) {
                int a = sa[i - 1];
                int b = sa[i];

                int a1 = rank[a];
                int b1 = rank[b];

                int a2 = (a + k < n) ? rank[a + k] : -1;
                int b2 = (b + k < n) ? rank[b + k] : -1;

                newRank[b] = newRank[a];

                if (a1 != b1 || a2 != b2) {
                    newRank[b]++;
                }
            }

            int[] temp = rank;
            rank = newRank;
            newRank = temp;

            if (rank[sa[n - 1]] == n - 1) {
                break;
            }
        }

        // Step 2: Build inverse suffix array
        int[] pos = new int[n];

        for (int i = 0; i < n; i++) {
            pos[sa[i]] = i;
        }

        // Step 3: Calculate LCP using Kasai's algorithm
        int[] lcp = new int[n];
        int h = 0;

        for (int i = 0; i < n; i++) {
            int r = pos[i];

            if (r == 0) {
                continue;
            }

            int j = sa[r - 1];

            while (i + h < n && j + h < n
                    && nums[i + h] == nums[j + h]) {
                h++;
            }

            lcp[r] = h;

            if (h > 0) {
                h--;
            }
        }

        // Step 4: Find minimum unique subarray length
        int ans = n;

        for (int r = 0; r < n; r++) {
            int prevLcp = lcp[r];

            int nextLcp = (r + 1 < n) ? lcp[r + 1] : 0;

            int maxLcp = Math.max(prevLcp, nextLcp);

            int start = sa[r];

            int uniqueLength = maxLcp + 1;

            if (uniqueLength <= n - start) {
                ans = Math.min(ans, uniqueLength);
            }
        }

        return ans;
    }
}
