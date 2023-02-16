package dnd.danverse.domain.matching.service;

import static dnd.danverse.global.exception.ErrorCode.EVENT_MATCH_NOT_FOUND;

import dnd.danverse.domain.event.entitiy.Event;
import dnd.danverse.domain.matching.entity.EventMatch;
import dnd.danverse.domain.matching.exception.EventMatchNotFoundException;
import dnd.danverse.domain.matching.repository.EventMatchRepository;
import dnd.danverse.domain.profile.entity.Profile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Event Match Entity 에 대한 순수 Service
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class EventMatchPureService {

  private final EventMatchRepository eventMatchRepository;


  /**
   * 이벤트 신청
   * @param event 신청하고자 하는 이벤트
   * @param profile 지원하고자 하는 사람의 프로필
   */
  @Transactional
  public void matchEvent(Event event, Profile profile) {

    EventMatch eventMatch = EventMatch.builder()
        .event(event)
        .profileGuest(profile)
        .build();

    log.info("이벤트 신청을 진행합니다. eventMatch: {}", eventMatch);
    eventMatchRepository.save(eventMatch);
  }

  /**
   * 지원자가 이벤트에 지원한 적이 있는지 확인
   * @param event 이벤트
   * @param profile 지원자 프로필
   * @return 지원한 적이 있으면 EventMatch 객체를 반환한다.
   */
  @Transactional(readOnly = true)
  public EventMatch checkIfEventSupported(Event event, Profile profile) {
    log.info("이벤트 지원 확인 위해 event: {}, memberId: {} 데이터를 통해서 찾습니다.", event.getId(), profile.getId());
    return eventMatchRepository.findByEventAndProfileGuest(event, profile)
        .orElseThrow(() -> new EventMatchNotFoundException(EVENT_MATCH_NOT_FOUND));
  }


  /**
   * 최종적으로 이벤트 지원 내역에 대해서 취소한다.
   * @param eventMatch 이벤트 지원 내역
   */
  @Transactional
  public void cancelEventApply(EventMatch eventMatch) {
    log.info("이벤트 지원 내역을 취소합니다. eventMatch: {}", eventMatch.getId());
    eventMatchRepository.delete(eventMatch);
  }
}
