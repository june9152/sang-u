package webChat.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import webChat.dto.ChatRoomMap;
import webChat.service.ChatService.ChatServiceMain;
import webChat.dto.ChatRoomDto;
import webChat.service.social.PrincipalDetails;

import java.util.UUID;


//웹 채팅방과 관련된 요청을 처리하는 컨트롤러 클래스로 채팅방을 생성하거나 방 상세정보를 확인하는 등의 작업을 처리
@Controller
@RequiredArgsConstructor
@Slf4j
public class ChatRoomController {

    // ChatService Bean 가져오기
    private final ChatServiceMain chatServiceMain;

    // 채팅방 생성
    // 채팅방 생성 후 다시 / 로 return
    @PostMapping("/chat/createroom")//
    public String createRoom(@RequestParam("roomName") String name,
                             @RequestParam("roomPwd") String roomPwd,
                             @RequestParam("secretChk") String secretChk,
                             @RequestParam(value = "maxUserCnt", defaultValue = "2") String maxUserCnt,
                             @RequestParam("chatType") String chatType,
                             RedirectAttributes rttr) {

        // log.info("chk {}", secretChk);

        // 매개변수 : 방 이름, 패스워드, 방 잠금 여부, 방 인원수
        ChatRoomDto room;

        room = chatServiceMain.createChatRoom(name, roomPwd, Boolean.parseBoolean(secretChk), Integer.parseInt(maxUserCnt), chatType);//채팅방을 생성하고, 그 결과를 room 변수에 저장


        log.info("CREATE Chat Room [{}]", room);//로그를 남김

        rttr.addFlashAttribute("roomName", room);//rttr.addFlashAttribute("roomName", room): 이 메소드는 room 객체를 리다이렉트 후에도 유지할 수 있도록 Flash Attribute에 추가하는 기능임
        // 이 Flash Attribute는 주로 새로운 페이지로 리다이렉트될 때 한번만 사용되는 데이터를 전달하는데 사용//roomName"이라는 이름으로 room 객체에 접근할 수 있게 됨
        return "redirect:/";
        //리다이렉트란, 클라이언트가 서버에 요청을 보낸 후, 서버에서 클라이언트에게 다른 위치로 이동하라는 지시를 내리는 것//클라이언트에게 루트 경로("/")로의 리다이렉트를 지시
        //그래서 사용자를 애플리케이션의 홈페이지로 다시 돌려보내는 역할을 함
    }

    // 채팅방 입장 화면//채팅방 상세정보를 보여줌
    // 파라미터로 넘어오는 roomId 를 확인후 해당 roomId 를 기준으로
    // 채팅방을 찾아서 클라이언트를 chatroom 으로 보낸다.
    @GetMapping("/chat/room")
    public String roomDetail(Model model, String roomId, @AuthenticationPrincipal PrincipalDetails principalDetails){
        //Model model는 뷰(View)에 전달할 데이터를 담는 모델 객체임

        log.info("roomId {}", roomId);//채팅방 ID를 로그에 출력

        // principalDetails 가 null 이 아니라면 로그인 된 상태!!
        if (principalDetails != null) {
            // 세션에서 로그인 유저 정보를 가져옴
            model.addAttribute("user", principalDetails.getUser());
        }//로그인한 사용자가 있는지 확인하는 부분

        ChatRoomDto room = ChatRoomMap.getInstance().getChatRooms().get(roomId);
        //ChatRoomMap에서 roomId에 해당하는 채팅방의 정보를 가져옴

        model.addAttribute("room", room);
        //모델 객체에 채팅방 정보를 추가하여, 이 정보가 뷰에 전달될 수 있도록 함

        if (ChatRoomDto.ChatType.MSG.equals(room.getChatType())) {
            return "chatroom";
        }else{
            model.addAttribute("uuid", UUID.randomUUID().toString());

            return "rtcroom";//UUID를 모델 객체에 추가한 후, "rtcroom" 뷰를 반환하여 이동하라는 지시를 내림
        }
        //만약 메시지 유형이라면 "chatroom" 뷰를, 아니라면 아래에 설명할 조건에 따라 다른 뷰를 반환

    }

    // 채팅방 비밀번호 확인
    @PostMapping("/chat/confirmPwd/{roomId}")
    @ResponseBody
    public boolean confirmPwd(@PathVariable String roomId, @RequestParam String roomPwd){

        // 넘어온 roomId 와 roomPwd 를 이용해서 비밀번호 찾기
        // 찾아서 입력받은 roomPwd 와 room pwd 와 비교해서 맞으면 true, 아니면  false
        return chatServiceMain.confirmPwd(roomId, roomPwd);
    }

    // // 채팅방 삭제
    // @GetMapping("/chat/delRoom/{roomId}")
    // public String delChatRoom(@PathVariable String roomId){
    //
    //     // roomId 기준으로 chatRoomMap 에서 삭제, 해당 채팅룸 안에 있는 사진 삭제
    //     chatServiceMain.delChatRoom(roomId);
    //
    //     return "redirect:/";
    // }

    // 유저 카운트
    @GetMapping("/chat/chkUserCnt/{roomId}")
    @ResponseBody
    public boolean chUserCnt(@PathVariable String roomId){

        return chatServiceMain.chkRoomUserCnt(roomId);
    }//이 메소드는 전반적으로 채팅방의 사용자 수가 방의 최대 인원 수를 초과하는지 확인하는 역할을 수행하며 만약 사용자 수가 최대 인원 수를 초과한다면 이 메소드는 false를 반환
}
