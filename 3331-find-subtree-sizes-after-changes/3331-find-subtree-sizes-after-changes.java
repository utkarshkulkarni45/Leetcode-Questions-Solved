
class Solution {
    List<List<Integer>> adj;
    int[] ans;
    int[] last;
    String s;

    public int[] findSubtreeSizes(int[] parent, String s) {

        int n = parent.length;

        this.s = s;

        adj = new ArrayList<>();
        ans = new int[n];
        last = new int[26];

        Arrays.fill(last, -1);

        // Build original tree
        for (int i = 0; i < n; i++) {
            adj.add(new ArrayList<>());
        }

        for (int i = 1; i < n; i++) {
            adj.get(parent[i]).add(i);
        }

        dfs(0, parent);

        return ans;
    }

    void dfs(int node, int[] parent) {

        int idx = s.charAt(node) - 'a';

        // Store previous nearest ancestor
        int prev = last[idx];

        // Current node becomes nearest ancestor
        last[idx] = node;

        // Every node contributes 1 to its subtree
        ans[node] = 1;

        // Process children
        for (int child : adj.get(node)) {
            dfs(child, parent);
        }

        // Find the new parent
        int newParent = (prev != -1) ? prev : parent[node];

        // Add current subtree size to new parent
        if (newParent != -1) {
            ans[newParent] += ans[node];
        }

        // Restore previous ancestor
        last[idx] = prev;
    }
}
