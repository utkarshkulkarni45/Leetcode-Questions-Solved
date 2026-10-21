
class Solution {
    public int minOperations(String s) {
        int n = s.length();

        int min = 'z';
        int max = 'a';
        boolean sorted = true;

        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);

            min = Math.min(min, c);
            max = Math.max(max, c);

            if (i > 0 && c < s.charAt(i - 1)) {
                sorted = false;
            }
        }

        // Already sorted
        if (sorted) return 0;

        // Cannot sort a descending string of length 2
        if (n == 2) return -1;

        // One operation is sufficient
        if (s.charAt(0) == min || s.charAt(n - 1) == max) {
            return 1;
        }

        // Check whether min or max occurs in the middle
        for (int i = 1; i < n - 1; i++) {
            if (s.charAt(i) == min || s.charAt(i) == max) {
                return 2;
            }
        }

        // Otherwise, three operations are required
        return 3;
    }
}
