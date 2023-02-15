package dnd.danverse.domain.event.service;

import static dnd.danverse.global.exception.ErrorCode.PROFILE_NOT_FOUND;

import dnd.danverse.domain.event.dto.request.EventSavedRequestDto;
import dnd.danverse.domain.event.dto.response.EventSavedResponseDto;
import dnd.danverse.domain.event.entitiy.Event;
import dnd.danverse.domain.event.exception.NoProfileException;
import dnd.danverse.domain.event.repository.EventRepository;
import dnd.danverse.domain.profile.ProfileRepository;
import dnd.danverse.domain.profile.dto.response.ProfileDto;
import dnd.danverse.domain.profile.entity.Profile;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 이벤트를 생성하는 서비스.
 */
@Service
@RequiredArgsConstructor
public class EventService {
  private final EventRepository eventRepository;
  private final ProfileRepository profileRepository;

  /**
   * 등록된 프로필이 있는 사용자에 한해서 글을 등록할 수 있다.
   * @param eventRequest 이벤트 요청 DTO
   * @param userId 등록하려는 사용자 Id
   * @return EventSavedResponseDto 이벤트 응답 DTO
   */
  @Transactional
  public EventSavedResponseDto createEvent(EventSavedRequestDto eventRequest, Long userId) {
    Optional<Profile> profileOptional = profileRepository.findByMember(userId);
    if (profileOptional.isEmpty()) {
      throw new NoProfileException(PROFILE_NOT_FOUND);
    }
    Profile profile = profileOptional.get();
    Event event = eventRepository.save(eventRequest.toEntity(profile));
    return new EventSavedResponseDto(event, new ProfileDto(profile));
  }


}
