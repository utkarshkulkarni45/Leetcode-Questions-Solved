class Solution {
    public long countMajoritySubarrays(int[] nums, int target) {
        int n = nums.length;

        int[] prefix = new int[n + 1];
        for (int i = 0; i < n; i++) {
            prefix[i + 1] = prefix[i] + (nums[i] == target ? 1 : -1);
        }

        int[] sorted = prefix.clone();
        java.util.Arrays.sort(sorted);

        long ans = 0;
        Fenwick tree = new Fenwick(n + 2);

        for (int i = 0; i <= n; i++) {
            int rank = lowerBound(sorted, prefix[i]) + 1;

            // count previous prefix sums smaller than current
            ans += tree.query(rank - 1);

            tree.add(rank, 1);
        }

        return ans;
    }

    private int lowerBound(int[] arr, int x) {
        int l = 0, r = arr.length;

        while (l < r) {
            int mid = l + (r - l) / 2;

            if (arr[mid] < x)
                l = mid + 1;
            else
                r = mid;
        }

        return l;
    }

    static class Fenwick {
        int[] bit;

        Fenwick(int n) {
            bit = new int[n + 1];
        }

        void add(int i, int val) {
            while (i < bit.length) {
                bit[i] += val;
                i += i & -i;
            }
        }

        int query(int i) {
            int sum = 0;

            while (i > 0) {
                sum += bit[i];
                i -= i & -i;
            }

            return sum;
        }
    }
}