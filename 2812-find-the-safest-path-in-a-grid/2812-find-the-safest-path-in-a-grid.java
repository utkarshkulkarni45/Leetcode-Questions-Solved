class Solution {
    public int maximumSafenessFactor(List<List<Integer>> grid) {
        
        int n = grid.size();
        int[][] dist = new int[n][n];
        for (int[] row : dist) {
            Arrays.fill(row, -1);
        }

        // Multi-source BFS to calculate distance from nearest thief
        Queue<int[]> q = new LinkedList<>();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid.get(i).get(j) == 1) {
                    dist[i][j] = 0;
                    q.offer(new int[]{i, j});
                }
            }
        }

        int[] dr = {1, -1, 0, 0};
        int[] dc = {0, 0, 1, -1};

        while (!q.isEmpty()) {
            int[] cur = q.poll();

            for (int k = 0; k < 4; k++) {
                int nr = cur[0] + dr[k];
                int nc = cur[1] + dc[k];

                if (nr >= 0 && nr < n && nc >= 0 && nc < n && dist[nr][nc] == -1) {
                    dist[nr][nc] = dist[cur[0]][cur[1]] + 1;
                    q.offer(new int[]{nr, nc});
                }
            }
        }

        // Binary search on safeness factor
        int low = 0, high = 2 * n, ans = 0;

        while (low <= high) {
            int mid = low + (high - low) / 2;

            if (canReach(dist, mid)) {
                ans = mid;
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return ans;
    }

    private boolean canReach(int[][] dist, int safe) {

        int n = dist.length;
        boolean[][] visited = new boolean[n][n];

        Queue<int[]> q = new LinkedList<>();

        if (dist[0][0] < safe) return false;

        q.offer(new int[]{0, 0});
        visited[0][0] = true;

        int[] dr = {1, -1, 0, 0};
        int[] dc = {0, 0, 1, -1};

        while (!q.isEmpty()) {
            int[] cur = q.poll();

            if (cur[0] == n - 1 && cur[1] == n - 1)
                return true;

            for (int k = 0; k < 4; k++) {
                int nr = cur[0] + dr[k];
                int nc = cur[1] + dc[k];

                if (nr >= 0 && nr < n && nc >= 0 && nc < n &&
                    !visited[nr][nc] && dist[nr][nc] >= safe) {

                    visited[nr][nc] = true;
                    q.offer(new int[]{nr, nc});
                }
            }
        }

        return false;
    }
}