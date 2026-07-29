class Solution {

    long LIMIT;
    long[][] C;

    public String smallestPalindrome(String s, int k) {
        LIMIT = k;

        int[] freq = new int[26];
        for (char ch : s.toCharArray()) freq[ch - 'a']++;

        int[] half = new int[26];
        int halfLen = 0;
        char mid = 0;

        for (int i = 0; i < 26; i++) {
            half[i] = freq[i] / 2;
            halfLen += half[i];
            if ((freq[i] & 1) == 1) mid = (char) ('a' + i);
        }

        buildComb(halfLen);

        if (countWays(half.clone(), halfLen) < k) return "";

        StringBuilder first = new StringBuilder();

        for (int pos = 0; pos < halfLen; pos++) {
            for (int c = 0; c < 26; c++) {
                if (half[c] == 0) continue;

                half[c]--;

                long ways = countWays(half, halfLen - pos - 1);

                if (ways >= k) {
                    first.append((char) ('a' + c));
                    break;
                } else {
                    k -= ways;
                    half[c]++;
                }
            }
        }

        StringBuilder ans = new StringBuilder(first);

        if (mid != 0) ans.append(mid);

        ans.append(first.reverse());

        return ans.toString();
    }

    void buildComb(int n) {
        C = new long[n + 1][n + 1];
        for (int i = 0; i <= n; i++) {
            C[i][0] = C[i][i] = 1;
            for (int j = 1; j < i; j++) {
                C[i][j] = Math.min(LIMIT, C[i - 1][j - 1] + C[i - 1][j]);
            }
        }
    }

    long countWays(int[] cnt, int total) {
        long res = 1;
        int rem = total;

        for (int i = 0; i < 26; i++) {
            int x = cnt[i];
            if (x == 0) continue;

            res *= C[rem][x];
            if (res >= LIMIT) return LIMIT;

            rem -= x;
        }

        return Math.min(res, LIMIT);
    }
}