package dnd.danverse.domain.event.dto.response;

import dnd.danverse.domain.event.entitiy.Event;
import dnd.danverse.domain.profile.dto.response.ProfileSimpleDto;
import dnd.danverse.domain.profile.entity.Profile;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 이벤트 데이터를 전달하기 위한 DTO.
 * - 프로필(id, name, imgUrl)
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
public class EventWithProfileDto {

  @ApiModelProperty(value = "이벤트 고유 ID")
  private Long id;
  @ApiModelProperty(value = "이벤트 주최자 프로필 정보")
  private ProfileSimpleDto profile;
  @ApiModelProperty(value = "이벤트 타입 ex) 콜라보, 쉐어")
  private String type;
  @ApiModelProperty(value = "이벤트 제목")
  private String title;
  @ApiModelProperty(value = "이벤트 모집 유형 ex) 댄스팀, 댄서")
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


  /**
   * 이벤트 데이터 업데이트 후, 업데이트 된 결과를 반환.
   *
   * @param event 이벤트
   * @param profile 프로필
   */
  public EventWithProfileDto(Event event, Profile profile) {
    this.id = event.getId();
    this.profile = new ProfileSimpleDto(profile);
    this.type = event.getEventType().getType();
    this.title = event.getTitle();
    this.recruitType = event.getRecruitType().getType();
    this.recruitCount = event.getRecruitCount();
    this.deadline = event.getDeadline();
    this.location = event.getLocation();
    this.description = event.getDescription();
    this.imgUrl = event.getEventImg().getImageUrl();
  }
}
