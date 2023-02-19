package dnd.danverse.domain.performance.service;

import dnd.danverse.domain.performance.dto.response.PerformDetailResponse;
import dnd.danverse.domain.performance.entity.Performance;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 공연 조회 복합 서비스.
 */
@Service
@RequiredArgsConstructor
public class PerformSearchComplexService {
  private final PerformancePureService performancePureService;

  /**
   * 공연 정보 상세 조회.
   *
   * @param performId 조회하고자 하는 performId.
   * @return 공연 정보와 프로필 정보를 담은 responseDto 반환.
   */
  public PerformDetailResponse getDetailPerform(Long performId) {
    Performance perform = performancePureService.getPerformanceDetail(performId);
    return new PerformDetailResponse(perform);
  }
}
