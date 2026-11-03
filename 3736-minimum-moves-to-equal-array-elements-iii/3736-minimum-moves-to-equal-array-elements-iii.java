class Solution {
    public int minMoves(int[] nums) {
        int max=Integer.MIN_VALUE,moves=0;
        for(int i=0;i<nums.length;i++) max=Math.max(max,nums[i]);
        for(int j=0;j<nums.length;j++) moves+=max-nums[j];
        return moves;
        
    }
}