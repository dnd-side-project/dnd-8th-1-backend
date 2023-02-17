package dnd.danverse.domain.matching.dto.request;

import io.swagger.annotations.ApiModelProperty;
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
  @ApiModelProperty(value = "이벤트 고유 ID")
  private Long eventId;

}
