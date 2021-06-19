package kakao.blind_2020;

/*
 * 카카오 2020 블라인드 채용 _ 외벽 점검
 * 알고리즘 : 단순 구현
 * 난이도 : 3
 * link : https://programmers.co.kr/learn/courses/30/lessons/60062
 * */
public class CheckWall {
    // 외벽의 길이 n, 1<= n <= 200
    // 취약지점 위치 배열 weak, 1<= weak.length <= 15,
    // 취약지점 k는 0<= k <= n-1, k는 시계방향으로의 거리
    // 각 친구가 1시간 동안 이동가능한 거리 배열 dist, 1<= dist.length <= 100
    // 취약 지점을 점검하기 위해 보내야 하는 친구 수의 최소값 return
    // 취약 지점을 전부 점검할 수 없는 경우에는 -1리턴
    // solution : 출발 지점부터 시계 또는 반시계 방향으로 외벽을 따라 이동하여 취약지점을 전부 점검하기 위해 보내야 하는 친구의 최소 수를 구해야한다.
    //              출발 지점은 마음대로이며, 자신이 가는 길의 취약점은 모두 점검 가능하다.
    public int solution(int n, int[] weak, int[] dist) {
        int answer = 0;
        return answer;
    }
}
