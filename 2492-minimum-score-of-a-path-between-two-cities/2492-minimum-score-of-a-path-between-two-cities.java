class Solution {
    public int minScore(int n, int[][] roads) {
        List<int[]>[]graph=new ArrayList[n+1];
        for(int i=1;i<=n;i++){
            graph[i]=new ArrayList<>();
        }
        for(int []r:roads){
            graph[r[0]].add(new int[]{r[1],r[2]});
            graph[r[1]].add(new int[]{r[0],r[2]});
        }
        boolean visited[]=new boolean[n+1];
        int ans=Integer.MAX_VALUE;
        Queue<Integer>q=new ArrayDeque<>();
        q.offer(1);
        visited[1]=true;
        while(!q.isEmpty()){
            int u=q.poll();
            for(int[] e:graph[u]){
                int v=e[0];
                int w=e[1];
                ans=Math.min(ans,w);
                if(!visited[v]){
                    visited[v]=true;
                    q.offer(v);
                }
            }
        }
        return ans;
    }
}