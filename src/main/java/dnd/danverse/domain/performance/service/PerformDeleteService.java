package dnd.danverse.domain.performance.service;

import dnd.danverse.domain.performance.entity.Performance;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 공연 삭제 복합 서비스.
 */
@Service
@RequiredArgsConstructor
public class PerformDeleteService {

  private final PerformancePureService performancePureService;
  private final PerformWriterValidationService performWriterValidationService;

  /**
   * 작성자와 삭제 요청 사용자가 동일한지 확인합니다.
   * 동일하다면, 삭제가 이뤄집니다.
   * 동일하지 않다면, 예외 처리를 해줍니다.
   *
   * @param performId 삭제하려는 공연 고유 Id.
   * @param memberId 삭제 요청 사용자 고유 Id.
   */
  public void deletePerform(Long performId, Long memberId) {
    performWriterValidationService.validatePerformWriter(performId, memberId);
    Performance performance = performancePureService.getPerformance(performId);
    performancePureService.deletePerform(performance);
  }
}
