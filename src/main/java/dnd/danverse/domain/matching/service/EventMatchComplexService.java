package dnd.danverse.domain.matching.service;

import dnd.danverse.domain.event.entitiy.Event;
import dnd.danverse.domain.event.service.EventPureService;
import dnd.danverse.domain.matching.dto.request.EventIdRequestDto;
import dnd.danverse.domain.profile.entity.Profile;
import dnd.danverse.domain.profile.service.ProfilePureService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Event Match 를 위한 복합 Service
 */
@Service
@RequiredArgsConstructor
public class EventMatchComplexService {

  private final EventMatchPureService eventMatchPureService;
  private final ProfilePureService profilePureService;
  private final EventPureService eventPureService;

  /**
   * 이벤트 신청 전 복합적인 검증을 진행한다.
   * 1. 신청자의 프로필이 존재하는지 확인한다.
   * 2. 신청 가능한 이벤트인지 확인한다. (존재하는지, 신청 기간이 지났는지)
   * 3. 신청자의 모집 유형이 이벤트의 모집 유형과 일치하는지 확인한다.
   * 4. 모든 검증이 완료되면, 이벤트 신청을 진행한다.
   * @param requestDto 이벤트 신청을 위한 요청 DTO
   * @param memberId 신청자의 고유 DB ID
   */
  public void matchEvent(EventIdRequestDto requestDto, Long memberId) {
    // id 에 해당하는 Profile 을 가지고 있는지 확인한다. 없으면 권한이 없다는 Exception 을 던진다.
    Profile applier = profilePureService.retrieveProfile(memberId);

    // requestDto 에 담긴 eventID를 통해서 신청 가능한 이벤트인지 확인한다. 아니면 Exception 을 던진다.
    Event targetEvent = eventPureService.checkAvailable(requestDto.getEventId());

    // 모집 유형이 일치한지 확인한다. 아니면 Exception 을 던진다.
    applier.checkMatchType(targetEvent.getRecruitType());

    // profile 도 가지고 있고, 신청 가능한 이벤트라면, 신청을 진행한다.
    eventMatchPureService.matchEvent(targetEvent, applier);
  }
}
