package dnd.danverse.domain.event.service;

import dnd.danverse.domain.event.dto.response.EventSavedResponseDto;
import dnd.danverse.domain.event.entitiy.Event;
import dnd.danverse.domain.profile.dto.response.ProfileDto;
import dnd.danverse.domain.profile.entity.Profile;
import dnd.danverse.domain.profile.service.ProfilePureService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 이벤트를 조회하는 서비스.
 */
@Service
@RequiredArgsConstructor
public class EventDetailComplexService {
  private final EventPureService eventPureService;
  private final ProfilePureService profilePureService;

  /**
   * 이벤트의 id를 통해서 상세조회를 할 수 있습니다.
   *
   * @param eventId     조회하려는 이벤트 Id
   * @return EventSavedResponseDto 이벤트 응답 DTO
   */
  public EventSavedResponseDto getEvent(Long eventId) {
    Event event = eventPureService.getDetailEvent(eventId);
    Profile profile = profilePureService.retrieveProfile(event.getProfile().getId());
    return new EventSavedResponseDto(event, new ProfileDto(profile));

  }

}
