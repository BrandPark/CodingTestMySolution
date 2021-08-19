package kakao.blind_2019;

import org.junit.*;

import java.util.*;

import static org.junit.Assert.*;

/*
 * 카카오 2019 블라인드 채용 _ 후보키
 * 알고리즘 : 단순 구현
 * 난이도 : 2
 * link : https://programmers.co.kr/learn/courses/30/lessons/42890
 * */
public class CandidateKey {
    private static String[][] relation;
    private static Set<List<Integer>> keySet;

    public int solution(String[][] relation) {

        // 속성의 값들을 순회하면서 유일성과 최소성을 만족하는지 찾는다.
        // 속성의 값쌍을 1~컬럼개수 만큼 늘려가면서 찾는다.
        // 유일성을 만족했지만 map에 이미 있는 키가 포함된다면 최소성을 만족하지 못한다.
        this.relation = relation;
        this.keySet = new HashSet<>();

        for(int i=1;i<=relation[0].length;i++){
            findKey(i);
        }

        for (List<Integer> key : keySet) {
            for (Integer col : key) {
                System.out.print(col + " ");
            }
            System.out.println();
        }
        return keySet.size();
    }

    private void findKey(int pickCnt) {
        dfs(pickCnt, 0, 0, new boolean[relation[0].length]);
    }

    private void dfs(int limitCnt, int cnt, int startIdx, boolean[] visited) {
        if(limitCnt == cnt) {
            // 선택된 컬럼 번호대로 row 끝까지 검사해서 유일성 테스트
            // 유일하지 않다면 바로 리턴
            Set<String> setForUniqueTest = new HashSet<>();

            for (int row = 0; row < relation.length; row++) {

                StringBuilder tmp = new StringBuilder();

                for (int col = 0; col < relation[0].length; col++) {
                    if(visited[col]) tmp.append(relation[row][col]);
                }

                if (!setForUniqueTest.add(tmp.toString())) {
                    return;
                }
            }

            // 컬럼 번호 조합을 List 로 만든다
            List<Integer> colList = new ArrayList<>();
            for (int i = 0; i < visited.length; i++) {
                if(visited[i])
                    colList.add(i);
            }

            // keySet 에 해당 키(컬럼조합)가 있는지 확인
            for (List<Integer> key : keySet) {
                if(colList.containsAll(key))
                    return;
            }

            keySet.add(colList);

            return;
        }

        for (int i = startIdx; i < relation[0].length; i++) {
            if (visited[i]) continue;
            visited[i] = true;
            dfs(limitCnt, cnt + 1, i + 1, visited);
            visited[i] = false;
        }
    }

    @Test
    public void setTest() {
        CandidateKey c = new CandidateKey();

        String[][] relation = {
                {"100","ryan","music","2"},
                {"200","apeach","math","2"},
                {"300","tube","computer","3"},
                {"400","con","computer","4"},
                {"500","muzi","music","3"},
                {"600","apeach","music","2"}
        };

        assertEquals(c.solution(relation), 2);
    }
}
