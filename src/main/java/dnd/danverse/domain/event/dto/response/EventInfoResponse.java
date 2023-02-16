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
  private final Long eventId;
  private final String title;
  private final String location;
  private final EventType type;
  private final String eventImg;
  private final LocalDateTime eventDeadLine;
  private final LocalDateTime eventCreatedAt;

  // 이벤트 주최자
  private final Long profileId;
  private final String profileName;
  private final String profileImg;

  @QueryProjection
   public EventInfoResponse(Long eventId, String title, String location, EventType type, String eventImg, LocalDateTime eventDeadLine, LocalDateTime eventCreatedAt, Long profileId, String profileName, String profileImg) {
      this.eventId = eventId;
      this.title = title;
      this.location = location;
      this.type = type;
      this.eventImg = eventImg;
      this.eventDeadLine = eventDeadLine;
      this.eventCreatedAt = eventCreatedAt;
      this.profileId = profileId;
      this.profileName = profileName;
      this.profileImg = profileImg;
    }
}
