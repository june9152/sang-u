package webChat.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import webChat.dto.WebSocketMessage;
import webChat.service.ChatService.RtcChatService;


// "/webrtc/usercount" 경로로 요청이 들어오면, 웹소켓 메시지를 처리하고, 사용자의 수를 반환하는 기능을 제공(?)
@RestController
@RequiredArgsConstructor
@Slf4j
public class RtcController {

    private final RtcChatService rtcChatService;

    @PostMapping("/webrtc/usercount")
    public String webRTC(@ModelAttribute WebSocketMessage webSocketMessage) {
        //요청 본문에 있는 데이터를 WebSocketMessage 객체로 자동 변환하여 파라미터로 제공

        log.info("MESSAGE : {}", webSocketMessage.toString());
        return Boolean.toString(rtcChatService.findUserCount(webSocketMessage));
    }


}

