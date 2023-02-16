package dnd.danverse.domain.event.dto.response;

import dnd.danverse.domain.event.entitiy.Event;
import dnd.danverse.domain.profile.dto.response.ProfileDto;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EventDetailResponseDto {

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

  @Builder
  public EventDetailResponseDto(Event event, ProfileDto profile) {
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
