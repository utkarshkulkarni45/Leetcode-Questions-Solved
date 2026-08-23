class Solution {
    public boolean sumGame(String num) {
        int n = num.length();
        int leftSum = 0, rightSum = 0;
        int leftQ = 0, rightQ = 0;

        for (int i = 0; i < n / 2; i++) {
            if (num.charAt(i) == '?') {
                leftQ++;
            } else {
                leftSum += num.charAt(i) - '0';
            }
        }

        for (int i = n / 2; i < n; i++) {
            if (num.charAt(i) == '?') {
                rightQ++;
            } else {
                rightSum += num.charAt(i) - '0';
            }
        }

        // Total '?' is odd -> Alice always gets the last move to make sums unequal
        if ((leftQ + rightQ) % 2 != 0) {
            return true;
        }

        // Bob wins if the initial sum difference balances out Bob's forced sum contribution
        // sumDiff + (qDiff / 2) * 9 == 0
        int sumDiff = leftSum - rightSum;
        int qDiff = leftQ - rightQ;

        return (sumDiff + (qDiff / 2) * 9.0) != 0;
    }
}