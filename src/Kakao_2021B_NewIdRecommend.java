
/*
* 카카오 2021 블라인드 채용 _ 신규 아이디 추천
* link : https://programmers.co.kr/learn/courses/30/lessons/72410
* */
public class Kakao_2021B_NewIdRecommend {
    public String solution(String new_id) {
        //1. 모든 대문자를 대응되는 소문자로 치환.
        new_id = new_id.toLowerCase();

        //2. 불가능한 문자 모두 제거
        new_id = new_id.replaceAll("[^a-z0-9-_.]","");

        //3. 마침표가 연속된 부분을 하나로 변경
        new_id = new_id.replaceAll("\\.\\.+", ".");

        //4. 마침표가 처음이나 끝에 위치한다면 제거
        new_id = new_id.replaceAll("^\\.|\\.$","");

        //5. 빈 문자열이라면 'a'를 대입한다.
        if(new_id.equals(""))
            new_id = "a";

        //6. 길이가 16이상이면, 첫 15개 문자를 제외한 나머지 문자를 모두 제거.
        //   제거 후 마침표가 끝에 위치한다면 마침표 제거
        if(new_id.length() >= 16){
            new_id = new_id.substring(0,15);
            if(new_id.charAt(14) == '.')
                new_id = new_id.substring(0,14);
        }

        StringBuilder answer = new StringBuilder(new_id);
        //7. 길이가 2이하라면, 마지막 문자를 길이가 3이 될 때까지 반복해서 붙인다.
        if(answer.length() <= 2){
            char endChar = answer.charAt(answer.length() - 1);
            while(answer.length() < 3){
                answer.append(endChar);
            }
        }

        return answer.toString();

    }
}
