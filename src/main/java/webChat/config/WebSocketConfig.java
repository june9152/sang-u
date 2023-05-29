package webChat.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

//Spring의 웹소켓 및 STOMP 프로토콜 설정을 위한 클래스를 정의
@Configuration//@Configuration은 이 클래스가 Spring의 구성 클래스임을 나타내는 어노테이션
@EnableWebSocketMessageBroker // 문자 채팅용
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
//WebSocketMessageBrokerConfigurer 인터페이스는 웹소켓 및 STOMP 메시지 브로커 구성을 위한 메서드를 제공


    // 웹 소켓 연결을 위한 엔드포인트 설정 및 stomp sub/pub 엔드포인트 설정
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // stomp 접속 주소 url => /ws-stomp
        registry.addEndpoint("/ws-stomp") // 연결될 엔드포인트
                .withSockJS(); // SocketJS 를 연결한다는 설정
    }
    //이 메서드는 STOMP 연결을 위한 엔드포인트(웹에서는 URL이 엔드포인트에 해당)를 등록
    //STOMP는 Simple Text Oriented Messaging Protocol의 약자로, 웹소켓을 통해 메시지를 전송하기 위한 프로토콜
    //프로토콜(protocol)이란 컴퓨터 네트워크에서 통신을 수행하는 데 필요한 규칙 또는 표준


    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 메시지를 구독하는 요청 url => 즉 메시지 받을 때
        registry.enableSimpleBroker("/sub");

        // 메시지를 발행하는 요청 url => 즉 메시지 보낼 때
        registry.setApplicationDestinationPrefixes("/pub");
    }
    //이 메서드는 메시지 브로커를 구성////메시지 브로커는 메시지의 발송자(Producer)로부터 메시지를 받아서, 그 메시지를 받아야 하는 수신자(Consumer)에게 전달
    //클라이언트는 "/pub"로 시작하는 주제로 메시지를 발행하고, "/sub"로 시작하는 주제를 구독하여 메시지를 받을 수 있음
    //예를 들어, 클라이언트는 "/pub/chat" 주제로 메시지를 발행할 수 있고, 다른 클라이언트는 "/sub/chat" 주제를 구독하여 chat 관련 메시지를 받을 수 있음
    //웹소켓에서는 "주제(Topic)"라는 개념을 사용하여 메시지를 구분
}
