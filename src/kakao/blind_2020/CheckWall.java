package kakao.blind_2020;

/*
 * 카카오 2020 블라인드 채용 _ 외벽 점검
 * 알고리즘 : 단순 구현
 * 난이도 : 3
 * link : https://programmers.co.kr/learn/courses/30/lessons/60062
 * */
public class CheckWall {
    // 1 <= n <= 200
    // 1 <= weak.length <= 15, 오름차순, 0이상 n-1 이하의 정수
    // 1 <= dist.length <= 8, 1이상 100이하 자연수
    // 모두 투입해도 불가능하면 -1 리턴
    // return : 모두 점검 가능한 최소 인원 수

    private int n, answer;
    private int[] weak, dist, weakSpread;
    private boolean finish;

    public int solution(int n, int[] weak, int[] dist) {
        this.n = n;
        this.answer = -1;
        this.weak = weak;
        this.dist = dist;
        this.weakSpread = new int[2 * n];

        initSpreadWeak();

        for (int i = 1; i <= dist.length; i++) {
            permutation(i, 0, new boolean[dist.length], new int[i]);
        }

        return answer;
    }

    private void permutation(int limit, int depth, boolean[] visit, int[] dist2) {
        if (finish) {
            return;
        }
        if (limit == depth) {
            check(dist2);
            return;
        }
        for (int i = 0; i < dist.length; i++) {
            if (!visit[i]) {
                visit[i] = true;
                dist2[depth] = dist[i];
                permutation(limit, depth + 1, visit, dist2);
                visit[i] = false;
            }
        }
    }

    private void check(int[] dist2) {
        for (int startIdx = 0; startIdx < weak.length; startIdx++) {
            int endIdx = startIdx + weak.length - 1;
            if (endIdx >= weakSpread.length) {
                answer = -1;
                finish = true;
                return;
            }

            int cur = weak[startIdx];
            for (int d : dist2) {
                cur += d;
                if (cur >= weakSpread[endIdx]) {
                    answer = dist2.length;
                    finish = true;
                    return;
                }
                for (int t = startIdx; t <= endIdx; t++) {
                    if(weakSpread[t] > cur){
                        cur = weakSpread[t];
                        break;
                    }
                }
            }
        }
    }

    private void initSpreadWeak() {
        for (int i = 0; i < weakSpread.length; i++) {
            int idx = i;
            if (i >= weak.length) {
                idx %= weak.length;
                weakSpread[i] = weak[idx] + n;
            } else {
                weakSpread[i] = weak[idx];
            }
        }
    }
}