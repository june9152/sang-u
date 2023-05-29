package webChat.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;


//GenericMapper 인터페이스는 일반적인 매퍼의 역할을 정의하는 공통 인터페이스임.D (DTO) 타입과 E (Entity) 타입의 매핑을 위한 메소드를 제공
public interface GenericMapper<D, E> {
    D toDto(E e);
    //이 메소드는 Entity (E 타입)를 DTO (D 타입)로 변환(실제 구현은 이 인터페이스를 구현하는 클래스에서 제공)

    E toEntity(D d);
    //이 메소드는 DTO (D 타입)를 Entity (E 타입)로 변환


//    /* 기존에 생성되어 있는 Entity 를 업데이트 하고 싶을 때 null 이 아닌 값만 업데이트 가능!!
//    *
//    * 메서드의 파라미터는 총 2개,
//    * @MappingTarget : 변환하여 객체를 return 하는 것이 아닌 인자로 받아 업데이트할 target 을 설정
//    * @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE) : Source 의 필드가 null 일 때 정책으로 null 값은 무시
//    * */
//    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
//    void of(D dto, @MappingTarget E entity);
}
