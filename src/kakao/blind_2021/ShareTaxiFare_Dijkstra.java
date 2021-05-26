package kakao.blind_2021;

import java.util.*;
import java.util.stream.IntStream;

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
    // 다익스트라의 핵심은 최소비용을 계속 업데이트하며 해당 길을 저장해 다음길을 탐색하는 것이다.

    private static List<List<Route>> graph = new ArrayList<>(); // 2차 배열 역할. 인덱스는 지점 번호
    public int solution(int n, int s, int a, int b, int[][] fares) {
        // 지점n을 인덱스로 사용할 수 있도록 graph에 리스트를 n+1개 넣는다.
        for(int i=0;i<=n;i++){
            graph.add(new ArrayList<>());
        }
        // fares의 edge들을 graph에 넣는다.
        for(int[] f : fares){
            graph.get(f[0]).add(new Route(f[1], f[2]));
            graph.get(f[1]).add(new Route(f[0], f[2]));
        }

        // a, b, s 각 지점을 출발지점으로 하여 다른 지점까지의 비용을 담을 배열을 초기화한다.
        int[] startA = IntStream.range(0,n+1).map(i->20000000).toArray();
        int[] startB = IntStream.range(0,n+1).map(i->20000000).toArray();
        int[] startS = IntStream.range(0,n+1).map(i->20000000).toArray();

        startA[a] = 0; startB[b] = 0; startS[s] = 0;

        // 다익스트라 메서드를 통해 각 배열에 해당지점(인덱스)까지의 최소비용을 넣는다.
        dijkstra(a, startA);
        dijkstra(b, startB);
        dijkstra(s, startS);

        // 택시 합승 도착지점(k)에 대하여 D(s->k) + D(a->k) + D(b->k)의 최소합을 구한다.
        int answer = Integer.MAX_VALUE;
        for(int k=1;k<=n;k++){
            int costSum = startS[k] + startA[k] + startB[k];
            answer = Math.min(answer, costSum);
        }

        return answer;
    }

    // p : 출발지점, startP : 지점p 부터 인덱스 지점까지의 최소비용 기록
    private void dijkstra(int p, int[] startP){
        // 지점에서 갈 수 있는 길 중 비용이 적은 길부터 시도해볼 수 있도록 우선순위 큐를 사용한다.
        // 출발지점과 직결된 길들은 최소비용 확정이기 때문에 큐에 넣고 기록한다.
        PriorityQueue<Route> pq = new PriorityQueue<>(graph.get(p));
        for(Route route : graph.get(p)){
            startP[route.endP] = route.cost;
        }

        while(!pq.isEmpty()){
            Route curRoute = pq.poll();
            // 선택한 길의 목적지 까지의 최소비용 기록이 선택한 길의 비용보다 작다면 해당 길은 최소비용 루트에 포함될 수 없다.
            // 이 조건이 없다면 모든 간선의 수만큼 계산을 해야한다.
            // 여기서 route는 출발지점부터 endP까지의 길을 나타내며 cost또한 출발지점부터 endP까지의 비용이다.
            if(startP[curRoute.endP] < curRoute.cost)
                continue;
            // 큐에서 꺼낸 길의 목적지와 연결된 또 다른 길들
            for(Route nextRoute : graph.get(curRoute.endP)){
                // 만약 새로운 길로 가서 도착한 지점이 현재까지의 비용기록보다 이득이라면 업데이트하고 큐에 넣는다.
                if(startP[nextRoute.endP] > startP[curRoute.endP] + nextRoute.cost){
                    startP[nextRoute.endP] = startP[curRoute.endP] + nextRoute.cost;
                    pq.add(new Route(nextRoute.endP, startP[nextRoute.endP]));
                }
            }

        }

    }

    private static class Route implements Comparable<Route> {
        private int endP, cost;
        public Route(int endP, int cost) { this.endP = endP; this.cost = cost; }
        @Override
        public int compareTo(Route r){
            return this.cost - r.cost;
        }
    }
}