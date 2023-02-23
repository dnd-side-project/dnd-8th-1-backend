package dnd.danverse.domain.validation;

import static dnd.danverse.global.exception.ErrorCode.PERFORMANCE_NOT_WRITER;

import dnd.danverse.domain.performance.entity.Performance;
import dnd.danverse.domain.performance.exception.NotSameProfileException;
import dnd.danverse.domain.performance.service.PerformancePureService;
import dnd.danverse.domain.profile.entity.Profile;
import dnd.danverse.domain.profile.service.ProfilePureService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 공연 작성자 와 API 요청자가 동일한지 검증하는 서비스.
 * WriterValidationService 인터페이스를 구현합니다.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class PerformWriterValidationService implements WriterValidationService<Performance> {

  private final PerformancePureService performancePureService;
  private final ProfilePureService profilePureService;

  @Override
  public Performance getTarget(Long targetId) {
    return performancePureService.getPerformanceDetail(targetId);
  }

  @Override
  public Profile getTargetProfile(Performance target) {
    return target.getProfileHost();
  }

  @Override
  public Profile retrieveProfile(Long memberId) {
    return profilePureService.retrieveProfile(memberId);
  }

  @Override
  public void throwException() {
    throw new NotSameProfileException(PERFORMANCE_NOT_WRITER);
  }
}
