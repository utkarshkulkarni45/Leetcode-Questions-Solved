class Solution {
    public boolean checkDivisibility(int n) {
        int temp = n;
        int digitSum = 0;
        int digitProduct = 1;

        while (temp > 0) {
            int digit = temp % 10;
            digitSum += digit;
            digitProduct *= digit;
            temp /= 10;
        }

        int totalSum = digitSum + digitProduct;

        // Prevent division by zero (if totalSum is 0)
        if (totalSum == 0) {
            return false;
        }

        return n % totalSum == 0;
    }
}