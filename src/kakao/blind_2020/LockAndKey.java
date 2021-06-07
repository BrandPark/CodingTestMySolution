package kakao.blind_2020;

import java.util.Arrays;

public class LockAndKey {
    // 자물쇠 N * N, 키 M * M
    // 3<= M <= N <= 20
    // key는 회전가능하고 lock의 홈을 다 채울 수 있다면 열 수 있다고 판단한다.
    // 홈 : 0, 돌기 : 1;
    public boolean solution(int[][] key, int[][] lock) {
        int n = lock.length;
        int m = key.length;

        // 1. 자물쇠의 홈의 개수를 센다.
        int hom = 0;
        for (int[] lo : lock) {
            for (int lo2 : lo) {
                if (lo2 == 0) hom++;
            }
        }

        // 2. 홈의 개수가 0이라면 true 리턴
        if (hom == 0)
            return true;

        // 3. lock의 테두리에 m-1칸 만큼의 두께로 홈을 만든다. map[2m-2+n][2m-2+n]
        int[][] expandLock = new int[2 * m - 2 + n][2 * m - 2 + n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                expandLock[i + m - 1][j + m - 1] = lock[i][j];
            }
        }

        // 4. key를 90도씩 회전시켜 map에 키를 맞춰본다.
        // (y, x) : expandLock에서의 key시작 위치
        // (k, t) : key에서의 격자 위치
        for (int rotateCount = 0; rotateCount < 4; rotateCount++) {
            // 4-1. key 회전
            key = rotate90Key(key, m);

            // 4-2. expandLock에서의 key 시작위치 y,x
            for (int y = 0; y <= m + n - 2; y++) {
                for (int x = 0; x <= m + n - 2; x++) {

                    // 4-3. key에서의 격자 위치 k,t
                    int correctCount = 0;
                    Loop1:
                    for (int k = 0; k < m; k++) {
                        for (int t = 0; t < m; t++) {
                            // 4-4. 자물쇠와 열쇠 모두 돌기라면 key를 움직인다.
                            if (expandLock[y + k][x + t] + key[k][t] == 2)
                                break Loop1;
                            // 4-5. 격자의 위치가 자물쇠안에 있고, 자물쇠의 홈이 키의 돌기와 만나면 count한다.
                            if (!isOutOfLock(y + k, x + t, m, n) && expandLock[y + k][x + t] == 0 && key[k][t] == 1)
                                correctCount++;
                            // 4-6. count가 자물쇠의 총 홈의 개수와 같아지면 true를 리턴한다.
                            if (correctCount == hom)
                                return true;
                        }
                    }
                }
            }
        }

        // 6. 90도씩 회전시킨 4개의 키가 모두 안 맞다면 false를 리턴한다.
        return false;
    }

    private boolean isOutOfLock(int y, int x, int m, int n) {
        return y < m - 1 || y > m + n - 2 || x < m - 1 || x > m + n - 2;
    }

    private int[][] rotate90Key(int[][] key, int m) {
        int[][] rotate = new int[m][m];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < m; j++) {
                rotate[j][m - 1 - i] = key[i][j];
            }
        }

        return rotate;
    }
}