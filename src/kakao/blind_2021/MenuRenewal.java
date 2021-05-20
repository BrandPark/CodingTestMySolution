package kakao.blind_2021;

import java.util.*;

/*
* 카카오 2021 블라인드 채용 순위 검색 문제
* 난이도 : 2
* link : https://programmers.co.kr/learn/courses/30/lessons/72412
* */
public class MenuRenewal {
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

    private static HashMap<String, List<Integer>> infoMap = new HashMap<>();
    private static String[] langs = {"cpp", "java", "python", "-"};
    private static String[] poss = {"backend", "frontend", "-"};
    private static String[] careers = {"junior", "senior", "-"};
    private static String[] foods = {"chicken", "pizza", "-"};

    public int[] solution(String[] info, String[] query) {
        int[] answer = new int[query.length];

        //모든 조합의 경우를 infoMap에 담는다.
        for(String l : langs)
            for(String p : poss)
                for(String c : careers)
                    for(String f : foods)
                        infoMap.put(l+p+c+f, new ArrayList<>());

        //for in in info
        for(String in : info){
            in = in.replaceAll("(\\w+) (\\w+) (\\w+) (\\w+)", "$1$2$3$4");
            String[] inArr = in.split(" ");
            //map에서 해당하는 조건을 찾아 점수를 넣는다.
            infoMap.get(inArr[0]).add(Integer.parseInt(inArr[1]));
        }

        //for q in query
        for(int i=0;i<query.length;i++){
            String q = query[i].replaceAll(" and ", "");
            String[] qArr = q.split(" ");
            //infoMap에서 조건에 해당하는 학생들의 점수 리스트를 가져와서 오름차순 정렬한다.
            List<Integer> scoreList = infoMap.get(qArr[0]);
            Collections.sort(scoreList);

            //이진탐색으로 요구하는 점수 이상의 학생 수를 센다.
            int index = Collections.binarySearch(scoreList, Integer.parseInt(qArr[1]));

            index = index < 0 ? Math.abs(index + 1) : index;

            int minValue = scoreList.get(index--);
            while(index >= 0){
                if(scoreList.get(index) < minValue)
                    break;
                minValue = scoreList.get(index);
                index--;
            }

            int result = scoreList.size() - index;

            answer[i] = result;
        }

        return answer;
    }
}