package kakao.blind_2021;

import java.util.*;

/*
 * 카카오 2021 블라인드 채용 _ 합승 택시요금 문제
 * 알고리즘 : Dijkstra (다익스트라)
 * 시간복잡도 :
 * 난이도 : 3
 * link : https://programmers.co.kr/learn/courses/30/lessons/72413
 * */
public class ShareTaxiFare_Dijkstra {
    // n개의 지점에서 두 사람은 출발지점(s)에서 출발하여 각각 a, b에 도착해야한다.
    // 중간까지 택시를 같이 타고 갈 수 있으며 목적지까지 드는 택시비용의 합산의 최소값을 구해라.
    // 3<= n <= 200
    // 1<= (s, a, b) <= n <= 200
    // fares[i][j] = {c, d, f}, f: c지점에서 d지점까지의 택시요금, 1<= f <= 100,000
    // 두 지점 간의 택시요금은 1개만 주어진다. (방향을 구분하지 않는다.)
    // a != b
    // 모든 i와 j에 대하여 i->j의 비용의 최소값을 업데이트 후 답을 구한다.
    private static List<List<Edge>> graph = new ArrayList<>();
    public int solution(int n, int s, int a, int b, int[][] fares) {
        // 지점의 수(n)가 인덱스로 사용될 수 있도록 graph에 List<Edge>를 n+1개 넣는다.
        for(int i=0;i<=n;i++){
            graph.add(new ArrayList<>());
        }
        // for f in fares
        // 지점을 인덱스로하여 graph에서 간선리스트를 꺼내 간선을 넣는다.
        for(int[] f : fares){
            Edge e1 = new Edge(f[0], f[1], f[2]);
            Edge e2 = new Edge(f[1], f[0], f[2]);
            graph.get(f[0]).add(e1);
            graph.get(f[1]).add(e2);
        }

        //지점 a, b, s를 출발지점으로하여 다른 지점까지의 최소비용을 담는 배열을 만들고 초기화 한다.
        int[] startA = new int[n+1];
        int[] startB = new int[n+1];
        int[] startS = new int[n+1];

        Arrays.fill(startA, 20000000);
        Arrays.fill(startB, 20000000);
        Arrays.fill(startS, 20000000);

        startA[a] = 0;
        startB[b] = 0;
        startS[s] = 0;

        // 다익스트라알고리즘을 수행한 결과를 각각의 배열에 담는다.
        dijkstra(a, startA);
        dijkstra(b, startB);
        dijkstra(s, startS);

        //D(s->k) + D(k->a) + D(k->b) = sum 의 최솟값을 구한다.
        int answer = Integer.MAX_VALUE;
        for(int k=1;k<=n;k++){
            int sum = startS[k] + startA[k] + startB[k];
            answer = Math.min(sum, answer);
        }

        return answer;
    }

    private void dijkstra(int start, int[] costs){
        // start와 연결되어있는 간선들을 비용이 적은것 부터 나오도록 우선순위 큐에 넣는다.
        PriorityQueue<Edge> pq = new PriorityQueue<>(graph.get(start));
        // 직결된 간선들의 비용은 최소비용이므로 담는다
        for(Edge e : graph.get(start)) {
            costs[e.sp2] = e.cost;
        }

        // 큐에서 간선하나를 꺼내고, 간선에 연결된 지점을 각각 sp1, sp2라 할 때
        // sp2에 연결된 다음간선들과 연결된 또 다른 지점 sp3가 있다.
        // (start -> sp3)의 비용보다 (start -> sp2)+(sp2 -> sp3)의 비용이 작다면 업데이트하고 큐에 다음 간선을 넣는다.
        while(!pq.isEmpty()){
            Edge curEdge = pq.poll();

            // 이미
            if(costs[curEdge.sp2] < curEdge.cost)
                continue;
            for(Edge nextEdge : graph.get(curEdge.sp2)){
                if(costs[nextEdge.sp2] > costs[curEdge.sp2] + nextEdge.cost){
                    costs[nextEdge.sp2] = costs[curEdge.sp2] + nextEdge.cost;
                    pq.add(new Edge(curEdge.sp2, nextEdge.sp2, costs[curEdge.sp2] + nextEdge.cost));
                }

            }

        }

    }
    private static class Edge implements Comparable<Edge> {
        private final int sp1, sp2, cost;
        public Edge(int sp1, int sp2, int cost){
            this.sp1 = sp1;
            this.sp2 = sp2;
            this.cost = cost;
        }
        @Override
        public int compareTo(Edge o){
            return cost - o.cost;
        }
    }
}