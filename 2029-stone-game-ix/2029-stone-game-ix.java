class Solution {
    public boolean stoneGameIX(int[] stones) {
        int[] cnt = new int[3];
        for (int stone : stones) {
            cnt[stone % 3]++;
        }
        
        int c0 = cnt[0];
        int c1 = cnt[1];
        int c2 = cnt[2];
        
        if (c0 % 2 == 0) {
            // Even number of 0s: Alice wins if both 1s and 2s are present
            return c1 >= 1 && c2 >= 1;
        } else {
            // Odd number of 0s: Alice wins if the absolute difference between 1s and 2s is >= 3
            return Math.abs(c1 - c2) >= 3;
        }
    }
}