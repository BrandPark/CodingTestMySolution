package kakao.blind_2020;

/*
 * 카카오 2020 블라인드 채용 _ 문자열 압축
 * 알고리즘 : 구현
 * 난이도 : 2
 * link : https://programmers.co.kr/learn/courses/30/lessons/60057
 * */
public class StringZip {
    public int solution(String s) {
        // 1 <= s.length() <= 1,000
        // s는 알파벳 소문자
        // 앞에서 부터 잘라서 압축
        // solution : 자르는 단위는 1부터 s의 길이 만큼이기 때문에 모두 해보아야 한다. 즉, 완전 탐색이다. 문제의 특징은 자르는 단위를 정했다면 문자열 s를 앞에서부터 단위 개수의 문자열을 뽑고 그 뒤의 문자열의 앞에서부터 같은 문자열로 시작하는지 검사한다는 것이다. 검사하는 방식이 동일 하기 때문에 재귀호출 방식(백 트래킹)으로 풀 수 있다.
        int answer = 0;
        for(int spLen=1; spLen<s.length();spLen++){
            answer = Math.min(answer, zipString("", s, spLen, 1, new StringBuilder()));
        }

        return answer;
    }

    private int zipString(String pre, String post, int spLen, int count, StringBuilder sb){
        if(post.length() < pre.length()){
            String s = count > 1 ? count + post : post;
            sb.append(s);
            return sb.length();
        }
        if(post.startsWith(pre)){
            zipString(pre, post.substring(spLen) , spLen, count+1, sb);
        }
        else {
            if(count > 1){
                sb.append(count + pre);
            }
            else{
                sb.append(pre);
            }
            zipString(post.substring(0,spLen), post.substring(spLen), spLen, 1, sb);
        }
        return sb.length();
    }
}