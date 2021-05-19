package kakao.blind_2021;

import java.util.*;

/*
* 카카오 2021 블라인드 채용 메뉴 리뉴얼 문제
* 난이도 : 1
* link : https://programmers.co.kr/learn/courses/30/lessons/72411
* */
public class MenuRenewal {
    public String[] solution(String[] orders, int[] course) {
        //손님이 가장 많이 함께 주문한 단품메뉴들을 코스요리 메뉴로 구성
        //코스요리 메뉴는 최소 2가지 이상의 단품으로 구성
        // 최소 2명이상의 손님으로부터 주문된 단품조합만 코스요리 후보
        // 2<= orders.length <= 10
        // 2<= orders[i].size() <= 10, 대문자, 같은 알파벳 중복x
        // 1<= course.length <= 10
        // 2<= course[i] <= 10, 오름차순 정렬, 같은 값 중복x
        // @Return : 오름차순 정렬, 조건에 맞는 메뉴 구성이 여러 개라면 모두 담는다.

        List<String> answer = new ArrayList<>();

        //for n in course
        for(int n : course){
            HashMap<String, Integer> map = new HashMap<>();
            //for order in orders
            for(String order : orders){
                //order에서 n만큼 고르는 조합의 경우들을 map : HashMap<String, Integer>에 담는다.
                //Integer: 개수, String: n개로 이루어진 단품 조합
                char[] orderChars = order.toCharArray();
                Arrays.sort(orderChars);
                boolean[] visited = new boolean[orderChars.length];
                dfs(n, 0, 0, orderChars, visited, map);
            }
            //map에서 Value(개수)가 가장 높은 Key를 뽑고, 같은 개수를 가진 모든 key를 answer에 추가
            List<Map.Entry<String, Integer>> entryList = new LinkedList<>(map.entrySet());
            Collections.sort(entryList, (o1, o2) -> o2.getValue().compareTo(o1.getValue()));

            int max = 0;
            for(Map.Entry<String, Integer> entry : entryList){
                if(max <= entry.getValue() && entry.getValue() >= 2){
                    max = entry.getValue();
                    answer.add(entry.getKey());
                    continue;
                }
                break;
            }
        }

        //answer 사전 순(오름차순) 정렬
        Collections.sort(answer);
        String[] ans = new String[answer.size()];
        answer.toArray(ans);
        return ans;
    }

    private void dfs(int n, int count, int index, char[] chars, boolean[] visited, HashMap<String, Integer> map){
        if(count == n){
            StringBuilder tmp = new StringBuilder();
            for(int i=0;i<visited.length;i++){
                if(visited[i]){
                    tmp.append(chars[i]);
                }
            }
            map.put(tmp.toString(), map.getOrDefault(tmp.toString(), 0) + 1);
            return;
        }

        for(int i=index;i<chars.length;i++) {
            if (!visited[i]) {
                visited[i] = true;
                dfs(n, count + 1, i + 1, chars, visited, map);
                visited[i] = false;
            }
        }
    }
}
