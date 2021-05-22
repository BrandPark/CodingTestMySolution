package kakao.blind_2021;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/*
 * 카카오 2021 블라인드 채용 _ 순위검색 문제
 * 난이도 : 2
 * link : https://programmers.co.kr/learn/courses/30/lessons/72412
 * */
public class RankSearch {
    //개발언어 : cpp, java, python
    //지원직군 : backend, frontend
    //경력구분 : junior, senior
    //소울푸드 : chicken, pizza
    //지원조건에 해당하고 원하는 점수 이상의 지원자가 몇명인지 알아내는 문제
    //1<= info.length <= 50,000
    //info[i] = "개발언어 직군 경력 소울푸드 점수"
    //1<= score <= 100,000
    //1<= query.length <= 100,000
    //query[i] = "개발언어 and 직군 and 경력 and 소울푸드 최소점수", 와일드카드(-) 가능

    private static String[] langs = {"cpp", "java", "python", "-"};
    private static String[] poss = {"backend", "frontend", "-"};
    private static String[] careers = {"junior", "senior", "-"};
    private static String[] foods = {"chicken", "pizza", "-"};
    private static HashMap<String, List<Integer>> lookupMap = new HashMap<>();

    public int[] solution(String[] info, String[] query) {
        //가능한 조합을 모두 lookupMap에 담는다.
        for (String l : langs)
            for (String p : poss)
                for (String c : careers)
                    for (String f : foods)
                        lookupMap.put(l + p + c + f, new ArrayList<>());


        //for in in info
        for (String in : info) {
            //와일드 카드(-)를 사용한 in의 가능한 모든 조합을 map에서 꺼내 점수 리스트에 점수를 넣는다.
            String[] inArr = in.split(" ");
            boolean[] visited = new boolean[4];
            int score = Integer.parseInt(inArr[4]);

            for (int wild = 0; wild <= 4; wild++) {
                putInfo(inArr, visited, 0, wild, 0, score);
            }
        }

        int[] answer = new int[query.length];
        //for q in query
        for (int i = 0; i < query.length; i++) {
            //조건을 키로infoMap에서 점수리스트를 가져온다.
            String q = query[i].replaceAll(" and ", "");
            String[] qArr = q.split(" ");
            List<Integer> scoreList = lookupMap.get(qArr[0]);
            //점수리스트가 비어있다면 0을 넣고 다음 q로 넘어간다.
            if (scoreList.isEmpty()) {
                answer[i] = 0;
                continue;
            }
            //비어있지 않다면 점수리스트를 정렬하고 이진탐색으로 조건의 점수이상의 학생 수를 알아낸다.
            int score = Integer.parseInt(qArr[1]);
            Collections.sort(scoreList);
            int index = Collections.binarySearch(scoreList, score);
            if (index < 0) {
                index = Math.abs(index + 1);
            } else {
                for (int k = index - 1; k >= 0; k--) {
                    if (scoreList.get(k) < score)
                        break;
                    index = k;
                }
            }
            answer[i] = scoreList.size() - index;
        }


        return answer;
    }

    private void putInfo(String[] infos, boolean[] visited, int count, int wild, int index, int score) {
        if (count == wild) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < visited.length; i++) {
                if (visited[i])
                    sb.append("-");
                else
                    sb.append(infos[i]);
            }
            lookupMap.get(sb.toString()).add(score);
            return;
        }
        for (int i = index; i < visited.length; i++) {
            if (visited[i])
                continue;
            visited[i] = true;
            putInfo(infos, visited, count + 1, wild, i + 1, score);
            visited[i] = false;
        }
    }

}