package dnd.danverse.domain.event.service;


import dnd.danverse.domain.event.dto.request.EventSavedRequestDto;
import dnd.danverse.domain.event.dto.response.EventWithProfileDto;
import dnd.danverse.domain.event.entitiy.Event;
import dnd.danverse.domain.profile.dto.response.ProfileSimpleDto;
import dnd.danverse.domain.profile.entity.Profile;
import dnd.danverse.domain.profile.service.ProfilePureService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 이벤트를 생성하는 서비스.
 */
@Service
@RequiredArgsConstructor
public class EventSaveComplexService {

  private final EventPureService eventPureService;
  private final ProfilePureService profilePureService;

  /**
   * 등록된 프로필이 있는 사용자에 한해서 글을 등록할 수 있다.
   * 프로필이 없는 사용자는 글을 등록할 수 없다.
   *
   * @param eventRequest 이벤트 요청 DTO
   * @param memberId     등록하려는 사용자 Id
   * @return EventSavedResponseDto 이벤트 응답 DTO
   */
  public EventWithProfileDto createEvent(EventSavedRequestDto eventRequest, Long memberId) {
    Profile profile = profilePureService.retrieveProfile(memberId);

    Event event = eventPureService.createEvent(eventRequest.toEntity(profile));
    return new EventWithProfileDto(event, new ProfileSimpleDto(profile));
  }


}
