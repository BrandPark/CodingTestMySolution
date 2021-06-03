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
        int answer = Integer.MAX_VALUE;
        for (int spLen = 1; spLen <= s.length() / 2 + 1; spLen++) {
//            answer = Math.min(answer, zipString("", s, spLen, 1).length());
            answer = Math.min(answer, zipString2(s, 1, spLen).length());
        }

        return answer;
    }

    private String zipString2(String s, int count, int spLen) {
        if (s.length() < spLen) {
            return s;
        }
        String preString = s.substring(0, spLen);
        String postString = s.substring(spLen);

        if (!postString.startsWith(preString)) {
            if (count > 1)
                return count + preString + zipString2(postString, 1, spLen);
            return preString + zipString2(postString, 1, spLen);
        }
        return zipString2(postString, count + 1, spLen);
    }

    private String zipString(String pre, String post, int spLen, int count) {
        // post가 pre보다 짧으면 tmp에 count + pre 와 post를 넣고 tmp.length()리턴
        if (post.length() < pre.length()) {
            if (count > 1)
                return count + pre + post;
            else
                return pre + post;
        }

        // post가 pre로 시작하면
        if (!pre.equals("") && post.startsWith(pre)) {
            return zipString(pre, post.substring(spLen), spLen, count + 1);
        }

        // post가 pre로 시작하지 않으면
        else {
            if (count > 1)
                return count + pre + zipString(post.substring(0, spLen), post.substring(spLen), spLen, 1);
            else
                return pre + zipString(post.substring(0, spLen), post.substring(spLen), spLen, 1);
        }
    }
}