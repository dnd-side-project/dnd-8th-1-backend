package dnd.danverse.domain.event.service;

import dnd.danverse.domain.event.dto.request.EventCondDto;
import dnd.danverse.domain.event.dto.response.EventInfoResponse;
import dnd.danverse.domain.event.repository.EventRepository;
import dnd.danverse.domain.profile.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 이벤트를 조회하는 서비스.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EventProfileService {

  private final ProfileRepository profileRepository;
  private final EventRepository eventRepository;

  /**
   * 이벤트 필터링과, 페이징을 적용한 이벤트 조회.
   * @param eventCondDto 이벤트 필터링 조건
   * @param pageable 페이징 조건
   * @return 페이징 처리된 이벤트 목록 (모집 기간이 지난 이벤트는 제외)
   */
  public Page<EventInfoResponse> searchAllEventWithCond(EventCondDto eventCondDto, Pageable pageable) {
    return eventRepository.searchAllEventWithCond(eventCondDto, pageable);
  }
}
