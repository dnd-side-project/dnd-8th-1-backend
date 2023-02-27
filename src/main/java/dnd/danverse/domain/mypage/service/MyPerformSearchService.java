package dnd.danverse.domain.mypage.service;

import dnd.danverse.domain.mypage.dto.response.MyPerformsDto;
import dnd.danverse.domain.performance.entity.Performance;
import dnd.danverse.domain.performance.service.PerformancePureService;
import dnd.danverse.domain.profile.entity.Profile;
import dnd.danverse.domain.profile.service.ProfilePureService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 자신이 등록한 공연 조회 복합 서비스.
 */
@Service
@RequiredArgsConstructor
public class MyPerformSearchService {

  private final ProfilePureService profilePureService;
  private final PerformancePureService performancePureService;

  /**
   * 자신이 작성한 공연 정보를 조회할 수 있는 서비스.
   *
   * @param memberId 조회를 요청하는 memberId.
   * @return 내가 등록한 공연 목록.
   */
  public List<MyPerformsDto> findMyPerforms(Long memberId) {
    Profile profile = profilePureService.retrieveProfile(memberId);
    List<Performance> myPerforms = performancePureService.getMyPerforms(profile.getId());
    return myPerforms.stream()
        .map(perform -> new MyPerformsDto(perform, profile))
        .collect(Collectors.toList());


  }

}
