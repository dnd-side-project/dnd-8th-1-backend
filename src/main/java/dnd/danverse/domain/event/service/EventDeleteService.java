package dnd.danverse.domain.event.service;

import dnd.danverse.domain.event.entitiy.Event;
import dnd.danverse.domain.matching.service.EventWriterValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 이벤트 삭제 복합 서비스.
 */
@Service
@RequiredArgsConstructor
public class EventDeleteService {

  private final EventPureService eventPureService;
  private final EventWriterValidationService eventWriterValidationService;

  /**
   * 작성자가 이벤트를 삭제할 수 있습니다.
   *
   * @param eventId 이벤트 고유 Id.
   * @param memberId 삭제 요청 보낸 멤버 Id.
   */
  public void deleteEvent(Long eventId, Long memberId) {
    eventWriterValidationService.validateEventWriter(eventId, memberId);
    Event event = eventPureService.checkIfDeleted(eventId);
    eventPureService.deleteEvent(event);
  }

}
