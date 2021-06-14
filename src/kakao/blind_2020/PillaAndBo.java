package kakao.blind_2020;

import java.util.*;

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
    // 겹치도록 설치하는 경우와, 없는 구조물을 삭제하는 경우는 없다.
    // 기둥은 바닥 위에 있거나 보의 한쪽 끝 부분 위에 있거나, 다른 기둥 위에 있어야 한다.
    // 보는 한쪽 끝 부분이 기둥 위에 있거나, 양쪽 끝 부분이 다른 보와 동시에 연결되어 있어야 한다.
    // 작업을 수행한 결과가 조건을 만족하지 않으면 작업은 무시된다.
    // answer은 order by x asc, y asc, 기둥 우선
    private static int BO = 1, PILLAR = 0;
    private static int INST = 1, DEL = 0;
    public int[][] solution(int n, int[][] build_frame) {
        // n까지 인덱스로 가질수 있는 map[][]을 만든다.
        int[][] map = new int[n+1][n+1];
        for(int[] m : map){
            Arrays.fill(m, -1);
        }

        // build_frame에서 하나 꺼낸다.
        for(int[] bf : build_frame){
            int x = bf[0], y = bf[1], what = bf[2], how = bf[3];

            // 설치
            if(how == INST) {
                if(isSure(y, x, what, map)){
                    if(map[y][x] > -1){
                        map[y][x] |= what;
                    }else{
                        map[y][x] = what;
                    }
                }
            }
            // 삭제
            else {
                // 기둥
                if(what == PILLAR) {
                    // 제거하고
                    if(map[y][x] > -1) {
                        map[y][x] ^= what+1;
                        // y+1, x에 있는 것이 괜찮은지 확인한다.
                        // 괜찮지 않다면 다시 돌려 놓는다.
                        if(!isSure(y+1, x, what, map)){
                            map[y][x] |= what+1;
                        }
                    }
                }
                // 보
                else {
                    // 제거하고
                    if(map[y][x] > -1){
                        map[y][x] ^= what+1;
                        // y, x-1과 y, x+1에 있는 것이 모두 괜찮은지 확인한다.
                        //괜찮지 않다면 다시 돌려 놓는다.
                        if(!isSure(y, x-1, what, map) || !isSure(y, x+1, what, map))
                            map[y][x] |= what+1;
                    }
                }
            }
        }

        // map을 순회하며 answer을 완성한다.
        List<int[]> answer = new ArrayList<>();
        for(int y = 0; y<=n;y++){
            for(int x = 0; x<=n;x++){
                if(map[y][x] != -1){
                    int[] arch = new int[]{x, y, map[y][x]};
                    answer.add(arch);
                }
            }
        }
        Collections.sort(answer, new Comparator<int[]>(){
            @Override
            public int compare(int[] o1, int[] o2){
                if(o1[0] == o2[0]){
                    if(o1[1] == o2[1]){
                        return o1[2] - o2[2];
                    }
                    return o1[1] - o2[1];
                }
                return o1[0] - o2[0];
            }
        });

        return answer.toArray(int[][] :: new);
    }
    private boolean isSure(int y, int x, int what, int[][] map){
        int n = map.length - 1;
        if(what == BO+1){
            // y-1, x 또는 y-1, x+1에 기둥이 있거나, y, x-1과 y,x+1에 보가 있어야한다.
            if(((!outOfBound(y-1, x, n) && isPillar(y-1,x,map))
                    || (!outOfBound(y-1,x+1,n) && isPillar(y-1,x+1,map)))
                    || ((!outOfBound(y,x-1,n) && isBo(y,x-1,map))
                    && (!outOfBound(y,x+1,n) && isBo(y,x+1,map))))
                return true;
            return false;
        }
        else {
            // y-1,x에 기둥이 또는 바닥, 또는 y, x-1에 보가 있어야한다.
            if(y == 0 || isPillar(y-1,x,map) || (!outOfBound(y, x-1, n) && isBo(y,x-1,map)))
                return true;
            return false;
        }
    }

    private boolean isBo(int y, int x, int[][] map){
        return (map[y][x] ^ (BO+1)) < 3;
    }
    private boolean isPillar(int y, int x, int[][] map){
        return (map[y][x] ^ (PILLAR+1)) < 3;
    }
    private boolean outOfBound(int y, int x, int n){
        return y<0||y>n||x<0||x>n;
    }
}