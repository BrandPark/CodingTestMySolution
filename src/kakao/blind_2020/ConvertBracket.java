package kakao.blind_2020;

/*
 * 카카오 2020 블라인드 채용 _ 괄호 변환
 * 알고리즘 : 단순 구현
 * 난이도 : 2
 * link : https://programmers.co.kr/learn/courses/30/lessons/60058
 * */
public class ConvertBracket {
    // '('개수와 ')'의 개수가 같다면 균형잡힌 괄호 문자열
    // '('와 ')'가 균형잡혔고 짝도 맞다면 올바른 괄호 문자열
    // 2 <= p <= 1000
    // 좌괄호와 우괄호의 개수는 항상 같다.

    public String solution(String p) {
        return getCorrectBracket(p);
    }
    private String getCorrectBracket(String w){
        // 1. 입력이 빈 문자열인 경우 빈 문자열 반환
        if(w.length() == 0) return "";

        // 2. 문자열 w를 두 "균형잡힌 괄호 문자열" u, v로 분리. 단, u는 더 이상 분리될 수 없어야 한다.
        int idx = getCorrectBracketIndex(w);
        String u = w.substring(0,idx+1);
        String v = w.substring(idx+1);

        if(isCorrectBracket(u)){
            return u + getCorrectBracket(v);
        }
        else{
            StringBuilder ret = new StringBuilder();
            ret.append("(").append(getCorrectBracket(v)).append(")");
            ret.append(getReverseBracket(u.substring(1, u.length()-1)));
            return ret.toString();
        }

    }
    private String getReverseBracket(String w){
        char[] chars = w.toCharArray();
        StringBuilder tmp = new StringBuilder();
        for(int i=0;i<chars.length;i++){
            if(chars[i] == '(') tmp.append(")");
            else tmp.append("(");
        }
        return tmp.toString();
    }

    private boolean isCorrectBracket(String w){
        return w.startsWith("(");
    }

    private int getCorrectBracketIndex(String w){
        char[] chars = w.toCharArray();
        int left = 0, right = 0;
        for(int i=0;i<chars.length;i++){
            if(chars[i] == '(') left++;
            else right++;
            if(left == right)
                return i;
        }
        return -1;
    }
}
