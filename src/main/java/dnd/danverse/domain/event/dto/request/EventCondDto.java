package dnd.danverse.domain.event.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 이벤트 필터링 조건을 담는 DTO
 * ModelAttribute 로써 사용되기 때문에 2가지 중 하나의 방법을 선택하면 된다.
 * 1. Setter + NoArgsConstructor
 * 2. Getter + AllArgsConstructor
 */
@Getter
@AllArgsConstructor
public class EventCondDto {

  /**
   * 이벤트 지역
   */
  private String location;

  /**
   * 이벤트 유형 (콜라보, 쉐어)
   */
  private String type;

}