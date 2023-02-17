package dnd.danverse.domain.event.dto.request;

import dnd.danverse.domain.common.Image;
import dnd.danverse.domain.common.TeamType;
import dnd.danverse.domain.event.entitiy.Event;
import dnd.danverse.domain.event.entitiy.EventType;
import dnd.danverse.domain.profile.entity.Profile;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 이벤트 데이터 관련 요청 DTO.
 * - 이벤트 타입(콜라보, 쉐어)
 * - 이벤트 제목
 * - 모집 유형(팀, 개인)
 * - 모집 인원
 * - 모집 마감 기한
 * - 활동 지역
 * - 이벤트 상세설명
 * - 이벤트 이미지 url
 */
@Getter
@NoArgsConstructor
public class EventSavedRequestDto {
  @ApiModelProperty(value = "이벤트 타입 ex) 콜라보, 쉐어")
  private String type;
  @ApiModelProperty(value = "이벤트 제목")
  private String title;
  @ApiModelProperty(value = "이벤트 모집 유형 ex) 팀, 개인")
  private String recruitType;
  @ApiModelProperty(value = "이벤트 모집 인원")
  private int recruitCount;
  @ApiModelProperty(value = "이벤트 모집 마감 기한")
  private LocalDateTime deadline;
  @ApiModelProperty(value = "이벤트 활동 지역")
  private String location;
  @ApiModelProperty(value = "이벤트 상세설명")
  private String description;
  @ApiModelProperty(value = "이벤트 이미지 url")
  private String imgUrl;



  public Event toEntity(Profile profile) {
    return Event.builder()
        .eventType(EventType.of(this.type))
        .title(this.title)
        .profile(profile)
        .recruitType(TeamType.of(this.recruitType))
        .recruitCount(this.recruitCount)
        .deadline(this.deadline)
        .location(this.location)
        .description(this.description)
        .eventImg(new Image(this.imgUrl))
        .build();
  }




}
