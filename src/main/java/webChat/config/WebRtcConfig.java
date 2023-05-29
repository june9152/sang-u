package webChat.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;
import webChat.rtc.SignalHandler;



// Spring Framework의 웹소켓을 설정하고 웹RTC(Web Real-Time Communication) 기능을 구성하는 클래스를 정의
@Configuration
@EnableWebSocket // 웹 소켓에 대해 자동 설정
@RequiredArgsConstructor
public class WebRtcConfig implements WebSocketConfigurer {
    /* TODO WebRTC 관련 */
    private final SignalHandler signalHandler;
    //SignalHandler는 웹소켓 메시지를 처리하는 데 사용될 것


    // signal 로 요청이 왔을 때 아래의 WebSockerHandler 가 동작하도록 registry 에 설정
    // 요청은 클라이언트 접속, close, 메시지 발송 등에 대해 특정 메서드를 호출한다
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(signalHandler, "/signal")
                .setAllowedOrigins("*");
    }
    //registerWebSocketHandlers 메서드는 WebSocketConfigurer 인터페이스의 메서드로, 웹소켓 핸들러를 등록하는 데 사용
    //여기서는 "/signal"이라는 URL 경로에 SignalHandler 인스턴스를 웹소켓 핸들러로 등록하고, 모든 출처("*")로부터의 웹소켓 연결을 허용하도록 설정


    // 웹 소켓에서 rtc 통신을 위한 최대 텍스트 버퍼와 바이너리 버퍼 사이즈를 설정한다?
    @Bean
    public ServletServerContainerFactoryBean createWebSocketContainer() {
        ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
        container.setMaxTextMessageBufferSize(8192);
        container.setMaxBinaryMessageBufferSize(8192);
        return container;
    }
    //ServletServerContainerFactoryBean은 서버 측 웹소켓 컨테이너를 설정하는 데 사용되는 FactoryBean임
    //여기서는 텍스트 메시지 버퍼 크기와 바이너리 메시지 버퍼 크기를 각각 8192바이트로 설정

}
