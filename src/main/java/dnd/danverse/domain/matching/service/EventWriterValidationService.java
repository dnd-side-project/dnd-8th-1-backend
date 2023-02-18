package dnd.danverse.domain.matching.service;

import static dnd.danverse.global.exception.ErrorCode.EVENT_MATCH_NOT_WRITER;

import dnd.danverse.domain.event.entitiy.Event;
import dnd.danverse.domain.event.service.EventPureService;
import dnd.danverse.domain.matching.exception.NotEventWriterException;
import dnd.danverse.domain.profile.entity.Profile;
import dnd.danverse.domain.profile.service.ProfilePureService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 이벤트 작성자가 맞는지 검증하는 Half 복합 Service.
 */
@Service
@RequiredArgsConstructor
public class EventWriterValidationService {

  private final EventPureService eventPureService;
  private final ProfilePureService profilePureService;

  /**
   * API 요청자가 이벤트 작성자가 맞는지 검증한다.
   * @param eventId 이벤트 글 아이디
   * @param memberId API 요청자 아이디
   */
  public void validateEventWriter(Long eventId, Long memberId) {
    Event event = eventPureService.getEvent(eventId); //입력받은 이벤트아이디로 이벤트글을 찾아온다.
    Profile writer = event.getProfile(); //가져온 이벤트안에 있는 프로필의 아이디를 가져온다.
    Profile profile = profilePureService.retrieveProfile(memberId); //프로필 순수 서비스에서 입력받은 멤버의 아이디의 프로필을 가져온다.

    if (writer.isNotSame(profile)) { //프로필끼리 비교하여 틀리면 403에러 발생
      throw new NotEventWriterException(EVENT_MATCH_NOT_WRITER);
    }
  }

}
