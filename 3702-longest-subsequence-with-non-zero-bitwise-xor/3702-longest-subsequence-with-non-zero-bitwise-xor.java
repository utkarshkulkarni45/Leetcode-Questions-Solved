class Solution {
    public int longestSubsequence(int[] nums) {
        int totalXor = 0;
        boolean hasNonZero = false;

        for (int num : nums) {
            totalXor ^= num;
            if (num != 0) {
                hasNonZero = true;
            }
        }

        // Case 1: All elements are zero -> impossible to get non-zero XOR
        if (!hasNonZero) {
            return 0;
        }

        // Case 2: Total XOR is non-zero -> take all elements
        if (totalXor != 0) {
            return nums.length;
        }

        // Case 3: Total XOR is zero, but non-zero elements exist -> remove one non-zero element
        return nums.length - 1;
    }
}