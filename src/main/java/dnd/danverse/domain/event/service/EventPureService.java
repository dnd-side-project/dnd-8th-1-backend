package dnd.danverse.domain.event.service;

import static dnd.danverse.global.exception.ErrorCode.EVENT_NOT_FOUND;

import dnd.danverse.domain.event.entitiy.Event;
import dnd.danverse.domain.event.exception.EventNotFoundException;
import dnd.danverse.domain.event.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Event Entity 에 대한 순수 Service
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class EventPureService {

  private final EventRepository eventRepository;

  /**
   * 이벤트 신청 가능 여부 확인
   * 이벤트가 존재하는지 확인하고, 신청 기간이 지났는지 확인한다.
   * @param eventId 이벤트 ID
   * @return 이벤트
   */
  @Transactional(readOnly = true)
  public Event checkAvailable(Long eventId) {
    Event event = getEvent(eventId);

    event.checkIfOverDeadline();
    return event;
  }

  /**
   * 이벤트 존재 여부 확인
   * @param eventId 이벤트 ID
   * @return 이벤트
   */
  private Event getEvent(Long eventId) {
    log.info("이벤트를 찾습니다. eventId: {}", eventId);
    return eventRepository.findById(eventId).orElseThrow(
        () -> new EventNotFoundException(EVENT_NOT_FOUND));
  }

  /**
   * 이벤트 존재 여부 확인
   * @param eventId 이벤트 ID
   * @return 이벤트
   */
  public Event checkIfDeleted(Long eventId) {
    return getEvent(eventId);
   }
   
  /**
   * 이벤트 생성
   * @param event 만들고자 하는 이벤트
   * @return DB에 저장된 Event
   */
  @Transactional
  public Event createEvent(Event event) {
    return eventRepository.save(event);
  }
}
