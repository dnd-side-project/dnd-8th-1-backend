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
