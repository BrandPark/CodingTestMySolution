package kakao.blind_2020;

/*
 * 카카오 2020 블라인드 채용 _ 기둥과 보
 * 알고리즘 : 단순 구현
 * 난이도 : 3
 * link : https://programmers.co.kr/learn/courses/30/lessons/60061
 * */
public class PillaAndBo {
    // 5<= n <= 100
    // 1<= build_frame.length <= 1000, 설치또는 삭제될 기둥과 보의 범위
    // build_frame[0].length = 4
    // build_frame[i][j] = {x, y, a, b}
    // (y,x) : 삭제할 교차점의 좌표,
    // a : 0은 기둥, 1은 보
    // b : 0은 삭제, 1은 설치
    // 구조물의 삭제 또는 설치는 (y,x)에서 보는 오른쪽, 기둥은 위쪽 방향으로 한다.
    // 바닥에 보를 설치하는 경우는 없다.
    public int[][] solution(int n, int[][] build_frame) {
        int[][] answer = {};
        return answer;
    }
}
