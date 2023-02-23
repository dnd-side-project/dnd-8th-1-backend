package dnd.danverse.domain.event.service;

import dnd.danverse.domain.event.entitiy.Event;

import dnd.danverse.domain.validation.WriterValidationService;

import org.springframework.stereotype.Service;

/**
 * 이벤트 삭제 복합 서비스.
 */
@Service
public class EventDeleteService {

  private final EventPureService eventPureService;
  private final WriterValidationService<Event> validateEventWriter;

  public EventDeleteService(EventPureService eventPureService, WriterValidationService<Event> validateEventWriter) {
    this.eventPureService = eventPureService;
    this.validateEventWriter = validateEventWriter;
  }

  /**
   * 작성자가 이벤트를 삭제할 수 있습니다.
   *
   * @param eventId 이벤트 고유 Id.
   * @param memberId 삭제 요청 보낸 멤버 Id.
   */
  public void deleteEvent(Long eventId, Long memberId) {
    Event event = validateEventWriter.validateWriter(eventId, memberId);
    eventPureService.deleteEvent(event);
  }

}
