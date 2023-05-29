package webChat.dto;

import lombok.*;


// WebRTC 연결 시 사용되는 클래스
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WebSocketMessage {
    private String from; // 보내는 유저 UUID
    private String type; // 메시지 타입
    private String data; // roomId
    private Object candidate; // 상태
    //ICE(Interactive Connectivity Establishment) 후보자에 대한 정보를 담는 객체임
    //ICE는 WebRTC에서 사용되는 기술로,두 클라이언트 간의 최적의 통신 경로를 찾는 데 사용

    private Object sdp; // sdp 정보
    //세션 설명 프로토콜(Session Description Protocol) 정보를 담는 객체임. 
    //SDP는 미디어 세션의 속성을 설명하는 데 사용되는 포맷임
}
