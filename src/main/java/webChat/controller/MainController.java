package webChat.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import webChat.service.ChatService.ChatServiceMain;
import webChat.service.ChatService.MsgChatService;
import webChat.service.social.PrincipalDetails;

//사용자가 웹사이트에 처음 접속했을 때 보게 될 페이지에 대한 요청을 처리
@Controller
@RequiredArgsConstructor
@Slf4j
public class MainController {

    private final ChatServiceMain chatServiceMain;

    // 채팅 리스트 화면
    // / 로 요청이 들어오면 전체 채팅룸 리스트를 담아서 return

    // 스프링 시큐리티의 로그인 유저 정보는 Security 세션의 PrincipalDetails 안에 담긴다
    // 정확히는 PrincipalDetails 안에 ChatUser 객체가 담기고, 이것을 가져오면 된다.
    @GetMapping("/")
    public String goChatRoom(Model model, @AuthenticationPrincipal PrincipalDetails principalDetails){
        // "/"로 접속했을 때 실행되는 메소드로 사용자에게 보여줄 페이지의 모델을 만들고, 페이지 이름을 반환

        model.addAttribute("list", chatServiceMain.findAllRoom());
        //ChatServiceMain 서비스의 findAllRoom() 메소드를 호출하여 모든 채팅방의 목록을 가져와 모델에 추가(이 정보는 템플릿 엔진에서 사용할 수 있음)

        // principalDetails 가 null 이 아니라면 로그인 된 상태!!
        if (principalDetails != null) {
            // 세션에서 로그인 유저 정보를 가져옴
            model.addAttribute("user", principalDetails.getUser());
            log.debug("user [{}] ",principalDetails);
        }//로그인된 사용자가 있는지 확인//만약 로그인된 사용자가 있다면, 그 사용자의 정보를 가져와 모델에 추가

//        model.addAttribute("user", "hey");
        log.debug("SHOW ALL ChatList {}", chatServiceMain.findAllRoom());
        return "roomlist";//최종적으로 "roomlist"라는 이름의 뷰를 반환하며 이는 실제로는 템플릿 엔진에 의해 "/templates/roomlist.html"로 변환
    }

}
