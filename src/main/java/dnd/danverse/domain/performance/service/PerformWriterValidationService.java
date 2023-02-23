package dnd.danverse.domain.performance.service;

import static dnd.danverse.global.exception.ErrorCode.PERFORMANCE_NOT_WRITER;

import dnd.danverse.domain.performance.entity.Performance;
import dnd.danverse.domain.performance.exception.NotSameProfileException;
import dnd.danverse.domain.profile.entity.Profile;
import dnd.danverse.domain.profile.service.ProfilePureService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 공연, 프로필 half 복합 서비스.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class PerformWriterValidationService {

  private final PerformancePureService performancePureService;
  private final ProfilePureService profilePureService;

  /**
   * 공연 수정을 요청하는 사용자와 실제 작성자가 동일한 지 비교한다.
   * 동일하지 않다면 예외처리를 한다.
   *
   * @param performId 공연 Id.
   * @param memberId 수정 요청 사용자 Id.
   * @return 수정하려는 Performance 객체.
   */
  @Transactional(readOnly = true)
  public Performance validatePerformWriter(Long performId, Long memberId) {
    Performance performance = performancePureService.getPerformanceDetail(performId);
    Profile writer = performance.getProfileHost();
    Profile profile = profilePureService.retrieveProfile(memberId);
    if (writer.isNotSame(profile)) {
      throw new NotSameProfileException(PERFORMANCE_NOT_WRITER);
    }
    return performance;
  }

}
