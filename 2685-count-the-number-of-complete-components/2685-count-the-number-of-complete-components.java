class Solution {
    public int countCompleteComponents(int n, int[][] edges) {
        List<Integer>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++) graph[i] = new ArrayList<>();

        for (int[] e : edges) {
            graph[e[0]].add(e[1]);
            graph[e[1]].add(e[0]);
        }

        boolean[] visited = new boolean[n];
        int completeComponents = 0;

        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                Queue<Integer> q = new ArrayDeque<>();
                q.offer(i);
                visited[i] = true;

                int nodes = 0;
                int edgesCount = 0;

                while (!q.isEmpty()) {
                    int u = q.poll();
                    nodes++;
                    edgesCount += graph[u].size();

                    for (int v : graph[u]) {
                        if (!visited[v]) {
                            visited[v] = true;
                            q.offer(v);
                        }
                    }
                }

                edgesCount /= 2; 
                if (edgesCount == nodes * (nodes - 1) / 2) {
                    completeComponents++;
                }
            }
        }
        return completeComponents;
    }
}
