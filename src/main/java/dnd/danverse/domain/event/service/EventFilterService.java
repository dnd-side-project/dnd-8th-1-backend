package dnd.danverse.domain.event.service;

import dnd.danverse.domain.event.dto.request.EventCondDto;
import dnd.danverse.domain.event.dto.response.EventInfoResponse;
import dnd.danverse.domain.event.repository.EventRepository;
import dnd.danverse.domain.performance.dto.response.PageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 이벤트를 조회하는 서비스.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EventFilterService {

  private final EventRepository eventRepository;

  /**
   * 이벤트 필터링과, 페이징을 적용한 이벤트 조회.
   *
   * @param eventCondDto 이벤트 필터링 조건
   * @return 페이징 처리된 이벤트 목록 (모집 기간이 지난 이벤트는 제외)
   */
  public PageDto<EventInfoResponse> searchAllEventWithCond(EventCondDto eventCondDto) {
    Page<EventInfoResponse> eventInfoResponses = eventRepository.searchAllEventWithCond(
        eventCondDto);
    return new PageDto<>(eventInfoResponses);
  }
}
