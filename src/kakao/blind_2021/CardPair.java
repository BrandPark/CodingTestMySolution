package kakao.blind_2021;

import java.util.ArrayList;
import java.util.List;

/*
 * 카카오 2021 블라인드 채용 _ 광고 삽입문제
 * 알고리즘 : 완전탐색
 * 난이도 : 3
 * link : https://programmers.co.kr/learn/courses/30/lessons/72415
 * */
public class CardPair {
    // 4 X 4 격자
    // 카드는 2쌍씩 존재. 즉, 캐릭터카드 짝이 8개 이하가 존재하고 중복되는 캐릭터가 있을 수 있다.
    // 캐릭터카드의 종류는 총 6개이다.
    // 움직이는 방법은 방향키(4개)와 ctrl + 방향키(4개)이다. ctrl을 누르고 움직일 시 해당방향에 존재하는 카드중 제일 가까운 카드의 위치로 이동한다. 방향키는 카드가 없는 위치도 갈 수 있다.
    // ctrl은 키 조작 횟수로 치지 않는다.
    // 카드를 뒤집기 위해서는 enter키가 필요하다. 즉, 카드 두장을 뒤집기 위해서는 최소 2 + a가 필요
    public int solution(int[][] board, int r, int c) {
        // solution :
        // 1) board에 등장한 캐릭터의 종류별 개수와 좌표를 적는다.
        // 2) 1)의 산출물을 사용하여 총 8개 캐릭터카드 쌍의 제거 순서의 경우의 수를 모두 구한다.
        // 3) 제거 순서의 경우의 수 중 하나를 뽑아 차례대로 제거해야 하는데, 이 때 제거할 종류의 카드가 board에 2장 이상이기 때문에 어느것을 먼저 제거할 지 정해야한다.
        // 3-1) 1)의 을 사용하여 카드 종류별 제거 순서의 경우의 수를 정한다.
        // 4) 제거 순서의 경우의 수 중 하나를 뽑아 차례대로 제거한다. 제거할 땐 해당 카드를 3-1)의 결과물에서 가능한 모든 경우의 수를 계산해 답을 구한다.


        // board에 등장하는 캐릭터카드를 인덱스로 좌표를 담는다.
        List<List<Pair>> cardLocations = new ArrayList<>();
        for(int i=0;i<7;i++){
            cardLocations.add(new ArrayList<>());
        }

        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                if(board[i][j] == 0)
                    continue;
                cardLocations.get(board[i][j]).add(new Pair(i,j));
            }
        }

        // 보드에 나온 카드 쌍들의 제거 순서 경우의 수를 모두 구한다.
        int[] countArray = new int[7];

        int cardPairAllCount = 0;
        for(int i=0;i<7;i++){
            int cardPairCount = cardLocations.get(i).size() / 2;
            countArray[i] = cardPairCount;
            cardPairAllCount += cardPairCount;
        }

        List<String> comb = new ArrayList<>();
        StringBuilder tmp = new StringBuilder();
        boolean[] visited = new boolean[7];
        // initAllComb(0, cardPairAllCount, visited, comb);

        return -1;
    }

    private static class Pair{
        private int y, x;
        public Pair(int y, int x){this.y = y; this.x = x;}
    }
}

