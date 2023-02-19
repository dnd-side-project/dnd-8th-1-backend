package dnd.danverse.domain.event.service;

import dnd.danverse.domain.event.dto.request.EventUpdateRequestDto;
import dnd.danverse.domain.event.dto.response.EventWithProfileDto;
import dnd.danverse.domain.event.entitiy.Event;
import dnd.danverse.domain.matching.service.EventWriterValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Event 글을 수정하기 위한 복합 Service.
 */
@Service
@RequiredArgsConstructor
public class EventUpdateService {

  private final EventPureService eventPureService;
  private final EventWriterValidationService eventWriterValidationService;

  /**
   * 1. 이벤트 작성자가 맞는지 검증한다.
   * 2. 이벤트 글의 마감일을 수정한다.
   * @param eventId 이벤트 글 아이디
   * @param memberId API 요청자 아이디
   */
  public void updateDeadline(Long eventId, Long memberId) {
    eventWriterValidationService.validateEventWriter(eventId, memberId);
    eventPureService.updateDeadline(eventId);
  }

  public EventWithProfileDto updateEventInfo(EventUpdateRequestDto eventDto, Long memberId) {
    // 아래를 수행하면 Event , Profile 객체가 모두 영속화 된다. (전부 각각의 프록시를 가지고 있다)
    eventWriterValidationService.validateEventWriter(eventDto.getId(), memberId);
    Event event = eventPureService.updateEventInfo(eventDto.getId(), eventDto);
    return new EventWithProfileDto(event, event.getProfile());
  }
}
