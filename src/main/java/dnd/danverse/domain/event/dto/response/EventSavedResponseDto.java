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
public class EventSavedResponseDto {

  private ProfileDto profile;
  private String type;
  private String title;
  private String recruitType;
  private int recruitCount;

  private LocalDateTime deadline;
  private String location;
  private String description;
  private String imgUrl;


  @Builder
  public EventSavedResponseDto(Event event, ProfileDto profile) {
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
