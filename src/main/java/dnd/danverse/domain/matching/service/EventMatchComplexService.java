package dnd.danverse.domain.matching.service;

import static dnd.danverse.global.exception.ErrorCode.*;

import dnd.danverse.domain.event.entitiy.Event;
import dnd.danverse.domain.event.service.EventPureService;
import dnd.danverse.domain.matching.dto.request.EventIdRequestDto;
import dnd.danverse.domain.matching.dto.response.ApplicantsResponseDto;
import dnd.danverse.domain.matching.entity.EventMatch;
import dnd.danverse.domain.matching.exception.NotEventWriterException;
import dnd.danverse.domain.profile.entity.Profile;
import dnd.danverse.domain.profile.service.ProfilePureService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Event Match 를 위한 복합 Service.
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
   * 4. 중복 신청이 아닌지 확인한다.
   * 5. 모든 검증이 완료되면, 이벤트 신청을 진행한다.
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

    // 중복으로 지원한 경우는 없는지 확인한다.
    eventMatchPureService.checkIfDuplicated(targetEvent, applier);

    // profile 도 가지고 있고, 신청 가능한 이벤트라면, 신청을 진행한다.
    eventMatchPureService.matchEvent(targetEvent, applier);
  }

  /**
   * 취소하고자 하는 이벤트 ID, 지원자 ID 가 필요하다.
   * 이벤트 ID가 우선적으로 존재하는지 확인한다. (이벤트가 삭제 된 경우 Exception)
   * 지원자 Member ID 로써 Profile 이 존재하는지 확인한다. (존재하지 않으면 Exception)
   * 지원자가 취소하고자 하는 이벤트 ID에 먼저 지원했는지 확인한다. (지원 한 적이 없으면 Exception)
   * 최종적으로 지원을 취소한다. (지원 내역 삭제)
   *
   * @param eventId 취소하고자 하는 이벤트 ID
   * @param memberId 지원자 ID
   */
  public void cancelEventApply(Long eventId, Long memberId) {
    Event event = eventPureService.checkIfDeleted(eventId);
    Profile profile = profilePureService.retrieveProfile(memberId);
    EventMatch eventMatch = eventMatchPureService.checkIfEventSupported(event.getId(), profile.getId());
    eventMatchPureService.cancelEventApply(eventMatch);
  }


  /**
   * 1. 입력받은 이벤트 아이디로 이벤트글을 가져온다. (event)
   * 2. 이벤트 글에 있는 프로필을 가져온다. (writer)
   * 3. 입력받은 멤버 아이디를 통해 프로필을 가져온다. (profile)
   * 4. profile 과 writer 를 비교하여 다르면 403에러 발생한다.
   * 5. 동일하면 신청자 리스트를 가져와서 dto 에 담아 반환한다.
   *
   * @param eventId 신청자 리스트를 조회하고자 하는 이벤트 글 아이디.
   * @param memberId 신청자 리스트를 조회하려고 하는 사용자 Id.
   */
  public List<ApplicantsResponseDto> getApplicants(Long eventId, Long memberId) {
    Event event = eventPureService.getEvent(eventId); //입력받은 이벤트아이디로 이벤트글을 찾아온다.
    Profile writer = event.getProfile(); //가져온 이벤트안에 있는 프로필의 아이디를 가져온다.
    Profile profile = profilePureService.retrieveProfile(memberId); //프로필 순수 서비스에서 입력받은 멤버의 아이디의 프로필을 가져온다.

    if (writer.isNotSame(profile)) { //프로필끼리 비교하여 틀리면 403에러 발생
      throw new NotEventWriterException(EVENT_MATCH_NOT_WRITER);
    }
    List<EventMatch> eventMatches = eventMatchPureService.getApplicants(eventId);

    return eventMatches.stream().map(ApplicantsResponseDto::new).collect(Collectors.toList());
  }


}
