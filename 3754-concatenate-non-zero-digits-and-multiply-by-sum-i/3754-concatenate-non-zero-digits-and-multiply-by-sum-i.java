class Solution {
    public long sumAndMultiply(int n) {
        if (n == 0) return 0;

        int[] digits = new int[10]; // max non-zero digits for n <= 1e9
        int m = 0;
        long sum = 0;

        // extract digits in reverse order
        while (n > 0) {
            int d = n % 10;
            if (d != 0) {
                digits[m++] = d;
                sum += d;
            }
            n /= 10;
        }

        // concatenate in original order (reverse back)
        long x = 0;
        for (int i = m - 1; i >= 0; i--) {
            x = x * 10 + digits[i];
        }

        return x * sum;
    }
}
