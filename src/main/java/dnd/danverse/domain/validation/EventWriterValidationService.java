package dnd.danverse.domain.validation;

import static dnd.danverse.global.exception.ErrorCode.EVENT_MATCH_NOT_WRITER;

import dnd.danverse.domain.event.entitiy.Event;
import dnd.danverse.domain.event.service.EventPureService;
import dnd.danverse.domain.matching.exception.NotEventWriterException;
import dnd.danverse.domain.profile.entity.Profile;
import dnd.danverse.domain.profile.service.ProfilePureService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 이벤트 작성자 와 API 요청자가 동일한지 검증하는 서비스.
 * WriterValidationService 인터페이스를 구현합니다.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class EventWriterValidationService implements WriterValidationService<Event> {

  private final EventPureService eventPureService;
  private final ProfilePureService profilePureService;

  @Override
  public Event getTarget(Long targetId) {
    return eventPureService.getEvent(targetId);
  }

  @Override
  public Profile getTargetProfile(Event target) {
    return target.getProfile();
  }


  @Override
  public Profile retrieveProfile(Long memberId) {
    return profilePureService.retrieveProfile(memberId);
  }

  @Override
  public void throwException() {
    throw new NotEventWriterException(EVENT_MATCH_NOT_WRITER);
  }
}
