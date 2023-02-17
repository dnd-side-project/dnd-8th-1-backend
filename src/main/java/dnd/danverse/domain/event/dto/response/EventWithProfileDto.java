package dnd.danverse.domain.event.dto.response;

import dnd.danverse.domain.event.entitiy.Event;
import dnd.danverse.domain.profile.dto.response.ProfileDto;
import java.time.LocalDateTime;
import lombok.Builder;
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

  private Long id;
  private ProfileDto profile;
  private String type;
  private String title;
  private String recruitType;
  private int recruitCount;

  private LocalDateTime deadline;
  private String location;
  private String description;
  private String imgUrl;

  /**
   * 이벤트 작성 responseDto. 이벤트 상세조회 responseDto.
   *
   * @param event 작성한 이벤트 글
   * @param profile 작성자 프로필
   */
  @Builder
  public EventWithProfileDto(Event event, ProfileDto profile) {
    this.id = event.getId();
    this.profile = profile;
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
