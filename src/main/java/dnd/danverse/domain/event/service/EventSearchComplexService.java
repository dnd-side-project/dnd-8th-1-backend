package dnd.danverse.domain.event.service;

import dnd.danverse.domain.event.dto.response.EventWithProfileDto;
import dnd.danverse.domain.event.entitiy.Event;
import dnd.danverse.domain.jwt.service.SessionUser;
import dnd.danverse.domain.matching.service.EventMatchPureService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 이벤트 상세조회 관련 서비스.
 */
@Service
@RequiredArgsConstructor
public class EventSearchComplexService {
  private final EventPureService eventPureService;
  private final EventMatchPureService eventMatchPureService;


  /**
   * 이벤트 아이디와 session User 를 통해서 이벤트 상세 조회를 할 수 있습니다.
   * 이벤트 정보와 상세 조회 요청한 사용자의 지원 이력 여부를 함께 반환합니다.
   * case1. 로그인하지 않은 경우. -> isApplied = false
   * case2. 로그인 O, 프로필 X. -> isApplied = false
   * case3. 로그인 O, 프로필 O, 지원 이력 X. -> isApplied = false
   * case4. 로그인 O, 프로필 O, 지원 이력 O. -> isApplied = true
   *
   * @param eventId 조회하고자 하는 이벤트 고유 Id.
   * @param sessionUser 조회 요청하는 session User.
   * @return 상세조회 관련 Dto 를 반환합니다.
   */
  public EventWithProfileDto searchDetail(Long eventId, SessionUser sessionUser) {

    Event event = eventPureService.getEventDetail(eventId);

    // 로그인을 안한 경우 및 프로필 없는 경우
    if (isNull(sessionUser)) {
      return new EventWithProfileDto(event, event.getProfile());
    }

    Long profileId = sessionUser.getProfileId();

    boolean isApplied = eventMatchPureService.checkIfApplied(event.getId(), profileId);

    return new EventWithProfileDto(event, event.getProfile(), isApplied);
  }

  /**
   * 조회 요청하는 사용자가 로그인을 하지 않은 사용자인지 혹은 프로필이 없는지 체크합니다.
   *
   * @param sessionUser 조회 요청하는 session User.
   * @return 로그인하지 않거나 혹은 프로필이 없다면 true 반환.
   */
  private boolean isNull(SessionUser sessionUser) {
    return sessionUser == null || sessionUser.getProfileId() == null;
  }

}
