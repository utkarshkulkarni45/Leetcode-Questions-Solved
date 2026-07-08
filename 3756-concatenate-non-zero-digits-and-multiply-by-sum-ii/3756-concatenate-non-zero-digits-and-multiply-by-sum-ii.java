class Solution {
    static final long MOD = 1000000007L;

    public int[] sumAndMultiply(String s, int[][] queries) {
        int n = s.length();

        ArrayList<Integer> pos = new ArrayList<>();
        ArrayList<Integer> dig = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            int d = s.charAt(i) - '0';
            if (d != 0) {
                pos.add(i);
                dig.add(d);
            }
        }

        int k = pos.size();

        long[] prefHash = new long[k + 1];
        long[] prefSum = new long[k + 1];
        long[] pow10 = new long[k + 1];

        pow10[0] = 1;
        for (int i = 1; i <= k; i++)
            pow10[i] = (pow10[i - 1] * 10) % MOD;

        for (int i = 0; i < k; i++) {
            prefHash[i + 1] = (prefHash[i] * 10 + dig.get(i)) % MOD;
            prefSum[i + 1] = prefSum[i] + dig.get(i);
        }

        int[] ans = new int[queries.length];

        for (int qi = 0; qi < queries.length; qi++) {
            int l = queries[qi][0];
            int r = queries[qi][1];

            int left = lowerBound(pos, l);
            int right = upperBound(pos, r) - 1;

            if (left > right) {
                ans[qi] = 0;
                continue;
            }

            int len = right - left + 1;

            long x = (prefHash[right + 1]
                    - (prefHash[left] * pow10[len]) % MOD
                    + MOD) % MOD;

            long sum = prefSum[right + 1] - prefSum[left];

            ans[qi] = (int) ((x * (sum % MOD)) % MOD);
        }

        return ans;
    }

    private int lowerBound(ArrayList<Integer> arr, int target) {
        int l = 0, r = arr.size();
        while (l < r) {
            int mid = (l + r) / 2;
            if (arr.get(mid) >= target)
                r = mid;
            else
                l = mid + 1;
        }
        return l;
    }

    private int upperBound(ArrayList<Integer> arr, int target) {
        int l = 0, r = arr.size();
        while (l < r) {
            int mid = (l + r) / 2;
            if (arr.get(mid) > target)
                r = mid;
            else
                l = mid + 1;
        }
        return l;
    }
}