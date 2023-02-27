package dnd.danverse.domain.matching.service;

import dnd.danverse.domain.matching.entity.EventMatch;
import dnd.danverse.domain.member.entity.Member;
import dnd.danverse.domain.member.service.MemberPureService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 이벤트 지원자에 대한 요청을 수락하는 복합 Service
 */
@Service
@RequiredArgsConstructor
public class EventAcceptComplexService {

  private final EventMatchPureService eventMatchPureService;
  private final MemberPureService memberPureService;


  /**
   * 이벤트 지원자에 대한 요청을 수락한다.
   * 1. 해당 지원자가 해당 이벤트에 지원한 적이 있는지 확인한다.
   * 2. 지원 내역이 존재하면, 수락 처리를 진행한다.
   *
   * @param eventId   이벤트 ID
   * @param memberId 지원자 ID
   */
  public void acceptApplicant(Long eventId, Long memberId) {
    // TODO : memberID 를 통해서 Profile 을 찾는 과정이 27번쨰 라인에 필요하다. (수정 완료)
    // TODO : 찾은 profile id 를 뽑아서, checkIfEventSupported 의 2번째 파라미터 값으로 넣어야한다. (수정완료)
    Member member = memberPureService.findMemberWithProfile(memberId);
    EventMatch eventMatch = eventMatchPureService.checkIfEventSupported(eventId, member.getProfile().getId());
    eventMatchPureService.acceptApplicant(eventMatch);
  }
}
