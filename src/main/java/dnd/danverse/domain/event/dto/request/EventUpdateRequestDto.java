package dnd.danverse.domain.event.dto.request;

import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 이벤트 수정 요청 DTO.
 */
@Getter
@NoArgsConstructor
public class EventUpdateRequestDto {

  @ApiModelProperty(value = "이벤트 고유 ID")
  private Long id;
  @ApiModelProperty(value = "이벤트 게시글 제목")
  private String title;
  @ApiModelProperty(value = "이벤트 활동 지역")
  private String location;
  @ApiModelProperty(value = "이벤트 타입 ex) 콜라보, 쉐어")
  private String type;
  @ApiModelProperty(value = "이벤트 이미지 url")
  private String imgUrl;
  @ApiModelProperty(value = "이벤트 모집 유형 ex) 댄스팀, 댄서")
  private String recruitType;
  @ApiModelProperty(value = "이벤트 상세 설명")
  private String description;
  @ApiModelProperty(value = "이벤트 모집 인원")
  private Integer recruitCount;
  @ApiModelProperty(value = "이벤트 모집 마감 기한")
  private LocalDateTime deadline;

}
