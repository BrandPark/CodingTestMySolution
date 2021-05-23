package kakao.blind_2021;

/*
 * 카카오 2021 블라인드 채용 _ 합승 택시요금 문제
 * 알고리즘 : Floyd Warshall (플로이드 와샬)
 * 시간복잡도 : O(n^3)
 * 난이도 : 3
 * link : https://programmers.co.kr/learn/courses/30/lessons/72413
 * */
public class ShareTaxiFare_FloydWarshall {
//     n개의 지점에서 두 사람은 출발지점(s)에서 출발하여 각각 a, b에 도착해야한다.
//     중간까지 택시를 같이 타고 갈 수 있으며 목적지까지 드는 택시비용의 합산의 최소값을 구해라.
//     3<= n <= 200
//     1<= (s, a, b) <= n <= 200
//     fares[i][j] = {c, d, f}, f: c지점에서 d지점까지의 택시요금, 1<= f <= 100,000
//     두 지점 간의 택시요금은 1개만 주어진다. (방향을 구분하지 않는다.)
//     a != b
//     모든 i와 j에 대하여 i->j의 비용의 최소값을 업데이트 후 답을 구한다.
    public int solution(int n, int s, int a, int b, int[][] fares) {
        // edges[i][j] = {i -> j로 가는 비용}
        // n=200이고 f의 최댓값은 10만이므로 edges[i][j]의 최대값은 199 * 10만이다.
        // edges[i][j]를 int의 최대값으로 설정하면 더하는 과정에서 오류가 발생하므로 적절한 값을 넣어준다.
        int[][] edges = new int[n+1][n+1];
        for(int i=1;i<=n;i++){
            for(int j=1;j<=n;j++){
                if(i != j)
                    edges[i][j] = 20000000;
            }
        }

        // fares의 원소들을 edges에 담는다.
        for(int[] f : fares){
            edges[f[0]][f[1]] = f[2];
            edges[f[1]][f[0]] = f[2];
        }

        // 지점i에서 지점j로 갈때 경유지점점(k)을 거쳐가는게 비용이 적다면 업데이트한다.
        for(int k=1;k<=n;k++){
            for(int i=1;i<=n;i++){
                for(int j=1;j<=n;j++){
                    if(edges[i][j] > edges[i][k] + edges[k][j]){
                        edges[i][j] = edges[i][k] + edges[k][j];
                        edges[j][i] = edges[i][k] + edges[k][j];
                    }
                }
            }
        }

        // D(s->k) + D(k->a) + D(k->b)의 최소값을 구한다.
        int answer = Integer.MAX_VALUE;
        for(int k=1;k<=n;k++){
            answer = Math.min(answer, edges[s][k] + edges[k][a] + edges[k][b]);
        }

        return answer;
    }
}
