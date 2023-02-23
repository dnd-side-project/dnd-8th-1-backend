package dnd.danverse.domain.matching.service;

import dnd.danverse.domain.event.entitiy.Event;
import dnd.danverse.domain.matching.dto.response.ApplicantsResponseDto;
import dnd.danverse.domain.matching.entity.EventMatch;
import dnd.danverse.domain.validation.WriterValidationService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

/**
 * Event Match 지원자를 찾기 위한 복합 Service.
 */
@Service
public class EventMatchSearchService {

  private final EventMatchPureService eventMatchPureService;
  private final WriterValidationService<Event> validateEventWriter;

  public EventMatchSearchService(EventMatchPureService eventMatchPureService, WriterValidationService<Event> validateEventWriter) {
    this.eventMatchPureService = eventMatchPureService;
    this.validateEventWriter = validateEventWriter;
  }


  /**
   * 1. 이벤트 작성자가 맞는지 검증한다.
   * 2. 이벤트 신청자 리스트를 조회한다.
   *
   * @param eventId 신청자 리스트를 조회하고자 하는 이벤트 글 아이디.
   * @param memberId 신청자 리스트를 조회하려고 하는 사용자 Id.
   */
  public List<ApplicantsResponseDto> getApplicants(Long eventId, Long memberId) {
    Event event = validateEventWriter.validateWriter(eventId, memberId);
    List<EventMatch> eventMatches = eventMatchPureService.getApplicants(event);
    return eventMatches.stream().map(ApplicantsResponseDto::new).collect(Collectors.toList());
  }


}
