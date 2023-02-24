package dnd.danverse.domain.performance.service;

import dnd.danverse.domain.performance.entity.Performance;
import dnd.danverse.domain.validation.WriterValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 공연 삭제 복합 서비스.
 */
@Service
@RequiredArgsConstructor
public class PerformDeleteService {

  private final PerformancePureService performancePureService;
  private final WriterValidationService<Performance> validatePerformWriter;

  /**
   * 작성자와 삭제 요청 사용자가 동일한지 확인합니다.
   * 동일하다면, 삭제가 이뤄집니다.
   * 동일하지 않다면, 예외 처리를 해줍니다.
   * 추상화 된 validationService 에 의존하고 있습니다.
   *
   * @param performId 삭제하려는 공연 고유 Id.
   * @param memberId 삭제 요청 사용자 고유 Id.
   */
  public void deletePerform(Long performId, Long memberId) {
    Performance performance = validatePerformWriter.validateWriter(performId, memberId);
    performancePureService.deletePerform(performance);
  }
}
