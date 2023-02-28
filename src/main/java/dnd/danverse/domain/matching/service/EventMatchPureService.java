package dnd.danverse.domain.matching.service;

import static dnd.danverse.global.exception.ErrorCode.EVENT_ALREADY_APPLIED;
import static dnd.danverse.global.exception.ErrorCode.EVENT_MATCH_NOT_FOUND;

import dnd.danverse.domain.event.entitiy.Event;
import dnd.danverse.domain.matching.entity.EventMatch;
import dnd.danverse.domain.matching.exception.AlreadyAppliedException;
import dnd.danverse.domain.matching.exception.EventMatchNotFoundException;
import dnd.danverse.domain.matching.repository.EventMatchRepository;
import dnd.danverse.domain.profile.entity.Profile;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Event Match Entity 에 대한 순수 Service.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class EventMatchPureService {

  private final EventMatchRepository eventMatchRepository;


  /**
   * 이벤트 신청.
   *
   * @param event   신청하고자 하는 이벤트
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
   * 지원자가 이벤트에 지원한 적이 있는지 확인.
   *
   * @param eventId   이벤트
   * @param profileId 지원자 프로필
   * @return 지원한 적이 있으면 EventMatch 객체를 반환한다.
   */
  @Transactional(readOnly = true)
  public EventMatch checkIfEventSupported(Long eventId, Long profileId) {
    log.info("이벤트 지원 확인 위해 eventId: {}, profileId: {} 데이터를 통해서 찾습니다.", eventId, profileId);
    return eventMatchRepository.findByEventAndProfileGuest(eventId, profileId)
        .orElseThrow(() -> new EventMatchNotFoundException(EVENT_MATCH_NOT_FOUND));
  }


  /**
   * 최종적으로 이벤트 지원 내역에 대해서 취소한다.
   *
   * @param eventMatch 이벤트 지원 내역
   */
  @Transactional
  public void cancelEventApply(EventMatch eventMatch) {
    log.info("이벤트 지원 내역을 취소합니다. eventMatch: {}", eventMatch.getId());
    eventMatchRepository.delete(eventMatch);
  }

  /**
   * 이벤트 신청자의 프로필을 가져온다.
   *
   * @param event 이벤트
   * @return List<EventMatch> 이벤트 매치 객체 리스트
   */
  @Transactional(readOnly = true)
  public List<EventMatch> getApplicants(Event event) {
    log.info("eventId 를 통해 EventMatch 를 가져오면서 profileGuest 와 fetch join, eventId: {}", event.getId());
    return eventMatchRepository.findByEvent(event);
  }

  /**
   * 이벤트 신청 중복 확인.
   *
   * @param targetEvent 신청하고자 하는 이벤트
   * @param applier     신청자
   */
  @Transactional(readOnly = true)
  public void checkIfDuplicated(Event targetEvent, Profile applier) {
    eventMatchRepository.findByEventAndProfileGuest(targetEvent.getId(), applier.getId())
        .ifPresent(eventMatch -> {
          throw new AlreadyAppliedException(EVENT_ALREADY_APPLIED);
        });
  }

  /**
   * 이벤트 지원자에 대한 요청을 수락한다.
   * isMatched = true 로 변경한다.
   * Dirty Checking 을 통해 DB 에 반영한다.
   *
   * @param eventMatch 이벤트 매치 객체
   */
  @Transactional
  public void acceptApplicant(EventMatch eventMatch) {
    eventMatch.accept();
  }

  /**
   * 파라미터로 받은 이벤트 id 들 중에서
   * 실제로 매칭된 이벤트 id 들을 가져온다.
   *
   * @param eventIds 사용자가 작성한 모든 이벤트 id 리스트
   * @return 실제로 매칭된 이벤트 id 리스트
   */
  @Transactional(readOnly = true)
  public List<Long> findTrueEventIds(List<Long> eventIds) {
    log.info("이벤트 ids를 통해서 EventMatch 가 true 인 모든 것을 가져옵니다. eventIds: {}", eventIds);
    return eventMatchRepository.findDistinctEventIdsByEventIdIn(eventIds);
  }


  /**
   * 이벤트 Id 와 프로필 Id 를 통해서 지원을 확인한다.
   *
   * @param eventId 이벤트 Id.
   * @param profileId 프로필 Id.
   * @return 해당 이벤트에 지원한 이력이 있다면 true, 없다면 false.
   */
  public boolean checkIfApplied(Long eventId, Long profileId) {
    log.info("이벤트 지원 확인 위해 eventId: {}, profileId: {} 데이터를 통해서 찾습니다.", eventId, profileId);
    return eventMatchRepository.findByEventAndProfileGuest(eventId, profileId).isPresent();
  }

  /**
   * 파라미터로 받은 profileId를 통해서 지원한 이벤트 내역을 찾습니다.
   *
   * @param profileId 지원한 프로필 Id.
   * @return 지원 내역 리스트.
   */
  @Transactional(readOnly = true)
  public List<EventMatch> findAppliedEvents(Long profileId) {
    log.info("프로필 id를 통하여 eventMatch 들을 찾습니다. profileId: {}", profileId);
    return eventMatchRepository.findAppliesEventsByProfileId(profileId);
  }
}
