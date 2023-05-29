package webChat.dto;

import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import java.util.LinkedHashMap;
import java.util.Map;


//채팅방의 목록을 관리하는 클래스(?)
// 싱글톤으로 생성
// 모든 ChatService 에서 ChatRooms가 공통된 필요함으로
@Getter
@Setter
public class ChatRoomMap {
    private static ChatRoomMap chatRoomMap = new ChatRoomMap();
    //static이므로 클래스의 어떤 인스턴스에서도 접근 가능

    private Map<String, ChatRoomDto> chatRooms = new LinkedHashMap<>();
    //이 맵은 채팅방의 ID를 키로, 해당 채팅방의 ChatRoomDto 객체를 값으로 가집니다.

    //싱글톤(Singleton) 패턴은 클래스의 인스턴스가 하나만 생성되도록 보장하는 디자인 패턴

//    @PostConstruct
//    private void init() {
//        chatRooms = new LinkedHashMap<>();
//    }

    private ChatRoomMap(){}

    public static ChatRoomMap getInstance(){
        return chatRoomMap;
    }
    //싱글톤 인스턴스에 접근하는 메소드로 이 메소드를 호출하면, 애플리케이션에서 유일한 ChatRoomMap 인스턴스를 반환
}
