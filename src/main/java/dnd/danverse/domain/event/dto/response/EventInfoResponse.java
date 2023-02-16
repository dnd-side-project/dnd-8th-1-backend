package dnd.danverse.domain.event.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import dnd.danverse.domain.event.entitiy.EventType;
import java.time.LocalDateTime;

import lombok.Getter;

/**
 * 필터링된 이벤트 전체 조회시 사용되는 응답 DTO
 */
@Getter
public class EventInfoResponse {

  // 이벤트
  private final Long id;
  private final String title;
  private final String location;
  private final EventType type;
  private final String imgUrl;
  private final LocalDateTime deadline;
  private final LocalDateTime createdAt;
  private final ProfileSimpleInfo profile;

  // 이벤트 주최자
  @Getter
  static class ProfileSimpleInfo {
    private final Long id;
    private final String name;
    private final String imgUrl;

    @QueryProjection
    public ProfileSimpleInfo(Long id, String name, String imgUrl) {
      this.id = id;
      this.name = name;
      this.imgUrl = imgUrl;
    }
  }

  @QueryProjection
   public EventInfoResponse(Long eventId, String title, String location, EventType type, String eventImg, LocalDateTime eventDeadline, LocalDateTime eventCreatedAt, Long profileId, String profileName, String profileImg) {
      this.id = eventId;
      this.title = title;
      this.location = location;
      this.type = type;
      this.imgUrl = eventImg;
      this.deadline = eventDeadline;
      this.createdAt = eventCreatedAt;
      this.profile = new ProfileSimpleInfo(profileId, profileName, profileImg);
    }
}
