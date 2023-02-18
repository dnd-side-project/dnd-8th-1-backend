package dnd.danverse.domain.matching.service;

import dnd.danverse.domain.matching.entity.EventMatch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 이벤트 지원자에 대한 요청을 수락하는 복합 Service
 */
@Service
@RequiredArgsConstructor
public class EventAcceptComplexService {

  private final EventMatchPureService eventMatchPureService;


  /**
   * 이벤트 지원자에 대한 요청을 수락한다.
   * 1. 해당 지원자가 해당 이벤트에 지원한 적이 있는지 확인한다.
   * 2. 지원 내역이 존재하면, 수락 처리를 진행한다.
   *
   * @param eventId   이벤트 ID
   * @param profileId 지원자 ID
   */
  public void acceptApplicant(Long eventId, Long profileId) {
    EventMatch eventMatch = eventMatchPureService.checkIfEventSupported(eventId, profileId);
    eventMatchPureService.acceptApplicant(eventMatch);
  }
}
