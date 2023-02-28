package dnd.danverse.domain.mypage.service;

import dnd.danverse.domain.event.entitiy.Event;
import dnd.danverse.domain.event.service.EventPureService;
import dnd.danverse.domain.matching.service.EventMatchPureService;
import dnd.danverse.domain.mypage.dto.response.MyEventsDto;
import dnd.danverse.domain.profile.entity.Profile;
import dnd.danverse.domain.profile.service.ProfilePureService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * MyPage 에서 사용하는 내가 작성한 이벤트 조회 복합 Service
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class MyEventsSearchService {

  private final ProfilePureService profilePureService;
  private final EventPureService eventPureService;
  private final EventMatchPureService eventMatchPureService;


  /**
   * 내가 주최한 이벤트 목록 조회
   * 1. member id 를 통해서 profile 을 찾는다.
   * 2. profile id 를 통해서, 자기 자신이 주최한 모든 이벤트를 찾는다.
   * 3. 이벤트의 모든 ids 를 통해서, 모든 Event Match 를 찾으며 동시에 True 결과만 찾고, 그에 대응하는 중복된 이벤트 ID 를 제거하여 반환
   * 4. 내가 주최한 이벤트 목록을 순회하며, 해당 이벤트가 한번이라도 매칭하여 True 인지 확인하여 MyEventsDto 를 생성하여 반환
   *
   * @param memberId 자신이 주최한 이벤트를 조회할 멤버의 ID
   * @return MyEventsDto List 내가 주최한 이벤트 목록 (매칭 된 여부 포함)
   */
  public List<MyEventsDto> findMyEvents(Long memberId) {
    Profile profile = profilePureService.retrieveProfile(memberId);
    List<Event> events = eventPureService.searchAllEventsByProfileId(profile.getId());

    // 내가 주최한 모든 이벤트의 ID 리스트
    List<Long> eventIds = events.stream().map(Event::getId).collect(Collectors.toList());

    // 내가 주최한 모든 이벤트 중 한번이라도 매칭된 이벤트 ID 리스트
    List<Long> trueEventIds = eventMatchPureService.findTrueEventIds(eventIds);

    return events.stream()
        .map(event -> getMyEventsDto(profile, trueEventIds, event))
        .collect(Collectors.toList());
  }

  /**
   * 파라미터로 받은 이벤트가 한번이라도 매칭되었는지 확인하여 MyEventsDto 를 생성하여 반환
   *
   * @param profile 자신의 프로필
   * @param trueEventIds 한번이라도 매칭된 이벤트 ID 리스트
   * @param event 이벤트
   * @return MyEventsDto
   */
  private MyEventsDto getMyEventsDto(Profile profile, List<Long> trueEventIds,
      Event event) {
    boolean isMatched = trueEventIds.contains(event.getId());
    return new MyEventsDto(event, profile, isMatched);
  }
}
