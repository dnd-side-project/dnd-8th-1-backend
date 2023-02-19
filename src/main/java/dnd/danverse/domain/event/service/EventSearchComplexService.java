package dnd.danverse.domain.event.service;

import dnd.danverse.domain.event.dto.response.EventWithProfileDto;
import dnd.danverse.domain.event.entitiy.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 이벤트 상세조회 관련 서비스.
 */
@Service
@RequiredArgsConstructor
public class EventSearchComplexService {
  private final EventPureService eventPureService;

  /**
   * 이벤트 아이디를 통해서 이벤트 글의 상세정보를 확인할 수 있습니다.
   *
   * @param eventId 조회하고자 하는 이벤트 고유 Id.
   * @return 상세조회 관련 Dto를 반환합니다.
   */
  public EventWithProfileDto searchDetail(Long eventId) {
    Event event = eventPureService.getEventDetail(eventId);
    return new EventWithProfileDto(event, event.getProfile());
  }

}
