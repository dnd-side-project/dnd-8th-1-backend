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
    return eventRepository.findProfileJoinFetch(eventId)
        .orElseThrow(() -> new EventNotFoundException(EVENT_NOT_FOUND));
  }

  /**
   * 지금 시간으로 마감 기한을 업데이트 한다.
   * @param eventId 이벤트 고유 ID
   */
  @Transactional
  public void updateDeadline(Long eventId) {
    Event event = getEvent(eventId);
    event.updateDeadline(LocalDateTime.now());
  }

  @Transactional
  public Event updateEventInfo(Long eventId, EventUpdateRequestDto requestDto) {
    // 이미 Event 는 영속화 되어 있기 때문에, 쿼리가 나가지 X
    Event event = getEvent(eventId);
    event.updateEventInfo(requestDto);
    return event;
  }
}
