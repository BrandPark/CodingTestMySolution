package kakao.blind_2019;

import java.util.HashSet;
import java.util.Set;

/*
 * 카카오 2019 블라인드 채용 _ 후보키_(비트마스크 알고리즘)
 * 알고리즘 : 단순 구현
 * 난이도 : 2
 * link : https://programmers.co.kr/learn/courses/30/lessons/42890
 * */
public class CandidateKey_Bitmask {
    // 1. 컬럼의 모든 조합을 구한다.
    // 2. 조합이 현재 keySet 의 key 들과 비트연산하여 포함관계인지 확인한다.
    // 3. 조합별로 데이터들을 문자열로 연결하여 set에 넣으면서 유일성을 만족하는지 확인한다.
    // 4. 모두 만족하면 keySet에 추가하고 다시 2번으로 돌아간다.

    private String[][] relation;
    private Set<Integer> keySet;
    private int maxRow;
    private int maxCol;

    public int solution(String[][] relation) {
        this.relation = relation;
        this.keySet = new HashSet<>();
        this.maxRow = relation.length;
        this.maxCol = relation[0].length;

        // 1. set : 조합
        for (int set = 1; set < (1 << maxCol); set++) {
            if(!isMinimal(set))
                continue;

            if (isUnique(set)) {
                keySet.add(set);
            }
        }

        return keySet.size();
    }

    private boolean isUnique(int set) {
        Set<String> setForUniqueTest = new HashSet<>();

        for (int row = 0; row < maxRow; row++) {

            StringBuilder sb = new StringBuilder();
            for (int th = 0; th < maxCol; th++) {

                if((set & (1 << th)) != 0)
                    sb.append(relation[row][th]);
            }

            if (!setForUniqueTest.add(sb.toString())) {
                return false;
            }
        }

        return true;
    }

    private boolean isMinimal(int set) {
        for(int key : keySet) {
            if((key & set) == key)
                return false;
        }
        return true;
    }
}