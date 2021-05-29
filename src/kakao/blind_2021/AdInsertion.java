package kakao.blind_2021;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

/*
 * 카카오 2021 블라인드 채용 _ 광고 삽입문제
 * 알고리즘 : 단순 구현
 * 난이도 : 3
 * link : https://programmers.co.kr/learn/courses/30/lessons/72414
 * */

public class AdInsertion {
    // 사람들이 가장 많이 보는 구간에 광고 삽입
    // 재생시간 = 끝나는 시간 - 시작시간
    // play_time, adv_time = HH:MM:SS,
    // 0 <= HH <= 99, 0 <= MM <= 59, 0 <= MM <= 59
    // 1<= logs.length <= 300,000
    // 초 단위로 시청하고있는 사람들을 count해야함. adv_time을 움직이면서 매번 검사하는 것은 매우 비효율적이므로 초단위로 사람들 수를 Array에 담은 후 검사하는게 효율적.
    public String solution(String play_time, String adv_time, String[] logs) {
        // 플레이 시간과 광고 시간 초단위로 변경
        int playSec = parseSecond(play_time);
        int advSec = parseSecond(adv_time);

        int[] countArray = new int[playSec+1];
        // for log in logs
        // 로그하나씩 초로 변경 후 인덱스로 사용해 시작시간을 +1, 종료시간을 -1 한다.
        for(String log : logs){
            String[] time = log.split("-");
            int startSec = parseSecond(time[0]);
            int endSec = parseSecond(time[1]);

            countArray[startSec]++;
            countArray[endSec]--;
        }

        // countArray에 누적합을 구해 다시 넣으면 해당 초에 몇명이 보고있는지 알 수 있다.
        for(int i=0;i<countArray.length-1;i++){
            countArray[i+1] += countArray[i];
        }

        // 이제 countArray에서 광고길이만큼 뽑았을 때의 합이 가장 큰 start지점을 찾아야한다. 문제의 답은 advSec 크기 만큼의 구간을 뽑아 보고있는 사람들의 시청시간을 모두 합해 가장 큰 구간을 찾아야 한다. 이 방법은 두 가지가 있다.

        //방법 1 : 직접 advSec는 움직이면서 가장 합이 큰 구간의 시작 점이 우리가 구하는 답이다.
        // 이때 특징이 있는데 다음 구간은 이전 구간에서 시작지점만큼의 인원을 빼고 다음 구간에서의 종료지점만큼의 인원을 더하기만 하면 된다.

        // 방법 2 : countArray를 한번 더 누적합을 수행하면 countArray[i] 는 0초부터 i초까지 영상을 본 사람들의 초를 모두 합한 것이다.
        // 따라서 광고시작시간을 s, 끝나는 시간을 e라 할 때 countArray[e] - countArray[s-1] 의 합이 가장 큰 s를 찾으면 된다.

        // 여기서는 방법 1로 하겠다.
        // 0부터 시작했을 때의 구간합으로 sum 초기화\
        // 최대 36만 크기의 countArray의 구간 합이기 때문에 타입을 long으로 한다.
        long sum = 0;
        for(int i=0;i<advSec;i++){
            sum += countArray[i];
        }

        // 구간을 이동시키면서 구간합의 최대를 찾는다.
        int advStart = 1;
        int advEnd = advSec;
        long maxSum = sum;
        int answer = 0;
        while(advEnd <= playSec) {
            sum = sum - countArray[advStart-1] + countArray[advEnd];
            if(maxSum < sum){
                maxSum = sum;
                answer = advStart;
            }
            advStart++;
            advEnd++;
        }

        return secToString(answer);
    }
    private String secToString(int sec){
        int hh = sec / 60 / 60;
        int MM = sec / 60 % 60;
        int ss = sec % 60 % 60;

        return String.format("%02d:%02d:%02d", hh,MM,ss);
    }
    private int parseSecond(String time){
        int[] timeArr = Arrays.stream(time.split(":")).mapToInt(Integer::parseInt).toArray();
        return (timeArr[0] * 60 * 60) + (timeArr[1] * 60) + timeArr[2];
    }
}