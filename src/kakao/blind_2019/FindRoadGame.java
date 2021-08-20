package kakao.blind_2019;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
 * 카카오 2019 블라인드 채용 _ 길 찾기 게임
 * 알고리즘 : 이진 트리 생성
 * 난이도 : 3
 * link : https://programmers.co.kr/learn/courses/30/lessons/42892
 * */
public class FindRoadGame {
    private static int nodeCnt;
    private static int depth = 0;

    public int[][] solution(int[][] nodeinfo) {
        this.nodeCnt = nodeinfo.length;
        // NodeList 생성
        List<Node> nodeList = new ArrayList<>();

        for(int i=0;i<nodeinfo.length;i++) {
            nodeList.add(new Node(nodeinfo[i][1], nodeinfo[i][0], i+1));
        }

        // NodeList y 기준 내림차순 정렬
        Collections.sort(nodeList);

        // root 꺼내고 나머지 Node를 순회하며 root 부터 타고내려가며 트리 완성
        Node root = nodeList.get(0);

        for(int i=1;i<nodeList.size();i++) {
            Node child = nodeList.get(i);
            bindNode(root, child);
        }

        int[][] result = new int[2][nodeCnt];

        // 전위 순회
        preOrder(root, result[0]);
        depth = 0;
        // 후위 순회
        postOrder(root, result[1]);

        return result;
    }

    private void preOrder(Node node, int[] result) {
        if(node == null || depth == result.length)
            return;
        result[depth++] = node.num;
        preOrder(node.left, result);
        preOrder(node.right, result);
    }

    private void postOrder(Node node, int[] result) {
        if(node == null || depth == result.length)
            return;
        postOrder(node.left, result);
        postOrder(node.right, result);
        result[depth++] = node.num;
    }

    private void bindNode(Node root, Node child) {
        if(child.x < root.x) {
            if(root.left != null) {
                bindNode(root.left, child);
            } else {
                root.left = child;
            }
        } else {
            if(root.right != null) {
                bindNode(root.right, child);
            } else {
                root.right = child;
            }
        }
    }

    private static class Node implements Comparable<Node> {

        private int y, x, num;
        private Node left, right;

        public Node(int y, int x, int num) {
            this.y = y;
            this.x = x;
            this.num = num;
        }

        @Override
        public int compareTo(Node n) {
            return n.y - y;
        }
    }
}
