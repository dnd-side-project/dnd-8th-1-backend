package dnd.danverse.domain.mypage.service;

import dnd.danverse.domain.matching.entity.EventMatch;
import dnd.danverse.domain.matching.service.EventMatchPureService;
import dnd.danverse.domain.mypage.dto.response.MyAppliesDto;
import dnd.danverse.domain.profile.entity.Profile;
import dnd.danverse.domain.profile.service.ProfilePureService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 지원한 이벤트 조회 복합 서비스.
 */
@Service
@RequiredArgsConstructor
public class MyAppliesSearchService {

  private final EventMatchPureService eventMatchPureService;
  private final ProfilePureService profilePureService;

  /**
   * memberId 를 통하여 자신이 지원한 이벤트와 매칭 여부를 확인할 수 있습니다.
   *
   * @param memberId 조회하려고 하는 사용자의 memberId.
   * @return 지원한 이벤트 정보를 담은 Dto 리스트.
   */
  public List<MyAppliesDto> findByApplies(Long memberId) {
    Profile profile = profilePureService.retrieveProfile(memberId);
    List<EventMatch> matches = eventMatchPureService.findAppliedEvents(profile.getId());
    return matches.stream()
        .map(match -> new MyAppliesDto(match, profile))
        .collect(Collectors.toList());
  }


}
