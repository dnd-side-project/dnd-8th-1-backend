package dnd.danverse.domain.event.service;

import static dnd.danverse.global.exception.ErrorCode.EVENT_NOT_FOUND;

import dnd.danverse.domain.event.dto.request.EventUpdateRequestDto;
import dnd.danverse.domain.event.entitiy.Event;
import dnd.danverse.domain.event.exception.EventNotFoundException;
import dnd.danverse.domain.event.repository.EventRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Event Entity 에 대한 순수 Service.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class EventPureService {

  private final EventRepository eventRepository;

  /**
   * 이벤트 신청 가능 여부 확인
   * 이벤트가 존재하는지 확인하고, 신청 기간이 지났는지 확인한다.
   *
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
   * 이벤트 존재 여부 확인.
   *
   * @param eventId 이벤트 ID
   * @return 이벤트
   */
  @Transactional(readOnly = true)
  public Event getEvent(Long eventId) {
    log.info("이벤트를 찾습니다. eventId: {}", eventId);
    return eventRepository.findById(eventId).orElseThrow(
        () -> new EventNotFoundException(EVENT_NOT_FOUND));
  }

  /**
   * 이벤트 존재 여부 확인.
   *
   * @param eventId 이벤트 ID
   * @return 이벤트
   */
  @Transactional(readOnly = true)
  public Event checkIfDeleted(Long eventId) {
    return getEvent(eventId);
  }


  /**
   * 이벤트를 저장할 수 있습니다.
   *
   * @param event 만들고자 하는 이벤트
   * @return DB에 저장된 Event
   */
  @Transactional
  public Event createEvent(Event event) {
    return eventRepository.save(event);
  }


  /**
   * 이벤트의 id를 통해서 상세조회를 할 수 있습니다.
   *
   * @param eventId     조회하려는 이벤트 Id
   * @return EventSavedResponseDto 이벤트 응답 DTO
   */
  @Transactional(readOnly = true)
   public Event getEventDetail(Long eventId) {
    log.info("Event 와 Profile 을 fetch join 으로 함께 가져옵니다 eventId : {}", eventId);
    return eventRepository.findProfileJoinFetch(eventId)
        .orElseThrow(() -> new EventNotFoundException(EVENT_NOT_FOUND));
  }

  /**
   * 지금 시간으로 마감 기한을 업데이트 한다.
   *
   * @param event 마감 기한 업데이트 하려는 이벤트
   */
  @Transactional
  public void updateDeadline(Event event) {
    log.info("이벤트 마감 기한을 업데이트 합니다. eventId: {}", event.getId());
    event.updateDeadline(LocalDateTime.now());
  }

  /**
   * 이벤트 정보를 업데이트 한다.
   *
   * @param event 이벤트
   * @param requestDto 이벤트 정보 업데이트 요청 DTO
   */
  @Transactional
  public void updateEventInfo(Event event, EventUpdateRequestDto requestDto) {
    // 이미 Event 는 영속화 되어 있기 때문에, 쿼리가 나가지 X
    log.info("이벤트 정보를 업데이트 합니다. eventId: {}", event.getId());
    event.updateEventInfo(requestDto);
  }

  /**
   * 이벤트를 삭제한다.
   *
   * @param event 삭제하고자 하는 이벤트
   */
  @Transactional
  public void deleteEvent(Event event) {
    log.info("이벤트를 삭제합니다. eventId: {}", event.getId());
    eventRepository.delete(event);
  }
}
