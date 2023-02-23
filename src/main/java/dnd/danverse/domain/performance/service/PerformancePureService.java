package dnd.danverse.domain.performance.service;

import static dnd.danverse.global.exception.ErrorCode.PERFORMANCE_NOT_FOUND;

import dnd.danverse.domain.performance.dto.request.PerformUpdateRequestDto;
import dnd.danverse.domain.performance.dto.response.ImminentPerformsDto;
import dnd.danverse.domain.performance.entity.Performance;
import dnd.danverse.domain.performance.exception.PerformanceNotFoundException;
import dnd.danverse.domain.performance.repository.PerformanceRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 공연 순수 서비스.
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class PerformancePureService {

  private final PerformanceRepository performanceRepository;

  /**
   * 임박한 공연을 4개까지 조회할 수 있다. 오늘 날짜 기준으로, 최근 공연 4개를 조회할 수 있다.
   */
  public List<ImminentPerformsDto> searchImminentPerforms() {
    LocalDate now = LocalDate.now();

    List<Performance> imminentPerforms = performanceRepository.findImminentPerforms(now);

    return imminentPerforms.stream()
        .map(ImminentPerformsDto::new)
        .limit(4)
        .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public Performance getPerformanceDetail(Long performId) {
    log.info("performance와 작성자의 profile을 fetch join 하여 찾습니다. performId: {}", performId);
    return performanceRepository.findPerformanceWithProfile(performId)
        .orElseThrow(() -> new PerformanceNotFoundException(PERFORMANCE_NOT_FOUND));
  }

  @Transactional
  public Performance createPerform(Performance performance) {
    return performanceRepository.save(performance);
  }

  /**
   * 공연 정보를 updateRequestDto 대로 수정합니다.
   *
   * @param performance 수정하려는 공연.
   * @param request 수정 요청 Dto.
   * @return 요청 Dto 대로 수정합니다.
   */
  @Transactional
  public Performance updatePerform(Performance performance, PerformUpdateRequestDto request) {
    log.info("요청 dto로 수정합니다.");
    return performance.updateInfo(request);
  }

}
