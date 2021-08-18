package kakao.blind_2019;

import org.junit.*;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

/*
 * 카카오 2020 블라인드 채용 _ 오픈채팅방
 * 알고리즘 : 단순 구현
 * 난이도 : 2
 * link : https://programmers.co.kr/learn/courses/30/lessons/42888
 * */
public class OpenChatting {
    public String[] solution(String[] record) {

    List<Message> result = new ArrayList<>();
    Map<String, User> userMap = new HashMap<>();

    for (String r : record) {
        String[] rs = r.split(" ");
        String cmd = rs[0];
        String id = rs[1];
        String username = null;
        if(rs.length > 2)
            username = rs[2];

        if (cmd.equals("Enter")) {
            // userMap 에 등록되어 있다면 User 를 꺼내 username 을 변경하고 result 에 메시지를 넣는다.
            // 신규 유저라면 User 를 만들어 userMap 에 등록하고 result 에 메시지를 넣는다.
            User savedUser = userMap.get(id);
            if (savedUser == null) {
                User user = new User(id, username);
                userMap.put(id, user);
                result.add(new Message(user, 0));
            } else {
                savedUser.username = username;
                result.add(new Message(savedUser, 0));
            }

        } else if (cmd.equals("Change")) {
            // userMap 에 닉네임을 변경한다.
            userMap.get(id).username = username;
        } else {
            // result 에 메시지를 추가한다.
            User user = userMap.get(id);
            result.add(new Message(user, 1));
        }
    }
    return result.stream().map(Message::getMessage).toArray(String[]::new);
}

private static class User {
    String id;
    String username;

    public User(String id, String username) {
        this.id = id;
        this.username = username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        User user = (User) o;
        return this.id.equals(user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

private static class Message {
    User user;
    int state;

    public Message(User user, int state) {
        this.user = user;
        this.state = state;
    }

    public String getMessage() {
        switch (state) {
            case 0:
                return user.username + "님이 들어왔습니다.";
            case 1:
                return user.username + "님이 나갔습니다.";
        }
        return "";
    }
}
    @Test
    public void test() {
        String[] record = {"Enter uid1234 Muzi", "Enter uid4567 Prodo", "Leave uid1234", "Enter uid1234 Prodo", "Change uid4567 Ryan"};
        String[] result = {"Prodo님이 들어왔습니다.", "Ryan님이 들어왔습니다.", "Prodo님이 나갔습니다.", "Prodo님이 들어왔습니다."};

        OpenChatting op = new OpenChatting();

        assertArrayEquals(result, op.solution(record));
    }
}