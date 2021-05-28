package kakao.blind_2021;
import java.util.Arrays;

/*
 * 카카오 2021 블라인드 채용 _ 광고 삽입문제
 * 알고리즘 : 단순 구현
 * 시간복잡도 :
 * 난이도 : 3
 * link : https://programmers.co.kr/learn/courses/30/lessons/72414
 * */

public class AdInsertion {
    // 사람들이 가장 많이 보는 구간에 광고 삽입
    // 재생시간 = 끝나는 시간 - 시작시간
    // play_time, adv_time = HH:MM:SS,
    // 0 <= HH <= 99, 0 <= MM <= 59, 0 <= MM <= 59
    // 사람들이 가장 많이 보는 구간에 광고 삽입
    // 재생시간 = 끝나는 시간 - 시작시간
    // play_time, adv_time = HH:MM:SS,
    // 0 <= HH <= 99, 0 <= MM <= 59, 0 <= MM <= 59
    // 1<= logs.length <= 300,000
    // 초 단위로 시청하고있는 사람들을 count해야함. adv_time을 움직이면서 매번 검사하는 것은 매우 비효율적이므로 초단위로 사람들 수를 Array에 담은 후 검사하는게 효율적.
    public String solution(String play_time, String adv_time, String[] logs) {

        return "";
    }
    private int parseSecond(String time){
        int[] timeArr = Arrays.stream(time.split(":")).mapToInt(Integer::parseInt).toArray();
        return (timeArr[0] * 60 * 60) + (timeArr[1] * 60) + timeArr[2];
    }
}