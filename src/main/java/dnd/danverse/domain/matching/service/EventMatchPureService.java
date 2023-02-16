package dnd.danverse.domain.matching.service;

import dnd.danverse.domain.event.entitiy.Event;
import dnd.danverse.domain.matching.entity.EventMatch;
import dnd.danverse.domain.matching.repository.EventMatchRepository;
import dnd.danverse.domain.profile.entity.Profile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Event Match Entity 에 대한 순수 Service
 */
@Service
@RequiredArgsConstructor
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

    eventMatchRepository.save(eventMatch);
  }
}
