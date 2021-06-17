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
    private static int[][] map;
    public int[][] solution(int n, int[][] build_frame) {
        // n까지 인덱스로 가질수 있는 map[][]을 만든다.
        map = new int[n+1][n+1];

        // build_frame에서 하나 꺼낸다.
        for(int[] bf : build_frame){
            int x = bf[0], y = bf[1], isBo = bf[2], isSet = bf[3];

            // 설치
            if(isSet == 1){
                // 설치가능하다면 설치
                // 비트연산자를 활용하여, 보는 10, 기둥은 01로 한다.
                if(isSure(y, x, isBo+1))  {
                    if(map[y][x] == 0)
                        map[y][x] = isBo + 1;
                    else
                        map[y][x] |= isBo + 1;
                }
            }
            // 삭제
            else {
                // 삭제할게 없다면 다음으로,
                if(map[y][x] == 0)
                    continue;

                // 기둥 삭제 시, 기둥이 있다면
                if (isBo == 0 && isGi(y, x)){

                    // 삭제를 한다.
                    map[y][x] ^= isBo+1;

                    // (y+1,x), (y+1, x-1)의 구조물에 이상이 있다면 rollback
                    if(!outOfBound(y+1, x, n))
                        if(!isSure(y+1, x, map[y+1][x])){
                            map[y][x] |= isBo+1;
                            continue;
                        }
                    if(!outOfBound(y+1, x-1, n))
                        if(!isSure(y+1, x-1, map[y+1][x-1])){
                            map[y][x] |= isBo+1;
                            continue;
                        }

                }

                // 보 삭제 시
                else if(isBo == 1 && isBo(y, x)){
                    // 삭제를 한다.
                    map[y][x] ^= isBo+1;

                    // x, x-1, x+1의 구조물에 이상이 있다면 rollback
                    if(!outOfBound(y, x, n))
                        if(!isSure(y, x, map[y][x])){
                            map[y][x] |= isBo+1;
                            continue;
                        }
                    if(!outOfBound(y, x-1, n))
                        if(!isSure(y, x-1, map[y][x-1])){
                            map[y][x] |= isBo+1;
                            continue;
                        }
                    if(!outOfBound(y, x+1, n))
                        if(!isSure(y, x+1, map[y][x+1])){
                            map[y][x] |= isBo+1;
                            continue;
                        }
                }
            }
        }

        // map을 순회하며 answer을 완성한다.
        List<int[]> answer = new ArrayList<>();
        for(int y = 0; y<=n;y++){
            for(int x = 0; x<=n;x++){
                if(map[y][x] == 1 || map[y][x] == 2){
                    answer.add(new int[]{x, y, map[y][x] - 1});
                }
                else if(map[y][x] == 3){
                    answer.add(new int[]{x, y, 0});
                    answer.add(new int[]{x, y, 1});
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

    private boolean isSure(int y, int x, int what){
        int n = map.length - 1;

        switch(what){
            case 0:
                return true;
            case 1:
                return isSureGi(y, x, n);
            case 2:
                return isSureBo(y, x, n);
            case 3:
                return isSureBo(y, x, n) && isSureGi(y, x, n);
        }

        return false;
    }
    private boolean isSureBo(int y, int x, int n){
        // 한쪽 끝 부분이 기둥(01)위에 있거나, 양쪽 끝에 보가 있다면, 참
        if(!outOfBound(y-1, x, n))
            if(isGi(y-1, x))
                return true;
        if(!outOfBound(y-1, x+1, n))
            if(isGi(y-1, x+1))
                return true;
        if(!outOfBound(y, x-1, n) && !outOfBound(y, x+1, n))
            return (isBo(y, x-1) && isBo(y, x+1));
        return false;
    }
    private boolean isSureGi(int y, int x, int n){
        // 밑에 무언가 있어야한다.
        if(y==0)
            return true;
        if(!outOfBound(y, x-1, n))
            if(isBo(y, x-1))
                return true;
        if(!outOfBound(y, x, n))
            if(isBo(y, x))
                return true;
        if(!outOfBound(y-1, x, n))
            return isGi(y-1, x);
        return false;
    }

    private boolean isGi(int y, int x){
        return map[y][x] > 0 && (map[y][x] ^ 1) < map[y][x];
    }

    private boolean isBo(int y, int x){
        return map[y][x] > 0 && (map[y][x] ^ 2 ) < map[y][x];
    }

    private boolean outOfBound(int y, int x, int n){
        return y < 0 || y > n || x < 0 || x > n;
    }
}