package dnd.danverse.domain.matching.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 이벤트 id를 전달하기 위한 DTO.
 */
@Getter
@NoArgsConstructor
public class EventIdRequestDto {

  /**
   * 이벤트 id
   */
  private Long eventId;

}
