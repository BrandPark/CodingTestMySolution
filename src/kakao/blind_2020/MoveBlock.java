package kakao.blind_2020;

import org.junit.*;

import static org.junit.Assert.*;

import java.util.*;

/*
 * 카카오 2020 블라인드 채용 _ 블럭 이동하기
 * 알고리즘 : bfs
 * 난이도 : 3
 * link : https://programmers.co.kr/learn/courses/30/lessons/60063
 * */
public class MoveBlock {
    private int n;
    private Set<Robot> visited;
    private int[][] board;

    public int solution(int[][] board) {
        this.n = board.length;
        this.visited = new HashSet<>();
        this.board = board;

        return bfs();
    }

    public int bfs() {
        LinkedList<Robot> q = new LinkedList<>();
        q.add(new Robot(0, 0, 0, 1, 0));
        visited.add(new Robot(0, 0, 0, 1, 0));

        while (!q.isEmpty()) {
            Robot cur = q.removeFirst();

            if (cur.isGoal()) {
                return cur.sec;
            }

            List<Robot> child = cur.getChildren();
            for (Robot r : child) {
                if (r.isValid() && !visited.contains(r)) {
                    q.add(r);
                    visited.add(r);
                }
            }
        }
        return -1;
    }

    private class Robot {
        private int y1, x1, y2, x2, sec;

        public Robot(int y1, int x1, int y2, int x2, int sec) {
            this.y1 = y1;
            this.x1 = x1;
            this.y2 = y2;
            this.x2 = x2;
            this.sec = sec;
        }

        public boolean isGoal() {
            return (y1 == n - 1 && x1 == n - 1) || (y2 == n - 1 && x2 == n - 1);
        }

        public List<Robot> getChildren() {
            List<Robot> children = new ArrayList<>();
            boolean isHorizontal = Math.abs(x2 - x1) == 1;

            int nextSec = this.sec + 1;

            // 가로방향
            if (isHorizontal) {
                // 위에 공간이 있다면
                if (y1 - 1 >= 0 && board[y1 - 1][x1] == 0 && board[y2 - 1][x2] == 0) {
                    children.add(new Robot(y1, x1, y1 - 1, x1, nextSec));   // (y1, x1) 축 상단 회전
                    children.add(new Robot(y2 - 1, x2, y2, x2, nextSec));   // (y2, x2) 축 상단 회전
                    children.add(new Robot(y1 - 1, x1, y2 - 1, x2, nextSec));   // 상단 이동
                }

                // 아래에 공간이 있다면
                if (y1 + 1 < n && board[y1 + 1][x1] == 0 && board[y2 + 1][x2] == 0) {
                    children.add(new Robot(y1, x1, y1 + 1, x1, nextSec));   // (y1, x1) 축 하단 회전
                    children.add(new Robot(y2 + 1, x2, y2, x2, nextSec));   // (y2, x2) 축 하단 회전
                    children.add(new Robot(y1 + 1, x1, y2 + 1, x2, nextSec));   // 하단 이동
                }

                // 왼쪽으로 이동
                if (x1 - 1 >= 0  && x2 - 1 >= 0 && board[y1][x1 - 1] == 0 && board[y2][x2 - 1] == 0) {
                    children.add(new Robot(y1, x1 - 1, y2, x2 - 1, nextSec));
                }

                // 오른쪽으로 이동
                if (x1 + 1 < n && x2 + 1 < n && board[y1][x1 + 1] == 0 && board[y2][x2 + 1] == 0) {
                    children.add(new Robot(y1, x1 + 1, y2, x2 + 1, nextSec));
                }
            }
            // 세로방향
            else {
                // 왼쪽에 공간이 있다면
                if (x1 - 1 >= 0 && board[y1][x1 - 1] == 0 && board[y2][x2 - 1] == 0) {
                    children.add(new Robot(y1, x1, y1, x1 - 1, nextSec));   // (y1, x1) 축 좌단 회전
                    children.add(new Robot(y2, x2 - 1, y2, x2, nextSec));   // (y2, x2) 축 좌단 회전
                    children.add(new Robot(y1, x1 - 1, y2, x2 - 1, nextSec));   // 왼쪽으로 이동
                }

                // 오른쪽에 공간이 있다면
                if (x1 + 1 < n && board[y1][x1 + 1] == 0 && board[y2][x2 + 1] == 0) {
                    children.add(new Robot(y1, x1, y1, x1 + 1, nextSec));   // (y1, x1) 축 우단 회전
                    children.add(new Robot(y2, x2 + 1, y2, x2, nextSec));   // (y2, x2) 축 우단 회전
                    children.add(new Robot(y1, x1 + 1, y2, x2 + 1, nextSec));   // 오른쪽으로 이동
                }

                // 위로 이동
                if (y1 - 1 >= 0 && y2 - 1 >= 0 && board[y1 - 1][x1] == 0 && board[y2 - 1][x2] == 0) {
                    children.add(new Robot(y1 - 1, x1, y2 - 1, x2, nextSec));
                }
                // 아래로 이동
                if (y1 + 1 < n && y2 + 1 < n && board[y1 + 1][x1] == 0 && board[y2 + 1][x2] == 0) {
                    children.add(new Robot(y1 + 1, x1, y2 + 1, x2, nextSec));
                }
            }

            return children;
        }

        public boolean isValid() {
            return (board[y1][x1] == 0) && (board[y2][x2] == 0)
                    && !isOutOfBound(y1, x1) && !isOutOfBound(y2, x2);
        }

        public boolean isOutOfBound(int y, int x) {
            return y < 0 || y >= n || x < 0 || x >= n;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Robot robot = (Robot) o;
            return (y1 == robot.y1 && x1 == robot.x1 && y2 == robot.y2 && x2 == robot.x2) ||
                    (y1 == robot.y2 && x1 == robot.x2 && y2 == robot.y1 && x2 == robot.x1);
        }

        @Override
        public int hashCode() {
            return Objects.hash(y1, x1, y2, x2) + Objects.hash(y2, x2, y1, x1);
        }
    }

    @Test
    public void test() {
        int[][] board = {
                {0, 0, 0, 1, 1},
                {0, 0, 0, 1, 0},
                {0, 1, 0, 1, 1},
                {1, 1, 0, 0, 1},
                {0, 0, 0, 0, 0}
        };

        int[][] board2 = {
                {0, 0},
                {0, 0}
        };
        int result = 7;

        MoveBlock mb = new MoveBlock();

        assertEquals(result, mb.solution(board));
        assertEquals(1, mb.solution(board2));
        assertEquals(new Robot(1, 1, 2, 2, 0), new Robot(2, 2, 1, 1, 1));
        HashSet<Robot> set = new HashSet<>();
        set.add(new Robot(1, 1, 0, 0, 1));
        assertTrue(set.contains(new Robot(0, 0, 1, 1, 0)));
    }
}