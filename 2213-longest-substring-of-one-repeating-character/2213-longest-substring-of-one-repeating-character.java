class Solution {

    static class SegmentTree {
        int n;
        char[] s;
        int[] maxLen;
        int[] prefLen;
        int[] suffLen;
        char[] leftChar;
        char[] rightChar;

        public SegmentTree(String str) {
            this.s = str.toCharArray();
            this.n = s.length;
            int size = 4 * n;
            maxLen = new int[size];
            prefLen = new int[size];
            suffLen = new int[size];
            leftChar = new char[size];
            rightChar = new char[size];
            build(1, 0, n - 1);
        }

        private void merge(int node, int l, int r, int mid) {
            int leftChild = 2 * node;
            int rightChild = 2 * node + 1;

            leftChar[node] = leftChar[leftChild];
            rightChar[node] = rightChar[rightChild];

            int leftLen = mid - l + 1;
            int rightLen = r - mid;

            // Compute prefLen
            prefLen[node] = prefLen[leftChild];
            if (prefLen[leftChild] == leftLen && rightChar[leftChild] == leftChar[rightChild]) {
                prefLen[node] += prefLen[rightChild];
            }

            // Compute suffLen
            suffLen[node] = suffLen[rightChild];
            if (suffLen[rightChild] == rightLen && rightChar[leftChild] == leftChar[rightChild]) {
                suffLen[node] += suffLen[leftChild];
            }

            // Compute maxLen
            maxLen[node] = Math.max(maxLen[leftChild], maxLen[rightChild]);
            if (rightChar[leftChild] == leftChar[rightChild]) {
                maxLen[node] = Math.max(maxLen[node], suffLen[leftChild] + prefLen[rightChild]);
            }
        }

        private void build(int node, int l, int r) {
            if (l == r) {
                maxLen[node] = 1;
                prefLen[node] = 1;
                suffLen[node] = 1;
                leftChar[node] = s[l];
                rightChar[node] = s[l];
                return;
            }
            int mid = l + (r - l) / 2;
            build(2 * node, l, mid);
            build(2 * node + 1, mid + 1, r);
            merge(node, l, r, mid);
        }

        public void update(int node, int l, int r, int idx, char ch) {
            if (l == r) {
                s[idx] = ch;
                leftChar[node] = ch;
                rightChar[node] = ch;
                return;
            }
            int mid = l + (r - l) / 2;
            if (idx <= mid) {
                update(2 * node, l, mid, idx, ch);
            } else {
                update(2 * node + 1, mid + 1, r, idx, ch);
            }
            merge(node, l, r, mid);
        }

        public int getMaxLen() {
            return maxLen[1];
        }
    }

    public int[] longestRepeating(String s, String queryCharacters, int[] queryIndices) {
        SegmentTree tree = new SegmentTree(s);
        int k = queryIndices.length;
        int[] result = new int[k];

        for (int i = 0; i < k; i++) {
            int idx = queryIndices[i];
            char ch = queryCharacters.charAt(i);
            tree.update(1, 0, s.length() - 1, idx, ch);
            result[i] = tree.getMaxLen();
        }

        return result;
    }
}