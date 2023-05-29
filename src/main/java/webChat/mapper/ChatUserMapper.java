package webChat.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import webChat.Entity.ChatUser;
import webChat.dto.ChatUserDto;

//MapStruct 라이브러리는 개발자가 정의한 매퍼 인터페이스에 따라 구현체를 자동으로 생성
//"매퍼(Mapper)"는 일반적으로 한 형식의 데이터를 다른 형식으로 변환하는 객체나 함수를 가리키는 용어

//ChatUser 엔티티와 ChatUserDto 데이터 전송 객체(DTO) 간의 변환을 담당
//즉, 이 매퍼를 사용하면 데이터베이스로부터 받아온 ChatUser 엔티티를 클라이언트에게 전달하기 위한 ChatUserDto로, 또는 그 반대로 변환할 수 있음//componentModel = "spring" 파라미터는 스프링 프레임워크가 이 매퍼를 자동으로 빈으로 등록하도록 지시
@Mapper(componentModel = "spring")//ChatUserMapper가 MapStruct 라이브러리의 매퍼임을 나타냄
public interface ChatUserMapper {
    ChatUserMapper INSTANCE = Mappers.getMapper(ChatUserMapper.class);//ChatUserMapper 인터페이스가 webChat.mapper 패키지에 속하도록 설정
    //INSTANCE는 외부에서 ChatUserMapper를 사용할 때 이용
    //Mappers.getMapper(Class) 메서드는 이렇게 생성된 구현체를 가져오는 역할을 함
    // 따라서 Mappers.getMapper(ChatUserMapper.class)는 ChatUserMapper 인터페이스의 구현체를 반환하는 코드


    ChatUserDto toDto(ChatUser e);
    ChatUser toEntity(ChatUserDto d);
    // 이 구현 클래스의 인스턴스는 실제로 ChatUser와 ChatUserDto 간의 변환을 수행하는 toDto와 toEntity 메서드를 가지고 있음
    
}
